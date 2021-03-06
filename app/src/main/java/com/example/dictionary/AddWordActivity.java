package com.example.dictionary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class AddWordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        /* Получить названия тем из БД */
        ArrayList<String> themesNames = new ArrayList<>();
        SQLiteDatabase dbDictionary = getBaseContext().openOrCreateDatabase("Dictionary.db", MODE_PRIVATE, null);
        dbDictionary.execSQL("CREATE TABLE IF NOT EXISTS themes (id INTEGER PRIMARY KEY AUTOINCREMENT, theme TEXT);");

        Cursor query = dbDictionary.rawQuery("SELECT theme FROM themes;", null);
        if (query.moveToFirst()){
            do {
                themesNames.add(query.getString(0));
            } while (query.moveToNext());
        }
        query.close();
        dbDictionary.close();

        /* Заполнить данные спиннера именами тем */
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, themesNames) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                textView.setTextColor(Color.parseColor("#FF851B"));
                textView.setTextSize(18);
                return view;
            }
        };
        spinner.setAdapter(adapter);
    }

    /* Добавить слово */
    public void onClickAdd(View view) {
        Spinner spinner = findViewById(R.id.spinner);
        TextView wordTextView = findViewById(R.id.Word);
        TextView translationTextView = findViewById(R.id.Translation);

        /* определить ID выбранной темы */
        SQLiteDatabase dbWords = getBaseContext().openOrCreateDatabase("Dictionary.db", MODE_PRIVATE, null);
        Cursor query = dbWords.rawQuery("SELECT id FROM themes WHERE theme = '"
                + spinner.getSelectedItem().toString() + "';", null);
        query.moveToFirst();
        int id = query.getInt(0);
        query.close();

        /* добавить слово в БД */
        dbWords.execSQL("CREATE TABLE IF NOT EXISTS words (name TEXT, translation TEXT, theme TEXT);");
        dbWords.execSQL(String.format("INSERT INTO words VALUES ('%s', '%s', '%s');",
                wordTextView.getText().toString(), translationTextView.getText().toString(), id));
        dbWords.close();

        finish();   // Завершить работу активности
    }
}
