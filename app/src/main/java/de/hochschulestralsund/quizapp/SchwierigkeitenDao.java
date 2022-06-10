package de.hochschulestralsund.quizapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SchwierigkeitenDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addSchwierigkeit(Schwierigkeiten schwierigkeit);

    @Query("select * from schwierigkeiten")
    public List<Schwierigkeiten> getAllSchwierigkeiten();

    @Delete
    void removeSchwierigkeit(Schwierigkeiten schwierigkeit);

    @Query("select * from schwierigkeiten where id = :schwierigkeitenId")
    Schwierigkeiten getSchwierigkeit(long schwierigkeitenId);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateSchwierigkeiten(Schwierigkeiten schwierigkeiten);

    @Query("delete from schwierigkeiten")
    void removeAllSchwierigkeiten();
}