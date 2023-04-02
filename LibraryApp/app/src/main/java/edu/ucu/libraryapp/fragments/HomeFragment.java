package edu.ucu.libraryapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false);

        binding.linearLoading.setVisibility(View.VISIBLE);

        binding.rvSection.setLayoutManager(new GridLayoutManager(getContext(), 2));

        viewSection();
        return binding.getRoot();

    }

    private void viewSection() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppUtils.VIEW_SECTION_ENDPOINT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

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

                            sectionAdapter = new SectionAdapter(getContext(), sectionDataModelList);
                            binding.rvSection.setAdapter(sectionAdapter);

                            sectionAdapter.setOnItemClickListener(new OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {
                                    SectionDataModel sectionDataModel = sectionDataModelList.get(position);

                                    Fragment fragmentHandler = new SubSectionFragment();
                                    Bundle args = new Bundle();
                                    args.putString("sectionID", sectionDataModel.getSectionID());
                                    args.putString("sectionName", sectionDataModel.getSectionName());
                                    args.putString("deweyDecimal", sectionDataModel.getDeweyDecimal());
                                    fragmentHandler.setArguments(args);
                                    AppUtils.openFragment(fragmentHandler, "", getActivity());

                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("anyText",response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        Volley.newRequestQueue(getContext()).add(stringRequest);
    }
}