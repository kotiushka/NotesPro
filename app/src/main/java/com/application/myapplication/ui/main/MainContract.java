package com.application.myapplication.ui.main;


import com.application.myapplication.data.model.Note;

import java.util.List;

public interface MainContract {

    interface View {
        void openSettingsActivity();
        void openEditorActivity(int noteId);
        void openBrowserPage(String page);
        List<Note> getNotesList();
        void notifyItemRemoved(int from, int to);
    }

    interface Presenter {
        void onSettingsOptionSelected();
        void onContactOptionSelected(String page);
        void onFabTapped();
        int getColumnIntegerCount(boolean enabled);
    }

}
