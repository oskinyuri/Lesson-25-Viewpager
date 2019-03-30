package com.example.lesson_25_viewpager;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ImagesRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Uri> mUriList;
    private Context mContext;
    private static View.OnClickListener mOnClickListener;

    public ImagesRecyclerAdapter(Context context) {
        mContext = context;
        mUriList = new ArrayList<>();
    }

    public void setData(List<Uri> uris){
        mUriList = uris;
        notifyDataSetChanged();
    }

    public void setItemClickListener(View.OnClickListener clickListener) {
        mOnClickListener = clickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View photoView = inflater.inflate(R.layout.item_gallery_image, parent, false);
        ImageViewHolder viewHolder = new ImageViewHolder(photoView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ImageView imageView = ((ImageViewHolder) holder).mPhotoImageView;
        Glide.with(mContext)
                .load(mUriList.get(position).getPath())
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return mUriList.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder{

        public ImageView mPhotoImageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            mPhotoImageView = itemView.findViewById(R.id.gallery_image);

            itemView.setTag(this);
            itemView.setOnClickListener(mOnClickListener);
        }
    }
}
