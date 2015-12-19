package com.ironfactory.appjam.controll.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewHolder> {

    private static final String TAG = "RankAdapter";
    private ArrayList<PictureDto> pictures;

    public RankAdapter(ArrayList<PictureDto> pictures) {
        this.pictures = pictures;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rank, parent, false);
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

        if (pictureEntity.likeEntity.getImageId() == 0) {
            Log.d(TAG, "좋아요 없음");
            holder.likeImage.setBackgroundResource(R.drawable.md_btn_selected);
        } else {
            Log.d(TAG, "좋아요 있음");
            holder.likeImage.setBackgroundResource(R.drawable.md_btn_selected);
        }
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
        TextView likeText;
        ImageView pictureImage;
        ImageView likeImage;

        public ViewHolder(View itemView) {
            super(itemView);

            nameText = (TextView) itemView.findViewById(R.id.item_rank_name);
            subjectText = (TextView) itemView.findViewById(R.id.item_rank_subject);
            createdText = (TextView) itemView.findViewById(R.id.item_rank_created);
            likeText = (TextView) itemView.findViewById(R.id.item_rank_like);
            pictureImage = (ImageView) itemView.findViewById(R.id.item_rank_image);
            likeImage = (ImageView) itemView.findViewById(R.id.item_rank_like_image);
        }
    }
}
