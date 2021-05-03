package com.babu.adminbug;

import android.os.Parcel;
import android.os.Parcelable;

public class UserBug implements Parcelable {
    public String Name;
    public String email;
    public String Bug_name;
    public String Bug_detail;
    public String url;
    public String type;

    public UserBug() {
    }

    public UserBug(String name, String email, String bug_name, String bug_detail, String url, String type) {
        Name = name;
        this.email = email;
        Bug_name = bug_name;
        Bug_detail = bug_detail;
        this.url = url;
        this.type = type;
    }

    protected UserBug(Parcel in) {
        Name = in.readString();
        email = in.readString();
        Bug_name = in.readString();
        Bug_detail = in.readString();
        url = in.readString();
        type = in.readString();
    }

    public static final Creator<UserBug> CREATOR = new Creator<UserBug>() {
        @Override
        public UserBug createFromParcel(Parcel in) {
            return new UserBug(in);
        }

        @Override
        public UserBug[] newArray(int size) {
            return new UserBug[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Name);
        parcel.writeString(email);
        parcel.writeString(Bug_name);
        parcel.writeString(Bug_detail);
        parcel.writeString(url);
        parcel.writeString(type);
    }

   /* public UserBug(String email, String bug_name, String bug_detail, String url, String type) {
        this.email = email;
        Bug_name = bug_name;
        Bug_detail = bug_detail;
        this.url = url;
        this.type = type;
    }*/
}
