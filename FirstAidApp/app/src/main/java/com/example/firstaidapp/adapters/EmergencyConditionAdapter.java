package com.example.firstaidapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstaidapp.R;
import com.example.firstaidapp.models.EmergencyCondition;

import java.util.List;

public class EmergencyConditionAdapter extends RecyclerView.Adapter<EmergencyConditionAdapter.ViewHolder> {

    private final Context context;
    private final List<EmergencyCondition> conditions;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(EmergencyCondition condition);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public EmergencyConditionAdapter(Context context, List<EmergencyCondition> conditions) {
        this.context = context;
        this.conditions = conditions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_emergency_condition, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EmergencyCondition condition = conditions.get(position);

        holder.imageView.setImageResource(condition.getImageResourceId());
        holder.titleTextView.setText(condition.getTitle());
        holder.descriptionTextView.setText(condition.getDescription());

        holder.cardView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(condition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return conditions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageView;
        TextView titleTextView;
        TextView descriptionTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            imageView = itemView.findViewById(R.id.image_view);
            titleTextView = itemView.findViewById(R.id.text_view_title);
            descriptionTextView = itemView.findViewById(R.id.text_view_description);
        }
    }
}