package com.maxscrub.bw.seepr;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Objects;
import javax.net.ssl.HttpsURLConnection;
import timber.log.Timber;


public class DiffActivity extends AppCompatActivity {
    TextView diff;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diff);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);

        diff = findViewById(R.id.diffText);
        String diffUrl = Objects.requireNonNull(getIntent().getExtras()).getString("diffUrl");
        new getData(diffUrl).execute();
    }

    @SuppressLint("StaticFieldLeak")
    class getData extends AsyncTask<String, String, String> {

        private String diffUrl;
        HttpsURLConnection urlConnection = null;

        getData(String diffUrl) {
            this.diffUrl = diffUrl;
        }

        protected String doInBackground(String... args) {
            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL(diffUrl);
                urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoOutput(true);
                urlConnection.connect();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
                char[] buffer = new char[2048];

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line).append("\n");
                }
                bufferedReader.close();
                String jsonString = result.toString();

                if (BuildConfig.DEBUG) {
                    Timber.d("DiffActivity JSONstring - %s", jsonString);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }

            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            diff.setText(result);
        }
    }
}
