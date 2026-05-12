package com.example.deepstack;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class FishAdapter extends RecyclerView.Adapter<FishAdapter.FishViewHolder> {

    private List<Fish> fishList = new ArrayList<>();
    private final WikiImageRepository wikiImageRepository = new WikiImageRepository();

    public void setFishList(List<Fish> fishList) {
        this.fishList = fishList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fish, parent, false);
        return new FishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FishViewHolder holder, int position) {
        Fish fish = fishList.get(position);
        holder.tvSpeciesName.setText(fish.getSpeciesName());
        holder.tvScientificName.setText(fish.getScientificName());

        // Jika gambar sudah di-cache di objek Fish, langsung load
        if (fish.getImageUrl() != null) {
            Glide.with(holder.itemView.getContext())
                    .load(fish.getImageUrl())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(holder.imgFish);
        } else {
            // Belum ada gambar, fetch dari Wikipedia
            holder.imgFish.setImageResource(R.mipmap.ic_launcher); // placeholder dulu
            wikiImageRepository.fetchImageForFish(fish.getSpeciesName(), imageUrl -> {
                fish.setImageUrl(imageUrl != null ? imageUrl : ""); // cache di objek
                // Update UI di main thread
                holder.imgFish.post(() -> {
                    if (imageUrl != null) {
                        Glide.with(holder.itemView.getContext())
                                .load(imageUrl)
                                .placeholder(R.mipmap.ic_launcher)
                                .error(R.mipmap.ic_launcher)
                                .into(holder.imgFish);
                    }
                });
            });
        }
    }

    @Override
    public int getItemCount() {
        return fishList.size();
    }

    static class FishViewHolder extends RecyclerView.ViewHolder {
        TextView tvSpeciesName, tvScientificName;
        ImageView imgFish;

        public FishViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSpeciesName = itemView.findViewById(R.id.tvSpeciesName);
            tvScientificName = itemView.findViewById(R.id.tvScientificName);
            imgFish = itemView.findViewById(R.id.imgFish);
        }
    }
}