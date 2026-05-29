package com.example.myapplication11;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private EditText etEnglish;
    private EditText etIndonesia;
    private TextView tvMessage;
    private DictionaryDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState) ;
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Inisialisasi komponen UI dari layout
        etEnglish = findViewById(R.id.etEnglish);
        etIndonesia = findViewById(R.id.etIndonesia);
        tvMessage = findViewById(R.id.tvMessage);

        // Inisialisasi database helper
        dbHelper = new DictionaryDbHelper(this);

        dbHelper = new DictionaryDbHelper(this);

        showDatabaseData();
    }

    // Fungsi tombol Translate (onClick="translate" di XML)
    public void translate(View view) {
        // Bersihkan pesan error sebelumnya
        if (tvMessage != null) tvMessage.setVisibility(View.GONE);

        String english = "";
        if (etEnglish != null && etEnglish.getText() != null) {
            english = etEnglish.getText().toString().trim();
        }

        String indonesia = null;
        if (dbHelper != null) {
            indonesia = dbHelper.queryTranslation(english);
        }

        // Tampilkan hasil pencarian
        if (indonesia != null) {
            if (etIndonesia != null) etIndonesia.setText(indonesia);
        } else {
            if (etIndonesia != null) etIndonesia.setText("");
            if (tvMessage != null) {
                tvMessage.setText("Data not found");
                tvMessage.setVisibility(View.VISIBLE);
            }
        }
    }

    // Fungsi tombol Add Data untuk berpindah halaman (onClick="addData" di XML)
    public void addData(View view) {
        Intent intent = new Intent(this, AddDataActivity.class);
        startActivity(intent);
    }

    private void showDatabaseData() {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM dictionary", null);

        while (cursor.moveToNext()) {

            int id = cursor.getInt(0);
            String english = cursor.getString(1);
            String indonesia = cursor.getString(2);

            Log.d("SQLITE_DATA", id + " | " + english + " | " + indonesia);
        }

        cursor.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Pastikan koneksi database ditutup saat activity dihancurkan
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    public void viewAll(View view) {
        Intent intent = new Intent(this, WordListActivity.class);
        startActivity(intent);
    }

}