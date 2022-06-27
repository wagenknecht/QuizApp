package de.hochschulestralsund.quizapp.Database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bestenliste")

public class Bestenliste {
    @PrimaryKey(autoGenerate = true)
    int id;
    private String name;
    private String kategorie;
    private String schwierigkeit;
    private int score;

    public Bestenliste(String name, String kategorie, String schwierigkeit, int score)    {
        this.name = name;
        this.kategorie = kategorie;
        this.schwierigkeit = schwierigkeit;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public String getKategorie()    {
        return kategorie;
    }

    public String getSchwierigkeit() { return schwierigkeit;}

    public int getScore()   {
        return score;
    }

    public void setName(String name)    {
        this.name = name;
    }

    public void setKategorie(String kategorie)  {
        this.kategorie = kategorie;
    }

    public void setSchwierigkeitId(String schwierigkeit) {this.schwierigkeit = schwierigkeit; }

    public void setScore (int score) {
        this.score = score;
    }
}
