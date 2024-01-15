package com.example.vom;

import java.util.ArrayList;

public class Reply {
    private String replyOne;
    private String replyTwo;

    public Reply(String theReplyOne, String theReplyTwo) {
        this.replyOne = theReplyOne;
        this.replyTwo = theReplyTwo;
    }

    public String getReplyOne() {
        return this.replyOne;
    }

    public String getReplyTwo() {
        return this.replyTwo;
    }

}
