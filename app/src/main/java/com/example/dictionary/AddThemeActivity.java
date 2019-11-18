package com.example.dictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddThemeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_theme);
    }

    public void onClickAdd(View view) {

        /* Интент вернется родительской активности*/
        Intent intent = new Intent();

        EditText editText = findViewById(R.id.Theme);
        Theme theme = new Theme(editText.getText().toString());
        intent.putExtra("theme", theme);

        /* Вернуть родительской активности интент с результирующим кодом */
        setResult(RESULT_OK, intent);

        /* Завершить работу активности*/
        finish();
    }
}
