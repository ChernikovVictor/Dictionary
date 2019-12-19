package com.example.dictionary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.*;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /* Константы для получения результатов вызова дочерних активностей */
    private final int REQUEST_ADD_THEME = 1;
    private final int REQUEST_ADD_WORD = 2;

    /* Имя файла для сохранения данных */
    private final String FILENAME = "THEMES.Dat";

    private ArrayList<Theme> themes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* получить экземпляр элемента ListView */
        ListView listThemes = findViewById(R.id.listThemes);

        /* получаем список тем из прошлого состояния или из файла*/
        if (savedInstanceState != null) {
            themes = (ArrayList<Theme>) savedInstanceState.getSerializable("themes");
        } else {
            themes = deserializeThemes();
        }

        /* названия тем для списка listThemes */
        String[] themeNames = Theme.themesToStringArray(themes);

        /* адаптер данных для содержимого ListView */
        ArrayAdapter<String> adapter = createStringAdapter(themeNames);
        listThemes.setAdapter(adapter);

        // событие: нажатие на элемента списка
        listThemes.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            // одиночный клик на элемент списка
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id)
            {
                Intent intent = new Intent(getBaseContext(), WordsInThemeActivity.class);
                intent.putExtra(Theme.class.getSimpleName(), themes.get(position));
                startActivity(intent);
            }
        });
    }

    /* Адаптер для элементов ListView */
    private ArrayAdapter<String> createStringAdapter(final String[] themeNames) {
        return new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, themeNames) {
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

    /* Событие: нажали на кнопку добавления темы */
    public void onClickAddTheme(View view) {
        Intent intent = new Intent(this, AddThemeActivity.class);

        /* Получить результат по завершении работы дочерней активности */
        startActivityForResult(intent, REQUEST_ADD_THEME);
    }

    /* Событие: нажали на кнопку добавления слова */
    public void onClickAddWord(View view) {
        Intent intent = new Intent(this, AddWordActivity.class);

        /* Передали список тем для выбора темы нового слова */
        intent.putExtra("themes", themes);

        /* Получить результат по завершении работы дочерней активности */
        startActivityForResult(intent, REQUEST_ADD_WORD);
    }

    /* Метод вызывается по завершении работы любой дочерней активности */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_ADD_THEME:
                    Theme theme = (Theme) data.getSerializableExtra("theme");
                    themes.add(theme);
                    break;
                case REQUEST_ADD_WORD:
                    String themeName = (String) data.getSerializableExtra("theme");
                    Word word = (Word) data.getSerializableExtra("word");
                    for (Theme t : themes) {
                        if (t.toString().equals(themeName)) {
                            t.addWord(word);
                            break;
                        }
                    }
                    break;
            }
        }
    }

    /* Метод вызывается при возобновлении работы активности после метода onPause(),
     * а также после метода onStart() в других случаях */
    @Override
    protected void onResume() {
        super.onResume();

        /* Обновим данные в отображаемом списке */
        ListView listThemes = findViewById(R.id.listThemes);

        /* названия тем для списка listThemes */
        String[] themeNames = Theme.themesToStringArray(themes);

        /* адаптер данных для содержимого ListView */
        ArrayAdapter<String> adapter = createStringAdapter(themeNames);
        listThemes.setAdapter(adapter);
    }

    /* Сохранить состояние при прерывании работы активности */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        /* Сохраним данные списка */
        outState.putSerializable("themes", themes);
        super.onSaveInstanceState(outState);
    }

    /* Метод вызывается перед уничтожением активности */
    @Override
    protected void onDestroy() {
        /* Сохраним результаты работы */
        serializeThemes();
        super.onDestroy();
    }

    /* Записать список тем в файл */
    private void serializeThemes() {
        try (ObjectOutputStream out = new ObjectOutputStream(openFileOutput(FILENAME, MODE_PRIVATE))){
            out.writeObject(themes);
            System.out.println("----------\nЗапись прошла успешно\n---------------");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("----------\nЗапись провалилась\n---------------");
        }
    }

    /* Считать список тем из файла */
    private ArrayList<Theme> deserializeThemes() {
        ArrayList<Theme> themes = new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(openFileInput(FILENAME))){
            themes = (ArrayList<Theme>) in.readObject();
            System.out.println("----------\nЧтение прошло успешно\n---------------");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("----------\nЧтение провалилось\n---------------");
        }
        return themes;
    }
}
