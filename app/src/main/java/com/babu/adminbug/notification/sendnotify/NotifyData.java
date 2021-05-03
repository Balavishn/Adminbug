package com.babu.adminbug.notification.sendnotify;

public class NotifyData {
    String title;
    String body;
    public NotifyData(String title, String body ) {

        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
