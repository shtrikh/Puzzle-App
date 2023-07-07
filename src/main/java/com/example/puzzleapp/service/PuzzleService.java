package com.example.puzzleapp.service;

import com.example.puzzleapp.model.ImageModel;

import java.awt.image.BufferedImage;
import java.util.Map;


public interface PuzzleService {

    BufferedImage rotateImage(BufferedImage image);

    boolean checkCorrect(Map<Integer, ImageModel> puzzleMap);
}
