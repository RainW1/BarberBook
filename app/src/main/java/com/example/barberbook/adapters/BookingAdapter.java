package com.example.barberbook.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barberbook.R;
import com.example.barberbook.models.Booking;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    private Context context;
    private List<Booking> bookingList;
    private OnBookingClickListener listener;

    public interface OnBookingClickListener {
        void onCancelClick(Booking booking, int position);
    }

    public BookingAdapter(Context context, List<Booking> bookingList, OnBookingClickListener listener) {
        this.context = context;
        this.bookingList = bookingList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_booking, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking booking = bookingList.get(position);

        holder.tvBarberName.setText(booking.getBarberName());
        holder.tvServices.setText(booking.getServices());
        holder.tvDate.setText(booking.getDate());
        holder.tvTime.setText(booking.getTime());
        holder.tvPrice.setText("Rp " + String.format("%,d", booking.getTotalPrice()).replace(",", "."));
        holder.tvStatus.setText(booking.getStatus());

        // Set status color
        int statusColor;
        switch (booking.getStatus()) {
            case "Upcoming":
                statusColor = R.color.status_upcoming;
                holder.btnCancel.setVisibility(View.VISIBLE);
                break;
            case "Completed":
                statusColor = R.color.status_completed;
                holder.btnCancel.setVisibility(View.GONE);
                break;
            case "Cancelled":
                statusColor = R.color.status_cancelled;
                holder.btnCancel.setVisibility(View.GONE);
                holder.itemView.setAlpha(0.6f);
                break;
            default:
                statusColor = R.color.text_secondary;
                holder.btnCancel.setVisibility(View.GONE);
        }
        holder.tvStatus.setBackgroundColor(ContextCompat.getColor(context, statusColor));

        // Cancel button click
        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onCancelClick(booking, holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public void updateList(List<Booking> newList) {
        bookingList = newList;
        notifyDataSetChanged();
    }

    public static class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView tvStatus, tvBarberName, tvServices, tvDate, tvTime, tvPrice;
        ImageView ivBarberPhoto;
        Button btnCancel;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvBarberName = itemView.findViewById(R.id.tvBarberName);
            tvServices = itemView.findViewById(R.id.tvServices);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            ivBarberPhoto = itemView.findViewById(R.id.ivBarberPhoto);
            btnCancel = itemView.findViewById(R.id.btnCancel);
        }
    }
}