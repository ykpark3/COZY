package com.example.cozy.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.cozy.R;

public class InformationViewPagerAdapter extends PagerAdapter {

    private View view;
    private Context context;

    public InformationViewPagerAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        switch (position){

            case 0 :
                view = layoutInflater.inflate(R.layout.view_pager_information_first, container, false);
                setFirstInformationViewPager();
                break;

            case 1:
                view = layoutInflater.inflate(R.layout.view_pager_information_second, container, false);
                break;

            case 2:
                view = layoutInflater.inflate(R.layout.view_pager_information_third, container, false);
                break;
        }

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public void setFirstInformationViewPager(){
        TextView textView = view.findViewById(R.id.title_first_information_view_pager);

        Spannable span = (Spannable)textView.getText();
        span.setSpan(new RelativeSizeSpan(1.5f),textView.getText().toString().indexOf("\n") +1
                ,textView.getText().toString().indexOf("\n")+4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.newbackgroundorangecolor)),
                textView.getText().toString().indexOf("C"),textView.getText().toString().indexOf("C") + 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}
