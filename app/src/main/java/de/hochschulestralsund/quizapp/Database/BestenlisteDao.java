package de.hochschulestralsund.quizapp.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BestenlisteDao {

    //add an entry to db
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addSpieler(Bestenliste bestenliste);

    //query which put all entries in one list
    @Query("select * from bestenliste")
    public List<Bestenliste> getAllBestenlisteEintraege();

    //remvove one entry from db
    @Delete
    void removeBestenlisteEintrag(Bestenliste bestenliste);

    //query which put the entries from the database into a list according to Id
    @Query("select * from bestenliste where id = :bestenlisteId")
    Bestenliste getBestenlisteEintrag(long bestenlisteId);

    //query which put the entries from the database into a list according to category and difficulty, sorted by score
    @Query("select * from bestenliste where kategorie = :kategorie and schwierigkeit = :difficulty order by score desc")
    List<Bestenliste> getBestenlisteCategoryDifficultyEntry(String kategorie, String difficulty);

    // update the db
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateBestenliste(Bestenliste bestenliste);

    // delete all entries from db
    @Query("delete from bestenliste")
    void removeAllEintraege();
}
