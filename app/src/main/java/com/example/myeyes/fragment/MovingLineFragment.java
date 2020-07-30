package com.example.myeyes.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myeyes.AdrressDialog;
import com.example.myeyes.R;

public class MovingLineFragment extends Fragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_moving_line,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button fistButtonClickInMovingLine = (Button)view.findViewById(R.id.firstButtonClickInMovingLine);
        setButtonClickListener(fistButtonClickInMovingLine);

    }

    //버튼 이벤트리스너 설정하는
    public void setButtonClickListener(Button firstButtonClickInMovingLine){
        firstButtonClickInMovingLine.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                AdrressDialog adrressDialog = new AdrressDialog(getActivity());
                adrressDialog.makeAdrressDialog();
            }
        });
    }

}
