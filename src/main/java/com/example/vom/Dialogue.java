package com.example.vom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dialogue {
    private String text;
    private List<Option> options;
    public Dialogue() {

    }
    public String getText() {
        return text;
    }
    public List<Option> getOptions() {
        return options;
    }
    public void setOptions(List<Option> options) {
        this.options = options;
    }
    public void setText(String text) {
        this.text = text;
    }
}
