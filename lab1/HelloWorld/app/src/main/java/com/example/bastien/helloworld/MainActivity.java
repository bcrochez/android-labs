package com.example.bastien.helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView text = (TextView) findViewById(R.id.counter);
        text.setText(Integer.toString(count));

        Button clicBtn = (Button) findViewById(R.id.clic);
        clicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText(Integer.toString(++count));
            }
        });

        Button launchBtn = (Button) findViewById(R.id.launch);
        launchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), MainActivity.class));
            }
        });
    }
}
