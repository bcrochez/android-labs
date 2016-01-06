package fr.upem.factorizer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import java.util.List;

public class FactorizationService extends Service {

    private FactorizeAsyncTask task;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        long number = intent.getExtras().getLong("number");

        task = new FactorizeAsyncTask() {
            @Override
            protected void onPostExecute(List<Long> factors) {
                StringBuilder sb = new StringBuilder();

                for (long factor : factors) {
                    sb.append(factor).append(" ");
                }
                Toast.makeText(getApplicationContext(), sb.toString(), Toast.LENGTH_LONG).show();
            }
        };
        task.execute(number);

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
