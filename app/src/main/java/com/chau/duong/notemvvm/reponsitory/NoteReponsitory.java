package com.chau.duong.notemvvm.reponsitory;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.chau.duong.notemvvm.NoteDataBase;
import com.chau.duong.notemvvm.dao.NoteDAO;
import com.chau.duong.notemvvm.model.Note;

import java.util.List;

public class NoteReponsitory {
    private NoteDAO noteDAO;
    private LiveData<List<Note>> allNotes;

    public NoteReponsitory(Application application) {
        NoteDataBase noteDataBase = NoteDataBase.getInsance(application);
        noteDAO = noteDataBase.getNoteDAO();
        allNotes = noteDAO.getAllByPiority();
    }

    public void insert(Note note) {
        new InsertNoteAsyncTask(noteDAO).execute(note);
    }

    public void update(Note note) {
       new UpdateNoteAsyncTask(noteDAO).execute(note);
    }

    public void delete(Note note) {
       new DeleteNoteAsyncTask(noteDAO).execute(note);
    }

    public void deleteAll() {
        new DeleteAllNoteAsyncTask(noteDAO).execute();
    }

    public LiveData<List<Note>> getAllNoteByPiority() {
        return allNotes;
    }

    public static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDAO noteDAO;

        public InsertNoteAsyncTask(NoteDAO noteDAO) {
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDAO.insert(notes[0]);
            return null;
        }

    }

    public static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDAO noteDAO;

        public UpdateNoteAsyncTask(NoteDAO noteDAO) {
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDAO.update(notes[0]);
            return null;
        }

    }

    public static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDAO noteDAO;

        public DeleteNoteAsyncTask(NoteDAO noteDAO) {
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDAO.delete(notes[0]);
            return null;
        }

    }

    public static class DeleteAllNoteAsyncTask extends AsyncTask<Void, Void, Void> {

        private NoteDAO noteDAO;

        public DeleteAllNoteAsyncTask(NoteDAO noteDAO) {
            this.noteDAO = noteDAO;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            noteDAO.deleteAllNote();
            return null;
        }
    }

}
