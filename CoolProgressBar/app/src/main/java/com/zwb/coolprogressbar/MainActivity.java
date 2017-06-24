package com.zwb.coolprogressbar;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zwb.coolprogressbar.view.HorizontalProgressBar;
import com.zwb.coolprogressbar.view.LinearProgressBar;
import com.zwb.coolprogressbar.view.RoundProgressBarWithNum;

public class MainActivity extends AppCompatActivity {
    private HorizontalProgressBar hpb;
    private RoundProgressBarWithNum rpb;
    private LinearProgressBar lpb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hpb = (HorizontalProgressBar) findViewById(R.id.hpb);
        rpb = (RoundProgressBarWithNum) findViewById(R.id.rpb);
        lpb = (LinearProgressBar) findViewById(R.id.lpb);
    }

    private int progress = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            hpb.setProgress(progress);
            rpb.setProgress(progress);
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
        lpb.start();
    }
}
