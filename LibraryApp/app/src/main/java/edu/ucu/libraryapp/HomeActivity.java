package edu.ucu.libraryapp;

import static edu.ucu.libraryapp.AppUtils.openFragment;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import edu.ucu.libraryapp.databinding.ActivityHomeBinding;
import edu.ucu.libraryapp.datamodels.SectionDataModel;
import edu.ucu.libraryapp.fragments.HistoryFragment;
import edu.ucu.libraryapp.fragments.HistoryLoggedinFragment;
import edu.ucu.libraryapp.fragments.HomeFragment;
import edu.ucu.libraryapp.fragments.ProfileLoggedInFragment;
import edu.ucu.libraryapp.fragments.ProfileNotLoggedInFragment;
import edu.ucu.libraryapp.fragments.SubSectionFragment;
import edu.ucu.libraryapp.sharedpreferences.LoginSharedPrefManager;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private Fragment fragmentHandler = null;

    List<SectionDataModel> sectionDataModelList = new ArrayList<>();

    String homeSectionID;
    String homeSectionName;
    String deweyDecimal;
    Dialog loadingDialog;

    LoginSharedPrefManager loginSharedPrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginSharedPrefManager = new LoginSharedPrefManager(this);

        setSupportActionBar(binding.toolbar.appToolbar);

        binding.toolbar.activityTitle.setText("Welcome to LibRoom");

        showBottomNavigationView();

        if(savedInstanceState == null){
            fragmentHandler = new HomeFragment();
            openFragment(fragmentHandler, "Home", this);
            binding.navView.getMenu().findItem(R.id.bottom_navigation_home).setChecked(true);
        }

    }

    @SuppressLint("NonConstantResourceId")
    private void showBottomNavigationView(){

        binding.navView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){

                case R.id.bottom_navigation_home:
                    fragmentHandler = new HomeFragment();
                    openFragment(fragmentHandler, "Home", this);
                    binding.navView.getMenu().findItem(R.id.bottom_navigation_home).setChecked(true);
                    break;
                case R.id.bottom_navigation_history:
                    if(loginSharedPrefManager.isLoggedIn()){
                        fragmentHandler = new HistoryLoggedinFragment();
                    }
                    else{
                        fragmentHandler = new HistoryFragment();
                    }
                    openFragment(fragmentHandler, "History", this);
                    binding.navView.getMenu().findItem(R.id.bottom_navigation_history).setChecked(true);

                    break;
                case R.id.bottom_navigation_scan_qr:
                    binding.navView.getMenu().findItem(R.id.bottom_navigation_scan_qr).setChecked(true);
                    scanQRCode();
                    break;
                case R.id.bottom_navigation_profile:
                    if(loginSharedPrefManager.isLoggedIn()){
                        fragmentHandler = new ProfileLoggedInFragment();
                    }
                    else{
                        fragmentHandler = new ProfileNotLoggedInFragment();
                    }
                    openFragment(fragmentHandler, "Profile", this);
                    binding.navView.getMenu().findItem(R.id.bottom_navigation_profile).setChecked(true);

                    break;

            }
            return true;
        });
    }

    private void scanQRCode(){

        ScanOptions scanOptions = new ScanOptions();
        scanOptions.setPrompt("Volume up to flash on");
        scanOptions.setBeepEnabled(true);
        scanOptions.setOrientationLocked(true);
        scanOptions.setCaptureActivity(CaptureQRActivity.class);
        launcher.launch(scanOptions);

    }

        ActivityResultLauncher<ScanOptions> launcher = registerForActivityResult(new ScanContract(), result ->
        {
            if(result.getContents() != null){


                binding.navView.getMenu().findItem(R.id.bottom_navigation_home).setChecked(true);

                loadingDialog = new Dialog(this);
                loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//...........
                loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                loadingDialog.setContentView(R.layout.dialog_loading);
                loadingDialog.setCancelable(false);
                loadingDialog.show();

                if(result.getContents().equals("000") || result.getContents().equals("100") ||
                        result.getContents().equals("200") ||result.getContents().equals("300") ||
                        result.getContents().equals("400") ||result.getContents().equals("500") ||
                        result.getContents().equals("600") ||result.getContents().equals("700") ||
                        result.getContents().equals("800") ||result.getContents().equals("900")){

                    viewSection(result.getContents());
                }
                else{
                    loadingDialog.dismiss();
                    Toast.makeText(HomeActivity.this, "Invalid QR code value.", Toast.LENGTH_SHORT).show();
                }

            }

        });

    private void viewSection(String resultContents) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppUtils.VIEW_SECTION_ENDPOINT,
                response -> {

                    try {

                        JSONArray section = new JSONArray(response);

                        for (int i = 0; i<section.length(); i++) {

                            JSONObject sectionObject = section.getJSONObject(i);

                            String sectionID = sectionObject.getString("sectionID");
                            String deweyDecimal = sectionObject.getString("deweyDecimal");
                            String sectionName = sectionObject.getString("sectionName");

                            SectionDataModel sectionDataModel = new SectionDataModel(sectionID,deweyDecimal,sectionName);

                            sectionDataModelList.add(sectionDataModel);

                        }

                        for(int list = 0; list<sectionDataModelList.size(); list++){
                            SectionDataModel sectionDataModel = sectionDataModelList.get(list);

                            if(sectionDataModel.getDeweyDecimal().equals(resultContents)) {
                                homeSectionID = sectionDataModel.getSectionID();
                                homeSectionName = sectionDataModel.getSectionName();
                                deweyDecimal = sectionDataModel.getDeweyDecimal();

                            }
                        }

                        if(deweyDecimal != null && homeSectionID !=null && homeSectionName != null){
                            Fragment fragmentHandler = new SubSectionFragment();
                            Bundle args = new Bundle();
                            args.putString("sectionID", homeSectionID);
                            args.putString("sectionName", homeSectionName);
                            args.putString("deweyDecimal", deweyDecimal);
                            fragmentHandler.setArguments(args);
                            openFragment(fragmentHandler, "Home", HomeActivity.this);
                        }
                        else {
                            Toast.makeText(HomeActivity.this, "Invalid QR code value.", Toast.LENGTH_SHORT).show();
                        }

                        loadingDialog.dismiss();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("anyText",response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        Volley.newRequestQueue(this).add(stringRequest);
    }

}