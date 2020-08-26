package com.votive.mycatvista.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.votive.mycatvista.R;
import com.votive.mycatvista.adapter.CommentAdapter;
import com.votive.mycatvista.apputils.Constants;
import com.votive.mycatvista.datbase.SQLiteHelper;
import com.votive.mycatvista.model.CommentModel;
import com.votive.mycatvista.model.ImageModel;

import java.util.ArrayList;

public class ImageDetailActivity extends AppCompatActivity {

    Context mContext;
    ImageModel imageModel;
    ImageView image_view;
    TextView save_comment;
    EditText comment_ext;
    RequestOptions options;
    ArrayList<CommentModel> CommentList;
    SQLiteHelper db;
    RecyclerView comment_list;
    NestedScrollView nestedScrollView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        mContext = this;

        imageModel = (ImageModel) getIntent().getSerializableExtra("imageModel");

        image_view = findViewById(R.id.image_view);
        comment_ext = findViewById(R.id.comment_ext);
        comment_list = findViewById(R.id.comment_list);
        save_comment = findViewById(R.id.save_comment);
        nestedScrollView = findViewById(R.id.nested_scroll_view);

        options = new RequestOptions()
                .centerCrop()
                .dontAnimate()
                .fitCenter()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher);

        Glide.with(mContext).load(imageModel.getImage()).apply(options).into(image_view);

        db = new SQLiteHelper(mContext);

        CommentList = db.getRecordsFromID(imageModel.getId());
        if (CommentList.size() > 0) {

            CommentAdapter commentAdapter = new CommentAdapter(mContext, CommentList);
            comment_list.setHasFixedSize(true);
            comment_list.setAdapter(commentAdapter);
        }

        save_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteHelper db = new SQLiteHelper(getApplicationContext());

                db.insertRecordAlternate(imageModel.getId(),
                        comment_ext.getText().toString());

                Intent i = new Intent(ImageDetailActivity.this, MainActivity.class);
                startActivityForResult(i, Constants.INSERT_Record);
            }
        });

    }
}
