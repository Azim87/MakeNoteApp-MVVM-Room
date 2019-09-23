package com.kubatov.makenotes.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.kubatov.makenotes.asyncTaskNote.DeleteAllAsyncTask;
import com.kubatov.makenotes.asyncTaskNote.DeleteAsyncTask;
import com.kubatov.makenotes.asyncTaskNote.InsertAsyncTask;
import com.kubatov.makenotes.asyncTaskNote.UpdateAsyncTask;
import com.kubatov.makenotes.dao.NoteDao;
import com.kubatov.makenotes.data.NoteDB;
import com.kubatov.makenotes.model.Note;
import java.util.List;

public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application){
        NoteDB noteDB = NoteDB.getInstance(application);
        noteDao = noteDB.noteDao();
        allNotes = noteDao.getAllNotes();

        this.noteDao = noteDao;
        this.allNotes = allNotes;
    }

    public void insert(Note note){
        new InsertAsyncTask(noteDao).execute(note);
    }

    public void update(Note note){
        new UpdateAsyncTask(noteDao).execute(note);
    }

    public void delete(Note note){
        new DeleteAsyncTask(noteDao).execute(note);
    }

    public void deleteAllNotes(){
        new DeleteAllAsyncTask(noteDao).execute();
    }

    public LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }
}
