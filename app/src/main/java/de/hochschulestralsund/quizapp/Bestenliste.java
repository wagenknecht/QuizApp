package de.hochschulestralsund.quizapp;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "bestenliste",
        foreignKeys = {
                @ForeignKey(
                        entity = Schwierigkeiten.class,
                        parentColumns = "id",
                        childColumns = "schwierigkeitId",
                        onDelete = ForeignKey.CASCADE
                )},
        indices = { @Index(value = "id")}
)

public class Bestenliste {
    @PrimaryKey(autoGenerate = true)
    int id;
    private String name;
    private String kategorie;
    private int schwierigkeitId;
    private int score;

    public Bestenliste(String name, String kategorie, int schwierigkeitId, int score)    {
        this.name = name;
        this.kategorie = kategorie;
        this.schwierigkeitId = schwierigkeitId;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public String getKategorie()    {
        return kategorie;
    }

    public int getSchwierigkeitId() { return schwierigkeitId;}

    public int getScore()   {
        return score;
    }

    public void setName(String name)    {
        this.name = name;
    }

    public void setKategorie(String kategorie)  {
        this.kategorie = kategorie;
    }

    public void setSchwierigkeitId(int schwierigkeitId) {this.schwierigkeitId = schwierigkeitId; }

    public void setScore(int score) {
        this.score = score;
    }
}
