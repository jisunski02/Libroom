package edu.ucu.libraryapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.ucu.libraryapp.AppUtils;
import edu.ucu.libraryapp.HomeActivity;
import edu.ucu.libraryapp.databinding.FragmentProfileloggedinBinding;
import edu.ucu.libraryapp.sharedpreferences.LoginSharedPrefManager;

public class ProfileLoggedInFragment extends Fragment {

    FragmentProfileloggedinBinding binding;
    LoginSharedPrefManager loginSharedPrefManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileloggedinBinding.inflate(inflater, container, false);

        loginSharedPrefManager = new LoginSharedPrefManager(requireActivity());

        if(loginSharedPrefManager.getStudentFirstName() != null){
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
            getStudentProfile();
        }

        binding.btnLogout.setOnClickListener(v->{
            loginSharedPrefManager.logOutStudent();
            Toast.makeText(requireActivity(), "Logged out successful", Toast.LENGTH_SHORT).show();
            AppUtils.gotoActivity(requireActivity(), HomeActivity.class);
        });

        return binding.getRoot();

    }

    private void getStudentProfile() {
        String studentNo = loginSharedPrefManager.getStudentNo();
        String viewStudentProfileURL = AppUtils.VIEW_STUDENT_PROFILE_ENDPOINT+ "student_no="+studentNo;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, viewStudentProfileURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                viewStudentProfile(response);
            }
        },
                error -> {
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        //This indicates that the reuest has either time out or there is no connection
                        binding.linearNointernet.setVisibility(View.VISIBLE);
                    }
                    else if (error instanceof ServerError) {
                        //Indicates that the server responded with a error response
                        binding.linearApi.setVisibility(View.VISIBLE);

                    }
                    binding.linearLoading.setVisibility(View.GONE);
                });

        RequestQueue requestQueue = Volley.newRequestQueue(requireActivity());
        requestQueue.add(stringRequest);

    }

    public void viewStudentProfile(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("tbl_students");

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String studentID = jo.getString("studentID");
                String studentNo = jo.getString("studentNo");
                String studentFirstName = jo.getString("studentFirstName");
                String studentMiddleName = jo.getString("studentMiddleName");
                String studentLastName = jo.getString("studentLastName");
                String studentGender = jo.getString("studentGender");
                String studentBirthdate = jo.getString("studentBirthdate");
                String studentCourse = jo.getString("studentCourse");
                String studentContactNo = jo.getString("studentContactNo");
                String studentEmailAddress = jo.getString("studentEmailAddress");
                String studentAddress = jo.getString("studentAddress");
                String studentStatus = jo.getString("studentStatus");

                binding.studentNo.setText(studentNo);
                binding.firstName.setText(studentFirstName);
                binding.middleName.setText(studentMiddleName);
                binding.lastName.setText(studentLastName);
                binding.gender.setText(studentGender);
                binding.birthDate.setText(studentGender);
                binding.course.setText(studentCourse);
                binding.contactNo.setText(studentContactNo);
                binding.emailAddress.setText(studentEmailAddress);
                binding.address.setText(studentAddress);

                if (studentStatus.equals("0")){
                    binding.status.setText("Active");
                }

                else {
                    binding.status.setText("Deactivated");
                }

                loginSharedPrefManager.setStudentId(studentID);
                loginSharedPrefManager.setStudentFirstName(studentFirstName);
                loginSharedPrefManager.setStudentMiddleName(studentMiddleName);
                loginSharedPrefManager.setStudentLastName(studentLastName);
                loginSharedPrefManager.setStudentGender(studentGender);
                loginSharedPrefManager.setStudentBirthDate(studentBirthdate);
                loginSharedPrefManager.setStudentCourse(studentCourse);
                loginSharedPrefManager.setStudentContactNo(studentContactNo);
                loginSharedPrefManager.setStudentEmailAddress(studentEmailAddress);
                loginSharedPrefManager.setStudentAddress(studentAddress);
                loginSharedPrefManager.setStudentStatus(studentStatus);

                binding.linearProfile.setVisibility(View.VISIBLE);
                binding.linearLoading.setVisibility(View.GONE);
            }

        }
        catch (JSONException e) {
            Log.e("Error here", e.toString());
        }

    }
}