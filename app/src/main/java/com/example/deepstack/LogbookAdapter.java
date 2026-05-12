package com.example.deepstack;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.deepstack.databinding.ItemLogbookBinding;
import java.util.ArrayList;
import java.util.List;

public class LogbookAdapter extends RecyclerView.Adapter<LogbookAdapter.LogbookViewHolder> {
    private List<Logbook> logbookList = new ArrayList<>();

    public void setLogbookList(List<Logbook> logbooks) {
        this.logbookList = logbooks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LogbookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLogbookBinding binding = ItemLogbookBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new LogbookViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LogbookViewHolder holder, int position) {
        Logbook logbook = logbookList.get(position);
        holder.bind(logbook);
    }

    @Override
    public int getItemCount() {
        return logbookList.size();
    }

    static class LogbookViewHolder extends RecyclerView.ViewHolder {
        private final ItemLogbookBinding binding;

        public LogbookViewHolder(ItemLogbookBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Logbook logbook) {
            binding.spotNameTextView.setText(
                    binding.getRoot().getContext().getString(R.string.spot_label, logbook.getSpotName())
            );
            binding.locationTextView.setText(
                    binding.getRoot().getContext().getString(
                            R.string.location_label,
                            logbook.getLatitude(),
                            logbook.getLongitude()
                    )
            );
            binding.gearIdTextView.setText(
                    binding.getRoot().getContext().getString(R.string.gear_label, logbook.getGearId())
            );
            String notes = logbook.getNotes() != null ? logbook.getNotes() : binding.getRoot().getContext().getString(R.string.notes_empty);
            binding.notesTextView.setText(
                    binding.getRoot().getContext().getString(R.string.notes_label, notes)
            );
            String createdAt = logbook.getCreatedAt() != null ? logbook.getCreatedAt() : binding.getRoot().getContext().getString(R.string.notes_empty);
            binding.createdAtTextView.setText(
                    binding.getRoot().getContext().getString(R.string.created_label, createdAt)
            );
        }
    }
}
