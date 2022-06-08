package de.hochschulestralsund.quizapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bestenlsite")
public class Bestenliste {
    @PrimaryKey(autoGenerate = true)
    int id;
    private String name;
    private String kategorie;
    private int score;

    public Bestenliste(String name, String kategorie, int score)    {
        this.name = name;
        this.kategorie = kategorie;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public String getKategorie()    {
        return kategorie;
    }

    public int getScore()   {
        return score;
    }

    public void setName(String name)    {
        this.name = name;
    }

    public void setKategorie(String kategorie)  {
        this.kategorie = kategorie;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
