package com.caqmei.noodletimer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView timerText;
    private Button controlBtn;

    private SeekBar scrubber;

    private CountDownTimer countDown;

    private int time = 180000;

    public void controlTimer(View view) {

        Log.i("Button Pressed", "Pressed");

        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.ichiran);

        if(view.getTag().equals("0")) {
            controlBtn.setText("Stop");
            view.setTag("1");
            countDown = new CountDownTimer(time + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {

                    Log.i("Seconds left", String.valueOf(millisUntilFinished / 1000));

                    int minutes = (int) millisUntilFinished / 60000;
                    int seconds = (int) (millisUntilFinished / 1000) % 60;

                    String secondString = Integer.toString(seconds);

                    if(seconds < 10) {
                        secondString = "0" + Integer.toString(seconds);
                    }

                    timerText.setText(minutes + ":" + secondString);
                }

                @Override
                public void onFinish() {
                    timerText.setText("0:00");
                    mediaPlayer.start();
                }
            }.start();

            scrubber.setEnabled(false);
        } else {
            controlBtn.setText("Start");
            view.setTag("0");
            scrubber.setEnabled(true);
            countDown.cancel();
            scrubber.setProgress(180);
            timerText.setText("3:00");
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerText = (TextView) findViewById(R.id.timer);

        scrubber = (SeekBar) findViewById(R.id.timerScrubber);
        scrubber.setMax(300);
        scrubber.setProgress(180);

        scrubber.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                int minutes = (int) progress / 60;
                int seconds = progress % 60;

                String secondString = Integer.toString(seconds);

                if(seconds < 10) {
                    secondString = "0" + Integer.toString(seconds);
                }

                timerText.setText(minutes + ":" + secondString);
                time = progress * 1000;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        controlBtn = (Button) findViewById(R.id.controlBtn);

    }
}
