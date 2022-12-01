package com.example.aliabuelhija_hw1_mobileapplications;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Activity_TOP10 extends AppCompatActivity  {
    private ScoresFragment fragmentScore;
    private MapsFragment fragmentMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top10);
        GameUtils.hideSystemUI(this);
        fragmentScore = new ScoresFragment();
        fragmentScore.setActivity(this);
        fragmentScore.setCallBackList(callBack_list);

        getSupportFragmentManager().beginTransaction().add(R.id.frameScores, fragmentScore)
                .commit();
        fragmentMap= new MapsFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frameMap, fragmentMap).commit();



    }
    CallBack_List callBack_list = new CallBack_List() {
        @Override
        public void zoom(double lat, double lon) {
            GoogleMap gm = fragmentMap.getmMap();
            LatLng point = new LatLng(lon, lat);
            gm.addMarker(new MarkerOptions()
                    .position(point)
                    .title("    " ));
            gm.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 13.0f));        }
    };
}