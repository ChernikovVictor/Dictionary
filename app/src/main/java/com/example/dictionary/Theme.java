package com.example.dictionary;

import java.util.ArrayList;

public class Theme
{
    private String name;
    private ArrayList<Word> words;

    public Theme(){}

    public Theme(String name)
    {
        this.name = name;
    }

    public void addWord(Word word)
    {
        words.add(word);
    }

    @Override
    public String toString()
    {
        return name;
    }
}
