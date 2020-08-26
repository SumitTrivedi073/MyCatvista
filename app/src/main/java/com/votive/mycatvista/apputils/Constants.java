package com.votive.mycatvista.apputils;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class Constants {
    public static final int ADD_RECORD = 0;
    public static final int UPDATE_RECORD_Alternate= 1;
    public static final int sharerecord= 2;
    public static final int deleteRecordAlternate = 3;
    public static final int displayAllRecords = 4;
    public static final int INSERT_Record= 5;
    public static final int ACTION_CALL = 6;
    public static final String DML_TYPE = "DML_TYPE";

    public static final String UPDATE = "Update";
    public static final String INSERT = "Insert";
    public static final String deleteRecord = "Delete";
    public static final String NAME = "NAME";
    public static final String COLUMN_ID = "ID";
    public static final String MOBILE = "MOBILE";
    public static final String EMAIL = "EMAIL";


    public static final String ID = "ID";


    public static byte[] getByteArray(Bitmap bitmap) {
         ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        return bos.toByteArray();
    }

    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}
