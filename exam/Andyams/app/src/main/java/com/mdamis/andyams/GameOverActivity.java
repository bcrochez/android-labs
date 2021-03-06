package com.mdamis.andyams;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    private TextView scoreTextView;
    private Button newGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        newGameButton = (Button) findViewById(R.id.newGameButton);

        int score = getIntent().getIntExtra("score", 0);

        scoreTextView.setText("Score : " + score);

        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOverActivity.this, AndyamsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
