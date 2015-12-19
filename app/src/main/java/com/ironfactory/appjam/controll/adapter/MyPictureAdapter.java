package com.ironfactory.appjam.controll.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ironfactory.appjam.R;
import com.ironfactory.appjam.controll.activity.BaseActivity;
import com.ironfactory.appjam.dtos.PictureDto;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by IronFactory on 2015. 12. 19..
 */
public class MyPictureAdapter extends RecyclerView.Adapter<MyPictureAdapter.ViewHolder> {

    private ArrayList<PictureDto> pictures;

    public MyPictureAdapter(ArrayList<PictureDto> pictures) {
        this.pictures = pictures;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_picture, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PictureDto pictureEntity = pictures.get(position);
        SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분");
        String created = format.format(pictureEntity.imageEntity.getCreated());
        holder.nameText.setText(pictureEntity.userEntity.getName());
        holder.subjectText.setText(pictureEntity.imageEntity.getSubject());
        holder.createdText.setText(created);
        Picasso.with(BaseActivity.context)
                .load("http://" + pictureEntity.imageEntity.getId() + pictureEntity.imageEntity.getCreated())
                .into(holder.pictureImage);
    }

    @Override
    public int getItemCount() {
        return pictures.size();
    }

    public void setPictures(ArrayList<PictureDto> pictureDtos) {
        pictures = pictureDtos;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        TextView subjectText;
        TextView createdText;
        ImageView pictureImage;

        public ViewHolder(View itemView) {
            super(itemView);

            nameText = (TextView) itemView.findViewById(R.id.item_my_picture_name);
            subjectText = (TextView) itemView.findViewById(R.id.item_my_picture_subject);
            createdText = (TextView) itemView.findViewById(R.id.item_my_picture_created);
            pictureImage = (ImageView) itemView.findViewById(R.id.item_my_picture_image);
        }
    }
}
