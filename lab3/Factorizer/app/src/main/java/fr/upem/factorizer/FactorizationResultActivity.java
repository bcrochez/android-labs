package fr.upem.factorizer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class FactorizationResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factorization_result);

        List<Long> factors = (List<Long>) getIntent().getSerializableExtra("factors");

        ListView listView = (ListView) findViewById(R.id.listView);

        ArrayAdapter<Long> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1);

        adapter.addAll(factors);
        listView.setAdapter(adapter);
    }
}
