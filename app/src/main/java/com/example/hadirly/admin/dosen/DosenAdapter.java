package com.example.hadirly.admin.dosen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.hadirly.R;

import java.util.List;

public class DosenAdapter extends RecyclerView.Adapter<com.example.hadirly.admin.dosen.DosenAdapter.ViewHolder> {

    private List<DosenModel> mahasiswaList;

    public DosenAdapter(List<DosenModel> mahasiswaList) {
        this.mahasiswaList = mahasiswaList;
    }

    @NonNull
    @Override
    public com.example.hadirly.admin.dosen.DosenAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dosen, parent, false);
        return new com.example.hadirly.admin.dosen.DosenAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.hadirly.admin.dosen.DosenAdapter.ViewHolder holder, int position) {
        DosenModel dosen = mahasiswaList.get(position);
        holder.nameTextView.setText("Nama :"+dosen.getNama());
        holder.emailTextView.setText("Email :"+dosen.getEmail());
        holder.nimTextView.setText("NIDN :"+dosen.getNidn());
        holder.nohpTextview.setText("NO HP :"+dosen.getNohp());

    }

    @Override
    public int getItemCount() {
        return mahasiswaList.size();
    }

    public void updateList(List<DosenModel> newList) {
        mahasiswaList = newList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView, emailTextView,nimTextView,nohpTextview;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nama);
            emailTextView = itemView.findViewById(R.id.email);
            nimTextView = itemView.findViewById(R.id.nidn);
            nohpTextview = itemView.findViewById(R.id.nomor);
        }
    }
}
