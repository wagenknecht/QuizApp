package de.hochschulestralsund.quizapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;




@Entity(tableName = "schwierigkeiten")
public class Schwierigkeiten {
    @PrimaryKey(autoGenerate = true)
    int id;
    private String name;

    public Schwierigkeiten(String name)    {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name)    {
        this.name = name;
    }
}



