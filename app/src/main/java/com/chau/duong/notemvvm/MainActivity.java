package com.chau.duong.notemvvm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.chau.duong.notemvvm.adapter.NoteAdapter;
import com.chau.duong.notemvvm.model.Note;
import com.chau.duong.notemvvm.viewmodel.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NoteAdapter.OnNoteClickListener {
    private static final int REQUEST_CODE_ADD = 1;
    private static final int REQUEST_CODE_EDIT = 2;
    public static final int ACTION_CODE_EDIT = 1;
    public static final int ACTION_CODE_ADD = 0;
    private static final String TAG = "MainActivity";
    private NoteViewModel noteViewModel;
    private RecyclerView rvListNote;
    private NoteAdapter noteAdapter;
    private FloatingActionButton fabAddNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvListNote = findViewById(R.id.rvListNote);
        fabAddNote = findViewById(R.id.fabAdd);
        rvListNote.setLayoutManager(new LinearLayoutManager(this));
        rvListNote.setHasFixedSize(true);
        noteAdapter = new NoteAdapter();
        noteAdapter.setOnNoteClickListener(this);
        rvListNote.setAdapter(noteAdapter);
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteAdapter.setNoteList(notes);
            }
        });
        fabAddNote.setOnClickListener(this);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                noteViewModel.delete(noteAdapter.getNotePosition(position));
            }
        }).attachToRecyclerView(rvListNote);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fabAdd:
                Intent intent = new Intent(this, AddEditNoteActivity.class);
                intent.putExtra("ACTION", ACTION_CODE_ADD);
                startActivityForResult(intent, REQUEST_CODE_ADD);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD) {
            if (resultCode == Activity.RESULT_OK) {
                Note note = (Note) data.getSerializableExtra("NOTE");
                noteViewModel.insert(note);
            }
        }
        if (requestCode == REQUEST_CODE_EDIT) {
            if (resultCode == Activity.RESULT_OK) {
                Note note = (Note) data.getSerializableExtra("NOTE");
                noteViewModel.update(note);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteAll:
                noteViewModel.deleteAllNote();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(Note note) {
        Intent intent = new Intent(this, AddEditNoteActivity.class);
        intent.putExtra("NOTE", note);
        intent.putExtra("ACTION", ACTION_CODE_EDIT);
        startActivityForResult(intent, REQUEST_CODE_EDIT);
    }
}