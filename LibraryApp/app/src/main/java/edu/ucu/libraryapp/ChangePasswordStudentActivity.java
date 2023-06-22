package edu.ucu.libraryapp;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.ucu.libraryapp.databinding.ActivityChangePasswordBinding;
import edu.ucu.libraryapp.sharedpreferences.LoginSharedPrefManager;

public class ChangePasswordStudentActivity extends AppCompatActivity {

    ActivityChangePasswordBinding binding;
    Dialog loadingDialog;

    LoginSharedPrefManager loginSharedPrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginSharedPrefManager = new LoginSharedPrefManager(this);

        binding.btnUpdatePassword.setOnClickListener(v->{
            if(!validatePassword()){
                return;
            }

        });

    }

    private boolean validatePassword(){
        String userType = loginSharedPrefManager.getKeyUserType();
        String studentNo = loginSharedPrefManager.getStudentNo();

        String currentPassword = loginSharedPrefManager.getStudentPassword();
        String etCurrentPassword = binding.currentPassword.getText().toString();
        String newPassword = binding.newPassword.getText().toString();
        String reenterNewPassword = binding.reenterNewPassword.getText().toString();

        if(etCurrentPassword.isEmpty()){
            binding.currentPassword.setText("Password field is required");
            return false;
        }

        else if(studentNo.equals(newPassword) && studentNo.equals(reenterNewPassword)){
            binding.newPasswordError.setText("Password must not be the same with your username");
            binding.currentPasswordError.setText("Password must not be the same with your username");
            binding.rePasswordError.setText("Password must not be the same with your username");
            return false;
        }

        else if(currentPassword.equals(newPassword) && currentPassword.equals(reenterNewPassword)){
            binding.newPasswordError.setText("Password must not be the same with your current password");
            binding.currentPasswordError.setText("Password must not be the same with current password");
            binding.rePasswordError.setText("Password must not be the same with current password");
            return false;
        }


        else if(newPassword.isEmpty()){
            binding.rePasswordError.setText("Password field is required");
            return false;
        }
        else if(newPassword.length() < 8){
            binding.newPasswordError.setText("Password too short");
            return false;
        }

        else if(reenterNewPassword.length() < 8){
            binding.rePasswordError.setText("Password too short");
            return false;
        }

        else if(!newPassword.equals(reenterNewPassword)){
            binding.currentPasswordError.setText(null);
            binding.newPasswordError.setText("Password doesn't match");
            binding.rePasswordError.setText("Password doesn't match");
            return false;
        }
        showDialog();
        updatePassword(studentNo, newPassword, userType);
        binding.currentPasswordError.setText(null);
        binding.newPasswordError.setText(null);
        binding.rePasswordError.setText(null);
        return true;
    }

    private void showDialog(){
        loadingDialog = new Dialog(ChangePasswordStudentActivity.this);
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//...........
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadingDialog.setContentView(R.layout.dialog_loading);

        TextView title_ = loadingDialog.findViewById(R.id.title);
        title_.setText("Updating password please wait...");

        loadingDialog.setCancelable(false);
        loadingDialog.show();

    }
    private void updatePassword(String username, String password, String type){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUtils.UPDATE_PASSWORD_ENDPOINT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String result = jsonObject.getString("success");

                            if(result.equals("1")){
                                AppUtils.toastMessage(ChangePasswordStudentActivity.this,"Update password successful");
                                finish();
                            }

                            else{
                                Log.e("log", response);
                            }
                        }
                        catch(Exception jsonException){
                            jsonException.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
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
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                params.put("type", type);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}