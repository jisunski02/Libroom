package edu.ucu.libraryapp.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import edu.ucu.libraryapp.AppUtils;
import edu.ucu.libraryapp.ChangePasswordStudentActivity;
import edu.ucu.libraryapp.HomeActivity;

import edu.ucu.libraryapp.databinding.DialogLogoutBinding;
import edu.ucu.libraryapp.databinding.FragmentProfileloggedinstudentBinding;
import edu.ucu.libraryapp.sharedpreferences.LoginSharedPrefManager;

public class ProfileLoggedInStudentFragment extends Fragment {

    FragmentProfileloggedinstudentBinding binding;
    LoginSharedPrefManager loginSharedPrefManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileloggedinstudentBinding.inflate(inflater, container, false);

        loginSharedPrefManager = new LoginSharedPrefManager(requireActivity());

        if(loginSharedPrefManager.getStudentNo() != null){
            binding.linearLoading.setVisibility(View.GONE);
            binding.studentNo.setText(loginSharedPrefManager.getStudentNo());
            binding.firstName.setText(loginSharedPrefManager.getStudentFirstName());
            binding.middleName.setText(loginSharedPrefManager.getStudentMiddleName());
            binding.lastName.setText(loginSharedPrefManager.getStudentLastName());
            binding.gender.setText(loginSharedPrefManager.getStudentGender());
            binding.birthDate.setText(loginSharedPrefManager.getStudentBirthDate());
            binding.course.setText(loginSharedPrefManager.getStudentCourse());
            binding.contactNo.setText(loginSharedPrefManager.getStudentContactNo());
            binding.emailAddress.setText(loginSharedPrefManager.getStudentEmailAddress());
            binding.address.setText(loginSharedPrefManager.getStudentAddress());

            if (loginSharedPrefManager.getStudentStatus().equals("0")){
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

        binding.btnChangePassword.setOnClickListener(v->{
            if(loginSharedPrefManager.getKeyUserType().equals("student")){
                AppUtils.gotoActivity(getActivity(), ChangePasswordStudentActivity.class);
            }
            else{
                AppUtils.gotoActivity(getActivity(), ChangePasswordStudentActivity.class);
            }


        });

        binding.btnLogout.setOnClickListener(v->{
            Dialog dialogLogout = new Dialog(requireContext());
            DialogLogoutBinding binding = DialogLogoutBinding.inflate(getLayoutInflater());
            dialogLogout.requestWindowFeature(Window.FEATURE_NO_TITLE);//...........
            dialogLogout.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialogLogout.setContentView(binding.getRoot());

            binding.btnYes.setOnClickListener(v2->{
                dialogLogout.dismiss();
                loginSharedPrefManager.logOutStudent();
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