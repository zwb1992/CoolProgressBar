package com.zwb.coolprogressbar;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zwb.coolprogressbar.view.HorizontalProgressBar;

public class MainActivity extends AppCompatActivity {
    private HorizontalProgressBar hpb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hpb = (HorizontalProgressBar) findViewById(R.id.hpb);
    }

    private int progress = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            hpb.setProgress(progress);
            if (what < 100) {
                Message message = Message.obtain();
                progress++;
                message.what = progress;
                handler.sendMessageDelayed(message, 100);
            }
        }
    };

    public void run(View view) {
        progress = 0;
        handler.sendEmptyMessage(progress);
    }
}