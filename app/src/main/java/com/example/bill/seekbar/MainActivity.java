package com.example.bill.seekbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    MarkedSeekBar sb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sb = (MarkedSeekBar) findViewById(R.id.sb);
        sb.setBufferProgress(70);
        sb.setFilmTitleMarkerPos(30);
        sb.setFilmTailMarkerPos(80);
        sb.setOnSeekBarChangeListener(new MarkedSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(MarkedSeekBar seekBar, float progress, boolean isFromUser) {
                Log.e("ZHZ","onProgressChanged : " + progress);
            }

            @Override
            public void onStartTrackingTouch(MarkedSeekBar seekBar) {
                Log.e("ZHZ","onStartTrackingTouch : " + seekBar.getProgress());
            }

            @Override
            public void onStopTrackingTouch(MarkedSeekBar seekBar) {
                Log.e("ZHZ","onStopTrackingTouch : " + seekBar.getProgress());
            }

        });


    }
}
