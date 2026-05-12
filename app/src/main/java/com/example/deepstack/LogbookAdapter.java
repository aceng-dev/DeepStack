package com.example.deepstack;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LogbookAdapter extends RecyclerView.Adapter<LogbookAdapter.LogbookViewHolder> {

    private List<Logbook> logbookList = new ArrayList<>();

    public void setLogbookList(List<Logbook> logbookList) {
        this.logbookList = logbookList;
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
        holder.tvCoordinates.setText("Lat: " + logbook.getLatitude() + ", Lon: " + logbook.getLongitude());
        holder.tvNotes.setText(logbook.getNotes());

        // Format tanggal jika diperlukan
        if (logbook.getCreatedAt() != null && logbook.getCreatedAt().length() >= 10) {
            holder.tvDate.setText(logbook.getCreatedAt().substring(0, 10));
        } else {
            holder.tvDate.setText(logbook.getCreatedAt());
        }

        // TODO: Ganti R.mipmap.ic_launcher dengan nama file gambar jangkar/spot pixel art Anda nanti
        holder.imgSpot.setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    public int getItemCount() {
        return logbookList.size();
    }

    static class LogbookViewHolder extends RecyclerView.ViewHolder {
        TextView tvSpotName, tvCoordinates, tvNotes, tvDate;
        ImageView imgSpot; // Tambahan untuk gambar

        public LogbookViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSpotName = itemView.findViewById(R.id.tvSpotName);
            tvCoordinates = itemView.findViewById(R.id.tvCoordinates);
            tvNotes = itemView.findViewById(R.id.tvNotes);
            tvDate = itemView.findViewById(R.id.tvDate);
            imgSpot = itemView.findViewById(R.id.imgSpot); // Inisialisasi ID gambar
        }
    }
}