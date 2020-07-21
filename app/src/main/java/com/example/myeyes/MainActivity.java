package com.example.myeyes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.sdk.newtoneapi.SpeechRecognizeListener;
import com.kakao.sdk.newtoneapi.SpeechRecognizerClient;
import com.kakao.sdk.newtoneapi.SpeechRecognizerManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SpeechRecognizeListener {

    private Context mContext;
    private SpeechRecognizerClient client;
    private static final int REQUEST_CODE_AUDIO_AND_WRITE_EXTERNAL_STORAGE = 0;
    private boolean isPermissionGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //권한을 확인하는 부분123123
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            //둘중 하나라도
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO) && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_AUDIO_AND_WRITE_EXTERNAL_STORAGE);
            } else {
                // 사용자가 거부하면서 다시 묻지 않기를 클릭.. 권한이 없다고 사용자에게 직접 알림.
                Toast.makeText(this, "권한 없습니다.", Toast.LENGTH_SHORT).show();
            }
        } else {
            // sdk 초기화
            SpeechRecognizerManager.getInstance().initializeLibrary(this);
            isPermissionGranted = true;
            //startUsingSpeechSDK();
        }

        /*
        // 클라이언트 생성
        String userdict = "";
        SpeechRecognizerClient.Builder builder = new SpeechRecognizerClient.Builder().
               setServiceType(SpeechRecognizerClient.SERVICE_TYPE_WEB).
                setUserDictionary(userdict);  // optional

        client = builder.build();*/
        //getHashKey(this);
    }

    public void onDestroy() {
        super.onDestroy();
        // API를 더이상 사용하지 않을 때 finalizeLibrary()를 호출한다.
        SpeechRecognizerManager.getInstance().finalizeLibrary();
    }


    //퍼미션 체크 후 콜백 메소드
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_AUDIO_AND_WRITE_EXTERNAL_STORAGE:

                //동의한경우
                if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "권한에 동의했습니다.", Toast.LENGTH_SHORT).show();
                    SpeechRecognizerManager.getInstance().initializeLibrary(this);
                    isPermissionGranted = true;
                }
                //동의하지 않은 경우
                else {
                    Toast.makeText(this, "권한이 없습니다.", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                break;
        }
    }

    public void mikeButton(View view) {

        //권한이 충족됐을때만 이벤트 실행
        if(!isPermissionGranted){
            Toast.makeText(this, "권한이 없습니다.", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_AUDIO_AND_WRITE_EXTERNAL_STORAGE);
            return;
        }

        int id = view.getId();
        String serviceType = SpeechRecognizerClient.SERVICE_TYPE_WEB;

        SpeechRecognizerClient.Builder builder = new SpeechRecognizerClient.Builder().setServiceType(serviceType);
        client = builder.build();

        client.setSpeechRecognizeListener(this);
        client.startRecording(true);
        Log.d("dsad", "finish");
        Toast.makeText(this, "음성인식을 시작합니다.", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void onReady() {
        Log.d("MainActivity", "모든 준비가 완료 되었습니다.");

    }

    @Override
    public void onBeginningOfSpeech() {
        Log.d("MainActivity", "말하기 시작 했습니다.");
    }

    @Override
    public void onEndOfSpeech() {
        Log.d("MainActivity", "말하기가 끝났습니다.");
    }

    @Override
    public void onError(int errorCode, String errorMsg) {
        Log.d("error","error");
    }

    @Override
    public void onPartialResult(String partialResult) {

    }

    @Override
    public void onResults(Bundle results) {
        final StringBuilder builder = new StringBuilder();

        final ArrayList<String> texts = results.getStringArrayList(SpeechRecognizerClient.KEY_RECOGNITION_RESULTS);
        ArrayList<Integer> confs = results.getIntegerArrayList(SpeechRecognizerClient.KEY_CONFIDENCE_VALUES);

        Log.d("MainActivity", "Result: " + texts);


        for (int i = 0; i < texts.size(); i++) {
            builder.append(texts.get(i));
            builder.append(" (");
            builder.append(confs.get(i).intValue());
            builder.append(")\n");
        }


        //모든 콜백함수들은 백그라운드에서 돌고 있기 때문에 메인 UI를 변경할려면 runOnUiThread를 사용해야 한다.
        final Activity activity = this;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (activity.isFinishing()) return;

            }
        });

    }

    @Override
    public void onAudioLevel(float audioLevel) {

    }

    @Override
    public void onFinished() {

    }

    //해쉬키 구하는 메소드
    @Nullable
    public static String getHashKey(Context context) {
        final String TAG = "KeyHash";
        String keyHash = null;
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                keyHash = new String(Base64.encode(md.digest(), 0));
                Log.d(TAG, keyHash);
            }

        } catch (Exception e) {
            Log.e("name not found", e.toString());
        }
        if (keyHash != null) {
            return keyHash;

        } else {
            return null;
        }
    }

}