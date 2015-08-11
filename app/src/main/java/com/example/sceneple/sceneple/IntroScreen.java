package com.example.sceneple.sceneple;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Message;
import android.view.Window;
import android.widget.TextView;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by p on 2015-08-04.
 */
public class IntroScreen extends Activity{

    private static Typeface mTypeface = null;
    private TextView mTextView = null;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.introscreen);
        mTypeface = Typeface.createFromAsset(this.getAssets(), "font/helvetica_extended_thin.ttf");
        mTextView = (TextView)findViewById(R.id.introText);
        mTextView.setTypeface(mTypeface);
        LoadingIntro();
    }

    private void LoadingIntro(){
        android.os.Handler mHandler = new android.os.Handler(){
            public void handleMessage(Message msg){
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fade, R.anim.hold);
            }
        };
        mHandler.sendEmptyMessageDelayed(0, 3000);
    }
}
