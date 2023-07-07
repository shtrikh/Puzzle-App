package com.example.puzzleapp.controller;

import com.example.puzzleapp.model.RotateImageRequest;
import com.example.puzzleapp.model.SwapImagesRequest;
import com.example.puzzleapp.service.ImageService;
import com.example.puzzleapp.service.PuzzleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;


@Controller
@RequiredArgsConstructor
public class ImageController {


    private final PuzzleService puzzleService;
    private final ImageService imageService;


    @GetMapping("/")
    public String showUploadForm() {
        return "upload-form";
    }

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file, Model model) {
        imageService.uploadImage(file, model);
        return "upload-result";
    }

    @PostMapping("/swap-images")
    public void swapImages(@RequestBody SwapImagesRequest request) {
        int draggedId = Integer.parseInt(request.getDraggedId());
        int targetId;
        if (request.getTargetId() == null) {
            targetId = 0;
        } else {
            targetId = Integer.parseInt(request.getTargetId());
        }
        imageService.addSwap(draggedId, targetId);
    }

    @PostMapping("/rotate-image")
    public void rotateImage(@RequestBody RotateImageRequest request) {
        int imageId = Integer.parseInt(request.getImageId());
        imageService.addRotate(imageId);
    }

    @GetMapping("/win-check")
    public String puzzleCheck() {
        if (puzzleService.checkCorrect(imageService.getPuzzleInfo())) {
            return "win";
        } else return "lose";
    }
    @GetMapping("/win")
    public String win(){
        return "win";
    }

}
