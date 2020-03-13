package com.example.dictionary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> themeNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* получить экземпляр элемента ListView */
        ListView listThemes = findViewById(R.id.listThemes);

        /* получаем список тем из прошлого состояния или из БД */
        if (savedInstanceState != null) {
            themeNames = (ArrayList<String>) savedInstanceState.getSerializable("themes");
        } else {
            themeNames = getThemesFromDatabase();
        }

        /* адаптер данных для содержимого ListView */
        ArrayAdapter<String> adapter = createStringAdapter(themeNames);
        listThemes.setAdapter(adapter);

        /* событие: нажатие на элемента списка */
        listThemes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /* одиночный клик на элемент списка */
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                Intent intent = new Intent(getBaseContext(), WordsInThemeActivity.class);
                intent.putExtra("theme", themeNames.get(position));
                startActivity(intent);
            }
        });
    }

    /* Адаптер для элементов ListView */
    private ArrayAdapter<String> createStringAdapter(ArrayList<String> themes) {
        return new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, themes) {
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
            };
    }

    /* Получить список тем из БД */
    private ArrayList<String> getThemesFromDatabase() {
        ArrayList<String> result = new ArrayList<>();

        /* подключиться к БД*/
        SQLiteDatabase dbDictionary = getBaseContext().openOrCreateDatabase("Dictionary.db", MODE_PRIVATE, null);
        dbDictionary.execSQL("CREATE TABLE IF NOT EXISTS themes (id INTEGER PRIMARY KEY AUTOINCREMENT, theme TEXT);");

        /* получить темы из БД, заполнить список */
        Cursor query = dbDictionary.rawQuery("SELECT theme FROM themes;", null);
        if (query.moveToFirst()){
            do {
                result.add(query.getString(0));
            } while (query.moveToNext());
        }
        query.close();
        dbDictionary.close();

        return result;
    }

    /* Событие: нажали на кнопку добавления темы */
    public void onClickAddTheme(View view) {
        Intent intent = new Intent(this, AddThemeActivity.class);
        startActivity(intent);
    }

    /* Событие: нажали на кнопку добавления слова */
    public void onClickAddWord(View view) {
        Intent intent = new Intent(this, AddWordActivity.class);
        startActivity(intent);
    }

    /* Метод вызывается при возобновлении работы активности после метода onPause(),
     * а также после метода onStart() в других случаях */
    @Override
    protected void onResume() {
        super.onResume();

        /* Обновим данные в отображаемом списке */
        ListView listThemes = findViewById(R.id.listThemes);
        themeNames = getThemesFromDatabase();

        /* адаптер данных для содержимого ListView */
        ArrayAdapter<String> adapter = createStringAdapter(themeNames);
        listThemes.setAdapter(adapter);
    }

    /* Сохранить состояние при прерывании работы активности */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        /* Сохраним данные списка */
        outState.putSerializable("themes", themeNames);
        super.onSaveInstanceState(outState);
    }
}
