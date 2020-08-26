package com.votive.mycatvista.datbase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.votive.mycatvista.model.CommentModel;

import java.util.ArrayList;


public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "FORM";
    public static final String TABLE_NAME = "FORMDetails";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_IMAGE_ID = "COLUMN_IMAGE_ID";
    public static final String COLUMN_COMMENT = "Email";
    private static final int DATABASE_VERSION = 1;
    ArrayList<CommentModel> CommentList = new ArrayList<CommentModel>();
    ArrayList<CommentModel> MainCommentList = new ArrayList<CommentModel>();


    private SQLiteDatabase database;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String qury = " CREATE TABLE " + TABLE_NAME + " ( "
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + COLUMN_IMAGE_ID + " TEXT ,"
                + COLUMN_COMMENT + " TEXT "
                + ")";
        db.execSQL(qury);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public void insertRecordAlternate(String image_id, String comment) {
        database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_IMAGE_ID, image_id);
        contentValues.put(COLUMN_COMMENT, comment);
        database.insert(TABLE_NAME, null, contentValues);
        database.close();
    }

    public void updateRecordAlternate(String ID, String name, String mobile, String email, byte[] image) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "UPDATE FORMDetails SET name = ?, mobile = ?, email = ?, image = ? WHERE ID = ?";
        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindString(1, name);
        statement.bindString(2, mobile);
        statement.bindString(3, email);
        statement.bindBlob(4, image);
        statement.bindDouble(5, (double) Integer.parseInt(ID));

        statement.execute();
        database.close();


    }

    public void deleteRecordAlternate(String ID) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "DELETE FROM FORMDetails WHERE ID = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double) Integer.parseInt(ID));

        statement.execute();
        database.close();

    }

    public ArrayList<CommentModel> getAllRecords() {


        database = this.getWritableDatabase();
        Cursor mcursor = database.rawQuery(" SELECT * FROM " + TABLE_NAME, null);


        CommentList.clear();
        CommentModel commentModel;

        if (mcursor.getCount() > 0) {
            for (int i = 0; i < mcursor.getCount(); i++) {
                mcursor.moveToNext();

                commentModel = new CommentModel();
                commentModel.setId(mcursor.getString(0));
                commentModel.setImage_id(mcursor.getString(1));
                commentModel.setComment(mcursor.getString(2));
                CommentList.add(commentModel);
            }
        }
        mcursor.close();

        return CommentList;
    }

    public ArrayList<CommentModel> getRecordsFromID(String id) {


        Log.e("id",id);

        MainCommentList.clear();
        CommentModel commentModel;
        database = this.getWritableDatabase();

        // Cursor mCursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE   COLUMN_IMAGE_ID ="+id, null);


        String query = "SELECT * FROM FORMDetails WHERE COLUMN_IMAGE_ID =" + "'" + id + "'";
        Log.e("query",query);

        Cursor  mCursor = database.rawQuery(query,null);
         Log.e("mCursor", String.valueOf(mCursor.getCount()));
        if (mCursor != null) {
            mCursor.moveToFirst();
           // move your cursor to first row
// Loop through the cursor
            while (mCursor.isAfterLast() == false) {
                commentModel = new CommentModel();
                commentModel.setId(mCursor.getString(0));
                commentModel.setImage_id(mCursor.getString(1));
                commentModel.setComment(mCursor.getString(2));
                MainCommentList.add(commentModel);
                Log.e("CommentList", String.valueOf(MainCommentList.size()));

                mCursor.moveToNext();
            }

            mCursor.close();
        }

       /*if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();


                commentModel = new CommentModel();
                commentModel.setId(cursor.getString(0));
                commentModel.setImage_id(cursor.getString(1));
                commentModel.setComment(cursor.getString(2));
                MainCommentList.add(commentModel);
                Log.e("CommentList", String.valueOf(MainCommentList.size()));
        }
        }
        cursor.close();
*/
        return MainCommentList;
    }
}
