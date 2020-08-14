package com.example.cozy.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cozy.Adapter.IntroViewPagerAdapter;
import com.example.cozy.Constant;
import com.example.cozy.Data.IntroImageViewData;
import com.example.cozy.UI.IntroViewPager;
import com.example.cozy.Server.Get;
import com.example.cozy.Activity.MainActivity;
import com.example.cozy.R;
import com.pm10.library.CircleIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class IntroFragment extends Fragment {

    private View view;
    private IntroViewPager viewPager;
    private IntroViewPagerAdapter introViewPagerAdapter;
    private ArrayList<IntroImageViewData> introImageViewDataArrayList;

    public String[] infecteeInformationDate;
    public float[] infecteeNumber;

    private int currentPage = 0;
    private Timer timer;
    //delay in milliseconds before task is to be executed
    private final long DELAY_MS = 500;
    // time in milliseconds between successive task executions.
    private final long PERIOD_MS = 3000;
    private MainActivity mainActivity;

    public IntroFragment(MainActivity mainActivity) {
        Log.d("introe","gt");
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

        viewPager = (IntroViewPager) view.findViewById(R.id.viewPager);
        introViewPagerAdapter = new IntroViewPagerAdapter(getActivity(), introImageViewDataArrayList, infecteeInformationDate, infecteeNumber);
        viewPager.setAdapter(introViewPagerAdapter);
        viewPager.setScrollDurationFactor(2);

        CircleIndicator circleIndicator = (CircleIndicator) view.findViewById(R.id.indicator);
        circleIndicator.setupWithViewPager(viewPager);

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            @Override
            public void run() {
                if (currentPage == 3) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++);
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
                timer.cancel();
                mainActivity.coronaInformationButton(coronaTextView);
            }
        });

        final TextView movingLineTextView = view.findViewById(R.id.button2);
        movingLineTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.cancel();
                mainActivity.movingLineButton(movingLineTextView);
            }
        });

        final TextView comparisionmovingLineTextView = view.findViewById(R.id.button3);
        comparisionmovingLineTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.cancel();
                mainActivity.comparisionMovingLineButton(comparisionmovingLineTextView);
            }
        });


    }

    public void initializeImageViewData() {

        introImageViewDataArrayList = new ArrayList<IntroImageViewData>();


        getCoronaInformation();

        DecimalFormat numberFormat = new DecimalFormat("###,###,###");

        introImageViewDataArrayList.add(new IntroImageViewData("일일 확진자\n" + numberFormat.format(infecteeNumber[3]) + "명" + "\n" +
                infecteeInformationDate[3] + " 기준", R.drawable.slide_image1));
        introImageViewDataArrayList.add(new IntroImageViewData("오늘확진자 현황", R.drawable.slide_image2));
        introImageViewDataArrayList.add(new IntroImageViewData("대한민국엔 \n위기극복의 DNA가 있습니다.", R.drawable.slide_image3));

    }

    public void getCoronaInformation() {
        String jsonString ="";

        Get get = new Get();
        get.execute(Constant.CORONA_INFORMATION_URL);

        try {
            jsonString= jsonString+ get.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            infecteeInformationDate = new String[4];
            infecteeNumber = new float[4];
            JSONObject infecteeDateJSONObject;
            JSONArray jsonChartArray = jsonObject.getJSONArray("recentInfectees");

            for (int index = 0; index < 4; index++) {
                infecteeDateJSONObject = jsonChartArray.getJSONObject(index);
                infecteeInformationDate[index] = "" + infecteeDateJSONObject.getString("date");
                infecteeNumber[index] = Float.parseFloat(infecteeDateJSONObject.getString("nationalOccurence")) +
                        Float.parseFloat(infecteeDateJSONObject.getString("overseasInflow"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
