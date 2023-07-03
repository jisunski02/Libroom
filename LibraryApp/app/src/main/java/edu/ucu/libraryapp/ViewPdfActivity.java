package edu.ucu.libraryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.ucu.libraryapp.databinding.ActivityViewPdfBinding;

public class ViewPdfActivity extends AppCompatActivity {

    ActivityViewPdfBinding binding;
    String url_pdf = "https://lib-room.online/assets/ebook/";
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewPdfBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String fileName = getIntent().getStringExtra("file_name");

        binding.cvClose.setOnClickListener(v->{
            finish();
        });

        new ViewPdfActivity.RetrievePdf().execute(url_pdf+fileName);
    }

    class RetrievePdf extends AsyncTask<String, Void, InputStream>{

        @Override
        protected void onPreExecute() {
            Toast.makeText(ViewPdfActivity.this, "Loading Pdf...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                if(httpURLConnection.getResponseCode()==200){
                    inputStream = new BufferedInputStream(httpURLConnection.getInputStream());

                }
            }
            catch (IOException e){
                    return null;
            }

            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            binding.viewPdfFile.fromStream(inputStream).load();
        }
    }
}