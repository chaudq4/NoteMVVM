package com.chau.duong.notemvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.chau.duong.notemvvm.model.Note;
import com.chau.duong.notemvvm.reponsitory.NoteReponsitory;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private NoteReponsitory noteReponsitory;
    private LiveData<List<Note>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteReponsitory = new NoteReponsitory(application);
        allNotes = noteReponsitory.getAllNoteByPiority();
    }

    public void insert(Note note) {
        noteReponsitory.insert(note);
    }

    public void update(Note note) {
        noteReponsitory.update(note);
    }

    public void delete(Note note) {
        noteReponsitory.delete(note);
    }

    public void deleteAllNote() {
        noteReponsitory.deleteAll();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
}
