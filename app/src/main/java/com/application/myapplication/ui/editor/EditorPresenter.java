package com.application.myapplication.ui.editor;

import android.os.Bundle;

import com.application.myapplication.R;
import com.application.myapplication.data.local.room.callbacks.OnNoteReceivedCallback;
import com.application.myapplication.data.local.room.dao.NoteDao;
import com.application.myapplication.data.local.room.database.AppDatabase;
import com.application.myapplication.data.local.room.repository.DataRepositoryImpl;
import com.application.myapplication.data.model.Note;

public class EditorPresenter implements EditorContract.Presenter {

    private final EditorContract.View mView;

    private final DataRepositoryImpl mDataRepository;

    public EditorPresenter(EditorContract.View mView, DataRepositoryImpl mDataRepository) {
        this.mView = mView;
        this.mDataRepository = mDataRepository;
    }

    @Override
    public void initalizeFromExtras(Bundle extras, OnNoteReceivedCallback callback) {
        if (extras != null) {
            int noteId = extras.getInt("note_id");
            if (noteId == 0) {
                mView.setIsEditing(false);
            } else {
                mView.setIsEditing(true);
                mView.setNoteId(noteId);

                mDataRepository.get(noteId, callback);
            }
        }
    }

    @Override
    public void deleteNote() {
        Note note = new Note(mView.getTitleString(), mView.getInfoString());
        note.setId(mView.getNoteId());

        mDataRepository.delete(note);

        if (!note.getInfo().isEmpty() && !note.getTitle().isEmpty()) {
            mView.makeToast(R.string.note_was_deleted);
        }
        mView.finishActivity();
    }


    @Override
    public void saveNote() {
        String noteTitle = mView.getTitleString();
        String noteInfo = mView.getInfoString();

        boolean isEditing = mView.getIsEditing();

        int noteId = mView.getNoteId();

        Note currentNote = new Note(noteTitle, noteInfo);
        currentNote.setId(noteId);

        if (!noteInfo.isEmpty() || !noteTitle.isEmpty()) {
            if (isEditing) {
                mDataRepository.update(currentNote);
            } else {
                mDataRepository.insert(currentNote);
            }
        } else {
            mView.makeToast(R.string.empty_note_deleted);
            if (isEditing) {
                mDataRepository.delete(currentNote);
            }
        }
    }





}
