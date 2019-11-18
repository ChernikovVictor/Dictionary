package com.example.dictionary;

import java.io.Serializable;
import java.util.ArrayList;

public class Theme implements Serializable
{
    private String name;
    private ArrayList<Word> words;

    public Theme(){ this(""); }

    public Theme(String name)
    {
        this.name = name;
        words = new ArrayList<>();
    }

    public Word getWord(int index) { return words.get(index); }
    public void addWord(Word word)
    {
        words.add(word);
    }

    @Override
    public String toString()
    {
        return name == null ? "" : name;
    }

    /* Получить массив слов с переводами */
    public String[] wordsToStringArray()
    {
        String[] array = new String[words.size()];
        for(int i = 0; i < array.length; i++)
        {
            array[i] = words.get(i).toString();
        }
        return array;
    }

    /* Получить массив имен тем из переданного списка */
    public static String[] themesToStringArray(ArrayList<Theme> themes) {
        if (themes == null)
            return new String[0];
        String[] array = new String[themes.size()];
        for(int i = 0; i < array.length; i++)
        {
            array[i] = themes.get(i).toString();
        }
        return array;
    }
}
