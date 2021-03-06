package com.ironfactory.appjam.controll.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.ironfactory.appjam.Global;
import com.ironfactory.appjam.R;
import com.ironfactory.appjam.controll.adapter.MyPictureAdapter;
import com.ironfactory.appjam.dtos.PictureDto;
import com.ironfactory.appjam.server.RequestInterface;
import com.ironfactory.appjam.server.RequestManager;

import java.util.ArrayList;

public class MyPictureFragment extends Fragment {
    private static final String TAG = "MyPictureFragment";

    private RecyclerView recyclerView;
    private MyPictureAdapter adapter;


    public MyPictureFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        return inflater.inflate(R.layout.fragment_my_picture, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");
        init(getView());
    }



    private void init(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_my_picture_recycler);

        setRecyclerView();
    }


    private void setRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new MyPictureAdapter(new ArrayList<PictureDto>());
        RequestManager.getMyImages(Global.userEntity.getId(), new RequestInterface.OnGetMyImages() {
            @Override
            public void onSuccess(ArrayList<PictureDto> pictureDtos) {
                adapter.setPictures(pictureDtos);
            }

            @Override
            public void onException() {
                new MaterialDialog.Builder(getContext())
                        .title("에러")
                        .content("내 그림 받아오기에 실패했습니다")
                        .show();
            }
        });
    }
}
