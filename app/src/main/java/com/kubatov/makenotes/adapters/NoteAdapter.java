package com.kubatov.makenotes.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.kubatov.makenotes.R;
import com.kubatov.makenotes.model.Note;


public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteViewHolder> {
    OnItemClickListener mListener;

    public NoteAdapter() {
        super(DIFF_CALLBACK);
    }
    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getPriority().equals(newItem.getPriority());
        }
    };

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_view, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note holderNote = getItem(position);
        holder.titleTv.setText(holderNote.getTitle());
        holder.priorityTv.setText(holderNote.getPriority());
        holder.descriptionTv.setText(holderNote.getDescription());
    }

    public Note getNotePositionAt(int position) {
        return getItem(position);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTv;
        public TextView priorityTv;
        public TextView descriptionTv;


        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.note_title);
            priorityTv = itemView.findViewById(R.id.note_priority);
            descriptionTv = itemView.findViewById(R.id.note_description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (mListener != null && position != RecyclerView.NO_POSITION) {
                        mListener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }
}
