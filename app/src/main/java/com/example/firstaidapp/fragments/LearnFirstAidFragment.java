package com.example.firstaidapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstaidapp.R;
import com.example.firstaidapp.adapters.FirstAidGuideAdapter;
import com.example.firstaidapp.models.FirstAidGuide;

import java.util.ArrayList;
import java.util.List;

public class LearnFirstAidFragment extends Fragment implements FirstAidGuideAdapter.OnItemClickListener {

    private RecyclerView recyclerViewGuides;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learn_first_aid, container, false);

        recyclerViewGuides = view.findViewById(R.id.recycler_view_guides);
        setupRecyclerView();

        return view;
    }

    private void setupRecyclerView() {
        List<FirstAidGuide> guides = getFirstAidGuides();
        FirstAidGuideAdapter adapter = new FirstAidGuideAdapter(getContext(), guides);
        adapter.setOnItemClickListener(this);

        recyclerViewGuides.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewGuides.setAdapter(adapter);
    }

    private List<FirstAidGuide> getFirstAidGuides() {
        List<FirstAidGuide> guides = new ArrayList<>();
        guides.add(new FirstAidGuide(1, "Dasar-dasar Pertolongan Pertama", "Pelajari prinsip dasar dan langkah-langkah pertolongan pertama", R.drawable.ic_basics, 8, 100));
        guides.add(new FirstAidGuide(2, "CPR dan Resusitasi", "Teknik penyelamatan nyawa dengan CPR dan resusitasi jantung paru", R.drawable.ic_cpr, 6, 50));
        guides.add(new FirstAidGuide(3, "Penanganan Luka dan Pendarahan", "Cara menangani berbagai jenis luka dan menghentikan pendarahan", R.drawable.ic_bleeding, 7, 30));
        guides.add(new FirstAidGuide(4, "Penanganan Patah Tulang", "Teknik immobilisasi dan penanganan fraktur", R.drawable.ic_fracture, 5, 0));
        guides.add(new FirstAidGuide(5, "Penanganan Tersedak", "Teknik Heimlich dan cara mengatasi tersedak pada berbagai usia", R.drawable.ic_choking, 4, 0));
        guides.add(new FirstAidGuide(6, "Penanganan Keracunan", "Identifikasi dan penanganan berbagai jenis keracunan", R.drawable.ic_poisoning, 6, 0));
        return guides;
    }

    @Override
    public void onItemClick(FirstAidGuide guide) {
        // In a real app, this would navigate to a detailed lesson view
        // For this simple framework, we'll just show a toast
        if (getContext() != null) {
            String message = guide.getProgress() > 0 ?
                    "Melanjutkan kursus: " + guide.getTitle() :
                    "Memulai kursus: " + guide.getTitle();
            android.widget.Toast.makeText(getContext(), message, android.widget.Toast.LENGTH_SHORT).show();
        }
    }
}