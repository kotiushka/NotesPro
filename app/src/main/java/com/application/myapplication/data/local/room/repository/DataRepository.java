package com.application.myapplication.data.local.room.repository;

import com.application.myapplication.data.local.room.callbacks.OnNoteReceivedCallback;
import com.application.myapplication.data.model.Note;

public interface DataRepository {

    void insert(Note note);
    void delete(Note note);
    void update(Note note);

    void get(int noteId, OnNoteReceivedCallback callback);
}
