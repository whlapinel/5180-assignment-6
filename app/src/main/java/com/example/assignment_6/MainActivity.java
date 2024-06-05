package com.example.assignment_6;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.assignment_6.SelectCountryFragment.FragmentSwitcher;

public class MainActivity extends AppCompatActivity implements
        MainFragment.FragmentSwitcher,
        CreateUserFragment.FragmentSwitcher,
        SelectDoBFragment.FragmentSwitcher,
        SelectCountryFragment.FragmentSwitcher {
    private static final String TAG = "MainActivity";

    private FragmentManager fm;
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.main, MainFragment.newInstance(user))
                .commit();
    }


    @Override
    public void switchFragment(Fragment fragment) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.main, fragment, fragment.getClass().getName());
        ft.addToBackStack(fragment.getClass().getName());
        ft.commit();
    }


    public void newProfileFragment(User user) {
        Fragment profileFragment = ProfileFragment.newInstance(user);
        switchFragment(profileFragment);
    }


    public void goToUpdatedProfile() {
        Fragment createFragment = CreateUserFragment.newInstance(user);
        switchFragment(createFragment);
    }

    public void setUser(User user) {
        //this.user = user;
        if (null != user) {
            if (user.getName() != null)
                this.user.setName(user.getName());
            if (user.getDob() != null)
                this.user.setDob(user.getDob());
            if (user.getAge() != null)
                this.user.setAge(user.getAge());
            if (user.getEmail() != null)
                this.user.setEmail(user.getEmail());
            if (user.getCountry() != null)
                this.user.setCountry(user.getCountry());
        }
        Log.d("user", "setUser: " + user.getCountry() + user.getDob() + user.getAge() + user.getEmail());
    }

    @Override
    public void popFragmentDOB() {
        fm.popBackStack();
    }

    @Override
    public void setDOB(String dob) {
        this.user.setDob(dob);
    }

    public void onCountrySelected(String country) {
        Log.d(TAG, "onCountrySelected: " + user.getName());

        this.user.setCountry(country);
    }


    public User getUser() {
        return this.user;
    }

    @Override
    public void setEmail(String email) {
        this.user.setEmail(email);
    }

    @Override
    public void setAge(int age) {
        this.user.setAge(age);
    }

    @Override
    public void setName(String name) {
        this.user.setName(name);
    }
}