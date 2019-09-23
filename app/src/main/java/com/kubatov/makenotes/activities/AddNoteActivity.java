package com.kubatov.makenotes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.kubatov.makenotes.R;
import com.kubatov.makenotes.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class AddNoteActivity extends AppCompatActivity {

    public final static String NOTE_TITLE = "package com.kubatov.makenotes.activities.NOTE_TITLE";
    public final static String NOTE_DESCRIPTION = "package com.kubatov.makenotes.activities.NOTE_DESCRIPTION";
    public final static String NOTE_PRIORITY = "package com.kubatov.makenotes.activities.NOTE_PRIORITY";

    private TextInputEditText titleInputEt;
    private TextInputEditText descriptionInputEt;
    private Spinner prioritySpinner;
    private Button saveButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note);
        setTitle("Add Note");

        initViews();
        saveNotes();

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left_white);
        setTitle("Make note ");
    }

    private void initViews() {
        titleInputEt = findViewById(R.id.edit_title);
        descriptionInputEt = findViewById(R.id.edit_desc);
        prioritySpinner = findViewById(R.id.priority_spinner);
        saveButton = findViewById(R.id.save_note_button);

        List<String> priority = new ArrayList<>();
        priority.add("Urgent");
        priority.add("Very urgent");
        priority.add("Over urgent");

        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, priority);

        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prioritySpinner.setAdapter(priorityAdapter);
    }

    private void saveNotes() {
        saveButton.setOnClickListener(v -> {
            String noteTitle = titleInputEt.getText().toString().trim();
            String noteDescription = descriptionInputEt.getText().toString().trim();
            String priority = (String) prioritySpinner.getSelectedItem();

            if (noteTitle.isEmpty() || noteDescription.isEmpty()) {
                Toast.makeText(this, "Please insert some info! ", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent noteIntent = new Intent();
            noteIntent.putExtra(NOTE_TITLE, noteTitle);
            noteIntent.putExtra(NOTE_DESCRIPTION, noteDescription);
            noteIntent.putExtra(NOTE_PRIORITY, priority);
            setResult(RESULT_OK, noteIntent);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
