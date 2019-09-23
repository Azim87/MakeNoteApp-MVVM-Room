package com.kubatov.makenotes.asyncTaskNote;

import android.os.AsyncTask;

import com.kubatov.makenotes.dao.NoteDao;
import com.kubatov.makenotes.model.Note;

public class DeleteAllAsyncTask extends AsyncTask<Note,Void, Void> {

    private NoteDao mNoteDao;

    public DeleteAllAsyncTask(NoteDao noteDao){
        mNoteDao = noteDao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        mNoteDao.deleteAll();
        return null;
    }
}
