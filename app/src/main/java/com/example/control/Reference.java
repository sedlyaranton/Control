package com.example.control;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

public class Reference extends AppCompatActivity {

    private TextView textRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reference);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textRef = (TextView) findViewById(R.id.text_ref);
        textRef.setMovementMethod(new ScrollingMovementMethod());
        textRef.setText(R.string.text_ref);

    }

}
