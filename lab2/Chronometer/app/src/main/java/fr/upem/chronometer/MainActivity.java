package fr.upem.chronometer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

        Button startBtn = (Button) findViewById(R.id.start);
        Button stopBtn = (Button) findViewById(R.id.stop);
        Button updateBtn = (Button) findViewById(R.id.update);
        Button resetBtn = (Button) findViewById(R.id.reset);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startChrono();
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
                mcv.invalidate();
                mcv.postDelayed(this, DELAY);
            }
        };
        mcv.postDelayed(refresh, DELAY);

    }

    private void startChrono() {
        if(!isStarted) {
            startTime = System.nanoTime();
            isStarted = true;
            //updateChrono(myClockView);
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
            mcv.setElapsedTime(System.nanoTime() - startTime);
        } else {
            mcv.setElapsedTime(endTime);
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
