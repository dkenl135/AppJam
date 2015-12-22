package com.ironfactory.appjam.controll.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.ironfactory.appjam.R;
import com.ironfactory.appjam.controll.adapter.RankAdapter;
import com.ironfactory.appjam.dtos.PictureDto;
import com.ironfactory.appjam.server.RequestInterface;
import com.ironfactory.appjam.server.RequestManager;

import java.util.ArrayList;

public class RankFragment extends Fragment {

    private static final String TAG = "RankFragment";
    private RecyclerView recyclerView;
    private RankAdapter adapter;

    public RankFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rank, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init(getView());
    }



    private void init(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_rank_recycler);

        setRecyclerView();
    }


    private void setRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new RankAdapter(new ArrayList<PictureDto>());
        recyclerView.setAdapter(adapter);

        RequestManager.getRankImages(new RequestInterface.OnGetRankImages() {
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
