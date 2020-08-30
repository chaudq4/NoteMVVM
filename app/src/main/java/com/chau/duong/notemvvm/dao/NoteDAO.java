package com.chau.duong.notemvvm.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.chau.duong.notemvvm.model.Note;

import java.util.List;

@Dao
public interface NoteDAO  {
    @Insert
    void insert(Note note);
    @Update
    void update(Note note);
    @Delete
    void delete(Note note);
    @Query("DELETE FROM note")
    void deleteAllNote();
    @Query("SELECT * FROM note ORDER BY piority ASC")
    LiveData<List<Note>> getAllByPiority();
}
