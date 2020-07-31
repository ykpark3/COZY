package com.example.myeyes.fragment;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myeyes.AdrressDialog;
import com.example.myeyes.Constant;
import com.example.myeyes.Database;
import com.example.myeyes.R;

public class MovingLineFragment extends Fragment {

    View view;
    private SQLiteDatabase database;
    private Button firstButtonClickInMovingLine, secondButtonClickInMovingLine, thirdButtonClickInMovingLine;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_moving_line, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        database = Database.getInstance(getActivity()).getReadableDatabase();

        firstButtonClickInMovingLine = (Button) view.findViewById(R.id.firstButtonClickInMovingLine);
        secondButtonClickInMovingLine = (Button) view.findViewById(R.id.secondButtonClickInMovingLine);
        thirdButtonClickInMovingLine = (Button) view.findViewById(R.id.thirdButtonClickInMovingLine);

        firstButtonClickInMovingLine.setText(Database.getInstance(getActivity()).getAdrress(database, 0));
        secondButtonClickInMovingLine.setText(Database.getInstance(getActivity()).getAdrress(database, 1));
        thirdButtonClickInMovingLine.setText(Database.getInstance(getActivity()).getAdrress(database, 2));

        setButtonClickListener(firstButtonClickInMovingLine);
        setButtonClickListener(secondButtonClickInMovingLine);
        setButtonClickListener(thirdButtonClickInMovingLine);


    }

    //버튼 이벤트리스너 설정하는
    public void setButtonClickListener(final Button buttonClickInMovingLine) {
        buttonClickInMovingLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(buttonClickInMovingLine.getText().equals(Constant.REGISER_MY_ADRRESS)){
                AdrressDialog adrressDialog = new AdrressDialog(getActivity());
                adrressDialog.makeAdrressDialog();
                }
                else
                    Toast.makeText(getActivity(), "확진자 동선은 어디어디입니다`", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
