package com.example.myeyes.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.myeyes.Constant;
import com.example.myeyes.JSONTask;
import com.example.myeyes.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CoronaInformationFragment extends Fragment {

    private View view;
    public String jsonString = "";
    public String temporaryString,liveDate,overseasInflowNumber,nationalOccurenceNumber,totalInfecteeNumber,accumulationInfecteeNumber,casualtyNumber;
    public String[] infecteeInformationDate;
    public float[] infceteeNumber;

    PieChart pieChart;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_corona_information,container,false);


        pieChart = (PieChart) view.findViewById(R.id.piechart);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,5,5,5);   // 간격 띄우기

        pieChart.setDragDecelerationFrictionCoef(0.2f);   // 드래그해서 돌아가게 하기

        pieChart.setDrawHoleEnabled(true);   // 가운데 원형 가능 여부
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setEntryLabelColor(Color.RED);   // 차트 항목별 제목 색깔

        pieChart.getLegend().setEnabled(false);   // 범례 없애기

        ArrayList city = new ArrayList();

        city.add(new PieEntry(40f,"대구"));
        city.add(new PieEntry(20f,"서울"));
        city.add(new PieEntry(15f,"경북"));
        city.add(new PieEntry(5f,"경기"));
        city.add(new PieEntry(20f,"기타"));


        //pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic); //애니메이션


        PieDataSet dataSet = new PieDataSet(city,"Cities");   // 항목
        dataSet.setSliceSpace(3f);   // 차트들 사이 간격
        dataSet.setSelectionShift(3f);   // 클릭하면 커짐
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);


        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);   // 항목 이름 밖으로
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);   // 퍼센트 밖으로

        dataSet.setValueLineColor(ColorTemplate.COLOR_NONE);   // 설명하는 선 없애기

        PieData data = new PieData(dataSet);   // 퍼센트
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.BLUE);

        pieChart.setData(data);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        JSONTask jsonTask = new JSONTask();
        jsonTask.execute(Constant.CORONA_INFORMATION_URL);

        //json으로 string 값 받아오기
        String jsonString ="";
        try {
            jsonString= jsonString+jsonTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        getCoronaInformation(jsonString);

        //setText로 동적으로 text 준다.
        TextView liveDateTextView = (TextView)view.findViewById(R.id.liveDate);
        liveDateTextView.setText(liveDate);


        //텍스트뷰 글자 크기 가변적 적용
        TextView upTextView = (TextView)view.findViewById(R.id.upTextView);
        temporaryString = "일일 확진자 수 : "+ totalInfecteeNumber +"명 \n(국내 확진 " + nationalOccurenceNumber + " + 해외 유입 " + overseasInflowNumber +")";
        upTextView.setText(temporaryString);
        Spannable span = (Spannable)upTextView.getText();
        span.setSpan(new RelativeSizeSpan(1.7f), 11,getStringEndIndex(upTextView.getText().toString()), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //텍스트뷰 글자 크기 가변적 적용
        TextView downTextView = (TextView)view.findViewById(R.id.downTextView);
        temporaryString = "누적 확진자 수 : " + accumulationInfecteeNumber + "명 \n누적 사망자 수 : "+ casualtyNumber + "명";
        downTextView.setText(temporaryString);

        span = (Spannable)downTextView.getText();
        span.setSpan(new RelativeSizeSpan(1.7f), 11,getStringEndIndex(downTextView.getText().toString()), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new RelativeSizeSpan(1.7f),getStringStartIndex(downTextView.getText().toString()),downTextView.getText().toString().length()
                ,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        super.onActivityCreated(savedInstanceState);
    }

    //동적으로 크기를 바꾸기위해 '명'이 나올때의 인덱스를 구함
    public int getStringEndIndex(String inputtedString){

        for(int index = 0; index<inputtedString.length();index++){
            if(inputtedString.charAt(index) == '명')
                return index+1;
        }
        return 0;
    }

    //사망자수 크기를 동적으로 바꾸기위해 뒤에서부터 인덱스 구함.
    public int getStringStartIndex(String inputtedString){
        for(int index = inputtedString.length()-1; index>0;index--){
            if(inputtedString.charAt(index) == ':')
                return index+2;
        }
        return 0;
    }


    public class DayAxisValueFormatter extends ValueFormatter {
        private final BarLineChartBase<?> chart;
        public DayAxisValueFormatter(BarLineChartBase<?> chart) {
            this.chart = chart;
        }

        @Override
        public String getFormattedValue(float value) {
            switch ((int) value){
                case 1 :
                    return infecteeInformationDate[0];

                case 2:
                    return infecteeInformationDate[1];

                case 3:
                    return infecteeInformationDate[2];

                case 4:
                    return infecteeInformationDate[3];

                default:
                    return "0";
            }
        }
    }

    public class MyValueFormatter extends ValueFormatter implements IValueFormatter {

        private DecimalFormat mFormat;

        public MyValueFormatter() {
            mFormat = new DecimalFormat("###,###,###"); // use one decimal
        }

        @Override
        public String getFormattedValue(float value) {
            // write your logic here
            return mFormat.format(value) + " 명"; // e.g. append a dollar-sign
        }
    }

    public void getCoronaInformation(String string) {
        try {
            JSONObject jsonObject = new JSONObject(string);
            liveDate = jsonObject.getString("liveDate");

            JSONObject infecteeInformationJSONObject = jsonObject.getJSONObject("dailyInfectee");
            nationalOccurenceNumber = infecteeInformationJSONObject.getString("nationalOccurence");
            overseasInflowNumber = infecteeInformationJSONObject.getString("overseasInflow");
            int temporaryTotalInfecteeNumber = Integer.parseInt(nationalOccurenceNumber) + Integer.parseInt(overseasInflowNumber);
            totalInfecteeNumber = Integer.toString( temporaryTotalInfecteeNumber);

            JSONArray jsonArray = jsonObject.getJSONArray("liveState");
            JSONObject accumulationInfecteeJSONObject = jsonArray.getJSONObject(0);
            accumulationInfecteeNumber = accumulationInfecteeJSONObject.getString("data");
            accumulationInfecteeJSONObject = jsonArray.getJSONObject(3);
            accumulationInfecteeNumber = accumulationInfecteeNumber.substring(4);
            casualtyNumber = accumulationInfecteeJSONObject.getString("data");

            infecteeInformationDate = new String[4];
            infceteeNumber = new float[4];
            JSONObject infecteeDateJSONObject;
            JSONArray jsonChartArray = jsonObject.getJSONArray("recentInfectees");

            for(int index = 0; index<4; index ++) {
                infecteeDateJSONObject = jsonChartArray.getJSONObject(index);
                infecteeInformationDate[index] = "" + infecteeDateJSONObject.getString("date");
                infceteeNumber[index] = Float.parseFloat(infecteeDateJSONObject.getString("nationalOccurence")) +
                        Float.parseFloat(infecteeDateJSONObject.getString("overseasInflow"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
