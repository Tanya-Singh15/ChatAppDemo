package com.example.chatapp;

public class individualMsg
{
    String text , senderId ;
    Long timeStamp ;

    public individualMsg()
    {

    }

    public individualMsg(String text, String senderId, Long timeStamp)
    {
        this.text = text;
        this.senderId = senderId;
        this.timeStamp = timeStamp;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public String getSenderId()
    {
        return senderId;
    }

    public void setSenderId(String senderId)
    {
        this.senderId = senderId;
    }

    public Long getTimeStamp()
    {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp)
    {
        this.timeStamp = timeStamp;
    }
}

