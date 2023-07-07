package com.example.puzzleapp.service;

import com.example.puzzleapp.model.ImageModel;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface ImageService {
    void uploadImage(MultipartFile file, Model model);

    void addPuzzle(ImageModel model);

    void addRotate(int place);

    void addSwap(int draggedPlace, int targetPlace);

    Map<Integer, ImageModel> getPuzzleInfo();
}
