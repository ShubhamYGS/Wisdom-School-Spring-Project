package com.webapp.ygsschool.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseFormEntity {
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
