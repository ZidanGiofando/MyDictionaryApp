package com.example.myapplication11;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class EditWordActivity extends AppCompatActivity {

    EditText etEnglish, etIndonesia;
    Button btnUpdate;
    DictionaryDbHelper dbHelper;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_word);

        etEnglish = findViewById(R.id.etEnglish);
        etIndonesia = findViewById(R.id.etIndonesia);
        btnUpdate = findViewById(R.id.btnUpdate);
        dbHelper = new DictionaryDbHelper(this);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        etEnglish.setText(intent.getStringExtra("english"));
        etIndonesia.setText(intent.getStringExtra("indonesia"));

        btnUpdate.setOnClickListener(v -> {
            dbHelper.updateWord(
                    id,
                    etEnglish.getText().toString(),
                    etIndonesia.getText().toString()
            );
            finish();
        });
    }


}
