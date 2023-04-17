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
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.ucu.libraryapp.databinding.ActivityLoginBinding;
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

                            if(success.equals("1")){
                                for(int a = 0; a <= jsonArray.length(); a++){
                                    JSONObject object = jsonArray.getJSONObject(a);

                                    String username = object.getString("username");
                                    String password = object.getString("password");
                                    String status = object.getString("status");

                                    loginSharedPrefManager.createStudentLoginSession(username);

                                    if(password.equals(username)){
                                        loadingDialog.dismiss();
                                        Toast.makeText(LoginActivity.this, "Reset Password", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        finish();
                                        finishAffinity();
                                        Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                                        loadingDialog.dismiss();
                                        AppUtils.gotoActivity(LoginActivity.this, HomeActivity.class);
                                    }
                                    /*SharedPrefManager.getInstance(getApplicationContext())
                                            .userLogin(id, username, email, password, firstname, lastname, address, phonenumber, status);
                                     Toast.makeText(getApplicationContext(), "Login success", Toast.LENGTH_SHORT).show();

                                    finish();
                                    finishAffinity(); */
                                }
                            }

                            if(success.equals("0")) {
                                loadingDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "No account found", Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("anyText", response);
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


}
