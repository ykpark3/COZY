package com.example.cozy.UI;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;

import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.cozy.Adapter.InformationViewPager2Adapter;
import com.example.cozy.Adapter.InformationViewPagerAdapter;
import com.example.cozy.R;
import com.pm10.library.CircleIndicator;

import me.relex.circleindicator.CircleIndicator2;
import me.relex.circleindicator.CircleIndicator3;

public class InformationDialog {

    private ViewPager2 informationViewPager;
    private InformationViewPager2Adapter informationViewPagerAdapter;
    private Context context;
    public Dialog dialog;

    public InformationDialog(Context context){
        this.context = context;
    }

    public void makeInformationDialog(){

        dialog = new Dialog(context);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.setContentView(R.layout.dialog_information);

        dialog.show();

        informationViewPager = dialog.findViewById(R.id.informationViewPager);
        //informationViewPagerAdapter = new InformationViewPagerAdapter(context);
        informationViewPagerAdapter = new InformationViewPager2Adapter();

        informationViewPager.setAdapter(informationViewPagerAdapter);
        informationViewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        CircleIndicator3 circleIndicator3 = (CircleIndicator3) dialog.findViewById(R.id.indicatorInformationDialog);
        circleIndicator3.setViewPager(informationViewPager);

      /*  CircleIndicator circleIndicator = (CircleIndicator) dialog.findViewById(R.id.indicatorInformationDialog);
        circleIndicator.setupWithViewPager(informationViewPager);*/
    }
}
