package com.application.myapplication.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.application.myapplication.R;
import com.application.myapplication.data.local.room.database.AppDatabase;
import com.application.myapplication.data.model.Note;
import com.application.myapplication.ui.editor.EditorActivity;
import com.application.myapplication.ui.settings.SettingsActivity;
import com.application.myapplication.ui.utils.SwapItemTouchHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private MainContract.Presenter mPresenter;

    private FloatingActionButton mFab;

    private RecyclerView mNotesRecycler;

    private List<Note> mNotesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        setListeners();

    }

    private void init() {
        mPresenter = new MainPresenter(this);

        mFab = findViewById(R.id.add_note_fab);
        mNotesRecycler = findViewById(R.id.notes_recycler);
    }

    private void setListeners() {
        mFab.setOnClickListener(l -> mPresenter.onFabTapped());

        ItemTouchHelper helper = new ItemTouchHelper(new SwapItemTouchHelper(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT |
                ItemTouchHelper.DOWN | ItemTouchHelper.UP, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this));

        helper.attachToRecyclerView(mNotesRecycler);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.settings_option) {
            mPresenter.onSettingsOptionSelected();
        } else if (itemId == R.id.contact_option) {
            mPresenter.onContactOptionSelected(getString(R.string.contact_url));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void openSettingsActivity() {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    @Override
    public void openEditorActivity(int noteId) {
        Intent editorIntent = new Intent(this, EditorActivity.class);
        editorIntent.putExtra("is_editing", true);
        editorIntent.putExtra("note_id", noteId);

        startActivity(editorIntent);
    }

    @Override
    public void openBrowserPage(String page) {
        Uri webpage = Uri.parse(page);
        startActivity(new Intent(Intent.ACTION_VIEW, webpage));
    }

    @Override
    public List<Note> getNotesList() {
        return mNotesList;
    }

    @Override
    public void notifyItemRemoved(int from, int to) {
        if (mNotesRecycler.getAdapter() != null) {
            mNotesRecycler.getAdapter().notifyItemMoved(from, to);
        }
    }


    public void onAllNotesReceived(List<Note> notes) {
        if (!notes.isEmpty()) {
            mNotesList = notes;

            MainRecyclerAdapter adapter = new MainRecyclerAdapter(mNotesList, this, this);

            mNotesRecycler.setAdapter(adapter);

            mNotesRecycler.setLayoutManager(new StaggeredGridLayoutManager(getColumnIntegerCount(), StaggeredGridLayoutManager.VERTICAL));

        }
    }

    private int getColumnIntegerCount() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return mPresenter.getColumnIntegerCount(sharedPreferences.getBoolean("gridColumnLayout", false));

    }

    @Override
    protected void onResume() {
        super.onResume();
        AppDatabase.getInstance(this).noteDao().getAll().observe(this, this::onAllNotesReceived);
    }
}