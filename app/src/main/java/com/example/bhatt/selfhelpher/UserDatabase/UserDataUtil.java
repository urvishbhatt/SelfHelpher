package com.example.bhatt.selfhelpher.UserDatabase;

/**
 * Created by bhatt on 24-07-2017.
 */

public class UserDataUtil {

    public static String Youtubeplaylist;

    public UserDataUtil(String Youtubeplaylist) {
        this.Youtubeplaylist = Youtubeplaylist;
    }

    public static String getYoutubeplaylist() {
        return Youtubeplaylist;
    }
}
