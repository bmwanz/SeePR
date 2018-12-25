package com.maxscrub.bw.seepr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class DiffActivity extends AppCompatActivity {
    TextView diff;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diff);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        diff = findViewById(R.id.diffText);

        String diffUrl = getIntent().getExtras().getString("diffUrl");
    }
}
