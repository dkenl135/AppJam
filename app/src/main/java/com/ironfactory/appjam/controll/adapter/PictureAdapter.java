package com.ironfactory.appjam.controll.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ironfactory.appjam.Global;
import com.ironfactory.appjam.R;
import com.ironfactory.appjam.controll.activity.BaseActivity;
import com.ironfactory.appjam.controll.activity.DrawActivity;
import com.ironfactory.appjam.dtos.PictureDto;
import com.ironfactory.appjam.entities.ImageEntity;
import com.ironfactory.appjam.entities.LikeEntity;
import com.ironfactory.appjam.entities.UserEntity;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by IronFactory on 2015. 12. 19..
 */
public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.ViewHolder> {

    private static final String TAG = "PictureAdapter";
    private ArrayList<PictureDto> pictures;

    public PictureAdapter(ArrayList<PictureDto> pictures) {
        this.pictures = pictures;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_picture, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final PictureDto pictureEntity = pictures.get(position);
        UserEntity userEntity = pictureEntity.userEntities.get(0);
        ImageEntity imageEntity = pictureEntity.imageEntities.get(0);
        LikeEntity likeEntity = pictureEntity.likeEntities.get(0);

        SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분");
        String created = format.format(imageEntity.getCreated());
        holder.nameText.setText(userEntity.getName());
        holder.subjectText.setText(imageEntity.getSubject());
        holder.createdText.setText(created);
        Log.d(TAG, "path = http://appjam-server.herokuapp.com/" + imageEntity.getId() + imageEntity.getCreated());
        Picasso.with(BaseActivity.context)
                .load("http://appjam-server.herokuapp.com/" + imageEntity.getId() + imageEntity.getCreated())
                .into(holder.pictureImage);

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaseActivity.context, DrawActivity.class);
                intent.putExtra(Global.IMAGE, pictureEntity);
                BaseActivity.context.startActivity(intent);
            }
        });
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
        LinearLayout container;

        public ViewHolder(View itemView) {
            super(itemView);

            nameText = (TextView) itemView.findViewById(R.id.item_my_picture_name_text);
            subjectText = (TextView) itemView.findViewById(R.id.item_my_picture_subject_text);
            createdText = (TextView) itemView.findViewById(R.id.item_my_picture_day_text);
            pictureImage = (ImageView) itemView.findViewById(R.id.item_my_picture_image);
            container = (LinearLayout) itemView.findViewById(R.id.item_my_picture_container);
        }
    }
}
