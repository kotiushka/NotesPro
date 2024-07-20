package com.application.myapplication.ui.utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;


import com.application.myapplication.ui.main.MainContract;

import java.util.Collections;

public class SwapItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    private final MainContract.View mView;

    public SwapItemTouchHelper(int dragDirs, int swipeDirs, MainContract.View mView) {
        super(dragDirs, swipeDirs);
        this.mView = mView;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        int from = viewHolder.getAdapterPosition();
        int to = target.getAdapterPosition();

        Collections.swap(mView.getNotesList(), from, to);
        mView.notifyItemRemoved(from, to);
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }
}
