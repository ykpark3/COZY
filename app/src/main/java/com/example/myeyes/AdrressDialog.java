package com.example.myeyes;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.widget.ListPopupWindow;

import java.lang.reflect.Field;

public class AdrressDialog {
    private Context context;

    public Spinner rootSpinner;
    public Spinner childSpinner;

    public AdrressDialog(Context context) {
        this.context = context;
    }

    public void makeAdrressDialog() {
        final Dialog dialog = new Dialog(context);

        //액티비티 타이틀바 섬기기
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //다이얼로그 배경 투명
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.setContentView(R.layout.dialog_adrress);

        dialog.show();

        //스피너 설정

        //시,도
        rootSpinner = (Spinner) dialog.findViewById(R.id.adrress_dialog_spinner);
        final ArrayAdapter rootArrayAdapter = new ArrayAdapter(context, R.layout.adrress_spinner_item, Constant.adrressInKorea);
        rootSpinner.setAdapter(rootArrayAdapter);


        //시,군,구 초기설정
        childSpinner = (Spinner) dialog.findViewById(R.id.adrress2_dialog_spinner);
        final ArrayAdapter initialChildArrayAdapter = new ArrayAdapter(context, R.layout.adrress_spinner_item, Constant.defaultAdrress);
        childSpinner.setAdapter(initialChildArrayAdapter);

/*
        rootSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                //"시군구" 로 초기화
                ArrayAdapter childArrayAdapter  = new ArrayAdapter(context, R.layout.adrress_spinner_item, Constant.defaultAdrress);

                switch (rootArrayAdapter.getItem(position).toString()) {

                    case "서울특별시":
                        childArrayAdapter = new ArrayAdapter(context, R.layout.adrress_spinner_item, Constant.adrressInSeoul);
                        break;

                    case "부산광역시":
                        childArrayAdapter = new ArrayAdapter(context, R.layout.adrress_spinner_item, Constant.adrressInBusan);
                        break;

                    case "대구광역시":
                        childArrayAdapter = new ArrayAdapter(context, R.layout.adrress_spinner_item, Constant.adrressInDaeGu);
                        break;

                    case "인천광역시":
                        childArrayAdapter = new ArrayAdapter(context, R.layout.adrress_spinner_item, Constant.adrressInIncheon);
                        break;

                    case "광주광역시":
                        childArrayAdapter = new ArrayAdapter(context, R.layout.adrress_spinner_item, Constant.adrressInGwangju);
                        break;

                    case "대전광역시":
                        childArrayAdapter = new ArrayAdapter(context, R.layout.adrress_spinner_item, Constant.adrressInDaejeon);
                        break;

                    case "울산광역시":
                        childArrayAdapter = new ArrayAdapter(context, R.layout.adrress_spinner_item, Constant.adrressInUlsan);
                        break;

                    case "세종특별자치시":
                        childArrayAdapter = new ArrayAdapter(context, R.layout.adrress_spinner_item, Constant.adrressInSejong);
                        break;

                    case "경기도":
                        childArrayAdapter = new ArrayAdapter(context, R.layout.adrress_spinner_item, Constant.adrressInGyeonggi);
                        break;

                    case "강원도":
                        childArrayAdapter = new ArrayAdapter(context, R.layout.adrress_spinner_item, Constant.adrressInGangwon);
                        break;

                    case "충청북도":
                        childArrayAdapter = new ArrayAdapter(context, R.layout.adrress_spinner_item, Constant.adrressInChungbuk);
                        break;

                    case "충청남도":
                        childArrayAdapter = new ArrayAdapter(context, R.layout.adrress_spinner_item, Constant.adrressInChungnam);
                        break;

                    case "전라북도":
                        childArrayAdapter = new ArrayAdapter(context, R.layout.adrress_spinner_item, Constant.adrressInJeonbuk);
                        break;

                    case "전라남도":
                        childArrayAdapter = new ArrayAdapter(context, R.layout.adrress_spinner_item, Constant.adrressInJeonnam);
                        break;

                    case "경싱북도":
                        childArrayAdapter = new ArrayAdapter(context, R.layout.adrress_spinner_item, Constant.adrressInGyeongbuk);
                        break;

                    case "경상남도":
                        childArrayAdapter = new ArrayAdapter(context, R.layout.adrress_spinner_item, Constant.adrressInGyeongnam);
                        break;

                    case "제주특별자치시":
                        childArrayAdapter = new ArrayAdapter(context, R.layout.adrress_spinner_item, Constant.adrressInJeJu);
                        break;

                }
                childSpinner.setAdapter(childArrayAdapter);
            }
        });*/


        //클릭이벤트
        rootSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //"시군구" 로 초기화
                ArrayAdapter childArrayAdapter  = new ArrayAdapter(context, R.layout.adrress_spinner_item, Constant.defaultAdrress);

                switch (rootArrayAdapter.getItem(i).toString()) {

                    case "서울특별시":
                        childArrayAdapter = new ArrayAdapter(context, R.layout.adrress_spinner_item, Constant.adrressInSeoul);
                        break;

                    case "부산광역시":
                        childArrayAdapter = new ArrayAdapter(context, R.layout.adrress_spinner_item, Constant.adrressInBusan);
                        break;

                    case "대구광역시":
                        childArrayAdapter = new ArrayAdapter(context, R.layout.adrress_spinner_item, Constant.adrressInDaeGu);
                        break;

                    case "인천광역시":
                        childArrayAdapter = new ArrayAdapter(context, R.layout.adrress_spinner_item, Constant.adrressInIncheon);
                        break;

                    case "광주광역시":
                        childArrayAdapter = new ArrayAdapter(context, R.layout.adrress_spinner_item, Constant.adrressInGwangju);
                        break;

                    case "대전광역시":
                        childArrayAdapter = new ArrayAdapter(context, R.layout.adrress_spinner_item, Constant.adrressInDaejeon);
                        break;

                    case "울산광역시":
                        childArrayAdapter = new ArrayAdapter(context, R.layout.adrress_spinner_item, Constant.adrressInUlsan);
                        break;

                    case "세종특별자치시":
                        childArrayAdapter = new ArrayAdapter(context, R.layout.adrress_spinner_item, Constant.adrressInSejong);
                        break;

                    case "경기도":
                        childArrayAdapter = new ArrayAdapter(context, R.layout.adrress_spinner_item, Constant.adrressInGyeonggi);
                        break;

                    case "강원도":
                        childArrayAdapter = new ArrayAdapter(context, R.layout.adrress_spinner_item, Constant.adrressInGangwon);
                        break;

                    case "충청북도":
                        childArrayAdapter = new ArrayAdapter(context, R.layout.adrress_spinner_item, Constant.adrressInChungbuk);
                        break;

                    case "충청남도":
                        childArrayAdapter = new ArrayAdapter(context, R.layout.adrress_spinner_item, Constant.adrressInChungnam);
                        break;

                    case "전라북도":
                        childArrayAdapter = new ArrayAdapter(context, R.layout.adrress_spinner_item, Constant.adrressInJeonbuk);
                        break;

                    case "전라남도":
                        childArrayAdapter = new ArrayAdapter(context, R.layout.adrress_spinner_item, Constant.adrressInJeonnam);
                        break;

                    case "경싱북도":
                        childArrayAdapter = new ArrayAdapter(context, R.layout.adrress_spinner_item, Constant.adrressInGyeongbuk);
                        break;

                    case "경상남도":
                        childArrayAdapter = new ArrayAdapter(context, R.layout.adrress_spinner_item, Constant.adrressInGyeongnam);
                        break;

                    case "제주특별자치시":
                        childArrayAdapter = new ArrayAdapter(context, R.layout.adrress_spinner_item, Constant.adrressInJeJu);
                        break;


                }
                childSpinner.setAdapter(childArrayAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }
}
