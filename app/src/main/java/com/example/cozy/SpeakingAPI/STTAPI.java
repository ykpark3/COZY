package com.example.cozy.SpeakingAPI;

import android.os.Bundle;
import android.util.Log;

import com.example.cozy.Fragment.MapFragment;
import com.example.cozy.Server.Post;
import com.kakao.sdk.newtoneapi.SpeechRecognizeListener;
import com.kakao.sdk.newtoneapi.SpeechRecognizerClient;
import com.kakao.sdk.newtoneapi.TextToSpeechClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class STTAPI implements SpeechRecognizeListener{
    public TextToSpeechClient ttsClient;
    public String sstString;

    String[] forwardToServer = new String[8];
    //String[] chatBotMessage = new String[4];

    public STTAPI(){

        //tts 클라이언트 생성
        ttsClient = new TextToSpeechClient.Builder()
                .setSpeechMode(TextToSpeechClient.NEWTONE_TALK_2)     // 음성합성방식
                .setSpeechSpeed(1.0)            // 발음 속도(0.5~4.0)
                .setSpeechVoice(TextToSpeechClient.VOICE_WOMAN_READ_CALM)  //TTS 음색 모드 설정(여성 차분한 낭독체)
                .setListener(new TTSAPI())
                .build();


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
        Log.d("MainActivity", "STT API ERROR.");
    }

    @Override
    public void onPartialResult(String partialResult) {
    }

    @Override
    public void onResults(Bundle results) {

        Log.d("!!!!!", "onResults");
        final ArrayList<String> texts = results.getStringArrayList(SpeechRecognizerClient.KEY_RECOGNITION_RESULTS);

        sstString = texts.get(0);
        Log.d("MainActivity", "Result texts: " + texts);
        Log.d("MainActivity", "Result!!!!! sstString: " + sstString);

        forwardToServer[0] = "url";
        forwardToServer[1] = "http://ec2-13-209-74-229.ap-northeast-2.compute.amazonaws.com:3000/danbee";
        forwardToServer[2] = "userInput";

        forwardToServer[3] = sstString;
        forwardToServer[4] = "latitude";
        forwardToServer[5] = String.valueOf(MapFragment.currentPosition.latitude);
        forwardToServer[6] = "longitude";
        forwardToServer[7] = String.valueOf(MapFragment.currentPosition.longitude);

        connectServer();

        /*
        chatBotMessage[0] = "url";
        chatBotMessage[1] = "https://danbee.ai/chatflow/engine.do";
        chatBotMessage[2] = "input_sentence";
        chatBotMessage[3] = sstString;
        connectChatBot();

         */
    }

    @Override
    public void onAudioLevel(float audioLevel) {

    }

    @Override
    public void onFinished() {
        Log.d("MainActivity", "STT API FINISHED.");

        ttsClient.play(sstString + "에 대해 검색합니다.");
    }


    // POST 방식으로 서버랑 연결
    private void connectServer() {

        Log.d("!!!!!","connectPost");

        Post post = new Post();
        post.execute(forwardToServer);

        // json으로 string 값 받아오기
        String jsonString = "";

        try {
            Log.d("!!!!!","json"+jsonString);

            jsonString= jsonString + post.get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        getServerInformation(jsonString);
    }


    // 서버에서 받은 정보
    private void getServerInformation(String jsonString) {
        String message;

        try {
            Log.d("!!!!","getServerInformation");

            Log.d("!!!!!","jsonString: "+jsonString);

            JSONObject jsonObject = new JSONObject(jsonString);
            message = jsonObject.getString("message");

            Log.d("!!!!!","message"+message);

        } catch (JSONException e) {

            Log.d("!!!!! here","JSONException");
            e.printStackTrace();
        }
    }


    /*
    // POST 방식으로 챗봇 연결
    private void connectChatBot() {

        Log.d("!!!!!","connectChatBot");

        ChatBot chatBot = new ChatBot();
        chatBot.execute(chatBotMessage);

        // json으로 string 값 받아오기
        String jsonString = "";

        try {
            Log.d("!!!!!","json"+jsonString);

            jsonString= jsonString + chatBot.get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        getChatbotInformation(jsonString);
    }


    // 챗봇에서 받은 정보
    private void getChatbotInformation(String jsonString) {
        JSONObject responseSet, result;
        JSONArray jsonArrayResult;
        JSONObject JSONObjectMessage;
        String message;

        try {
            Log.d("!!!!","getChatbotInformation");

            Log.d("!!!!!","jsonString: "+jsonString);

            JSONObject jsonObject = new JSONObject(jsonString);

            responseSet = jsonObject.getJSONObject("responseSet");
            result = responseSet.getJSONObject("result");
            jsonArrayResult = result.getJSONArray("result");
            JSONObjectMessage = jsonArrayResult.getJSONObject(0);

            message = JSONObjectMessage.getString("message");

            Log.d("!!!!!","message:    "+message);


        } catch (JSONException e) {

            Log.d("!!!!! here","JSONException");
            e.printStackTrace();
        }
    }

     */
}
