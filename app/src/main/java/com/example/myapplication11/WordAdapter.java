package com.example.myapplication11;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder> {

    private List<Word> words;

    private Context context;

    // CONSTRUCTOR
    public WordAdapter(Context context,
                       List<Word> words) {

        this.context = context;
        this.words = words;
    }

    // VIEWHOLDER
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvEnglish, tvIndonesia;

        ImageButton btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            tvEnglish = itemView.findViewById(R.id.tvEnglish);

            tvIndonesia = itemView.findViewById(R.id.tvIndonesia);

            btnEdit = itemView.findViewById(R.id.btnEdit);

            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                         int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_word, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,
                                 int position) {

        Word word = words.get(position);

        // TAMPILKAN DATA
        holder.tvEnglish.setText(word.english);

        holder.tvIndonesia.setText(
                "Indonesia : " + word.indonesia
        );

        // EDIT BUTTON
        holder.btnEdit.setOnClickListener(v -> {

            Intent intent = new Intent(context, EditWordActivity.class);

            intent.putExtra("id", word.id);

            intent.putExtra("english", word.english);

            intent.putExtra("indonesia", word.indonesia);

            context.startActivity(intent);
        });

        // DELETE BUTTON
        holder.btnDelete.setOnClickListener(v -> {

            if (context instanceof WordListActivity) {

                ((WordListActivity) context).deleteWord(word.id);
            }
        });
    }

    @Override
    public int getItemCount() {

        return words.size();
    }
}