package de.hochschulestralsund.quizapp.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Bestenliste.class}, version = 16, exportSchema = false)
public abstract class AppDatabase  extends RoomDatabase{

    private static AppDatabase INSTANCE;

    public abstract BestenlisteDao bestenlisteDao();

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context, AppDatabase.class, "appdatabase")

                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}

