package com.example.firstaidapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstaidapp.R;
import com.example.firstaidapp.adapters.EmergencyConditionAdapter;
import com.example.firstaidapp.models.EmergencyCondition;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class FirstAidGuideFragment extends Fragment implements EmergencyConditionAdapter.OnItemClickListener {

    private RecyclerView recyclerViewConditions;
    private SearchView searchView;
    private TabLayout tabLayout;
    private List<EmergencyCondition> allConditions;
    private EmergencyConditionAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_aid_guide, container, false);

        recyclerViewConditions = view.findViewById(R.id.recycler_view_conditions);
        searchView = view.findViewById(R.id.search_view);
        tabLayout = view.findViewById(R.id.tab_layout);

        setupTabs();
        setupSearchView();
        setupRecyclerView();

        return view;
    }

    private void setupTabs() {
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_all));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_injuries));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_medical));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                filterConditionsByCategory(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Not needed
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Not needed
            }
        });
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterConditionsByQuery(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterConditionsByQuery(newText);
                return true;
            }
        });
    }

    private void setupRecyclerView() {
        allConditions = getAllEmergencyConditions();
        adapter = new EmergencyConditionAdapter(getContext(), allConditions);
        adapter.setOnItemClickListener(this);

        recyclerViewConditions.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewConditions.setAdapter(adapter);
    }

    private List<EmergencyCondition> getAllEmergencyConditions() {
        List<EmergencyCondition> conditions = new ArrayList<>();

        // General conditions
        conditions.add(new EmergencyCondition(1, "Kecelakaan di Jalan", "Penanganan korban kecelakaan lalu lintas", R.drawable.ic_accident));
        conditions.add(new EmergencyCondition(2, "CPR", "Resusitasi jantung paru untuk korban henti jantung", R.drawable.ic_cpr));
        conditions.add(new EmergencyCondition(3, "Pendarahan", "Menghentikan pendarahan dan mencegah infeksi", R.drawable.ic_bleeding));
        conditions.add(new EmergencyCondition(4, "Tersedak", "Penanganan korban tersedak dengan teknik Heimlich", R.drawable.ic_choking));
        conditions.add(new EmergencyCondition(5, "Keracunan", "Pertolongan pertama untuk korban keracunan", R.drawable.ic_poisoning));
        conditions.add(new EmergencyCondition(6, "Fraktur", "Penanganan sementara untuk patah tulang", R.drawable.ic_fracture));

        // Injuries
        conditions.add(new EmergencyCondition(7, "Luka Bakar", "Penanganan berbagai tingkat luka bakar", R.drawable.ic_burn));
        conditions.add(new EmergencyCondition(8, "Luka Sayat", "Penanganan luka sayat dan mencegah infeksi", R.drawable.ic_cut));
        conditions.add(new EmergencyCondition(9, "Cedera Kepala", "Penanganan cedera kepala dan gegar otak", R.drawable.ic_head_injury));

        // Medical conditions
        conditions.add(new EmergencyCondition(10, "Serangan Jantung", "Penanganan gejala serangan jantung", R.drawable.ic_heart_attack));
        conditions.add(new EmergencyCondition(11, "Stroke", "Pengenalan gejala stroke dan penanganan awal", R.drawable.ic_stroke));
        conditions.add(new EmergencyCondition(12, "Kejang", "Penanganan kejang dan epilepsi", R.drawable.ic_seizure));

        return conditions;
    }

    private void filterConditionsByCategory(int categoryPosition) {
        if (categoryPosition == 0) {
            // All conditions
            adapter = new EmergencyConditionAdapter(getContext(), allConditions);
        } else if (categoryPosition == 1) {
            // Injuries
            List<EmergencyCondition> filteredList = new ArrayList<>();
            for (EmergencyCondition condition : allConditions) {
                if (condition.getId() >= 7 && condition.getId() <= 9) {
                    filteredList.add(condition);
                }
            }
            adapter = new EmergencyConditionAdapter(getContext(), filteredList);
        } else if (categoryPosition == 2) {
            // Medical conditions
            List<EmergencyCondition> filteredList = new ArrayList<>();
            for (EmergencyCondition condition : allConditions) {
                if (condition.getId() >= 10) {
                    filteredList.add(condition);
                }
            }
            adapter = new EmergencyConditionAdapter(getContext(), filteredList);
        }

        adapter.setOnItemClickListener(this);
        recyclerViewConditions.setAdapter(adapter);
    }

    private void filterConditionsByQuery(String query) {
        if (query.isEmpty()) {
            adapter = new EmergencyConditionAdapter(getContext(), allConditions);
        } else {
            List<EmergencyCondition> filteredList = new ArrayList<>();
            for (EmergencyCondition condition : allConditions) {
                if (condition.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                        condition.getDescription().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(condition);
                }
            }
            adapter = new EmergencyConditionAdapter(getContext(), filteredList);
        }

        adapter.setOnItemClickListener(this);
        recyclerViewConditions.setAdapter(adapter);
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