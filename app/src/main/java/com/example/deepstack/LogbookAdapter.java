package com.example.deepstack;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        holder.tvDate.setText(logbook.getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return logbookList.size();
    }

    static class LogbookViewHolder extends RecyclerView.ViewHolder {
        TextView tvSpotName, tvCoordinates, tvNotes, tvDate;

        public LogbookViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSpotName = itemView.findViewById(R.id.tvSpotName);
            tvCoordinates = itemView.findViewById(R.id.tvCoordinates);
            tvNotes = itemView.findViewById(R.id.tvNotes);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}
