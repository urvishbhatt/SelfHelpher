package com.example.bhatt.selfhelpher.UserDatabase;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by bhatt on 23-07-2017.
 */

public class UserContract {

    public static final String CONTENT_AUTHORITY = "com.example.bhatt.selfhelpher.UserDatabase";

    public static final Uri BASE_CONTENT_URL = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String USER_COURSE = "users";

    public static final class UserEntry implements BaseColumns{

        public static final Uri CONTENT_URL = Uri.withAppendedPath(BASE_CONTENT_URL,USER_COURSE);

        public final static String TABLE_NAME = "users";

        public final static String _ID = BaseColumns._ID;

        public final static String SUBJECT = "subject";

        public final static String COURSEID = "course_id";

        public final static String PLAYLIST = "playlist";
    }
}
