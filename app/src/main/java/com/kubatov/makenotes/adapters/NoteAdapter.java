package com.kubatov.makenotes.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kubatov.makenotes.R;
import com.kubatov.makenotes.model.Note;
import com.kubatov.makenotes.viewHolder.NoteViewHolder;

import java.util.ArrayList;
import java.util.List;


public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {
    private List<Note> notes = new ArrayList<>();


    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_view, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note holderNote = notes.get(position);
        holder.titleTv.setText(holderNote.getTitle());
        holder.priorityTv.setText(holderNote.getPriority());
        holder.descriptionTv.setText(holderNote.getDescription());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public Note getNotePositionAt(int position){
        return notes.get(position);
    }
}
