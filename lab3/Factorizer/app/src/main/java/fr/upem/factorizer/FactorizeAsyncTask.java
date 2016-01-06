package fr.upem.factorizer;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

public class FactorizeAsyncTask extends AsyncTask<Long, Long, List<Long>> {

    public FactorizeAsyncTask() {
    }

    @Override
    protected List<Long> doInBackground(Long... params) {
        long number = params[0];
        long limit = (long) Math.sqrt(number) + 1;
        ArrayList<Long> factors = new ArrayList<>();
        for (long i = 2; i < limit; i++) {
            if (isCancelled()) {
                return null;
            }
            while (number % i == 0) {
                factors.add(i);
                number /= i;
            }
        }
        if (number > 1) {
            factors.add(number);
        }
        return factors;
    }
}