package com.application.myapplication.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.myapplication.R;
import com.application.myapplication.data.model.Note;

import java.util.ArrayList;
import java.util.List;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.NotesHolder> {

    private List<Note> mNotesList;

    private final LayoutInflater mLayoutInflater;

    private final MainContract.View mView;

    public MainRecyclerAdapter(List<Note> notes, Context context, MainContract.View view) {
        this.mNotesList = notes;
        this.mLayoutInflater = LayoutInflater.from(context);

        this.mView = view;
    }

    @NonNull
    @Override
    public MainRecyclerAdapter.NotesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.note_card_holder, parent, false);
        return new NotesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainRecyclerAdapter.NotesHolder holder, int position) {
        holder.bindTo(position);
    }

    @Override
    public int getItemCount() {
        return mNotesList.size();
    }

    public class NotesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView mTitleText;
        private final TextView mInfoText;

        public NotesHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            mTitleText = itemView.findViewById(R.id.note_title);
            mInfoText = itemView.findViewById(R.id.note_info);
        }

        public void bindTo(int position) {
            Note currentNote = mNotesList.get(position);

            mTitleText.setText(currentNote.getTitle());
            mInfoText.setText(currentNote.getInfo());
        }

        @Override
        public void onClick(View view) {
            Note currentNote = mNotesList.get(getAdapterPosition());
            mView.openEditorActivity(currentNote.getId());
        }
    }

}
