package com.example.bhatt.selfhelpher.coursedatabase;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by bhatt on 20-07-2017.
 */

public class CourseContract {

    public static final String CONTENT_AUTHORITY = "com.example.bhatt.selfhelpher";

    public static final Uri BASE_CONTENT_URL = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_COURSE = "courses";

    public static final class CourseEntry implements BaseColumns {

        public static final Uri CONTENT_URL = Uri.withAppendedPath(BASE_CONTENT_URL,PATH_COURSE);

        public final static String TABLE_NAME = "courses";

        public final static String _ID = BaseColumns._ID;

        public final static String SUBJECT = "subject";

        public final static String COURSEID = "course_id";

        public final static String PLAYLIST = "playlist";

    }
}
