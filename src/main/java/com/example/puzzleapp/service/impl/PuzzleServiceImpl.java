package com.example.puzzleapp.service.impl;

import com.example.puzzleapp.model.ImageModel;
import com.example.puzzleapp.service.PuzzleService;
import org.springframework.stereotype.Service;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.Map;


@Service
public class PuzzleServiceImpl implements PuzzleService {

    public static final double THETA = Math.PI / 2;

    @Override
    public BufferedImage rotateImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        AffineTransform transform = new AffineTransform();
        transform.rotate(THETA, width / 2, height / 2);

        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);

        BufferedImage rotatedImage = new BufferedImage(height, width, image.getType());

        op.filter(image, rotatedImage);

        return rotatedImage;
    }

    @Override
    public boolean checkCorrect(Map<Integer, ImageModel> puzzleMap) {
        for (int i = 0; i < 16; i++) {
            ImageModel model = puzzleMap.get(i);
            if (model.getPlace() != model.getId() || model.getRotateTimes() % 4 != 0)
                return false;
        }
        return true;
    }


}

