package com.example.mobileappfinal;

public class chat_bot_Message {
    public String content;
    public boolean isUser;

    public chat_bot_Message(String content, boolean isUser) {
        this.content = content;
        this.isUser = isUser;
    }
}
