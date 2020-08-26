package com.votive.mycatvista.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.votive.mycatvista.R;
import com.votive.mycatvista.model.CommentModel;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    public List<CommentModel> comment_list;
    Context mContext;
   

    public CommentAdapter(Context mContext, List<CommentModel> myListData) {
        this.comment_list = myListData;
        this.mContext = mContext;
     
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        ViewHolder vh = new ViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //final History myListData = listdata[position];
        holder.comment_txt.setText(String.valueOf(position+1)+" )  "+comment_list.get(position).getComment());
      
    }


    @Override
    public int getItemCount() {
        //return histories.size();
        return comment_list.size();
    }

   
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView comment_txt;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            comment_txt = itemView.findViewById(R.id.comment_txt);
   

        }
    }

}
