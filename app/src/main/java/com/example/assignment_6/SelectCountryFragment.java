package com.example.assignment_6;



import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.example.assignment_6.databinding.FragmentSelectCountryBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectCountryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectCountryFragment extends Fragment {


    private static User user;
    private static final String USER = "user";

    public SelectCountryFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SelectCountryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectCountryFragment newInstance(User user) {
        SelectCountryFragment fragment = new SelectCountryFragment();
        Bundle args = new Bundle();
        args.putSerializable(USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = (User) getArguments().getSerializable(USER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentSelectCountryBinding binding = FragmentSelectCountryBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        Bundle args = getArguments();
        if (args != null && args.containsKey(USER)) {
            user = (User) args.getSerializable(USER);
            // Use userData to access user information
        }

        if (user == null) {
            return view;
        }else{
            // Load countries from a resource file or array
            countries = Data.countries;
            //Log.d("Countries", countries.toString());
            // Find the RadioGroup in the layout
            RadioGroup radioGroup = view.findViewById(R.id.radio_group_countries);

            // Populate the RadioGroup with RadioButtons for each country
            for (int i = 0; i < countries.length; i++) {
                //Log.d("Countries", i+"");
                RadioButton radioButton = new RadioButton(getContext());
                radioButton.setText(countries[i]);
                radioButton.setId(i);
                radioGroup.addView(radioButton);
            }

            // Set onClickListener for the Submit button
            Button submitButton = view.findViewById(R.id.submit_button);
            submitButton.setOnClickListener(v -> {
                // Get the selected country
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId != -1) {
                    String selectedCountry = countries[selectedId];
                    Log.d("selectedCountry", selectedCountry);

                    mListener.onCountrySelected(selectedCountry);
                    //user.setCountry(selectedCountry);
                    mListener.setUser(user);
                    mListener.goToUpdatedProfile();

                } else {
                    Toast.makeText(getContext(), "Please select a country", Toast.LENGTH_SHORT).show();
                }
            });

            // Set onClickListener for the Cancel button
            Button cancelButton = view.findViewById(R.id.cancel_button);
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.goToUpdatedProfile();
                }
            });

        }
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (SelectCountryFragment.FragmentSwitcher) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement FragmentSwitcher");
        }

    }

    private SelectCountryFragment.FragmentSwitcher mListener;
    private String[] countries; // Array of country names

    public interface FragmentSwitcher {
        void goToUpdatedProfile();

        void setUser(User user);
        void onCountrySelected(String country);
    }
}