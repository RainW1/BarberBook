package com.example.barberbook.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barberbook.R;
import com.example.barberbook.models.Barber;

import java.util.List;

public class AdminBarberAdapter extends RecyclerView.Adapter<AdminBarberAdapter.ViewHolder> {

    private Context context;
    private List<Barber> barberList;
    private OnBarberActionListener listener;

    public interface OnBarberActionListener {
        void onAvailabilityChanged(Barber barber, boolean isAvailable);
        void onBarberClick(Barber barber, int position);
    }

    public AdminBarberAdapter(Context context, List<Barber> barberList, OnBarberActionListener listener) {
        this.context = context;
        this.barberList = barberList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_admin_barber, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Barber barber = barberList.get(position);

        holder.tvBarberName.setText(barber.getName());
        holder.tvBarberRating.setText("⭐ " + barber.getRating() + " • " + barber.getExperience() + " years exp");

        // Set availability status
        if (barber.isAvailable()) {
            holder.tvBarberStatus.setText("Available");
            holder.tvBarberStatus.setTextColor(ContextCompat.getColor(context, R.color.status_upcoming));
            holder.itemView.setAlpha(1f);
        } else {
            holder.tvBarberStatus.setText("Not Available");
            holder.tvBarberStatus.setTextColor(ContextCompat.getColor(context, R.color.status_cancelled));
            holder.itemView.setAlpha(0.7f);
        }

        // Set switch without triggering listener
        holder.switchAvailable.setOnCheckedChangeListener(null);
        holder.switchAvailable.setChecked(barber.isAvailable());

        // Switch listener
        holder.switchAvailable.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (listener != null) {
                listener.onAvailabilityChanged(barber, isChecked);
            }
        });

        // Card click for edit
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onBarberClick(barber, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return barberList.size();
    }

    public void updateList(List<Barber> newList) {
        barberList = newList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivBarberPhoto;
        TextView tvBarberName, tvBarberRating, tvBarberStatus;
        SwitchCompat switchAvailable;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBarberPhoto = itemView.findViewById(R.id.ivBarberPhoto);
            tvBarberName = itemView.findViewById(R.id.tvBarberName);
            tvBarberRating = itemView.findViewById(R.id.tvBarberRating);
            tvBarberStatus = itemView.findViewById(R.id.tvBarberStatus);
            switchAvailable = itemView.findViewById(R.id.switchAvailable);
        }
    }
}