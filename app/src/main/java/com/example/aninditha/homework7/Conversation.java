package com.example.aninditha.homework7;

/**
 * Created by Aninditha on 11/23/2016.
 */

public class Conversation {

    String authorID;
    String authorName;
    Msg latestMessaage;
    int unRead;

    public int getUnRead() {
        return unRead;
    }

    public void incUnRead() {
        this.unRead = this.unRead + 1;
    }

    public Conversation(String authorID, String authorName) {
        this.authorID = authorID;
        this.authorName = authorName;
        unRead=0;
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




    public Msg getLatestMessaage() {
        return latestMessaage;
    }

    public void setLatestMessaage(Msg latestMessaage) {
        this.latestMessaage = latestMessaage;
    }
}
