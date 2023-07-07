package com.example.puzzleapp.service.impl;

import com.example.puzzleapp.model.ImageModel;
import com.example.puzzleapp.service.ImageService;
import com.example.puzzleapp.service.PuzzleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class ImageServiceImpl implements ImageService {
    private static final String SAVE_FOLDER = "uploaded-images";
    private static final String SAVE_SHUFFLE_FOLDER = "uploaded-images-shuffle";
    public Map<Integer, ImageModel> puzzleInfo = new HashMap();
    private final PuzzleService puzzleService;

    @Override
    public void uploadImage(MultipartFile file, Model model) {
        try {
            BufferedImage originalImage = ImageIO.read(file.getInputStream());
            int width = originalImage.getWidth() / 4;
            int height = originalImage.getHeight() / 4;

            String uploadPath = System.getProperty("user.dir") + File.separator;
            File uploadFolder = new File(uploadPath);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }
            String fileName = "image.jpg";
            File outputFile = new File(uploadPath + SAVE_FOLDER, fileName);
            ImageIO.write(originalImage, "jpg", outputFile);

            int count = 0;
            List<Integer> randList = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15));
            Collections.shuffle(randList);
            Random rand = new Random();
            for (int y = 0; y < originalImage.getHeight(); y += height) {
                for (int x = 0; x < originalImage.getWidth(); x += width) {
                    BufferedImage subImage = originalImage.getSubimage(x, y, width, height);
                    ImageModel imageModel = new ImageModel();

                    imageModel.setId(count);
                    int i = rand.nextInt(4);
                    imageModel.setRotateTimes(i);
                    for (; i > 0; i--) {
                        subImage = puzzleService.rotateImage(subImage);
                    }
                    imageModel.setPlace(randList.get(count));
                    String fileShuffleName = "subimage_" + randList.get(count) + ".jpg";
                    File outputShuffleFile = new File(uploadPath + SAVE_SHUFFLE_FOLDER, fileShuffleName);
                    ImageIO.write(subImage, "jpg", outputShuffleFile);

                    addPuzzle(imageModel);

                    count++;
                }
            }

            model.addAttribute("message", "Good Luck! \n " +
                    "!WARNING! Check result only if you sure that puzzle is correct or you can loose your prgress. \n" +
                    "!INFO! To rotate image double click on it.");
        } catch (IOException e) {
            model.addAttribute("message", "An error occurred while processing the image.");
        }
    }

    @Override
    public void addPuzzle(ImageModel model) {
        puzzleInfo.put(model.getPlace(), model);
    }

    @Override
    public void addRotate(int place) {
        puzzleInfo.get(place).setRotateTimes(puzzleInfo.get(place).getRotateTimes() + 1);
    }

    @Override
    public void addSwap(int draggedPlace, int targetPlace) {
        ImageModel draggedPuzzle = puzzleInfo.get(draggedPlace);
        ImageModel targetPuzzle = puzzleInfo.get(targetPlace);

        draggedPuzzle.setPlace(targetPlace);
        targetPuzzle.setPlace(draggedPlace);

        puzzleInfo.remove(draggedPlace);
        puzzleInfo.remove(targetPlace);

        puzzleInfo.put(draggedPlace, targetPuzzle);
        puzzleInfo.put(targetPlace, draggedPuzzle);
    }

    @Override
    public Map<Integer, ImageModel> getPuzzleInfo() {
        return puzzleInfo;
    }

}
