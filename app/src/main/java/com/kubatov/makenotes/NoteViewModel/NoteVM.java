package com.kubatov.makenotes.NoteViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearSmoothScroller;

import com.kubatov.makenotes.model.Note;
import com.kubatov.makenotes.repository.NoteRepository;

import java.util.List;


public class NoteVM extends AndroidViewModel {

    private NoteRepository mRepository;
    private LiveData<List<Note>> allNotes;


    public NoteVM(@NonNull Application application) {
        super(application);
        mRepository = new NoteRepository(application);
        allNotes = mRepository.getAllNotes();
    }

    public void insert(Note note){
        mRepository.insert(note);
    }

    public void update(Note note){
        mRepository.update(note);
    }

    public void delete(Note note){
        mRepository.delete(note);
    }

    public void deleteAllNotes(){
        mRepository.deleteAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
}
