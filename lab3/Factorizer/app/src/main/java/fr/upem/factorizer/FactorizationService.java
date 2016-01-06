package fr.upem.factorizer;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import java.io.Serializable;
import java.util.List;

public class FactorizationService extends Service {

    private FactorizeAsyncTask task;
    private int nextNotificationID = 1;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final long number = intent.getExtras().getLong("number");

        task = new FactorizeAsyncTask() {
            private final int notificationID = nextNotificationID++;
            private long lastNotificationUpdate = -1;

            @Override
            protected void onProgressUpdate(Long... values) {
                if (System.nanoTime() - lastNotificationUpdate < 500000000) {
                    return;
                }

                NotificationCompat.Builder builder = new NotificationCompat.Builder(FactorizationService.this)
                        .setSmallIcon(android.R.drawable.ic_notification_overlay)
                        .setContentTitle("Factorization")
                        .setContentText(String.valueOf(number) + " : factorization in progress");

                int max = (int) values[0].longValue();
                int progress = (int) values[1].longValue();
                builder.setProgress(max, progress, false);

                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                notificationManager.notify(notificationID, builder.build());
                lastNotificationUpdate = System.nanoTime();
            }

            @Override
            protected void onPostExecute(List<Long> factors) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(FactorizationService.this)
                        .setSmallIcon(android.R.drawable.ic_notification_overlay)
                        .setContentTitle("Factorization")
                        .setContentText(String.valueOf(number) + " : factorization complete");

                Intent resultIntent = new Intent(FactorizationService.this, FactorizationResultActivity.class);
                resultIntent.putExtra("factors", (Serializable) factors);
                PendingIntent pendingResultIntent = PendingIntent.getActivity(FactorizationService.this, 0,
                        resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                builder.setContentIntent(pendingResultIntent);
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                notificationManager.notify(notificationID, builder.build());
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
