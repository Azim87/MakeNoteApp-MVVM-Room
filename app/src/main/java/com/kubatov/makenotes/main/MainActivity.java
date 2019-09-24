package com.kubatov.makenotes.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kubatov.makenotes.Constants;
import com.kubatov.makenotes.NoteViewModel.NoteVM;
import com.kubatov.makenotes.R;
import com.kubatov.makenotes.activities.AddEditNoteActivity;
import com.kubatov.makenotes.adapters.NoteAdapter;
import com.kubatov.makenotes.model.Note;
import com.kubatov.makenotes.util.Toaster;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import static com.kubatov.makenotes.Constants.NOTE_DESCRIPTION;
import static com.kubatov.makenotes.Constants.NOTE_ID;
import static com.kubatov.makenotes.Constants.NOTE_PRIORITY;
import static com.kubatov.makenotes.Constants.NOTE_TITLE;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.note_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.add_note_fab) FloatingActionButton actionButton;

    private NoteVM mNoteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final NoteAdapter noteAdapter = new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);

        actionButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
            startActivityForResult(intent, Constants.ADD_REQUEST_CODE);
        });

        mNoteViewModel = ViewModelProviders.of(MainActivity.this).get(NoteVM.class);
        mNoteViewModel.getAllNotes().observe(this, (List<Note> notes) -> {
            noteAdapter.submitList(notes);

            new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                    ItemTouchHelper.LEFT |
                            ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView,
                                      @NonNull RecyclerView.ViewHolder viewHolder,
                                      @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    mNoteViewModel.delete(noteAdapter.getNotePositionAt(viewHolder.getAdapterPosition()));
                    Toaster.showMessage("Note deleted ");
                }
            }).attachToRecyclerView(recyclerView);

            noteAdapter.setOnItemClickListener(note -> {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                intent.putExtra(NOTE_ID, note.getId());
                intent.putExtra(NOTE_TITLE, note.getTitle());
                intent.putExtra(NOTE_DESCRIPTION, note.getDescription());
                intent.putExtra(NOTE_PRIORITY, note.getPriority());
                startActivityForResult(intent, Constants.EDIT_REQUEST_CODE);
            });

        });
        hideFabOnScroll();
    }

    /**/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.ADD_REQUEST_CODE && resultCode == RESULT_OK) {
            String title = data.getStringExtra(NOTE_TITLE);
            String description = data.getStringExtra(NOTE_DESCRIPTION);
            String priority = data.getStringExtra(NOTE_PRIORITY);

            Note note = new Note(title, description, priority);
            mNoteViewModel.insert(note);

            Toaster.showMessage("Note is saved");

        } else if (requestCode == Constants.EDIT_REQUEST_CODE && resultCode == RESULT_OK) {
            int id = data.getIntExtra(NOTE_ID, -1);

            if (id == -1) {
                Toaster.showMessage("Note can't be updated");
                return; }

            String title = data.getStringExtra(NOTE_TITLE);
            String description = data.getStringExtra(NOTE_DESCRIPTION);
            String priority = data.getStringExtra(NOTE_PRIORITY);

            Note note = new Note(title, description, priority);
            note.setId(id);
            mNoteViewModel.update(note);

            Toaster.showMessage("Updated");

        } else {
            Toaster.showMessage("not saved");

        }
    }

    private void hideFabOnScroll() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0 && actionButton.getVisibility() == View.VISIBLE) {
                    actionButton.setVisibility(View.GONE);
                } else if (dy < 0 && actionButton.getVisibility() != View.VISIBLE) {
                    actionButton.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clear_notes:
                clearAllNotes();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void clearAllNotes() {
        mNoteViewModel.deleteAllNotes();
        Toaster.showMessage("All noted deleted");
        return;
    }
}
