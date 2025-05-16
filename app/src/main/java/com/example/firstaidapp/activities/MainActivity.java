package com.example.firstaidapp.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.firstaidapp.R;
import com.example.firstaidapp.fragments.FirstAidGuideFragment;
import com.example.firstaidapp.fragments.HomeFragment;
import com.example.firstaidapp.fragments.LearnFirstAidFragment;
import com.example.firstaidapp.fragments.UserInfoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Updated navigation listener implementation
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.nav_first_aid) {
                selectedFragment = new FirstAidGuideFragment();
            } else if (itemId == R.id.nav_learn) {
                selectedFragment = new LearnFirstAidFragment();
            } else if (itemId == R.id.nav_user) {
                selectedFragment = new UserInfoFragment();
            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
                return true;
            }
            return false;
        });

        // Set Home fragment as default
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}