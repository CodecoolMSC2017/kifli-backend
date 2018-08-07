package com.codecool.projectkifli.model;

import java.util.List;
import java.util.Map;

public class CategoryPostData {

    private String name;
    private List<Map<String, String>> attributes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Map<String, String>> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Map<String, String>> attributes) {
        this.attributes = attributes;
    }
}
