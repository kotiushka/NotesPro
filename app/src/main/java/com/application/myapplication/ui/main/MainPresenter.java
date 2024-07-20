package com.application.myapplication.ui.main;

import android.util.Log;

import com.application.myapplication.data.local.room.dao.NoteDao;
import com.application.myapplication.data.local.room.database.AppDatabase;
import com.application.myapplication.data.model.Note;

import java.util.List;

public class MainPresenter implements MainContract.Presenter {

    private final MainContract.View mView;


    public MainPresenter(MainContract.View view) {
        this.mView = view;
    }

    @Override
    public void onSettingsOptionSelected() {
        mView.openSettingsActivity();
    }

    @Override
    public void onContactOptionSelected(String page) {
        mView.openBrowserPage(page);
    }

    @Override
    public void onFabTapped() {
        mView.openEditorActivity(0);
    }

    public int getColumnIntegerCount(boolean enabled) {
        return enabled ? 2 : 1;
    }


}
