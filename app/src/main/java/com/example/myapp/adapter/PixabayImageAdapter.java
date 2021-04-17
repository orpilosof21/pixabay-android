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

    private OnItemClickListener mOnItemClickListener;

    private List<PixabayImage> imageList;
    private int rowLayout;
    private Context context;


    public PixabayImageAdapter(List<PixabayImage> imageList, int rowLayout, Context context) {
        this.imageList = imageList;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @NonNull
    @Override
    public PixabayImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout,parent,false);
        PixabayImageViewHolder pixabayImageViewHolder = new PixabayImageViewHolder(view,mOnItemClickListener);
        return pixabayImageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PixabayImageViewHolder holder, int position) {
        String image_url = imageList.get(position).getWebformat_url();
        Picasso.with(context).load(image_url).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public interface OnItemClickListener{
        void onImageClicked(View v, int position, PixabayImage pixabayImage);
    }

    public  void setmOnItemClickListener(OnItemClickListener clickListener){
        this.mOnItemClickListener=clickListener;
    }


    public class PixabayImageViewHolder extends RecyclerView.ViewHolder {
        LinearLayout imageLayout;
        ImageView imageView;

        public PixabayImageViewHolder(View v, final OnItemClickListener onItemClickListener) {
            super(v);
            imageLayout = v.findViewById(R.id.image_layout);
            imageView = v.findViewById(R.id.pixabay_image);
            imageView.setOnClickListener(v1 -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION){
                    onItemClickListener.onImageClicked(v1,pos, imageList.get(pos));
                }
            });
        }
    }
}