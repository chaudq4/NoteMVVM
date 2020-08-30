package com.chau.duong.notemvvm;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.chau.duong.notemvvm.dao.NoteDAO;
import com.chau.duong.notemvvm.model.Note;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDataBase extends RoomDatabase {
    private static NoteDataBase instance;

    public abstract NoteDAO getNoteDAO();

    public static synchronized NoteDataBase getInsance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), NoteDataBase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private NoteDAO noteDAO;

        public PopulateDbAsyncTask(NoteDataBase noteDataBase) {
           noteDAO=noteDataBase.getNoteDAO();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDAO.insert(new Note("Title 1","Des 1",1));
            noteDAO.insert(new Note("Title 2","Des 2",2));
            noteDAO.insert(new Note("Title 3","Des 3",3));
            noteDAO.insert(new Note("Title 4","Des 4",4));


            return null;
        }
    }

}
