package com.example.hadirly;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private List<HomeModel> mahasiswaList;

    public HomeAdapter(List<HomeModel> mahasiswaList) {
        this.mahasiswaList = mahasiswaList != null ? mahasiswaList : new ArrayList<>(); // Avoid null list
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hadir, parent, false);
        return new HomeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        HomeModel hadir = mahasiswaList.get(position);
        if (hadir != null) { // Check for null object
            holder.matkulView.setText(hadir.getMatkul());
            holder.dateView.setText(hadir.getDate());
        }
    }

    @Override
    public int getItemCount() {
        return mahasiswaList != null ? mahasiswaList.size() : 0; // Handle case where mahasiswaList is null
    }

    public void updateList(List<HomeModel> newList) {
        mahasiswaList = newList != null ? newList : new ArrayList<>(); // Avoid null
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView dateView, matkulView;

        public ViewHolder(View itemView) {
            super(itemView);
            dateView = itemView.findViewById(R.id.date);
            matkulView = itemView.findViewById(R.id.matkul);
        }
    }
}
