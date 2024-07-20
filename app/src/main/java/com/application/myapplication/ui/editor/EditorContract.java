package com.application.myapplication.ui.editor;

import android.os.Bundle;

import com.application.myapplication.data.local.room.callbacks.OnNoteReceivedCallback;
import com.application.myapplication.data.model.Note;

public interface EditorContract {

    interface View {
        void finishActivity();

        void setIsEditing(boolean isEditing);
        void setNoteId(int noteId);

        void makeToast(int resId);

        String getTitleString();
        String getInfoString();
        int getNoteId();
        boolean getIsEditing();
    }

    interface Presenter {
        void saveNote();
        void initalizeFromExtras(Bundle extras, OnNoteReceivedCallback callback);

        void deleteNote();
    }

}
