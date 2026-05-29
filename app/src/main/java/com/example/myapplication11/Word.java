package com.example.myapplication11;

public class Word {
    public int id;
    public String english;
    public String indonesia;

    public Word(int id, String english, String indonesia) {
        this.id = id;
        this.english = english;
        this.indonesia = indonesia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getIndonesia() {
        return indonesia;
    }

    public void setIndonesia(String indonesia) {
        this.indonesia = indonesia;
    }
}
