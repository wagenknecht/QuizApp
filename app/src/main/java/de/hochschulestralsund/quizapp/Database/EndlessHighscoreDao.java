package de.hochschulestralsund.quizapp.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EndlessHighscoreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addSpieler(EndlessHighscore endlessHighscore);

    @Query("select * from bestenliste")
    public List<EndlessHighscore> getAllEndlessHighscoreEntries();

    @Delete
    void removeEndlessHighscoreEntry(EndlessHighscore endlessHighscore);

    @Query("select * from endlessHighscore where id = :endlessHighscoreId")
    Bestenliste getEndlessHighscoreEntry(long endlessHighscoreId);

    @Query("select * from endlessHighscore where kategorie = :kategorie order by score desc")
    List<EndlessHighscore> getEndlessHighscoreCategoryEntry(String kategorie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateEndlessHighscore(EndlessHighscore endlessHighscore);

    @Query("delete from endlessHighscore")
    void removeAllEintraege();
}
