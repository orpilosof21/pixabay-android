package com.example.myapp.adapter;

import com.example.myapp.R;
import com.example.myapp.model.PixabayImage;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PixabayImageAdapter extends RecyclerView.Adapter<PixabayImageAdapter.PixabayImageViewHolder> {

    private List<PixabayImage> imageList;
    private int rowLayout;
    private Context context;
    public static final String image_base_path="http://image.tmdb.org/t/p/w342//";


    public PixabayImageAdapter(List<PixabayImage> imageList, int rowLayout, Context context) {
        this.imageList = imageList;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @NonNull
    @Override
    public PixabayImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout,parent,false);
        return new PixabayImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PixabayImageViewHolder holder, int position) {
        String image_url = imageList.get(position).getWebformat_url();
        Picasso.with(context).load(image_url).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class PixabayImageViewHolder extends RecyclerView.ViewHolder {
        LinearLayout imageLayout;
        ImageView image;

        public PixabayImageViewHolder(View v) {
            super(v);
            imageLayout = v.findViewById(R.id.image_layout);
            image = v.findViewById(R.id.pixabay_image);
        }
    }
}