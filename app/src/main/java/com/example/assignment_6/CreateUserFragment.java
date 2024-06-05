package com.example.assignment_6;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment_6.databinding.FragmentCreateUserBinding;
import com.example.assignment_6.databinding.FragmentMainBinding;



public class CreateUserFragment extends Fragment {
    private static final String TAG = "CreateUserFragment";


    private EditText nameEditText, emailEditText, ageEditText;
    private TextView countryTextView, dobTextView;
    private Button selectCountryButton, selectDobButton, submitButton;

    User user;

    User userModified;

    private static final String USER = "user";

    public CreateUserFragment() {
        // Required empty public constructor
    }

    public static CreateUserFragment newInstance(User user) {
        CreateUserFragment fragment = new CreateUserFragment();
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
       // return inflater.inflate(R.layout.fragment_create_user, container, false);
        // Initialize views
        FragmentCreateUserBinding binding = FragmentCreateUserBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        nameEditText = view.findViewById(R.id.editTextText);
        emailEditText = view.findViewById(R.id.EditTextEmailAddress);
        ageEditText = view.findViewById(R.id.editAgeTextNumber);
        countryTextView = view.findViewById(R.id.CountryValuetextView7);
        dobTextView = view.findViewById(R.id.DOBValuetextView7);
        selectCountryButton = view.findViewById(R.id.CountryButton);
        selectDobButton = view.findViewById(R.id.DOBButton);
        submitButton = view.findViewById(R.id.Submit);

        if(mListener.getUser() != null){
            userModified = mListener.getUser();
            Log.d(TAG, "onCreateView: " + userModified.getName());
            Log.d(TAG, "onCreateView: " + "before checking getEmail()");

            if(userModified.getEmail()!=null){
                emailEditText.setText(userModified.getEmail());
            }else if (emailEditText.getText()!=null){
                userModified.setEmail(emailEditText.getText().toString());
                mListener.setEmail(emailEditText.getText().toString());
            }
            Log.d(TAG, "onCreateView: " + "before checking getAge()");
            if(userModified.getAge()!=null){
                ageEditText.setText(String.valueOf(userModified.getAge()));
            }else if (ageEditText.getText()!=null && !ageEditText.getText().toString().isEmpty()){
                userModified.setAge(Integer.parseInt(ageEditText.getText().toString()));
                mListener.setAge(Integer.parseInt(ageEditText.getText().toString()));
            }
            Log.d(TAG, "onCreateView: " + "before checking getDob()");
            if(userModified.getDob()!=null){
                dobTextView.setText(userModified.getDob());
            }else if (dobTextView.getText()!=null){
                userModified.setDob(dobTextView.getText().toString());
            }
            if(userModified.getCountry()!=null){
                countryTextView.setText(userModified.getCountry());
            }else if (countryTextView.getText()!=null){
                userModified.setDob(countryTextView.getText().toString());
            }
            Log.d(TAG, "onCreateView: " + "before checking getName()");
            if(userModified.getName()!=null){
                nameEditText.setText(userModified.getName());
            }else if (nameEditText.getText()!=null){
                userModified.setName(nameEditText.getText().toString());
                mListener.setName(nameEditText.getText().toString());
            }

        }

        // Set onClickListener for selectCountryButton
        selectCountryButton.setOnClickListener(v -> {
            // Show single-choice AlertDialog to select country
            // Array of countries
            userModified.setName(nameEditText.getText().toString());
            userModified.setEmail(emailEditText.getText().toString());
            userModified.setAge(Integer.parseInt(ageEditText.getText().toString()));
            mListener.setUser(userModified);
            mListener.switchFragment(SelectCountryFragment.newInstance(userModified));

        });



        // Set onClickListener for selectDobButton
        selectDobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // startActivityForResult(new Intent(CreateUserActivity.this, SelectDoBActivity.class), Activity.REQUEST_SELECT_DOB);
                //CreateUserFragment.FragmentSwitcher listener = (CreateUserFragment.FragmentSwitcher) getActivity();
                //assert listener != null;
                mListener.setUser(userModified);
                mListener.switchFragment(SelectDoBFragment.newInstance(userModified));

            }
        });

       // mListener.setUser(user);
       // dobTextView.setText(user.getDob());
        // Set onClickListener for submitButton
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String age = ageEditText.getText().toString();
                String country = countryTextView.getText().toString();
                String dob = dobTextView.getText().toString();

                if (name.isEmpty() || email.isEmpty() || age.isEmpty()) {
                    Toast.makeText(getActivity(), "All fields are required", Toast.LENGTH_SHORT).show();
                } else if (country.equals("N/A Select") || dob.equals("N/A Select")) {
                    Toast.makeText(getActivity(), "Country and DoB are required", Toast.LENGTH_SHORT).show();
                } else {
                    User user = new User(name, email, Integer.parseInt(age), country, dob);
                    //FragmentSwitcher listener = (CreateUserFragment.FragmentSwitcher) getActivity();
                    //assert listener != null;
                    mListener.setUser(user);
                    mListener.newProfileFragment(user);
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (CreateUserFragment.FragmentSwitcher) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement FragmentSwitcher");
        }

    }

    private FragmentSwitcher mListener;
    public interface FragmentSwitcher {
        void newProfileFragment(User user);
        void setUser(User user);

        void switchFragment(Fragment fragment);


        User getUser();

        void setEmail(String email);
        void setAge(int age);
        void setName(String name);




    }

   /* public void setUser(User user) {
        this.user = user;
    }*/
}