package com.example.hadirly.admin.mahasiswa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.hadirly.R;
import java.util.List;

public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.ViewHolder> {

    private List<MahasiswaModel> mahasiswaList;

    public MahasiswaAdapter(List<MahasiswaModel> mahasiswaList) {
        this.mahasiswaList = mahasiswaList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mahasiswa, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MahasiswaModel mahasiswa = mahasiswaList.get(position);
        holder.nameTextView.setText(mahasiswa.getName());
        holder.kelasTextView.setText(mahasiswa.getKelas());
        holder.nimTextView.setText(mahasiswa.getNim());

    }

    @Override
    public int getItemCount() {
        return mahasiswaList.size();
    }

    public void updateList(List<MahasiswaModel> newList) {
        mahasiswaList = newList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView, kelasTextView,nimTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nama);
            kelasTextView = itemView.findViewById(R.id.prodi);
            nimTextView = itemView.findViewById(R.id.nim);
        }
    }
}
