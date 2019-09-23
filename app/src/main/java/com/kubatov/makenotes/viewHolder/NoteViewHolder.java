package com.kubatov.makenotes.viewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kubatov.makenotes.R;

public class NoteViewHolder extends RecyclerView.ViewHolder {

    public TextView titleTv;
    public TextView priorityTv;
    public TextView descriptionTv;


    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);
        titleTv = itemView.findViewById(R.id.note_title);
        priorityTv = itemView.findViewById(R.id.note_priority);
        descriptionTv = itemView.findViewById(R.id.note_description);
    }
}
