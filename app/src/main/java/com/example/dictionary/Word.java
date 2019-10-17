package com.example.dictionary;

import java.io.Serializable;

public class Word implements Serializable
{
    private String name;
    private String translation;

    public Word(){ this("", ""); }

    public Word(String name, String translation)
    {
        this.name = name;
        this.translation = translation;
    }

    public String getName(){ return name; }
    public String getTranslation(){ return translation; }

    @Override
    public String toString()
    {
        return name + " - " + translation;
    }
}
