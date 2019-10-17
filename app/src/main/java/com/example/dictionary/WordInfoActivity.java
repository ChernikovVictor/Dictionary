package com.example.dictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class WordInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_info);

        // получить слово из контекста, заполнить поля данными из слова
        Word word = (Word) getIntent().getSerializableExtra(Word.class.getSimpleName());
        TextView textView = findViewById(R.id.textViewWord);
        textView.setText(word.getName() == null ? "null" : "Слово: " + word.getName());
        textView = findViewById(R.id.textViewTranslation);
        textView.setText(word.getTranslation() == null ? "null" : "Перевод: " + word.getTranslation());
    }
}
