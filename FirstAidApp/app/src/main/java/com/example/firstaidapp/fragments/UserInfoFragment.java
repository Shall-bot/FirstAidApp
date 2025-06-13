package com.example.firstaidapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.firstaidapp.R;

public class UserInfoFragment extends Fragment {

    private EditText editTextName, editTextAge, editTextAllergies, editTextChronicConditions,
            editTextMedications, editTextEmergencyContact;
    private RadioGroup radioGroupGender;
    private Spinner spinnerBloodType;
    private Button buttonSave, buttonClear;

    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);

        // Initialize SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);

        // Initialize views
        editTextName = view.findViewById(R.id.edit_text_name);
        editTextAge = view.findViewById(R.id.edit_text_age);
        radioGroupGender = view.findViewById(R.id.radio_group_gender);
        spinnerBloodType = view.findViewById(R.id.spinner_blood_type);
        editTextAllergies = view.findViewById(R.id.edit_text_allergies);
        editTextChronicConditions = view.findViewById(R.id.edit_text_chronic_conditions);
        editTextMedications = view.findViewById(R.id.edit_text_medications);
        editTextEmergencyContact = view.findViewById(R.id.edit_text_emergency_contact);
        buttonSave = view.findViewById(R.id.button_save);
        buttonClear = view.findViewById(R.id.button_clear);

        // Load saved data
        loadUserData();

        // Set button click listeners
        buttonSave.setOnClickListener(v -> saveUserData());
        buttonClear.setOnClickListener(v -> clearUserData());

        return view;
    }

    private void loadUserData() {
        editTextName.setText(sharedPreferences.getString("name", ""));
        editTextAge.setText(sharedPreferences.getString("age", ""));

        int genderId = sharedPreferences.getInt("gender", R.id.radio_male);
        radioGroupGender.check(genderId);

        int bloodTypePosition = sharedPreferences.getInt("bloodType", 0);
        spinnerBloodType.setSelection(bloodTypePosition);

        editTextAllergies.setText(sharedPreferences.getString("allergies", ""));
        editTextChronicConditions.setText(sharedPreferences.getString("chronicConditions", ""));
        editTextMedications.setText(sharedPreferences.getString("medications", ""));
        editTextEmergencyContact.setText(sharedPreferences.getString("emergencyContact", ""));
    }

    private void saveUserData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("name", editTextName.getText().toString());
        editor.putString("age", editTextAge.getText().toString());
        editor.putInt("gender", radioGroupGender.getCheckedRadioButtonId());
        editor.putInt("bloodType", spinnerBloodType.getSelectedItemPosition());
        editor.putString("allergies", editTextAllergies.getText().toString());
        editor.putString("chronicConditions", editTextChronicConditions.getText().toString());
        editor.putString("medications", editTextMedications.getText().toString());
        editor.putString("emergencyContact", editTextEmergencyContact.getText().toString());

        editor.apply();

        Toast.makeText(getContext(), R.string.data_saved, Toast.LENGTH_SHORT).show();
    }

    private void clearUserData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        editTextName.setText("");
        editTextAge.setText("");
        radioGroupGender.check(R.id.radio_male);
        spinnerBloodType.setSelection(0);
        editTextAllergies.setText("");
        editTextChronicConditions.setText("");
        editTextMedications.setText("");
        editTextEmergencyContact.setText("");

        Toast.makeText(getContext(), R.string.data_cleared, Toast.LENGTH_SHORT).show();
    }
}