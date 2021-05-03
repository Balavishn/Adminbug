package com.babu.adminbug.notification.sendnotify;

public class FirebaseMessage {
    String to;
    NotifyData notification;

    public FirebaseMessage(String to, NotifyData notification) {
        this.to = to;
        this.notification = notification;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public NotifyData getNotification() {
        return notification;
    }

    public void setNotification(NotifyData notification) {
        this.notification = notification;
    }
}
