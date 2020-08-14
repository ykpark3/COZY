package com.example.cozy.UI;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;

import androidx.viewpager.widget.ViewPager;

import com.example.cozy.Adapter.InformationViewPagerAdapter;
import com.example.cozy.R;
import com.pm10.library.CircleIndicator;

public class InformationDialog {

    private ViewPager informationViewPager;
    private InformationViewPagerAdapter informationViewPagerAdapter;
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
        informationViewPagerAdapter = new InformationViewPagerAdapter(context);

        informationViewPager.setAdapter(informationViewPagerAdapter);

        CircleIndicator circleIndicator = (CircleIndicator) dialog.findViewById(R.id.indicatorInformationDialog);
        circleIndicator.setupWithViewPager(informationViewPager);
    }
}
