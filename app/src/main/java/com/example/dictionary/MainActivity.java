package com.example.dictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // получить экземпляр элемента ListView
        ListView listThemes = findViewById(R.id.listThemes);

        // заполнение списка для проверки работы активностей
        Word word1 = new Word("слово 1", "word 1");
        Word word2 = new Word("слово 2", "word 2");
        Word word3 = new Word("слово 3", "word 3");
        final Theme theme1 = new Theme("тема 1");
        final Theme theme2 = new Theme("тема 2");
        theme1.addWord(word1);
        theme1.addWord(word2);
        theme2.addWord(word1);
        theme2.addWord(word3);

        // названия тем для списка listThemes
        String[] themeNames = new String[] {theme1.toString(), theme2.toString()};

        // адаптер данных для содержимого ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, themeNames);
        listThemes.setAdapter(adapter);

        // событие: нажатие на элемента списка
        listThemes.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            // одиночный клик на элемент списка
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id)
            {
                Intent intent = new Intent(getBaseContext(), WordsInThemeActivity.class);
                intent.putExtra(Theme.class.getSimpleName(), position == 0 ? theme1 : theme2);
                startActivity(intent);
            }
        });
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
