package edu.ucu.libraryapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
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
import edu.ucu.libraryapp.BookOverviewActivity;
import edu.ucu.libraryapp.adapters.BookAdapter;
import edu.ucu.libraryapp.adapters.SubSectionAdapter;
import edu.ucu.libraryapp.databinding.FragmentSubsectionBinding;
import edu.ucu.libraryapp.datamodels.BookDataModel;
import edu.ucu.libraryapp.datamodels.SubSectionDataModel;
import edu.ucu.libraryapp.interfaces.OnItemClickListener;

public class SubSectionFragment extends Fragment {

    FragmentSubsectionBinding binding;
    private List<SubSectionDataModel> subSectionDataModelList = new ArrayList<>();
    private SubSectionAdapter subSectionAdapter;
    private List<BookDataModel> bookDataModelList = new ArrayList<>();
    private BookAdapter bookAdapter;
    private String homeSectionID;
    private String homeSectionName;
    private String deweyDecimal;

    boolean isSectionLoading = false;
    boolean isSubsectionLoading = false;
    private String subSectionIDCatcher;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSubsectionBinding.inflate(inflater, container, false);


        homeSectionID = getArguments().getString("sectionID");
        homeSectionName = getArguments().getString("sectionName");
        deweyDecimal = getArguments().getString("deweyDecimal");

        binding.tvSectionName.setText(homeSectionName);

        binding.rvSubsection.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvBooks.setLayoutManager(new GridLayoutManager(getContext(), 2));

        binding.linearLoading.setVisibility(View.VISIBLE);

        viewSubSection();
        isSectionLoading = true;
        getBookBySectionID();

        return binding.getRoot();
    }


    private void viewSubSection() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppUtils.VIEW_SUB_SECTION_ENDPOINT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //converting the string to json array object
                            JSONArray section = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < section.length(); i++) {

                                JSONObject sectionObject = section.getJSONObject(i);

                                String subSectionID = sectionObject.getString("subSectionID");
                                String deweyDecimal = sectionObject.getString("deweyDecimal");
                                String sectionID = sectionObject.getString("sectionID");
                                String subSectionName = sectionObject.getString("subSectionName");

                                SubSectionDataModel subSectionDataModel = new SubSectionDataModel(subSectionID, deweyDecimal, sectionID, subSectionName);

                                if (homeSectionID.equals(sectionID)) {
                                    subSectionDataModelList.add(subSectionDataModel);
                                }

                            }

                            subSectionAdapter = new SubSectionAdapter(getContext(), subSectionDataModelList);
                            binding.rvSubsection.setAdapter(subSectionAdapter);

                            subSectionAdapter.setOnItemClickListener(new OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {
                                    SubSectionDataModel subSectionDataModel = subSectionDataModelList.get(position);

                                    binding.tvSubSectionName.setText(subSectionDataModel.getSubSectionName());
                                    binding.tvSubSectionName.setVisibility(View.VISIBLE);
                                    binding.linearLoading.setVisibility(View.VISIBLE);
                                    binding.linearSubsection.setVisibility(View.GONE);
                                    isSectionLoading = false;
                                    isSubsectionLoading = true;
                                    subSectionIDCatcher = subSectionDataModel.getSubSectionID();
                                    getBookBySectionID();

                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("anyText", response);
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

    private void getBookBySectionID() {
        bookDataModelList.clear();
        binding.rvBooks.setAdapter(null);
        String viewBookBySectionID_URL = AppUtils.VIEW_BOOKS_BY_SECTIONID_ENDPOINT;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, viewBookBySectionID_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                viewBooks(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getActivity(), error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    public void viewBooks(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("tbl_book");

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String titleID = jo.getString("titleID");
                String sectionID = jo.getString("sectionID");
                String subSectionID = jo.getString("subSectionID");
                String bookID = jo.getString("bookID");
                String bookTitle = jo.getString("bookTitle");
                String bookAuthor = jo.getString("bookAuthor");
                String sectionDewey = jo.getString("sectionDewey");
                String sectionName = jo.getString("sectionName");
                String subSectionDewey = jo.getString("subSectionDewey");
                String subSectionName = jo.getString("subSectionName");
                String bookAccessionNo = jo.getString("bookAccessionNo");
                String bookEdition = jo.getString("bookEdition");
                String bookVolume = jo.getString("bookVolume");
                String bookPublisher = jo.getString("bookPublisher");
                String bookPubDate = jo.getString("bookPubDate");
                String bookNumOfPages = jo.getString("bookNumOfPages");
                String bookStatus = jo.getString("bookStatus");

                BookDataModel bookDataModel = new BookDataModel(
                        titleID, sectionID,
                        subSectionID, bookID,
                        bookTitle, bookAuthor,
                        sectionDewey, sectionName,
                        subSectionDewey, subSectionName,
                        bookAccessionNo, bookEdition,
                        bookVolume, bookPublisher,
                        bookPubDate, bookNumOfPages, bookStatus
                );

                if(isSectionLoading &&homeSectionID.equals(sectionID)){
                    bookDataModelList.add(bookDataModel);
                    binding.linearLoading.setVisibility(View.GONE);
                }

                if(isSubsectionLoading && subSectionIDCatcher.equals(subSectionID)){
                    bookDataModelList.add(bookDataModel);
                    binding.linearLoading.setVisibility(View.GONE);
                }


            }



            bookAdapter = new BookAdapter(getActivity(), bookDataModelList);
            binding.rvBooks.setAdapter(bookAdapter);



            bookAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    BookDataModel bookDataModel = bookDataModelList.get(position);

                    Intent intent = new Intent(getActivity(), BookOverviewActivity.class);
                    intent.putExtra("title", bookDataModel.getTitle());
                    intent.putExtra("author", bookDataModel.getTitleAuthor());
                    intent.putExtra("section", bookDataModel.getSectionName());
                    intent.putExtra("sub_section", bookDataModel.getSubsectionName());
                    intent.putExtra("edition", bookDataModel.getEdition());
                    intent.putExtra("volume", bookDataModel.getVolume());
                    intent.putExtra("publisher", bookDataModel.getPublisher());
                    intent.putExtra("pub_date", bookDataModel.getPublicationDate());
                    intent.putExtra("num_pages", bookDataModel.getNumberOfPages());
                    intent.putExtra("status", bookDataModel.getStatus());
                    startActivity(intent);

                }
            });

            if(bookAdapter.getItemCount()==0){
                binding.linearSubsection.setVisibility(View.VISIBLE);
            }
            else {
                binding.linearSubsection.setVisibility(View.GONE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}