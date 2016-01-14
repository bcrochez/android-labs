package com.mdamis.andyams;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class AndyamsActivity extends AppCompatActivity implements View.OnClickListener, AdapterView
        .OnItemLongClickListener {

    private TextView totalScoreTextView;
    private ArrayList<TextView> diceValues;
    private ArrayList<CheckBox> checkBoxes;
    private Button throwDicesButton;
    private ListView scorePolicyListView;
    private ArrayAdapter<Andyams.ScorePolicy> adapter;
    private ArrayList<Andyams.ScorePolicy> scorePolicies;

    private int throwCount = 0;
    private int totalScore = 0;
    private int round = 1;
    private int delay = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_andyams);

        scorePolicies = new ArrayList<>();
        scorePolicies.addAll(Arrays.asList(Andyams.getScorePolicies()));

        totalScoreTextView = (TextView) findViewById(R.id.totalScoreTextView);
        setTotalScoreTextView();

        diceValues = new ArrayList<>();
        diceValues.add((TextView) findViewById(R.id.dice1));
        diceValues.add((TextView) findViewById(R.id.dice2));
        diceValues.add((TextView) findViewById(R.id.dice3));
        diceValues.add((TextView) findViewById(R.id.dice4));
        diceValues.add((TextView) findViewById(R.id.dice5));

        for (TextView diceTextView : diceValues) {
            diceTextView.setText("X");
        }

        checkBoxes = new ArrayList<>();
        checkBoxes.add((CheckBox) findViewById(R.id.checkbox1));
        checkBoxes.add((CheckBox) findViewById(R.id.checkbox2));
        checkBoxes.add((CheckBox) findViewById(R.id.checkbox3));
        checkBoxes.add((CheckBox) findViewById(R.id.checkbox4));
        checkBoxes.add((CheckBox) findViewById(R.id.checkbox5));

        System.out.println(randomDiceValue());
        throwDicesButton = (Button) findViewById(R.id.throwDicesButton);


        scorePolicyListView = (ListView) findViewById(R.id.scorePolicyListView);
        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, scorePolicies);
        scorePolicyListView.setAdapter(adapter);
        scorePolicyListView.setVisibility(View.GONE);
        scorePolicyListView.setOnItemLongClickListener(this);

        throwDicesButton.setOnClickListener(this);

    }

    private int randomDiceValue() {
        int min = 1;
        int max = 6;
        Random random = new Random();

        return random.nextInt(max - min + 1) + min;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Andyams.ScorePolicy scorePolicy = scorePolicies.get(position);
        int[] dices = new int[5];
        for (int i = 0; i < diceValues.size(); i++) {
            dices[i] = Integer.valueOf(diceValues.get(i).getText().toString());
        }
        int score = scorePolicy.computeScore(dices);
        totalScore += score;
        setTotalScoreTextView();
        Toast.makeText(getApplicationContext(), "Score : " + score, Toast.LENGTH_LONG).show();
        scorePolicies.remove(scorePolicy);
        adapter.notifyDataSetChanged();
        if (round == 13) {
            endGame();
        }
        nextRound();
        return true;
    }

    private void setTotalScoreTextView() {
        totalScoreTextView.setText("Score : " + totalScore);
    }

    private void nextRound() {
        throwCount = 0;
        round++;

        scorePolicyListView.setVisibility(View.GONE);

        for (CheckBox checkBox : checkBoxes) {
            checkBox.setChecked(false);
        }

        for (TextView diceTextView : diceValues) {
            diceTextView.setText("X");
        }

        throwDicesButton.setOnClickListener(this);
    }

    private void endGame() {
        Intent intent = new Intent(this, GameOverActivity.class);
        intent.putExtra("score", totalScore);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        totalScore = savedInstanceState.getInt("totalScore");
        totalScoreTextView.setText(totalScore + "");
        round = savedInstanceState.getInt("round");
        throwCount = savedInstanceState.getInt("throwCount");

        for (int i = 0; i < diceValues.size(); i++) {
            diceValues.get(i).setText(savedInstanceState.getString("dice" + i));
        }

        for (int i = 0; i < checkBoxes.size(); i++) {
            checkBoxes.get(i).setChecked(savedInstanceState.getBoolean("checkbox" + i));
        }

        //Score Policy
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("totalScore", totalScore);
        outState.putInt("round", round);
        outState.putInt("throwCount", throwCount);

        for (int i = 0; i < diceValues.size(); i++) {
            outState.putString("dice" + i, diceValues.get(i).getText().toString());
        }

        for (int i = 0; i < checkBoxes.size(); i++) {
            outState.putBoolean("checkbox" + i, checkBoxes.get(i).isChecked() ? true : false);
        }

        //Score Policy
    }

    @Override
    public void onClick(View v) {
        throwDicesButton.setOnClickListener(null);

        int count = 0;
        if (throwCount == 0) {
            for (int i = 0; i < diceValues.size(); i++) {
                final TextView diceTextView = diceValues.get(i);
                diceTextView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        diceTextView.setText(randomDiceValue() + "");
                    }
                }, i * delay);
                count++;
            }
        } else {
            for (int i = 0; i < checkBoxes.size(); i++) {
                if (!checkBoxes.get(i).isChecked()) {
                    count++;
                    final TextView diceTextView = diceValues.get(i);
                    diceTextView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            diceTextView.setText(randomDiceValue() + "");
                        }
                    }, count * delay);
                }
            }
        }

        throwCount++;
        if (throwCount < 3) {
            throwDicesButton.postDelayed(new Runnable() {
                @Override
                public void run() {
                    throwDicesButton.setOnClickListener(AndyamsActivity.this);
                }
            }, count * delay);
        } else {
            scorePolicyListView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    scorePolicyListView.setVisibility(View.VISIBLE);
                }
            }, count * delay);
        }
    }
}
