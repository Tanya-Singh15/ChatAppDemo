package com.example.chatapp;

public class ChatModel
{

    String tvMessengerName;
    String tvLastMessage;
    String civProPic;

    public ChatModel()
    {
        String tvMessengerName = new String();
        String tvLastMessage = new String();
    }

    public String getTvMessengerName() {
        return tvMessengerName;
    }

    public void setTvMessengerName(String tvMessengerName) {
        this.tvMessengerName = tvMessengerName;
    }

    public String getTvLastMessage() {
        return tvLastMessage;
    }

    public void setTvLastMessage(String tvLastMessage) {
        this.tvLastMessage = tvLastMessage;
    }

    public String getCivProPic() {
        return civProPic;
    }

    public void setCivProPic(String civProPic) {
        this.civProPic = civProPic;
    }

    public ChatModel(String tvMessengerName, String tvLastMessage, String civProPic)
    {
        this.tvMessengerName = tvMessengerName;
        this.tvLastMessage = tvLastMessage;
        this.civProPic = civProPic;
    }
}
