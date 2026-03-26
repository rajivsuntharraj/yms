package com.application.yms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Properties {
    private String site;
    private Integer zoom;
    private String type;
}
