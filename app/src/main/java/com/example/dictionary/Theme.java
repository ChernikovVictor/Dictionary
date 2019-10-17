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
        return name;
    }

    public String[] wordsToStringArray()
    {
        String[] array = new String[words.size()];
        for(int i = 0; i < array.length; i++)
        {
            array[i] = words.get(i).toString();
        }
        return array;
    }
}
