package com.example.myapplication11; // Sesuaikan dengan nama package proyek Anda

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class AddDataActivity extends AppCompatActivity {
    private EditText etEnglish;
    private EditText etIndonesia;
    private TextView tvAddMessage;
    private DictionaryDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_data);

        // Inisialisasi komponen UI dari layout
        etEnglish = findViewById(R.id.etAddEnglish);
        etIndonesia = findViewById(R.id.etAddIndonesia);
        tvAddMessage = findViewById(R.id.tvAddMessage);

        // Inisialisasi database helper
        dbHelper = new DictionaryDbHelper(this);

        Button btnSave = findViewById(R.id.btnSave);
        Button btnBack = findViewById(R.id.btnBack);

        // Logika klik tombol simpan dan kembali menggunakan Lambda Expression
        btnSave.setOnClickListener(v -> saveData());
        btnBack.setOnClickListener(v -> finish());
    }

    // Fungsi untuk memproses penyimpanan data
    private void saveData() {

        // Bersihkan pesan sebelumnya
        if (tvAddMessage != null) {
            tvAddMessage.setVisibility(View.GONE);
        }

        String english = "";
        String indonesia = "";

        if (etEnglish != null && etEnglish.getText() != null) {
            english = etEnglish.getText().toString().trim();
        }

        if (etIndonesia != null && etIndonesia.getText() != null) {
            indonesia = etIndonesia.getText().toString().trim();
        }

        // =========================
        // VALIDASI INPUT KOSONG
        // =========================

        if (english.isEmpty()) {
            etEnglish.setError("English field is required");
            etEnglish.requestFocus();
            return;
        }

        if (indonesia.isEmpty()) {
            etIndonesia.setError("Indonesia field is required");
            etIndonesia.requestFocus();
            return;
        }

        long id = -1;

        if (dbHelper != null) {
            id = dbHelper.addEntry(english, indonesia);
        }

        // =========================
        // CEK HASIL SIMPAN
        // =========================

        if (id == -1) {

            if (tvAddMessage != null) {
                tvAddMessage.setText("Failed to save. Maybe word already exists.");
                tvAddMessage.setVisibility(View.VISIBLE);
            }

        } else {

            if (tvAddMessage != null) {
                tvAddMessage.setText("Saved");
                tvAddMessage.setVisibility(View.VISIBLE);

                // Delay agar pesan terlihat
                tvAddMessage.postDelayed(() -> finish(), 800);
            } else {
                finish();
            }
        }
    }
}