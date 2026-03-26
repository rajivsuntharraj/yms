package com.application.yms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Geometry {
    private String type;
    private List<List<List<Double>>> coordinates;
}
