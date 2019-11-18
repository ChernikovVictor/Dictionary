package com.example.dictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class AddWordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        /* Получить названия тем из переданного списка тем */
        String[] themesNames = Theme.themesToStringArray(
                (ArrayList<Theme>) getIntent().getSerializableExtra("themes"));

        /* Заполнить данные спиннера именами тем */
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, themesNames);
        spinner.setAdapter(adapter);
    }

    /* Добавить слово */
    public void onClickAdd(View view) {
        Spinner spinner = findViewById(R.id.spinner);
        TextView wordTextView = findViewById(R.id.Word);
        TextView translationTextView = findViewById(R.id.Translation);

        Word word = new Word(wordTextView.getText().toString(),
                translationTextView.getText().toString());
        String theme = spinner.getSelectedItem().toString();

        /* Интент вернется родительской активности*/
        Intent intent = new Intent();
        intent.putExtra("theme", theme);
        intent.putExtra("word", word);

        /* Вернуть родительской активности интент с результирующим кодом */
        setResult(RESULT_OK, intent);

        /* Завершить работу активности*/
        finish();
    }
}
