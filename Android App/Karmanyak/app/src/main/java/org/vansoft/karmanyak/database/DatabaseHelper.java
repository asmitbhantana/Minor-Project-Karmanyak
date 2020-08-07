package org.vansoft.karmanyak.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import org.vansoft.karmanyak.model.Event;
import org.vansoft.karmanyak.model.User;

import java.util.ConcurrentModificationException;

@Database(entities = {User.class, Event.class},version = 16,exportSchema = false)
public abstract class DatabaseHelper extends RoomDatabase {
    public static DatabaseHelper INSTANCE;

    public abstract UserDao userDao();
    public abstract EventDao eventDao();


    public static DatabaseHelper getINSTANCE(Context context) {
        if(INSTANCE==null){
            INSTANCE = Room.databaseBuilder(context, DatabaseHelper.class, "appDatabase")
//Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
                    // To simplify the exercise, allow queries on the main thread.
                    // Don't do this on a real app!
                    .allowMainThreadQueries()
                    // recreate the database if necessary
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
