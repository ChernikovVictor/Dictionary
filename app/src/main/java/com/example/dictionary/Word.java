package com.example.dictionary;

public class Word
{
    private String name;
    private String translation;

    public Word(){}

    public Word(String name, String translation)
    {
        this.name = name;
        this.translation = translation;
    }

    @Override
    public String toString()
    {
        return name + " - " + translation;
    }
}
