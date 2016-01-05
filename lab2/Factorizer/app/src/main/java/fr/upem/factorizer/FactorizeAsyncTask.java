package fr.upem.factorizer;

import android.os.AsyncTask;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class FactorizeAsyncTask extends AsyncTask<Long, Long, List<Long>> {

    private final FactorizeActivity activity;

    public FactorizeAsyncTask(FactorizeActivity activity) {
        this.activity = activity;
    }

    @Override
    protected List<Long> doInBackground(Long... params) {
        long number = params[0];
        long limit = (long) Math.sqrt(number) + 1;
        activity.setMaxProgress((int) limit);
        ArrayList<Long> factors = new ArrayList<>();
        for (long i = 2; i < limit; i++) {
            if (isCancelled()) {
                return null;
            }
            while (number % i == 0) {
                factors.add(i);
                number /= i;
            }
            publishProgress(i);
        }
        if (number > 1) {
            factors.add(number);
        }
        return factors;
    }

    @Override
    protected void onProgressUpdate(Long... values) {
        super.onProgressUpdate(values);
        int progress = (int) values[0].longValue();
        activity.updateProgressBar(progress);
    }

    @Override
    protected void onPostExecute(List<Long> factors) {
        super.onPostExecute(factors);

        activity.getProgressBar().setVisibility(View.GONE);
        activity.changeDataSet(factors);
    }
}
