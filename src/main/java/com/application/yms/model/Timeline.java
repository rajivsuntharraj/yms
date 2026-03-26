package com.application.yms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Timeline {
    private String created_by;
    private Long created_date;
    private Long updated_date;
    private String updated_by;
}
