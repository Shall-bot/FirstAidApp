package com.example.firstaidapp.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstaidapp.R;
import com.example.firstaidapp.adapters.EmergencyConditionAdapter;
import com.example.firstaidapp.models.EmergencyCondition;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements EmergencyConditionAdapter.OnItemClickListener {

    private RecyclerView recyclerViewEmergencyConditions;
    private Button btnCallPolice, btnCallAmbulance, btnCallFireDept;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerViewEmergencyConditions = view.findViewById(R.id.recycler_view_emergency_conditions);
        btnCallPolice = view.findViewById(R.id.btn_call_police);
        btnCallAmbulance = view.findViewById(R.id.btn_call_ambulance);
        btnCallFireDept = view.findViewById(R.id.btn_call_fire_dept);

        setupRecyclerView();
        setupEmergencyButtons();

        return view;
    }

    private void setupRecyclerView() {
        List<EmergencyCondition> emergencyConditions = getEmergencyConditions();
        EmergencyConditionAdapter adapter = new EmergencyConditionAdapter(getContext(), emergencyConditions);
        adapter.setOnItemClickListener(this);

        recyclerViewEmergencyConditions.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerViewEmergencyConditions.setAdapter(adapter);
    }

    private List<EmergencyCondition> getEmergencyConditions() {
        List<EmergencyCondition> conditions = new ArrayList<>();
        conditions.add(new EmergencyCondition(1, "Kecelakaan di Jalan", "Penanganan korban kecelakaan lalu lintas", R.drawable.accident));
        conditions.add(new EmergencyCondition(2, "CPR", "Resusitasi jantung paru untuk korban henti jantung", R.drawable.cpr));
        conditions.add(new EmergencyCondition(3, "Pendarahan", "Menghentikan pendarahan dan mencegah infeksi", R.drawable.bleeding));
        conditions.add(new EmergencyCondition(4, "Tersedak", "Penanganan korban tersedak dengan teknik Heimlich", R.drawable.choking));
        conditions.add(new EmergencyCondition(5, "Keracunan", "Pertolongan pertama untuk korban keracunan", R.drawable.poisoning));
        conditions.add(new EmergencyCondition(6, "Fraktur", "Penanganan sementara untuk patah tulang", R.drawable.fracture));
        return conditions;
    }

    private void setupEmergencyButtons() {
        btnCallPolice.setOnClickListener(v -> makeEmergencyCall("110"));
        btnCallAmbulance.setOnClickListener(v -> makeEmergencyCall("118"));
        btnCallFireDept.setOnClickListener(v -> makeEmergencyCall("113"));
    }

    private void makeEmergencyCall(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }

    @Override
    public void onItemClick(EmergencyCondition condition) {
        FirstAidDetailFragment detailFragment = FirstAidDetailFragment.newInstance(condition.getId());
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null)
                .commit();
    }
}