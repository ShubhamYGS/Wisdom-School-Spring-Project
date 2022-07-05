package com.webapp.ygsschool.model;

import lombok.Data;

@Data
public class Holiday extends BaseFormEntity{
    private String day;
    private String reason;
    private Type type;

    public enum Type {
        FESTIVAL, FEDERAL;
    }
}
