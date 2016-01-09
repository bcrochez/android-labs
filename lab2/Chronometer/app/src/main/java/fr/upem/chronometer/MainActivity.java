package fr.upem.chronometer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static long DELAY = 100;
    private long startTime = 0;
    private long endTime = 0;
    private boolean isStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MyClockView mcv = (MyClockView) findViewById(R.id.my_clock);
        //final TextView elapsedTime = (TextView) findViewById(R.id.elapse_time);

        Button startBtn = (Button) findViewById(R.id.start);
        Button stopBtn = (Button) findViewById(R.id.stop);
        Button updateBtn = (Button) findViewById(R.id.update);
        Button resetBtn = (Button) findViewById(R.id.reset);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startChrono(mcv);
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopChrono();
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateChrono(mcv);
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetChrono();
            }
        });

        final Runnable refresh = new Runnable() {
            @Override
            public void run() {
                updateChrono(mcv);
                mcv.postDelayed(this, DELAY);
            }
        };
        mcv.postDelayed(refresh, DELAY);

    }

    private void startChrono(MyClockView myClockView) {
        if(!isStarted) {
            startTime = System.nanoTime();
            isStarted = true;
            updateChrono(myClockView);
        }
    }

    private void stopChrono() {
        if(isStarted) {
            endTime = System.nanoTime() - startTime;
            isStarted = false;
        }
    }

    private void updateChrono(MyClockView mcv) {
        if(isStarted) {
            //tv.setText(Long.toString((System.nanoTime() - startTime)/1000000000));
            mcv.setElapsedTime((int) (System.nanoTime() - startTime)/1000000000);
        } else {
            //tv.setText(Long.toString(endTime/1000000000));
            mcv.setElapsedTime((int) endTime/1000000000);
        }
    }

    private void resetChrono() {
        if(isStarted) {
            startTime = System.nanoTime();
        } else {
            endTime = 0;
        }
    }
}
