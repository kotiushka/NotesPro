package com.application.myapplication.ui.editor;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.application.myapplication.R;
import com.application.myapplication.data.local.room.callbacks.OnNoteReceivedCallback;
import com.application.myapplication.data.local.room.repository.DataRepositoryImpl;
import com.application.myapplication.data.model.Note;

public class EditorActivity extends AppCompatActivity implements EditorContract.View, OnNoteReceivedCallback {

    private boolean mIsEditing;

    private int mNoteId;

    private EditText mTitle;
    private EditText mInfo;

    private EditorContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_holder);

        init();
    }

    private void init() {
        mPresenter = new EditorPresenter(this, new DataRepositoryImpl(this));

        mTitle = findViewById(R.id.note_title_editor);
        mInfo = findViewById(R.id.note_info_editor);

        mPresenter.initalizeFromExtras(getIntent().getExtras(), this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.editor, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.delete_note_menu) {
            mPresenter.deleteNote();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void setIsEditing(boolean isEditing) {
        mIsEditing = isEditing;
    }

    @Override
    public void setNoteId(int mNoteId) {
        this.mNoteId = mNoteId;
    }

    @Override
    public String getTitleString() {
        return mTitle.getText().toString();
    }

    @Override
    public String getInfoString() {
        return mInfo.getText().toString();
    }

    @Override
    public int getNoteId() {
        return mNoteId;
    }

    @Override
    public boolean getIsEditing() {
        return mIsEditing;
    }

    @Override
    public void makeToast(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.saveNote();
    }

    @Override
    public void onReceived(Note note) {
        mTitle.setText(note.getTitle());
        mInfo.setText(note.getInfo());
    }

}