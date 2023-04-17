package edu.ucu.libraryapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import edu.ucu.libraryapp.AppUtils;
import edu.ucu.libraryapp.LoginActivity;
import edu.ucu.libraryapp.databinding.FragmentProfilenotloggedinBinding;

public class ProfileNotLoggedInFragment extends Fragment {

    FragmentProfilenotloggedinBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfilenotloggedinBinding.inflate(inflater, container, false);

        binding.btnLogin.setOnClickListener(v->{
            AppUtils.gotoActivity(requireActivity(), LoginActivity.class);
        });

        return binding.getRoot();


    }
}