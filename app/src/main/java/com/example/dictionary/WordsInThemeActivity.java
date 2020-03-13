package com.example.dictionary;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class WordsInThemeActivity extends ListActivity
{
    final ArrayList<String> words = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        /* подключиться к базе данных */
        SQLiteDatabase dbDictionary = getBaseContext().openOrCreateDatabase("Dictionary.db", MODE_PRIVATE, null);
        dbDictionary.execSQL("CREATE TABLE IF NOT EXISTS words (name TEXT, translation TEXT, themeID INTEGER);");

        /* получить тему из контекста, заполнить список словами из БД */
        String theme = getIntent().getStringExtra("theme");
        Cursor query = dbDictionary.rawQuery("SELECT id FROM themes WHERE theme = '" + theme + "';", null);
        query.moveToFirst();
        int id = query.getInt(0);
        query = dbDictionary.rawQuery("SELECT name, translation FROM words WHERE themeID = '" + id + "';", null);
        if (query.moveToFirst()) {
            do {
                words.add(query.getString(0) + " - " + query.getString(1));
            } while (query.moveToNext());
        }
        query.close();
        dbDictionary.close();

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
        String[] tmp = words.get(position).split(" - ");
        intent.putExtra(Word.class.getSimpleName(), new Word(tmp[0], tmp[1]));
        startActivity(intent);
    }
}
