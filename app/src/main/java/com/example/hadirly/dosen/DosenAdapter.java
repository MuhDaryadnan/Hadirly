package com.example.hadirly.dosen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.hadirly.R;
import java.util.List;

public class DosenAdapter extends RecyclerView.Adapter<DosenAdapter.ClassViewHolder>  {
    private List<DosenModel> dataList;
    private DosenAdapter.OnItemClickListener listener;


    public interface OnItemClickListener {
        void onItemClick(DosenModel classData);
    }

    public DosenAdapter (List<DosenModel> dataList, DosenAdapter.OnItemClickListener listener) {
        this.dataList = dataList;
        this.listener = listener;

    }

    @NonNull
    @Override
    public DosenAdapter.ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_class2, parent, false);
        return new DosenAdapter.ClassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DosenAdapter.ClassViewHolder holder, int position) {
        DosenModel data = dataList.get(position);
        holder.dosenTextView.setText(data.getDosen());
        holder.matkulTextView.setText(data.getNama());
        holder.infoTextView.setText(data.getKelas() + " Semester " + data.getSemester() + " - " + data.getTahun());

        // Set button click listener to notify the listener
        holder.button.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(data);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ClassViewHolder extends RecyclerView.ViewHolder {
        TextView dosenTextView, matkulTextView, infoTextView;
        ImageView imageView;
        Button button;

        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            dosenTextView = itemView.findViewById(R.id.dosen);
            matkulTextView = itemView.findViewById(R.id.nama);
            infoTextView = itemView.findViewById(R.id.info);
            button = itemView.findViewById(R.id.button);
        }
    }
}
