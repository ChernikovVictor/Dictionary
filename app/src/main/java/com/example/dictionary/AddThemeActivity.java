package com.example.dictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
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

        /* подключиться к БД */
        SQLiteDatabase dbDictionary = getBaseContext().openOrCreateDatabase("Dictionary.db", MODE_PRIVATE, null);
        dbDictionary.execSQL("CREATE TABLE IF NOT EXISTS themes (id INTEGER PRIMARY KEY AUTOINCREMENT, theme TEXT);");

        /* Добавить тему в БД */
        EditText editText = findViewById(R.id.Theme);
        String theme = editText.getText().toString();
        try {
            dbDictionary.execSQL("INSERT INTO themes (theme) VALUES ('" + theme + "');");
        } catch (SQLException ignored) { }

        dbDictionary.close();
        finish();   // Завершить работу активности
    }
}
