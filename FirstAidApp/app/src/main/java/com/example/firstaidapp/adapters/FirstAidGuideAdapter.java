package com.example.firstaidapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstaidapp.R;
import com.example.firstaidapp.models.FirstAidGuide;

import java.util.List;

public class FirstAidGuideAdapter extends RecyclerView.Adapter<FirstAidGuideAdapter.ViewHolder> {

    private final Context context;
    private final List<FirstAidGuide> guides;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(FirstAidGuide guide);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public FirstAidGuideAdapter(Context context, List<FirstAidGuide> guides) {
        this.context = context;
        this.guides = guides;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_first_aid_guide, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FirstAidGuide guide = guides.get(position);

        holder.imageView.setImageResource(guide.getImageResourceId());
        holder.titleTextView.setText(guide.getTitle());
        holder.descriptionTextView.setText(guide.getDescription());
        holder.lessonsTextView.setText(context.getString(R.string.lessons_count, guide.getLessons()));

        if (guide.getProgress() > 0) {
            holder.progressBar.setVisibility(View.VISIBLE);
            holder.progressBar.setProgress(guide.getProgress());
            holder.progressTextView.setVisibility(View.VISIBLE);

            if (guide.getProgress() == 100) {
                holder.progressTextView.setText(R.string.completed);
                holder.progressTextView.setTextColor(context.getResources().getColor(R.color.colorAccent));
                holder.actionButton.setText(R.string.review);
            } else {
                holder.progressTextView.setText(context.getString(R.string.progress_percent, guide.getProgress()));
                holder.actionButton.setText(R.string.continue_learning);
            }
        } else {
            holder.progressBar.setVisibility(View.GONE);
            holder.progressTextView.setVisibility(View.GONE);
            holder.actionButton.setText(R.string.start_learning);
        }

        holder.actionButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(guide);
            }
        });
    }

    @Override
    public int getItemCount() {
        return guides.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView;
        TextView descriptionTextView;
        TextView lessonsTextView;
        TextView progressTextView;
        ProgressBar progressBar;
        Button actionButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            titleTextView = itemView.findViewById(R.id.text_view_title);
            descriptionTextView = itemView.findViewById(R.id.text_view_description);
            lessonsTextView = itemView.findViewById(R.id.text_view_lessons);
            progressTextView = itemView.findViewById(R.id.text_view_progress);
            progressBar = itemView.findViewById(R.id.progress_bar);
            actionButton = itemView.findViewById(R.id.button_action);
        }
    }
}