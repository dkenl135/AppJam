package com.ironfactory.appjam.controll.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.ironfactory.appjam.R;
import com.ironfactory.appjam.controll.activity.DrawActivity;
import com.ironfactory.appjam.controll.adapter.PictureAdapter;
import com.ironfactory.appjam.dtos.PictureDto;
import com.ironfactory.appjam.server.RequestInterface;
import com.ironfactory.appjam.server.RequestManager;

import java.util.ArrayList;

public class PictureFragment extends Fragment {
    private static final String TAG = "PictureFragment";
    private RecyclerView recyclerView;
    private PictureAdapter adapter;
    private FloatingActionButton fab;


    public PictureFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_picture, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init(getView());
    }


    private void init(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_picture_recycler);

        fab = (FloatingActionButton) rootView.findViewById(R.id.fragment_picture_new_Image);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DrawActivity.class);
                startActivity(intent);
            }
        });

        setRecyclerView();
    }


    private void setRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new PictureAdapter(new ArrayList<PictureDto>());
        recyclerView.setAdapter(adapter);

        RequestManager.getImages(new RequestInterface.OnGetImages() {
            @Override
            public void onSuccess(ArrayList<PictureDto> pictureDtos) {
                adapter.setPictures(pictureDtos);
            }

            @Override
            public void onException() {
                new MaterialDialog.Builder(getContext())
                        .title("에러")
                        .content("그림 받아오기에 실패했습니다")
                        .show();
            }
        });
    }
}
