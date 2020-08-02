package com.example.myeyes.fragment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myeyes.AdrressDialog;
import com.example.myeyes.Constant;
import com.example.myeyes.Database;
import com.example.myeyes.Inferface.AdrressDialogListener;
import com.example.myeyes.R;

import java.net.InterfaceAddress;

public class MovingLineFragment extends Fragment {

    public View view;
    private SQLiteDatabase database;
    private TextView[] adrressButtons;
    private Integer defalutButtonId;
    private Integer dataCount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_moving_line, container, false);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        database = Database.getInstance(getActivity()).getReadableDatabase();

        dataCount = Database.getInstance(getActivity()).getDataCount(database);
        adrressButtons = new TextView[dataCount + 1];
        defalutButtonId = 2112;

        LinearLayout linearLayout = view.findViewById(R.id.adrressButtonLinearLayout);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.bottomMargin = getDPI(getActivity(), 15);
        layoutParams.leftMargin = getDPI(getActivity(), 15);
        layoutParams.rightMargin = getDPI(getActivity(), 15);

        //버튼 동적 생성, 있는 데이터 수 만큼 동적 생성
        for (int data = 0; data < dataCount; data++, defalutButtonId++) {
            adrressButtons[data] = new TextView(getActivity());

            adrressButtons[data].setHeight(getDPI(getActivity(), 110));
            adrressButtons[data].setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
            adrressButtons[data].setId(defalutButtonId);
            adrressButtons[data].setText(Database.getInstance(getActivity()).getAdrress(database, data), TextView.BufferType.SPANNABLE);
            //adrressButtons[data].setBackground(getResources().getDrawable(R.drawable.adrressbutton));
            adrressButtons[data].setBackgroundColor(Color.parseColor("#ffffff"));
            adrressButtons[data].setTextSize(getDPI(getActivity(), 8));
            adrressButtons[data].setLayoutParams(layoutParams);
            adrressButtons[data].setStateListAnimator(null);
            adrressButtons[data].setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            adrressButtons[data].setPadding(getDPI(getActivity(), 10), 0, 0, 0);
            adrressButtons[data].setCompoundDrawablesWithIntrinsicBounds(R.drawable.adrress_icon, 0, 0, 0);
            adrressButtons[data].setClickable(true);

            //주소 이름 부분만 크게하는 span
            Spannable span = (Spannable)adrressButtons[data].getText();
            span.setSpan(new RelativeSizeSpan(1.3f), 2,adrressButtons[data].getText().toString().indexOf("\n"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            span.setSpan(new StyleSpan(Typeface.BOLD), 2,adrressButtons[data].getText().toString().indexOf("\n"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            setButtonClickListener(adrressButtons[data]);
            linearLayout.addView(adrressButtons[data]);

        }

        //추가하기 버튼은 마지막 동적 생성
        adrressButtons[dataCount] = new TextView(getActivity());
        adrressButtons[dataCount].setHeight(getDPI(getActivity(), 150));
        adrressButtons[dataCount].setLayoutParams(layoutParams);
        adrressButtons[dataCount].setId(defalutButtonId);
        adrressButtons[dataCount].setTextSize(getDPI(getActivity(), 10));
        adrressButtons[dataCount].setText(Constant.REGISER_MY_ADRRESS);
        adrressButtons[dataCount].setTextColor(Color.parseColor("#e2e2e2"));
        adrressButtons[dataCount].setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
        adrressButtons[dataCount].setBackground(getResources().getDrawable(R.drawable.adrressbutton));
        adrressButtons[dataCount].setClickable(true);
        adrressButtons[dataCount].setStateListAnimator(null);

        setButtonClickListener(adrressButtons[dataCount]);
        linearLayout.addView(adrressButtons[dataCount]);

    }

    //버튼 이벤트리스너 설정하는
    public void setButtonClickListener(final TextView buttonClickInMovingLine) {
        buttonClickInMovingLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (buttonClickInMovingLine.getText().equals(Constant.REGISER_MY_ADRRESS)) {
                    AdrressDialog adrressDialog = new AdrressDialog(getActivity());
                    adrressDialog.makeAdrressDialog();

                    //인터페이스를 사용하여 콜백메소드 정의
                    adrressDialog.setDialogListener(new AdrressDialogListener() {
                        @Override
                        public void onRegisterButtonClicked() {
                            refreshFragment();
                        }
                    });

                } else
                    Toast.makeText(getActivity(), "확진자 동선은 어디어디입니다`", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void refreshFragment() {
        Fragment currnetFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.detach(currnetFragment);
        ft.attach(currnetFragment);
        ft.commit();
    }

    public Integer getDPI(Context context, float dpiValue) {

        int pixel = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpiValue, context.getResources().getDisplayMetrics());
        return pixel;
    }

}
