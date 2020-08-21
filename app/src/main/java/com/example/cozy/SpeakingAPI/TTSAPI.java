package com.example.cozy.SpeakingAPI;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.airbnb.lottie.LottieAnimationView;
import com.example.cozy.Activity.MainActivity;
import com.example.cozy.R;
import com.kakao.sdk.newtoneapi.TextToSpeechListener;

public class TTSAPI implements TextToSpeechListener {

    private MainActivity mainActivity;

    public TTSAPI(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onFinished() {
        Log.d("MainActivity", "TTS API FINISHED.");
        mainActivity.runOnUiThread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                mainActivity.mainMikeButton.setImageResource(R.drawable.main_mike1);
                mainActivity.mainMikeButton.setBackground(mainActivity.getDrawable(R.drawable.mike_button));
                mainActivity.mikeLottieAnimation.setVisibility(LottieAnimationView.INVISIBLE);
                mainActivity.mikeLottieAnimation.pauseAnimation();
            }
        });
    }

    @Override
    public void onError(int code, String message) {
        Log.d("MainActivity", "TTS API ERROR.");
    }


}
