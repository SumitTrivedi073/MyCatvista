package com.votive.mycatvista.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.votive.mycatvista.R;
import com.votive.mycatvista.activity.ImageDetailActivity;
import com.votive.mycatvista.model.ImageModel;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter implements Filterable {

    private final String TAG = CustomAdapter.class.getSimpleName();
    Context mContext;
    ArrayList<ImageModel> ImageArrayList;
    ArrayList<ImageModel> ImageListFiltered;

    LayoutInflater inflater;
    private RequestOptions options;
    private ViewHolder holder;

    public CustomAdapter(Context context, ArrayList<ImageModel> arrayList) {
        this.mContext = context;
        this.ImageArrayList = arrayList;
        this.ImageListFiltered =  arrayList;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return ImageListFiltered.size();
    }

    public Object getItem(int position) {
        return ImageListFiltered.get(position);
    }

    public long getItemId(int position) {
        return ImageListFiltered.indexOf(getItem(position));
    }


    public View getView(final int position, View convertView, final ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.image_item, null);
            holder = new ViewHolder();

            holder.item_layout = convertView.findViewById(R.id.image_relative);
            holder.imageView = convertView.findViewById(R.id.image);

            options = new RequestOptions()
                    .centerCrop()
                    .dontAnimate()
                    .fitCenter()
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final ImageModel imageModel = ImageListFiltered.get(position);

        Glide.with(mContext).load(imageModel.getImage()).apply(options).into(holder.imageView);

        holder.item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ImageDetailActivity.class);
                intent.putExtra("imageModel",imageModel);
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    public class ViewHolder {
        ConstraintLayout item_layout;
        ImageView imageView;
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    ImageListFiltered = ImageArrayList;
                } else {
                    ArrayList<ImageModel> filteredStorageList = new ArrayList<>();


                    for (ImageModel model : ImageArrayList) {
                        if (model.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredStorageList.add(model);
                        }
                    }
                    ImageListFiltered = filteredStorageList;

                }
                Filter.FilterResults filterResults = new Filter.FilterResults();
                filterResults.values = ImageListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                ImageListFiltered = (ArrayList<ImageModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
