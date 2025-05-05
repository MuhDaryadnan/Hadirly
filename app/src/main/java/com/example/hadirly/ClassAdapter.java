package com.example.hadirly;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.List;

public class ClassAdapter extends FirebaseRecyclerAdapter<ClassData, ClassAdapter.ClassViewHolder> {

    public ClassAdapter(@NonNull FirebaseRecyclerOptions<ClassData> options) {
        super(options);
    }

    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_class, parent, false);
        return new ClassViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ClassViewHolder holder, int position, @NonNull ClassData model) {

        Log.d("FirebaseData", "Binding data: " + model.getNama() + model.getDosen() + model.getNama() + model.getTahun());
        holder.dosenTextView.setText(model.getDosen());
        holder.matkulTextView.setText(model.getNama());
        holder.infoTextView.setText(model.getKelas() + " Semester " + model.getSemester() + " - " + model.getTahun());
    }

    // ViewHolder untuk RecyclerView item
    public class ClassViewHolder extends RecyclerView.ViewHolder {
        TextView dosenTextView, matkulTextView, infoTextView;
        ImageView imageView;
        Button button;

        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            dosenTextView = itemView.findViewById(R.id.dosen);
            matkulTextView = itemView.findViewById(R.id.nama);
            infoTextView = itemView.findViewById(R.id.info);// Sesuaikan dengan ID di XML
            button = itemView.findViewById(R.id.button);
        }
    }
}
