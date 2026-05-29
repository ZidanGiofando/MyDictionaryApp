package com.example.myapplication11;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class WordListActivity extends AppCompatActivity {

    private RecyclerView recyclerWords;
    private WordAdapter wordAdapter;
    private final List<Word> words = new ArrayList<>();
    private final List<Word> filteredWords = new ArrayList<>();
    private DictionaryDbHelper dbHelper;
    private TextView tvEmpty;
    private EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);

        dbHelper = new DictionaryDbHelper(this);
        recyclerWords = findViewById(R.id.recyclerWords);
        tvEmpty = findViewById(R.id.tvEmpty);
        etSearch = findViewById(R.id.etSearch);
        Button btnBack = findViewById(R.id.btnBack);

        recyclerWords.setLayoutManager(new LinearLayoutManager(this));
        wordAdapter = new WordAdapter(this, filteredWords);
        recyclerWords.setAdapter(wordAdapter);

        btnBack.setOnClickListener(v -> finish());

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterWords(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        loadWords();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadWords();
    }

    private void loadWords() {
        List<Word> list = dbHelper.listAll();
        words.clear();
        filteredWords.clear();
        if (list != null) {
            words.addAll(list);
            filteredWords.addAll(list);
        }
        wordAdapter.notifyDataSetChanged();
        updateEmptyView();
    }

    private void filterWords(String keyword) {
        filteredWords.clear();
        for (Word w : words) {
            if (w.english.toLowerCase().contains(keyword.toLowerCase())
                    || w.indonesia.toLowerCase().contains(keyword.toLowerCase())) {
                filteredWords.add(w);
            }
        }
        wordAdapter.notifyDataSetChanged();
        updateEmptyView();
    }

    private void updateEmptyView() {
        if (filteredWords.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            tvEmpty.setVisibility(View.GONE);
        }
    }

    public void deleteWord(int id) {
        new AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Delete this word?")
                .setPositiveButton("YES", (dialog, which) -> {
                    dbHelper.deleteById(id);
                    loadWords();
                })
                .setNegativeButton("NO", null)
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}
