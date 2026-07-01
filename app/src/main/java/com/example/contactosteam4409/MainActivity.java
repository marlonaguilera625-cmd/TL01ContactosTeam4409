package com.example.contactosteam4409;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.contactosteam4409.views.RegistroActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(MainActivity.this, RegistroActivity.class);
        startActivity(intent);
        finish();
    }
}