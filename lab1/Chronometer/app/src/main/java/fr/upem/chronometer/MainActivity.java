package fr.upem.chronometer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private double startTime = 0;
    private double endTime = 0;
    private boolean isStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView elapsedTime = (TextView) findViewById(R.id.elapse_time);

        Button startBtn = (Button) findViewById(R.id.start);
        Button stopBtn = (Button) findViewById(R.id.stop);
        Button updateBtn = (Button) findViewById(R.id.update);
        Button resetBtn = (Button) findViewById(R.id.reset);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startChrono(elapsedTime);
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
                updateChrono(elapsedTime);
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetChrono();
            }
        });
    }

    private void startChrono(TextView tv) {
        if(!isStarted) {
            startTime = System.nanoTime();
            isStarted = true;
            updateChrono(tv);
        }
    }

    private void stopChrono() {
        if(isStarted) {
            endTime = System.nanoTime() - startTime;
            isStarted = false;
        }
    }

    private void updateChrono(TextView tv) {
        if(isStarted) {
            tv.setText(Double.toString((System.nanoTime() - startTime)/1000000000.));
        } else {
            tv.setText(Double.toString(endTime/1000000000.));
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
