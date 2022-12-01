package com.example.aliabuelhija_hw1_mobileapplications;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import java.util.ArrayList;


public class ScoresFragment extends Fragment {

    private AppCompatActivity activity;

    private CallBack_List callBackList;

    private MaterialButton[] top;

    private ArrayList<Score> scores;


    public ScoresFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scores, container, false);


        findViews(view);
        addPlayer();
        return view;
    }

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void setCallBackList(CallBack_List callBackList) {
        this.callBackList = callBackList;
    }

    private void findViews(View view) {
        top = new MaterialButton[]{
                view.findViewById(R.id.score_1),
                view.findViewById(R.id.score_2),
                view.findViewById(R.id.score_3),
                view.findViewById(R.id.score_4),
                view.findViewById(R.id.score_5),
                view.findViewById(R.id.score_6),
                view.findViewById(R.id.score_7),
                view.findViewById(R.id.score_8),
                view.findViewById(R.id.score_9),
                view.findViewById(R.id.score_10)
        };
    }

    private void addPlayer() {
        String js = MSPV3.getMe().getString("MY_DB", "");
        MyDB DB= new Gson().fromJson(js, MyDB.class);
        scores = DB.getRecords();

        if(scores.size()<10){
            for (int i = 0; i < scores.size(); i++) {
                top[i].setText("Name: "+scores.get(i).getName()+ " Score: "+scores.get(i).getScore());
                Score temp = scores.get(i);
                top[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callBackList.zoom(temp.getLon(),temp.getLat());
                    }
                });

            }
        }else{
            for (int i = 0; i < 10; i++) {
                top[i].setText("Name: "+scores.get(i).getName()+ " Score: "+scores.get(i).getScore());
                Score temp = scores.get(i);

                top[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callBackList.zoom(temp.getLon(),temp.getLat());
                    }
                });
            }
        }

    }


}