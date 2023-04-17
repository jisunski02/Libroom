package edu.ucu.libraryapp.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.ucu.libraryapp.AppUtils;
import edu.ucu.libraryapp.adapters.SectionAdapter;
import edu.ucu.libraryapp.databinding.FragmentHomeBinding;
import edu.ucu.libraryapp.datamodels.SectionDataModel;
import edu.ucu.libraryapp.interfaces.OnItemClickListener;

public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    private List<SectionDataModel> sectionDataModelList = new ArrayList<>();
    private SectionAdapter sectionAdapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater,container,false);

        viewSection();

        return binding.getRoot();

    }

    private void viewSection() {

        binding.linearLoading.setVisibility(View.VISIBLE);

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
                        binding.linearLoading.setVisibility(View.GONE);

                        binding.rvSection.setLayoutManager(new GridLayoutManager(getContext(), 2));
                        sectionAdapter = new SectionAdapter(getContext(), sectionDataModelList);
                        binding.rvSection.setAdapter(sectionAdapter);

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


}