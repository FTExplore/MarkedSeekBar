package com.example.bill.seekbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
            public void onProgressChanged(MarkedSeekBar seekBar, int i, boolean b) {
            }

            @Override
            public void onStartTrackingTouch(MarkedSeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(MarkedSeekBar seekBar) {
            }
        });
    }
}
