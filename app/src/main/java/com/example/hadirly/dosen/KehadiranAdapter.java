package com.example.hadirly.dosen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hadirly.R;

import java.util.List;

public class KehadiranAdapter extends RecyclerView.Adapter<KehadiranAdapter.KehadiranViewHolder> {
    private List<KehadiranModel> kelasList;

    public KehadiranAdapter(List<KehadiranModel> kelasList) {
        this.kelasList = kelasList;
    }

    @NonNull
    @Override
    public KehadiranViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lihat_kehadiran, parent, false);
        return new KehadiranAdapter.KehadiranViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KehadiranAdapter.KehadiranViewHolder holder, int position) {
        KehadiranModel kelas = kelasList.get(position);
        holder.NAMA.setText(kelas.getNama());
        holder.NIM.setText(kelas.getNim());
        holder.STATUS.setText(kelas.getStatus());
    }

    @Override
    public int getItemCount() {
        return kelasList.size();
    }

    public static class KehadiranViewHolder extends RecyclerView.ViewHolder{
        TextView NAMA,NIM,STATUS;
        public KehadiranViewHolder(@NonNull View itemView) {
            super(itemView);
            NAMA = itemView.findViewById(R.id.nama_hadir);
            NIM = itemView.findViewById(R.id.nim_hadir);
            STATUS = itemView.findViewById(R.id.status);


        }
    }
}

