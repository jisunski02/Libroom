package edu.ucu.libraryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import edu.ucu.libraryapp.databinding.ActivityBookOverviewBinding;


public class BookOverviewActivity extends AppCompatActivity {

    ActivityBookOverviewBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookOverviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title");
        String author = bundle.getString("author");
        String section = bundle.getString("section");
        String sub_section = bundle.getString("sub_section");
        String edition = bundle.getString("edition");
        String volume = bundle.getString("volume");
        String publisher = bundle.getString("publisher");
        String pub_date = bundle.getString("pub_date");
        String num_pages = bundle.getString("num_pages");
        String status = bundle.getString("status");

        binding.tvTitle.setText(title);
        binding.tvAuthor.setText(author);
        binding.tvSection.setText(section);
        binding.tvSubSection.setText(sub_section);
        binding.tvEdition.setText(edition);
        binding.tvVolume.setText(volume);
        binding.tvPublisher.setText(publisher);
        binding.tvPublicationDate.setText(pub_date);
        binding.tvNumOfPages.setText(num_pages);

        if(status.equals("0")){
            binding.tvStatus.setText("Available");
        }
        if(status.equals("1")){
            binding.tvStatus.setText("Not Available");
        }
        if(status.equals("2")){
            binding.tvStatus.setText("Reserved");
        }


        binding.btnBorrow.setOnClickListener(view -> {
            AppUtils.toastMessage(this, "Under construction, this feature will be unlocked soon.");
        });

        binding.cvClose.setOnClickListener(view -> finish());
    }

}