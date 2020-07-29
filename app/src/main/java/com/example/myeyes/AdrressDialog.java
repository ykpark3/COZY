package com.example.myeyes;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AdrressDialog {
    private Context context;

    public Spinner spinner;

    public AdrressDialog(Context context){
        this.context = context;
    }

    public void callFunction(){
        final Dialog dialog = new Dialog(context);

        //액티비티 타이틀바 섬기기
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.dialog_adrress);

        dialog.show();

        spinner = (Spinner)dialog.findViewById(R.id.adrress_dialog_spinner);
        ArrayAdapter arrayAdapter = new ArrayAdapter(context,R.layout.support_simple_spinner_dropdown_item,Constant.adrressInKorea);

        spinner.setAdapter(arrayAdapter);




    }
}
