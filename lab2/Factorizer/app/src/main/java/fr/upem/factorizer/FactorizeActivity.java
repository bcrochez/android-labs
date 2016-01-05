package fr.upem.factorizer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

public class FactorizeActivity extends AppCompatActivity {

    private EditText numberEditText;
    private Button button;
    private ProgressBar progressBar;
    private ListView listView;
    private ArrayAdapter<Long> adapter;
    private FactorizeAsyncTask factorizeAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        listView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        numberEditText = (EditText) findViewById(R.id.numberEditText);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long number;
                try {
                    number = Long.parseLong(numberEditText.getText().toString());
                    if (factorizeAsyncTask == null || factorizeAsyncTask.isCancelled() || factorizeAsyncTask
                            .getStatus() == AsyncTask.Status.FINISHED) {
                        factorizeAsyncTask = new FactorizeAsyncTask(FactorizeActivity.this);
                        progressBar.setVisibility(View.VISIBLE);
                        factorizeAsyncTask.execute(number);
                    } else {
                        factorizeAsyncTask.cancel(true);
                        progressBar.setVisibility(View.GONE);
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Enter a valid number", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        factorizeAsyncTask.cancel(true);
    }

    public void updateProgressBar(int progress) {
        progressBar.setProgress(progress);
    }

    public void setMaxProgress(int limit) {
        System.out.println(limit);
        progressBar.setMax(limit);
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void changeDataSet(List<Long> factors) {
        adapter.clear();
        adapter.addAll(factors);
        adapter.notifyDataSetChanged();
    }
}
