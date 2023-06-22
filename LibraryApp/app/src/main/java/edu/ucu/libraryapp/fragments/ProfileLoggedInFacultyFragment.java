package edu.ucu.libraryapp.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import edu.ucu.libraryapp.AppUtils;
import edu.ucu.libraryapp.HomeActivity;
import edu.ucu.libraryapp.R;
import edu.ucu.libraryapp.databinding.DialogLogoutBinding;
import edu.ucu.libraryapp.databinding.FragmentProfileloggedinfacultyBinding;
import edu.ucu.libraryapp.databinding.FragmentProfileloggedinstudentBinding;
import edu.ucu.libraryapp.sharedpreferences.LoginSharedPrefManager;

public class ProfileLoggedInFacultyFragment extends Fragment {

    FragmentProfileloggedinfacultyBinding binding;
    LoginSharedPrefManager loginSharedPrefManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileloggedinfacultyBinding.inflate(inflater, container, false);

        loginSharedPrefManager = new LoginSharedPrefManager(requireActivity());

        if(loginSharedPrefManager.getFacultyNo() != null){
            binding.linearLoading.setVisibility(View.GONE);
            binding.facultyNo.setText(loginSharedPrefManager.getFacultyNo());
            binding.firstName.setText(loginSharedPrefManager.getFacultyFirstName());
            binding.middleName.setText(loginSharedPrefManager.getFacultyMiddleName());
            binding.lastName.setText(loginSharedPrefManager.getFacultyLastName());
            binding.gender.setText(loginSharedPrefManager.getFacultyGender());
            binding.emailAddress.setText(loginSharedPrefManager.getFacultyEmailAddress());

            if (loginSharedPrefManager.getFacultyStatus().equals("0")){
                binding.status.setText("Active");
            }

            else {
                binding.status.setText("Deactivated");
            }


        }

        else {
            binding.linearProfile.setVisibility(View.GONE);
            //getStudentProfile();
        }

        binding.btnLogout.setOnClickListener(v->{
            Dialog dialogLogout = new Dialog(requireContext());
            DialogLogoutBinding binding = DialogLogoutBinding.inflate(getLayoutInflater());
            dialogLogout.requestWindowFeature(Window.FEATURE_NO_TITLE);//...........
            dialogLogout.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogLogout.setContentView(binding.getRoot());

            binding.btnYes.setOnClickListener(v2->{
                dialogLogout.dismiss();
                loginSharedPrefManager.logOutFaculty();
                Toast.makeText(requireActivity(), "Logged out successful", Toast.LENGTH_SHORT).show();
                AppUtils.gotoActivity(requireActivity(), HomeActivity.class);
            });

            binding.btnNo.setOnClickListener(v3->{
                dialogLogout.dismiss();
            });

            dialogLogout.setCancelable(true);
            dialogLogout.show();

        });

        return binding.getRoot();

    }


}