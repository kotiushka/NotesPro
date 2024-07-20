package com.application.myapplication.data.local.room.repository;

import android.content.Context;

import com.application.myapplication.data.local.room.callbacks.OnNoteReceivedCallback;
import com.application.myapplication.data.local.room.dao.NoteDao;
import com.application.myapplication.data.local.room.database.AppDatabase;
import com.application.myapplication.data.model.Note;

public class DataRepositoryImpl implements DataRepository{

    private final NoteDao mNoteDao;

    public DataRepositoryImpl(Context context) {
        this.mNoteDao = AppDatabase.getInstance(context).noteDao();
    }

    @Override
    public void insert(Note note) {
        AppDatabase.databaseWriteExecutor.execute(() -> mNoteDao.insert(note));
    }

    @Override
    public void delete(Note note) {
        AppDatabase.databaseWriteExecutor.execute(() -> mNoteDao.delete(note));
    }

    @Override
    public void update(Note note) {
        AppDatabase.databaseWriteExecutor.execute(() -> mNoteDao.update(note));
    }

    @Override
    public void get(int noteId, OnNoteReceivedCallback callback) {
        AppDatabase.databaseWriteExecutor.execute(() -> callback.onReceived(mNoteDao.get(noteId)));
    }

}
