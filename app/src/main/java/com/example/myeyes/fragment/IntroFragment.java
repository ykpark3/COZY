package com.example.myeyes.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.myeyes.Adapter.IntroViewPagerAdapter;
import com.example.myeyes.IntroImageViewData;
import com.example.myeyes.MainActivity;
import com.example.myeyes.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class IntroFragment extends Fragment {

    private View view;
    private ViewPager viewPager;
    private IntroViewPagerAdapter introViewPagerAdapter;
    private ArrayList<IntroImageViewData> introImageViewDataArrayList;
    private int currentPage = 0;
    private Timer timer;
    //delay in milliseconds before task is to be executed
    private final long DELAY_MS = 500;
    // time in milliseconds between successive task executions.
    private final long PERIOD_MS = 3000;
    private MainActivity mainActivity;

    public IntroFragment(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_intro, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //타이틀 글씨 색 변경
        TextView textView = (TextView) view.findViewById(R.id.titleTextView);
        Spannable span = (Spannable) textView.getText();
        span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.newbackgroundorangecolor)), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        initializeImageViewData();

        viewPager = view.findViewById(R.id.viewPager);
        introViewPagerAdapter = new IntroViewPagerAdapter(getActivity(), introImageViewDataArrayList);
        viewPager.setAdapter(introViewPagerAdapter);

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            @Override
            public void run() {
                if (currentPage == 3) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

        final TextView coronaTextView = view.findViewById(R.id.coronaInformationView);
        coronaTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.coronaInformationButton(coronaTextView);
            }
        });

        final TextView movingLineTextView = view.findViewById(R.id.button2);
        movingLineTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.movingLineButton(movingLineTextView);
            }
        });

        final TextView comparisionmovingLineTextView = view.findViewById(R.id.button3);
        comparisionmovingLineTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.comparisionMovingLineButton(comparisionmovingLineTextView);
            }
        });


    }

    public void initializeImageViewData() {
        introImageViewDataArrayList = new ArrayList<IntroImageViewData>();

        introImageViewDataArrayList.add(new IntroImageViewData("일일 확진자\n36명\n2020-08-08 기준", R.drawable.slide_image1));
        introImageViewDataArrayList.add(new IntroImageViewData("오늘확진자 현황", R.drawable.slide_image2));
        introImageViewDataArrayList.add(new IntroImageViewData("대한민국엔 \n위기극복의 DNA가 있습니다.", R.drawable.slide_image3));

    }
}
