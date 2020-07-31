package com.example.myeyes.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myeyes.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

public class ComparisionMovingLineFragment extends Fragment {

    RadioButton radioButton1, radioButton2, radioButton3;
    RadioGroup radioGroup;

    Circle circle;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comparision_moving_line, container, false);

        radioButton1 = (RadioButton) view.findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton) view.findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) view.findViewById(R.id.radioButton3);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);

        /*
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d("@@@", "그룹");

                switch (checkedId) {
                    case R.id.radioButton1:
                        Log.d("@@@", "111");
                        nearKilometer(1000);
                        break;
                    case R.id.radioButton2:
                        Log.d("@@@", "222");
                        nearKilometer(2000);
                        break;
                    case R.id.radioButton3:
                        Log.d("@@@", "333");
                        nearKilometer(3000);
                        break;
                }
            }
        });
         */


        radioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("!!!!!", "11");
                drawCircle(1000, 14);
            }
        });

        radioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("!!!!!", "22");
                drawCircle(2000, 13);
            }
        });

        radioButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("!!!!!", "33");
                drawCircle(3000, 12);
            }
        });

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.fragmentGoogle, new MapFragment());
        fragmentTransaction.commit();
    }


    // 선택한 km에 따라 circle 추가
    private void drawCircle(int radius, int zoom) {
        Log.d("!!!!!", "addCircle");

        MapFragment.mMap.animateCamera(CameraUpdateFactory.zoomTo(zoom));

        if(circle != null) {
            circle.remove();
        }

        CircleOptions kilometer = new CircleOptions().center(MapFragment.currentPosition)   // 원점
                .radius(radius)   // 반지름 (단위: m)
                .strokeWidth(0f)   // 선 너비: 0f(선 없음)
                .fillColor(Color.parseColor("#4C880000"));   // 배경색, 4C는 투명도 30%

        circle = MapFragment.mMap.addCircle(kilometer);
    }

}
