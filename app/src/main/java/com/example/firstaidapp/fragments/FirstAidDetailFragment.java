package com.example.firstaidapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.firstaidapp.R;
import com.example.firstaidapp.services.EmergencyTimerService;

public class FirstAidDetailFragment extends Fragment {

    private static final String ARG_CONDITION_ID = "condition_id";
    private int conditionId;

    private ImageView imageView;
    private TextView titleTextView, descriptionTextView, stepsTextView, tipsTextView;
    private Button startTimerButton;

    public static FirstAidDetailFragment newInstance(int conditionId) {
        FirstAidDetailFragment fragment = new FirstAidDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CONDITION_ID, conditionId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            conditionId = getArguments().getInt(ARG_CONDITION_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_aid_detail, container, false);

        imageView = view.findViewById(R.id.image_view);
        titleTextView = view.findViewById(R.id.text_view_title);
        descriptionTextView = view.findViewById(R.id.text_view_description);
        stepsTextView = view.findViewById(R.id.text_view_steps);
        tipsTextView = view.findViewById(R.id.text_view_tips);
        startTimerButton = view.findViewById(R.id.btn_start_timer);

        loadConditionDetails();

        startTimerButton.setOnClickListener(v -> startEmergencyTimer());

        return view;
    }

    private void loadConditionDetails() {
        // In a real app, this would load from a database or repository
        // Here we're just using switch case for simplicity
        switch (conditionId) {
            case 1: // Kecelakaan di Jalan
                imageView.setImageResource(R.drawable.ic_accident);
                titleTextView.setText(R.string.accident_title);
                descriptionTextView.setText(R.string.accident_description);
                stepsTextView.setText(R.string.accident_steps);
                tipsTextView.setText(R.string.accident_tips);
                break;
            case 2: // CPR
                imageView.setImageResource(R.drawable.ic_cpr);
                titleTextView.setText(R.string.cpr_title);
                descriptionTextView.setText(R.string.cpr_description);
                stepsTextView.setText(R.string.cpr_steps);
                tipsTextView.setText(R.string.cpr_tips);
                break;
            // Add cases for other conditions
            default:
                // Default content
                break;
        }
    }

    private void startEmergencyTimer() {
        Intent serviceIntent = new Intent(getActivity(), EmergencyTimerService.class);
        serviceIntent.putExtra("TIMER_DURATION", 300); // 5 minutes in seconds
        serviceIntent.putExtra("TIMER_TITLE", titleTextView.getText().toString());
        requireActivity().startService(serviceIntent);
    }
}