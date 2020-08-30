package com.chau.duong.notemvvm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chau.duong.notemvvm.model.Note;

import static com.chau.duong.notemvvm.MainActivity.ACTION_CODE_ADD;
import static com.chau.duong.notemvvm.MainActivity.ACTION_CODE_EDIT;

public class AddEditNoteActivity extends AppCompatActivity {
    private EditText edtTitle;
    private EditText edtDescription;
    private NumberPicker npPiority;
    private int actionCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnote);
        initView();
    }

    private void initView() {
        edtTitle = findViewById(R.id.edtTitle);
        edtDescription = findViewById(R.id.edtDescription);
        npPiority = findViewById(R.id.npPiority);
        npPiority.setMinValue(1);
        npPiority.setMaxValue(10);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);
        actionCode = getIntent().getIntExtra("ACTION", -1);
        if (actionCode == ACTION_CODE_EDIT) {
            setTitle("Edit Note");
            Note note = (Note) getIntent().getSerializableExtra("NOTE");
            edtTitle.setText(note.getTitle());
            edtDescription.setText(note.getDescription());
            npPiority.setValue(note.getPiority());
        } else if (actionCode == ACTION_CODE_ADD)
            setTitle("Add note");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                saveNote();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveNote() {
        String title = edtTitle.getText().toString();
        String description = edtDescription.getText().toString();
        int piority = npPiority.getValue();
        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please insert information!", Toast.LENGTH_LONG).show();
            return;
        }
        if (actionCode == ACTION_CODE_EDIT) {
            Note note = (Note) getIntent().getSerializableExtra("NOTE");
            note.setDescription(description);
            note.setTitle(title);
            note.setPiority(piority);
            Intent intent = new Intent();
            intent.putExtra("NOTE", note);
            setResult(Activity.RESULT_OK, intent);
            finish();
            return;
        } else {
            Intent intent = new Intent();
            intent.putExtra("NOTE", new Note(title, description, piority));
            setResult(RESULT_OK, intent);
            finish();
        }

    }
}
