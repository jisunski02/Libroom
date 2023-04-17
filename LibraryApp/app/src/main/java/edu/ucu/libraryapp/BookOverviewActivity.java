package edu.ucu.libraryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import edu.ucu.libraryapp.databinding.ActivityBookOverviewBinding;
import edu.ucu.libraryapp.sharedpreferences.LoginSharedPrefManager;
import edu.ucu.libraryapp.volleyrequesthandler.RequestHandler;


public class BookOverviewActivity extends AppCompatActivity {

    ActivityBookOverviewBinding binding;
    LoginSharedPrefManager loginSharedPrefManager;
    Dialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookOverviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginSharedPrefManager = new LoginSharedPrefManager(this);

        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title");
        String author = bundle.getString("author");
        String section = bundle.getString("section");
        String sub_section = bundle.getString("sub_section");
        String dewey_decimal = bundle.getString("dewey_decimal");
        String dewey_decimal_sub = bundle.getString("dewey_decimal_sub");
        String edition = bundle.getString("edition");
        String volume = bundle.getString("volume");
        String publisher = bundle.getString("publisher");
        String pub_date = bundle.getString("pub_date");
        String num_pages = bundle.getString("num_pages");
        String status = bundle.getString("status");
        String accession_no = bundle.getString("accession_no");
        String title_id = bundle.getString("title_id");


        binding.tvTitle.setText(title);
        binding.tvAuthor.setText(String.format("by %s", author));
        binding.tvSection.setText(section);
        binding.tvSubSection.setText(sub_section);
        binding.tvEdition.setText(edition);
        binding.tvVolume.setText(volume);
        binding.tvPublisher.setText(publisher);
        binding.tvPublicationDate.setText(pub_date);
        binding.tvNumOfPages.setText(num_pages);
        binding.tvDeweyDecimalSection.setText(dewey_decimal);
        binding.tvDeweyDecimalSubSection.setText(dewey_decimal_sub);

        binding.btnBorrow.setOnClickListener(view -> {
            if(loginSharedPrefManager.isLoggedIn()){

                loadingDialog = new Dialog(BookOverviewActivity.this);
                loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//...........
                loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                loadingDialog.setContentView(R.layout.dialog_loading);

                TextView title_ = loadingDialog.findViewById(R.id.title);
                title_.setText("Submitting book reservation...");

                loadingDialog.setCancelable(false);
                loadingDialog.show();

                borrowBook(accession_no, title_id, loginSharedPrefManager.getStudentId());
            }

            else {
                Dialog proceedLoginDialog = new Dialog(this);
                proceedLoginDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//...........
                proceedLoginDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                proceedLoginDialog.setContentView(R.layout.dialog_loginnow);

                Button proceedToLogin = proceedLoginDialog.findViewById(R.id.btn_proceed_tologin);

                proceedToLogin.setOnClickListener(v->{
                    AppUtils.gotoActivity(BookOverviewActivity.this, LoginActivity.class);
                });

                proceedLoginDialog.setCancelable(true);
                proceedLoginDialog.show();
            }
        });

        binding.cvClose.setOnClickListener(view -> finish());
    }

    private void borrowBook(String accession_no, String title_id, String student_id){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                AppUtils.RESERVE_BOOK_ENDPOINT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String result = jsonObject.getString("success");

                            if(result.equals("1")){
                                loadingDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Reserved successful", Toast.LENGTH_SHORT).show();
                                AppUtils.gotoActivity(BookOverviewActivity.this, HomeActivity.class);
                                finish();
                                finishAffinity();
                            }

                            else{
                                loadingDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Book already reserved", Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("anyText", response);
                            Toast.makeText(getApplicationContext(), "Reservation Fail \n" + response.toString(), Toast.LENGTH_SHORT).show();
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
                params.put("accession_no", accession_no);
                params.put("title_id", title_id);
                params.put("student_id", student_id);
                return checkParams(params);
            }
        };


            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }

    private Map<String, String> checkParams(Map<String, String> map){
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> pairs = (Map.Entry<String, String>)it.next();
            if(pairs.getValue()==null){
                map.put(pairs.getKey(), "");
            }
        }
        return map;
    }
}