package com.application.myapplication.data.local.room.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.application.myapplication.data.local.room.dao.NoteDao;
import com.application.myapplication.data.model.Note;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Note.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase obj = null;

    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract NoteDao noteDao();

    public static AppDatabase getInstance(Context context) {
        if (obj == null) {
            synchronized (AppDatabase.class) {
                if (obj == null) {
                    obj = Room.databaseBuilder(context, AppDatabase.class, "notes_database").build();
                }
            }
        }
        return obj;
    }
}
