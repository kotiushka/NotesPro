package com.application.myapplication.data.local.room.callbacks;

import com.application.myapplication.data.model.Note;

@FunctionalInterface
public interface OnNoteReceivedCallback {

    void onReceived(Note note);

}
