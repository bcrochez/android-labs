package fr.upem.factorizer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    long number = Long.parseLong(editText.getText().toString());
                    Intent intent = new Intent(MainActivity.this, FactorizationService.class);
                    intent.putExtra("number", number);
                    startService(intent);
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Enter a valid number", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
