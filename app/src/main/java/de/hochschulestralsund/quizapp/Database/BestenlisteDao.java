package de.hochschulestralsund.quizapp.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import de.hochschulestralsund.quizapp.Database.Bestenliste;

@Dao
public interface BestenlisteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addSpieler(Bestenliste bestenliste);

    @Query("select * from bestenliste")
    public List<Bestenliste> getAllBestenlisteEintraege();

    @Delete
    void removeBestenlisteEintrag(Bestenliste bestenliste);

    @Query("select * from bestenliste where id = :bestenlisteId")
    Bestenliste getBestenlisteEintrag(long bestenlisteId);

    @Query("select * from bestenliste where kategorie = :kategorie")
    List<Bestenliste> getBestenlisteCategoryEntry(String kategorie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateBestenliste(Bestenliste bestenliste);

    @Query("delete from bestenliste")
    void removeAllEintraege();
}