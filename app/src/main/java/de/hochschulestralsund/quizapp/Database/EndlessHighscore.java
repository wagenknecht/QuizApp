package de.hochschulestralsund.quizapp.Database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "endlessHighscore")

public class EndlessHighscore {
    @PrimaryKey(autoGenerate = true)
    int id;
    private String name;
    private String kategorie;
    private int score;

    public EndlessHighscore(String name, String kategorie, int score)    {
        this.name = name;
        this.kategorie = kategorie;
        this.score = score;
    }

    //getter and setter for all variables

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
