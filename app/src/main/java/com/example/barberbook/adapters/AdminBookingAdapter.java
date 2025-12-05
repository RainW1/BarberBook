package com.example.barberbook.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barberbook.R;
import com.example.barberbook.database.DatabaseHelper;
import com.example.barberbook.models.Booking;

import java.util.List;

public class AdminBookingAdapter extends RecyclerView.Adapter<AdminBookingAdapter.ViewHolder> {

    private Context context;
    private List<Booking> bookingList;
    private DatabaseHelper databaseHelper;
    private OnAdminActionListener listener;

    public interface OnAdminActionListener {
        void onCompleteClick(Booking booking, int position);
        void onCancelClick(Booking booking, int position);
    }

    public AdminBookingAdapter(Context context, List<Booking> bookingList, OnAdminActionListener listener) {
        this.context = context;
        this.bookingList = bookingList;
        this.listener = listener;
        this.databaseHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_admin_booking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Booking booking = bookingList.get(position);

        // Get customer name from user_id
        String customerName = databaseHelper.getUserNameById(booking.getUserId());

        holder.tvCustomerName.setText(customerName);
        holder.tvBarberName.setText(booking.getBarberName());
        holder.tvServices.setText(booking.getServices());
        holder.tvDateTime.setText(booking.getDate() + ", " + booking.getTime());
        holder.tvPrice.setText("Rp " + String.format("%,d", booking.getTotalPrice()).replace(",", "."));
        holder.tvStatus.setText(booking.getStatus());

        // Set status color & visibility
        int statusColor;
        switch (booking.getStatus()) {
            case "Upcoming":
                statusColor = R.color.status_upcoming;
                holder.layoutActions.setVisibility(View.VISIBLE);
                holder.itemView.setAlpha(1f);
                break;
            case "Completed":
                statusColor = R.color.status_completed;
                holder.layoutActions.setVisibility(View.GONE);
                holder.itemView.setAlpha(1f);
                break;
            case "Cancelled":
                statusColor = R.color.status_cancelled;
                holder.layoutActions.setVisibility(View.GONE);
                holder.itemView.setAlpha(0.6f);
                break;
            default:
                statusColor = R.color.text_secondary;
                holder.layoutActions.setVisibility(View.GONE);
                holder.itemView.setAlpha(1f);
        }
        holder.tvStatus.setBackgroundColor(ContextCompat.getColor(context, statusColor));

        // Button clicks
        holder.btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onCompleteClick(booking, holder.getAdapterPosition());
                }
            }
        });

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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvStatus, tvCustomerName, tvPrice, tvBarberName, tvServices, tvDateTime;
        LinearLayout layoutActions;
        Button btnComplete, btnCancel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvCustomerName = itemView.findViewById(R.id.tvCustomerName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvBarberName = itemView.findViewById(R.id.tvBarberName);
            tvServices = itemView.findViewById(R.id.tvServices);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            layoutActions = itemView.findViewById(R.id.layoutActions);
            btnComplete = itemView.findViewById(R.id.btnComplete);
            btnCancel = itemView.findViewById(R.id.btnCancel);
        }
    }
}
