package com.example.assignment_6;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.assignment_6.databinding.FragmentProfileBinding;
import com.example.assignment_6.databinding.FragmentSelectDoBBinding;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectDoBFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectDoBFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match

    private static User user;
    private static final String USER = "user";

    private DatePicker datePicker;
    private Button submitButton, cancelButton;


    public SelectDoBFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user
     * @return A new instance of fragment SelectDoBFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectDoBFragment newInstance(User user) {
        SelectDoBFragment fragment = new SelectDoBFragment();
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
        //return inflater.inflate(R.layout.fragment_select_do_b, container, false);
        FragmentSelectDoBBinding binding = FragmentSelectDoBBinding.inflate(inflater, container, false);
        //SelectDoBFragment.FragmentSwitcher listener = (SelectDoBFragment.FragmentSwitcher) getActivity();
        View view = binding.getRoot();

        if (getArguments() != null) {
            user = (User) getArguments().getSerializable(USER);

        }

        if (user == null) {
            return view;
        }else{
            datePicker = view.findViewById(R.id.calendarView);
            submitButton = view.findViewById(R.id.SubmitDOB);
            cancelButton = view.findViewById(R.id.CancelbuttonDOB);

            // Set max date to 18 years from today
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR, -18);
            datePicker.setMaxDate(calendar.getTimeInMillis());

            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int day = datePicker.getDayOfMonth();
                    int month = datePicker.getMonth() + 1; // Month is zero-based
                    int year = datePicker.getYear();
                    String dob =  month + "/" + day + "/" + year;

                    user.setDob(dob);
                    assert mListener != null;
                    mListener.setUser(user);
                    mListener.setDOB(dob);
                    mListener.goToUpdatedProfile();

                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    assert mListener != null;
                    mListener.popFragmentDOB();
                }
            });
        }
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (SelectDoBFragment.FragmentSwitcher) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement FragmentSwitcher");
        }

    }

    private SelectDoBFragment.FragmentSwitcher mListener;
    public interface FragmentSwitcher {
        void goToUpdatedProfile();

        void setUser(User user);

        void popFragmentDOB();

        void setDOB(String dob);
    }
}