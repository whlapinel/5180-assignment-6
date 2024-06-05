package com.example.assignment_6;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.assignment_6.databinding.FragmentMainBinding;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class MainFragment extends Fragment {

    private static final String USER = "user";

    User user;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(User user) {
        MainFragment fragment = new MainFragment();
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
       // return inflater.inflate(R.layout.fragment_main, container, false);
        FragmentMainBinding binding = FragmentMainBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        Button startBtn = binding.startBtn;
        startBtn.setOnClickListener(v -> {
            //mListener = (MainFragment.FragmentSwitcher) getActivity();
            mListener.switchFragment(CreateUserFragment.newInstance(user));

        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (MainFragment.FragmentSwitcher) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement FragmentSwitcher");
        }

    }

    private MainFragment.FragmentSwitcher mListener;
    public interface FragmentSwitcher {
        void switchFragment(Fragment fragment);
    }
}