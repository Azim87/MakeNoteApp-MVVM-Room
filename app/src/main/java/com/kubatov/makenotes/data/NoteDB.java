package com.kubatov.makenotes.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.kubatov.makenotes.dao.NoteDao;
import com.kubatov.makenotes.model.Note;

@Database(entities = Note.class, version = 2)

public abstract class NoteDB extends RoomDatabase {

    private final static String NOTE_DB = "note_db";

    private static NoteDB instance;

    public abstract NoteDao noteDao();


    public static synchronized NoteDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDB.class, NOTE_DB)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
