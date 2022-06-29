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

    //add an entry to db
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addSpieler(EndlessHighscore endlessHighscore);

    //query which put all entries in one list
    @Query("select * from bestenliste")
    public List<EndlessHighscore> getAllEndlessHighscoreEntries();

    //remvove one entry from db
    @Delete
    void removeEndlessHighscoreEntry(EndlessHighscore endlessHighscore);

    //query which put the entries from the database into a list according to Id
    @Query("select * from endlessHighscore where id = :endlessHighscoreId")
    Bestenliste getEndlessHighscoreEntry(long endlessHighscoreId);

    //query which put the entries from the database into a list according to category, sorted by score
    @Query("select * from endlessHighscore where kategorie = :kategorie order by score desc")
    List<EndlessHighscore> getEndlessHighscoreCategoryEntry(String kategorie);

    // update the db
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateEndlessHighscore(EndlessHighscore endlessHighscore);

    // delete all entries from db
    @Query("delete from endlessHighscore")
    void removeAllEintraege();
}
