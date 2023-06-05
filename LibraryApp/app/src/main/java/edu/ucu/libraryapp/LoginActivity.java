package edu.ucu.libraryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.ucu.libraryapp.databinding.ActivityLoginBinding;
import edu.ucu.libraryapp.datamodels.StudentDataModel;
import edu.ucu.libraryapp.sharedpreferences.LoginSharedPrefManager;
import edu.ucu.libraryapp.volleyrequesthandler.RequestHandler;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    Dialog loadingDialog;
    LoginSharedPrefManager loginSharedPrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginSharedPrefManager = new LoginSharedPrefManager(this);

        binding.btnLogin.setOnClickListener(v->{
            if(!isValidUsername() | !isValidPassword()){
                return;
            }

                loadingDialog = new Dialog(LoginActivity.this);
                loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//...........
                loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                loadingDialog.setContentView(R.layout.dialog_loading);

                TextView title = loadingDialog.findViewById(R.id.title);
                title.setText("Logging in...");

                loadingDialog.setCancelable(false);
                loadingDialog.show();

                logInStudent();

        });

    }

    private void logInStudent(){
        final String username = binding.username.getText().toString().trim();
        final String password = binding.studentPassword.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                AppUtils.LOGIN_STUDENT_ENDPOINT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            String message = jsonObject.getString("message");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            if(message.equals("success")){
                                for(int a = 0; a <= jsonArray.length(); a++){
                                    JSONObject object = jsonArray.getJSONObject(a);

                                    String id = object.getString("id");
                                    String username = object.getString("username");
                                    String password = object.getString("password");
                                    String status = object.getString("status");
                                    String type = object.getString("type");

                                    if(password.equals(username)){
                                        loadingDialog.dismiss();
                                        Toast.makeText(LoginActivity.this, "Reset Password", Toast.LENGTH_LONG).show();
                                    }

                                    else if(status.equals("1")){
                                        Toast.makeText(LoginActivity.this, "Your account has been deactivited", Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        if(type.equals("student")){
                                            loginSharedPrefManager.setKeyUserType(type);
                                            loginSharedPrefManager.createStudentLoginSession(username);
                                            getStudentProfile();
                                        }

                                        if(type.equals("faculty")){
                                            loginSharedPrefManager.setKeyUserType(type);
                                            loginSharedPrefManager.createFacultyLoginSession(username);
                                            getFacultyProfile();
                                        }

                                        if(type.equals("admin")){
                                            Toast.makeText(LoginActivity.this, "No Account found.", Toast.LENGTH_LONG).show();
                                        }
                                    }

                                }
                            }

                            if(success.equals("0")) {
                                loadingDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "No account found", Toast.LENGTH_LONG).show();
                                Log.e("Response", response);
                            }


                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                            loadingDialog.dismiss();
                            Log.e("anyText", response+" "+ loginSharedPrefManager.getStudentId());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            //This indicates that the reuest has either time out or there is no connection
                            loadingDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof ServerError) {
                            //Indicates that the server responded with a error response
                            Toast.makeText(getApplicationContext(), "Server unavailable at the moment", Toast.LENGTH_SHORT).show();

                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    private boolean isValidUsername(){
        String username = binding.username.getText().toString().trim();
        if(username.isEmpty()){
            binding.username.setError("Username is required");
            return false;
        }

        else{
            binding.username.setError(null);
            return true;
        }
    }

    private boolean isValidPassword(){
        String password = binding.studentPassword.getText().toString().trim();
        if(password.isEmpty()){
            binding.studentPassword.setError("Password is required");
            return false;
        }

        else{
            binding.studentPassword.setError(null);
            return true;
        }
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
                        Log.e("error", error.toString());
                    }
                    else if (error instanceof ServerError) {
                        //Indicates that the server responded with a error response
                        Log.e("error", error.toString());

                    }

                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
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

            }
            loadingDialog.dismiss();
            AppUtils.gotoActivity(LoginActivity.this, HomeActivity.class);
            finish();
            finishAffinity();
            Log.e("id", response+" "+ loginSharedPrefManager.getStudentId());


            Log.e("studentno", response+" "+ loginSharedPrefManager.getStudentNo());
        }
        catch (JSONException e) {
            Log.e("Error here", e.toString());
        }

    }

    private void getFacultyProfile() {
        String facultyNo = loginSharedPrefManager.getFacultyNo();
        String viewStudentProfileURL = AppUtils.VIEW_FACULTY_PROFILE_ENDPOINT+ "employee_no="+facultyNo;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, viewStudentProfileURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                viewFacultyProfile(response);
            }
        },
                error -> {
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        //This indicates that the reuest has either time out or there is no connection
                        Log.e("error", error.toString());
                    }
                    else if (error instanceof ServerError) {
                        //Indicates that the server responded with a error response
                        Log.e("error", error.toString());

                    }

                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public void viewFacultyProfile(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("tbl_faculty");

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String facultyID = jo.getString("facultyID");
                String facultyFirstName = jo.getString("facultyFirstName");
                String facultyMiddleName = jo.getString("facultyMiddleName");
                String facultyLastName = jo.getString("facultyLastName");
                String facultyGender = jo.getString("facultyGender");
                String facultyEmailAddress = jo.getString("facultyEmailAddress");
                String facultyStatus = jo.getString("facultyStatus");

                loginSharedPrefManager.setFacultyId(facultyID);
                loginSharedPrefManager.setFacultyFirstName(facultyFirstName);
                loginSharedPrefManager.setFacultyMiddleName(facultyMiddleName);
                loginSharedPrefManager.setFacultyLastName(facultyLastName);
                loginSharedPrefManager.setFacultyGender(facultyGender);
                loginSharedPrefManager.setFacultyEmailAddress(facultyEmailAddress);
                loginSharedPrefManager.setFacultyStatus(facultyStatus);

            }
            loadingDialog.dismiss();
            AppUtils.gotoActivity(LoginActivity.this, HomeActivity.class);
            finish();
            finishAffinity();
            Log.e("id", response+" "+ loginSharedPrefManager.getStudentId());


            Log.e("studentno", response+" "+ loginSharedPrefManager.getStudentNo());
        }
        catch (JSONException e) {
            Log.e("Error here", e.toString());
        }

    }

}
