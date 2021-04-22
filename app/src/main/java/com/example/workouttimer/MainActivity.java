package com.example.workouttimer;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.os.Bundle;
import java.util.Locale;

import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
    private int seconds = 0;
    private boolean running, wasRunning;
    TextView txtPrevWorkout;
    EditText editWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) { // Returns state after orientation changes
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) // Keeps state after orientation change
    {
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        wasRunning = running;
        running = false;
    }


    public void onClickStart(View view)
    {
        running = true;
    }
    public void onClickStop(View view)
    {
        running = false;
    }
    public void onClickReset(View view)
    {
        txtPrevWorkout = findViewById(R.id.txtPrevWorkout);
        editWorkout = findViewById(R.id.editWorkout);

        if(editWorkout.getText().toString().length() != 0){
            String prevWorkout = editWorkout.getText().toString();
            txtPrevWorkout.setText(String.format(Locale.getDefault(), "You Spent %02d:%02d on %s last time.", ((seconds % 3600) / 60), (seconds % 60), (prevWorkout)));
        }
        running = false;
        seconds = 0;
    }

    private void runTimer()
    {
        final TextView timeView = (TextView)findViewById(R.id.time_view);

        final Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override

            public void run()
            {
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                String time = String.format(Locale.getDefault(), "%02d:%02d", minutes, secs);

                timeView.setText(time);

                if (running) { seconds++; }
                handler.postDelayed(this, 1000); // 1 Second delay
            }
        });
    }
}