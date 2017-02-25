package com.example.aninditha.homework7;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Aninditha on 11/23/2016.
 */

public class Msg {

    String ID;
    String authorID;
    String authorName;
    String text;
    long time;
    String date;
    boolean isRead;

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public Msg(String authorID, String authorName, String text) {
        this.authorID = authorID;
        this.authorName = authorName;
        this.text = text;
        this.time = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String currentDateandTime = sdf.format(new Date());
        this.date   = currentDateandTime;
    }

    public Msg(){

    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
