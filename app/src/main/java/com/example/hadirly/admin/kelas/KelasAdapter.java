package com.example.hadirly.admin.kelas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hadirly.R;
import java.util.List;

public class KelasAdapter extends RecyclerView.Adapter<KelasAdapter.ViewHolder> {

    private List<KelasModel> kelasList;
    private KelasAdapter.OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(KelasModel kelasModel);
    }

    // Constructor dengan listener
    public KelasAdapter(List<KelasModel> kelasList, OnItemClickListener listener) {
        this.kelasList = kelasList;
        this.listener = (listener);
    }

    // Untuk dipanggil dari fragment saat filtering atau data berubah

    @NonNull
    @Override
    public KelasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kelas, parent, false);
        return new KelasAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KelasAdapter.ViewHolder holder, int position) {
        KelasModel kelas = kelasList.get(position);
        holder.nameTextView.setText(kelas.getNama());
        holder.infoTextView.setText(kelas.getInfo());
        holder.dosenTextView.setText(kelas.getDosen());
        holder.kelasTextview.setText(kelas.getKelas());

        holder.itemView.setOnClickListener(v -> listener.onItemClick(kelas));

    }

    @Override
    public int getItemCount() {
        return kelasList.size();
    }

    public void updateList(List<KelasModel> newList) {
        kelasList = newList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView, infoTextView,dosenTextView,kelasTextview;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nama);
            infoTextView = itemView.findViewById(R.id.info);
            dosenTextView = itemView.findViewById(R.id.dosen);
            kelasTextview = itemView.findViewById(R.id.prodi);
        }
    }
}
