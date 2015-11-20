package com.apress.gerber.reminders;

/**
 * Created by wangjue on 15/11/20.
 */
public class Reminder {
    private int mId;
    private String mContent;
    private int mImportant;

    public Reminder(int id, String content, int important) {
        mId = id;
        mContent = content;
        mImportant = important;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String mContent) {
        this.mContent = mContent;
    }

    public int getImportant() {
        return mImportant;
    }

    public void setImportant(int mImportant) {
        this.mImportant = mImportant;
    }

}
