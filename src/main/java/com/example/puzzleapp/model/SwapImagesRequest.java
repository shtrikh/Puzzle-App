package com.example.puzzleapp.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SwapImagesRequest {
    private String draggedId;
    private String targetId;
}
