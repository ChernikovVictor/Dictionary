package com.example.dictionary;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class WordsInThemeActivity extends ListActivity
{
    /* выбранная тема */
    Theme theme;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        /* получить тему из контекста, заполнить список словами */
        theme = (Theme) getIntent().getSerializableExtra(Theme.class.getSimpleName());
        String[] words = theme.wordsToStringArray();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, words);
        setListAdapter(adapter);
    }

    // событие: клик по элементу списка (по слову)
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        Intent intent = new Intent(getBaseContext(), WordInfoActivity.class);
        intent.putExtra(Word.class.getSimpleName(), theme.getWord(position));
        startActivity(intent);
    }
}
