package com.drdisagree.iconify;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Extras extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.extras);

        // Header
        TextView header = findViewById(R.id.header);
        header.setText("Extras");
    }
}