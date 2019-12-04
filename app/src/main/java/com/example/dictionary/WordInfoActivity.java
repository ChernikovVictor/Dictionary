package com.example.dictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class WordInfoActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    /* Конвертер текста в звук */
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_info);

        /* получить слово из контекста, заполнить поля данными из слова */
        final Word word = (Word) getIntent().getSerializableExtra(Word.class.getSimpleName());

        TextView textView = findViewById(R.id.textViewWord);
        textView.setText(word.getName() == null ? "null" : "Слово: " + word.getName());

        textView = findViewById(R.id.textViewTranslation);
        textView.setText(word.getTranslation() == null ? "null" : "Перевод: " + word.getTranslation());

        /* Создать событие для кнопки: озвучить переведенное слово */
        textToSpeech = new TextToSpeech(this, this);
        Button buttonVoice = findViewById(R.id.buttonVoice);
        buttonVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textToSpeech.speak(word.getTranslation(), TextToSpeech.QUEUE_FLUSH,
                        null, "id");
            }
        });
    }

    /* Инициализация и настройка параметров озвучивания текста */
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                System.out.println("This Language is not supported");
            }
        } else {
            System.out.println("Error: Initialization of TextToSpeech failed");
        }
    }

    @Override
    protected void onDestroy() {
        /* Освободить ресурсы TextToSpeech */
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}
