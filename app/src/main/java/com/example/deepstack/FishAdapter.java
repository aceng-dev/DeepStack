package com.example.deepstack;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FishAdapter extends RecyclerView.Adapter<FishAdapter.FishViewHolder> {

    private List<Fish> fishList = new ArrayList<>();

    public void setFishList(List<Fish> fishList) {
        this.fishList = fishList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fish, parent, false);
        return new FishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FishViewHolder holder, int position) {
        Fish fish = fishList.get(position);
        holder.tvSpeciesName.setText(fish.getSpeciesName());
        holder.tvScientificName.setText(fish.getScientificName());
    }

    @Override
    public int getItemCount() {
        return fishList.size();
    }

    static class FishViewHolder extends RecyclerView.ViewHolder {
        TextView tvSpeciesName, tvScientificName;

        public FishViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSpeciesName = itemView.findViewById(R.id.tvSpeciesName);
            tvScientificName = itemView.findViewById(R.id.tvScientificName);
        }
    }
}
