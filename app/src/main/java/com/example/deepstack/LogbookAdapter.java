package com.example.deepstack;

import android.view.LayoutInflater;import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LogbookAdapter extends RecyclerView.Adapter<LogbookAdapter.LogbookViewHolder> {

    private List<Logbook> logbookList = new ArrayList<>();
    private OnItemLongClickListener longClickListener;

    public interface OnItemLongClickListener {
        void onItemLongClick(Logbook logbook);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.longClickListener = listener;
    }

    public void setLogbookList(List<Logbook> logbookList) {
        this.logbookList = logbookList;
        // Jika performa terasa berat saat refresh, pertimbangkan gunakan DiffUtil nanti
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LogbookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_logbook, parent, false);
        return new LogbookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LogbookViewHolder holder, int position) {
        Logbook logbook = logbookList.get(position);

        holder.tvSpotName.setText(logbook.getSpotName());

        // Gunakan String.format agar lebih rapi dan menghindari peringatan lint
        String coords = String.format(Locale.getDefault(), "Lat: %s, Lon: %s",
                logbook.getLatitude(), logbook.getLongitude());
        holder.tvCoordinates.setText(coords);

        holder.tvNotes.setText(logbook.getNotes());

        // Perbaikan: Sekarang getCreatedAt() sudah bisa dipanggil setelah langkah 1 dilakukan
        String date = logbook.getCreatedAt();
        if (date != null && date.length() >= 10) {
            holder.tvDate.setText(date.substring(0, 10));
        } else {
            holder.tvDate.setText(date != null ? date : "");
        }

        holder.imgSpot.setImageResource(R.drawable.ic_list_spot);

        holder.itemView.setOnLongClickListener(v -> {
            if (longClickListener != null) {
                longClickListener.onItemLongClick(logbook);
                return true;
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return logbookList.size();
    }

    // Ubah menjadi public agar tidak muncul peringatan visibility scope
    public static class LogbookViewHolder extends RecyclerView.ViewHolder {
        TextView tvSpotName, tvCoordinates, tvNotes, tvDate;
        ImageView imgSpot;

        public LogbookViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSpotName = itemView.findViewById(R.id.tvSpotName);
            tvCoordinates = itemView.findViewById(R.id.tvCoordinates);
            tvNotes = itemView.findViewById(R.id.tvNotes);
            tvDate = itemView.findViewById(R.id.tvDate);
            imgSpot = itemView.findViewById(R.id.imgSpot);
        }
    }
}