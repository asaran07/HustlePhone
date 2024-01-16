package com.example.vom;

public class Choice {
    private String optionA;
    private String optionB;

    public Choice(String optionA, String optionB) {
        this.optionA = optionA;
        this.optionB = optionB;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }
}
