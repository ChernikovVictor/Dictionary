package com.example.dictionary;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

        getListView().setBackgroundColor(Color.parseColor("#001F3F"));

        this.setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, words) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);

                textView.setTextColor(Color.parseColor("#FF851B"));
                textView.setTextSize(30);
                Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Grinched 2.0.ttf");
                textView.setTypeface(typeface);
                return view;
            }
        });
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
