package com.example.hadirly.dosen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hadirly.R;

import java.util.List;

public class KelasAdapter extends RecyclerView.Adapter<KelasAdapter.KelasViewHolder> {

    private List<KelasModel> kelasList;

    public KelasAdapter(List<KelasModel> kelasList) {
        this.kelasList = kelasList;
    }

    @NonNull
    @Override
    public KelasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_kelas_dosen, parent, false);
        return new KelasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KelasViewHolder holder, int position) {
        KelasModel kelas = kelasList.get(position);
        holder.nama.setText(kelas.nama);
        holder.jadwal.setText(kelas.jadwal);
    }

    @Override
    public int getItemCount() {
        return kelasList.size();
    }

    public static class KelasViewHolder extends RecyclerView.ViewHolder {
        TextView nama, jadwal;

        public KelasViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.namaKelas);
            jadwal = itemView.findViewById(R.id.jadwalKelas);
        }
    }
}

