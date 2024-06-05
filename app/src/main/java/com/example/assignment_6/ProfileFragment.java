package com.example.assignment_6;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.assignment_6.databinding.FragmentProfileBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {


    private static final String USER = "user";

    private TextView nameTextView, emailTextView, ageTextView, countryTextView, dobTextView;



    private static User user;
    private static final String TAG = "ProfileFragment";

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(User user) {
        ProfileFragment fragment = new ProfileFragment();
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
        //return inflater.inflate(R.layout.fragment_profile, container, false);
        FragmentProfileBinding binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        if (user == null) {
            return view;
        }else{
            nameTextView = view.findViewById(R.id.NameValue);
            emailTextView = view.findViewById(R.id.EmailValue);
            ageTextView = view.findViewById(R.id.AgeValue);
            countryTextView = view.findViewById(R.id.CountryValuetextView);
            dobTextView = view.findViewById(R.id.DOBValuetextView);


            if (user != null) {
                nameTextView.setText(user.getName());
                emailTextView.setText(user.getEmail());
                ageTextView.setText(String.valueOf(user.getAge()));
                countryTextView.setText(user.getCountry());
                dobTextView.setText(user.getDob());
            }

        }
        return view;
    }


    public void setUser(User user) {
        this.user = user;
    }
  }