package com.application.yms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationConfig {
    private List<String> email_id;
    private String message;
    private Boolean is_enabled;
}

