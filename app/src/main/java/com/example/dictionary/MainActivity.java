package com.example.dictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickAddTheme(View view)
    {
        startActivity(new Intent(this, AddThemeActivity.class));
    }

    public void onClickAddWord(View view)
    {
        startActivity(new Intent(this, AddWordActivity.class));
    }
}
