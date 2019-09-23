package com.kubatov.makenotes.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kubatov.makenotes.NoteViewModel.NoteVM;
import com.kubatov.makenotes.R;
import com.kubatov.makenotes.activities.AddNoteActivity;
import com.kubatov.makenotes.adapters.NoteAdapter;
import com.kubatov.makenotes.model.Note;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int NOTE_REQUEST_CODE = 1;


    NoteAdapter noteAdapter;
    RecyclerView recyclerView;
    FloatingActionButton actionButton;

    private NoteVM mNoteVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.note_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        noteAdapter = new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);

        actionButton = findViewById(R.id.add_note_fab);
        actionButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
            startActivityForResult(intent, NOTE_REQUEST_CODE);
        });

        mNoteVM = ViewModelProviders.of(MainActivity.this).get(NoteVM.class);
        mNoteVM.getAllNotes().observe(this, (List<Note> notes) -> {
        noteAdapter.setNotes(notes);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT |
                         ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mNoteVM.delete(noteAdapter.getNotePositionAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note deleted ", Toast.LENGTH_SHORT).show();

                if (viewHolder.getAdapterPosition() == 0){
                    Toast.makeText(MainActivity.this, "note is empty", Toast.LENGTH_SHORT).show();
                }
            }
        }).attachToRecyclerView(recyclerView);

        });
        hideFabOnScroll();
    }

    private void hideFabOnScroll(){
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
                }
                else if (dy < 0 && actionButton.getVisibility() != View.VISIBLE) {
                    actionButton.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    @Nullable
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NOTE_REQUEST_CODE || resultCode == RESULT_OK) {

            String title = data.getStringExtra(AddNoteActivity.NOTE_TITLE);
            String description = data.getStringExtra(AddNoteActivity.NOTE_DESCRIPTION);
            String priority = data.getStringExtra(AddNoteActivity.NOTE_PRIORITY);

            Note note = new Note(title, description, priority);
            mNoteVM.insert(note);

            Toast.makeText(this, "Note saved!", Toast.LENGTH_SHORT).show();

        }if (resultCode == RESULT_CANCELED){
            Toast.makeText(this, "Note not saved!", Toast.LENGTH_SHORT).show();
        }
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
            mNoteVM.deleteAllNotes();
            Toast.makeText(this, "notes are deleted", Toast.LENGTH_SHORT).show();
            return;
    }
}
