package edu.ucu.libraryapp.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.ucu.libraryapp.AppUtils;
import edu.ucu.libraryapp.BookOverviewActivity;
import edu.ucu.libraryapp.R;
import edu.ucu.libraryapp.adapters.PdfAdapter;
import edu.ucu.libraryapp.adapters.SectionAdapter;
import edu.ucu.libraryapp.databinding.FragmentHomeBinding;
import edu.ucu.libraryapp.datamodels.PDFDataModel;
import edu.ucu.libraryapp.datamodels.SectionDataModel;

public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    private List<SectionDataModel> sectionDataModelList = new ArrayList<>();
    private SectionAdapter sectionAdapter;

    private List<PDFDataModel> pdfDataModelList = new ArrayList<>();
    private PdfAdapter pdfAdapter;
    boolean loadedTvSection;
    boolean loadedPdfSection;
    Dialog loadingDialog;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater,container,false);

        viewSection();
        loadedTvSection = true;

        binding.tvSection.setOnClickListener(v->{
            binding.tvSection.setTextColor(Color.WHITE);
            binding.tvSection.setBackgroundResource(R.drawable.bg_button);
            binding.pdfSection.setTextColor(Color.BLACK);
            binding.pdfSection.setBackgroundResource(R.drawable.bg_button2);
            viewSection();
        });

        binding.pdfSection.setOnClickListener(v->{
            binding.pdfSection.setTextColor(Color.WHITE);
            binding.pdfSection.setBackgroundResource(R.drawable.bg_button);
            binding.tvSection.setTextColor(Color.BLACK);
            binding.tvSection.setBackgroundResource(R.drawable.bg_button2);
            viewPDF();
        });

        return binding.getRoot();

    }

    private void viewSection() {
        binding.rvSection.setAdapter(null);
        sectionDataModelList.clear();
        showDialogLoading("Loading book sections...");

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


                        binding.rvSection.setLayoutManager(new GridLayoutManager(getContext(), 2));
                        sectionAdapter = new SectionAdapter(getContext(), sectionDataModelList);
                        binding.rvSection.setAdapter(sectionAdapter);

                        loadingDialog.dismiss();


                        sectionAdapter.setOnItemClickListener(position -> {
                            SectionDataModel sectionDataModel = sectionDataModelList.get(position);

                            Fragment fragmentHandler = new SubSectionFragment();
                            Bundle args = new Bundle();
                            args.putString("sectionID", sectionDataModel.getSectionID());
                            args.putString("sectionName", sectionDataModel.getSectionName());
                            args.putString("deweyDecimal", sectionDataModel.getDeweyDecimal());
                            fragmentHandler.setArguments(args);
                            AppUtils.openFragment(fragmentHandler, "", getActivity());

                        });

                    }
                    catch (JSONException e) {
                        Log.e("ParseFail", e.toString());
                        binding.linearLoading.setVisibility(View.GONE);
                        binding.linearApi.setVisibility(View.VISIBLE);
                    }
                },
                error -> {
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Log.e("TimeoutOrNoconnection", error.toString());
                        binding.linearNointernet.setVisibility(View.VISIBLE);
                    }
                    else if (error instanceof ServerError) {
                        Log.e("PHPERROR", error.toString());
                        binding.linearApi.setVisibility(View.VISIBLE);


                    }
                    binding.linearLoading.setVisibility(View.GONE);

                }) {

        };

        Volley.newRequestQueue(requireActivity()).add(stringRequest);
    }

    private void viewPDF() {
        binding.rvSection.setAdapter(null);
        pdfDataModelList.clear();
        showDialogLoading("Loading pdf sections...");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppUtils.VIEW_PDF_ENDPOINT,
                response -> {

                    try {
                        JSONArray section = new JSONArray(response);

                        for (int i = 0; i<section.length(); i++) {


                            JSONObject sectionObject = section.getJSONObject(i);

                            String pdfId = sectionObject.getString("pdfId");
                            String pdfTitle = sectionObject.getString("pdfTitle");
                            String pdfFileName = sectionObject.getString("pdfFileName");

                            PDFDataModel pdfDataModel = new PDFDataModel(pdfId,pdfTitle,pdfFileName);

                            pdfDataModelList.add(pdfDataModel);

                        }

                        binding.rvSection.setLayoutManager(new LinearLayoutManager(getContext()));
                        pdfAdapter = new PdfAdapter(getContext(), pdfDataModelList);
                        binding.rvSection.setAdapter(pdfAdapter);

                        loadingDialog.dismiss();

                    }
                    catch (JSONException e) {
                        Log.e("ParseFail", e.toString());
                        binding.linearLoading.setVisibility(View.GONE);
                        binding.linearApi.setVisibility(View.VISIBLE);
                    }
                },
                error -> {
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Log.e("TimeoutOrNoconnection", error.toString());
                        binding.linearNointernet.setVisibility(View.VISIBLE);
                    }
                    else if (error instanceof ServerError) {
                        Log.e("PHPERROR", error.toString());
                        binding.linearApi.setVisibility(View.VISIBLE);


                    }
                    binding.linearLoading.setVisibility(View.GONE);

                }) {

        };

        Volley.newRequestQueue(requireActivity()).add(stringRequest);
    }


    private void showDialogLoading(String message){
        loadingDialog = new Dialog(requireActivity());
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//...........
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadingDialog.setContentView(R.layout.dialog_loading);

        TextView title_ = loadingDialog.findViewById(R.id.title);
        title_.setText(message);

        loadingDialog.setCancelable(false);
        loadingDialog.show();
    }

}