package edu.ucu.libraryapp.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
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
import edu.ucu.libraryapp.R;
import edu.ucu.libraryapp.adapters.ReservationBorrowedAdapter;
import edu.ucu.libraryapp.adapters.ReturnedAdapter;
import edu.ucu.libraryapp.databinding.FragmentHistoryloggedinBinding;
import edu.ucu.libraryapp.datamodels.ReservationDataModel;
import edu.ucu.libraryapp.sharedpreferences.LoginSharedPrefManager;

public class HistoryLoggedinFragment extends Fragment {

    FragmentHistoryloggedinBinding binding;
    private final List<ReservationDataModel> reservationDataModelList = new ArrayList<>();
    LoginSharedPrefManager loginSharedPrefManager;
    ReservationBorrowedAdapter reservationAdapter;
    RequestQueue requestQueue;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHistoryloggedinBinding.inflate(inflater, container, false);

        loginSharedPrefManager = new LoginSharedPrefManager(requireActivity());

        if (loginSharedPrefManager.getKeyUserType().equals("student")) {
            getStudentReservedBook(false,"0");
        } else {
            getFacultyReservedBook(false,"0");
        }

        binding.btnReserved.setOnClickListener(v -> {
            binding.btnReserved.setTextColor(Color.WHITE);
            binding.btnReserved.setBackgroundResource(R.drawable.bg_button);
            binding.btnBorrowed.setTextColor(Color.BLACK);
            binding.btnBorrowed.setBackgroundResource(R.drawable.bg_button2);
            binding.btnReturned.setTextColor(Color.BLACK);
            binding.btnReturned.setBackgroundResource(R.drawable.bg_button2);
            binding.message.setText("No reserved books yet");

            binding.tvMessage.setText("Loading borrowed books...");
            if (loginSharedPrefManager.getKeyUserType().equals("student")) {
                getStudentReservedBook(false,"0");
            } else {
                getFacultyReservedBook(false,"0");
            }

        });

        binding.btnBorrowed.setOnClickListener(v -> {
            binding.btnReserved.setTextColor(Color.BLACK);
            binding.btnReserved.setBackgroundResource(R.drawable.bg_button2);
            binding.btnBorrowed.setTextColor(Color.WHITE);
            binding.btnBorrowed.setBackgroundResource(R.drawable.bg_button);
            binding.btnReturned.setTextColor(Color.BLACK);
            binding.btnReturned.setBackgroundResource(R.drawable.bg_button2);

            binding.message.setText("No borrowed books yet");

            binding.tvMessage.setText("Loading borrowed books...");

            if (loginSharedPrefManager.getKeyUserType().equals("student")) {
                getStudentBorrowedBook(true,"0");
            } else {
                getFacultyBorrowedBook(true,"0");
            }

        });

        binding.btnReturned.setOnClickListener(v -> {
            binding.btnReserved.setTextColor(Color.BLACK);
            binding.btnReserved.setBackgroundResource(R.drawable.bg_button2);
            binding.btnBorrowed.setTextColor(Color.BLACK);
            binding.btnBorrowed.setBackgroundResource(R.drawable.bg_button2);
            binding.btnReturned.setTextColor(Color.WHITE);
            binding.btnReturned.setBackgroundResource(R.drawable.bg_button);
            binding.rvReservation.setAdapter(null);
            binding.message.setText("No returned books yet");

            binding.tvMessage.setText("Loading returned books...");
            if (loginSharedPrefManager.getKeyUserType().equals("student")) {
                getStudentReturnedBook(false);
            } else {
                getFacultyReturnedBook(false);
            }

        });

        return binding.getRoot();
    }


    private void getStudentReservedBook(boolean isBorrowed, String status) {

        String studentID = loginSharedPrefManager.getStudentId();
        String viewReservationURL = AppUtils.VIEW_STUDENT_RESERVATION_ENDPOINT + "borrower_id=" + studentID + "&status=" + status;

        reservationDataModelList.clear();
        binding.rvReservation.setAdapter(null);
        binding.linearLoading.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, viewReservationURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                viewStudentFacultyBorrowedBooks(isBorrowed, response);
            }
        },
                error -> {
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        //This indicates that the request has either time out or there is no connection
                        binding.linearNointernet.setVisibility(View.VISIBLE);
                    } else if (error instanceof ServerError) {
                        //Indicates that the server responded with a error response
                        binding.linearApi.setVisibility(View.VISIBLE);

                    }
                    binding.linearLoading.setVisibility(View.GONE);
                });

        RequestQueue requestQueue = Volley.newRequestQueue(requireActivity());
        requestQueue.add(stringRequest);


    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        if (requestQueue != null) {
            requestQueue.cancelAll(this);
        }
    }

    private void getStudentBorrowedBook(boolean isBorrowed, String status) {

        String studentID = loginSharedPrefManager.getStudentId();
        String viewReservationURL = AppUtils.VIEW_STUDENT_BORROWED_ENDPOINT + "student_id=" + studentID + "&status=" + status;

        reservationDataModelList.clear();
        binding.rvReservation.setAdapter(null);
        binding.linearLoading.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, viewReservationURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                viewStudentFacultyBorrowedBooks(isBorrowed, response);
            }
        },
                error -> {
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        //This indicates that the request has either time out or there is no connection
                        binding.linearNointernet.setVisibility(View.VISIBLE);
                    } else if (error instanceof ServerError) {
                        //Indicates that the server responded with a error response
                        binding.linearApi.setVisibility(View.VISIBLE);

                    }
                    binding.linearLoading.setVisibility(View.GONE);
                });

        requestQueue = Volley.newRequestQueue(requireActivity());
        requestQueue.add(stringRequest);

    }

    private void getFacultyReservedBook(boolean isBorrowed,String status) {

        String facultyID = loginSharedPrefManager.getFacultyId();
        String viewReservationURL = AppUtils.VIEW_FACULTY_RESERVATION_ENDPOINT + "faculty_id=" + facultyID + "&status=" + status;

        reservationDataModelList.clear();
        binding.rvReservation.setAdapter(null);
        binding.linearLoading.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, viewReservationURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                viewStudentFacultyBorrowedBooks(isBorrowed, response);
            }
        },
                error -> {
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        //This indicates that the request has either time out or there is no connection
                        binding.linearNointernet.setVisibility(View.VISIBLE);
                    } else if (error instanceof ServerError) {
                        //Indicates that the server responded with a error response
                        binding.linearApi.setVisibility(View.VISIBLE);

                    }
                    binding.linearLoading.setVisibility(View.GONE);
                });

        requestQueue = Volley.newRequestQueue(requireActivity());
        requestQueue.add(stringRequest);

    }

    private void getFacultyBorrowedBook(boolean isBorrowed, String status) {

        String facultyID = loginSharedPrefManager.getFacultyId();
        String viewReservationURL = AppUtils.VIEW_FACULTY_BORROWED_ENDPOINT + "faculty_id=" + facultyID + "&status=" + status;

        reservationDataModelList.clear();
        binding.rvReservation.setAdapter(null);
        binding.linearLoading.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, viewReservationURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                viewStudentFacultyBorrowedBooks(isBorrowed, response);
            }
        },
                error -> {
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        //This indicates that the request has either time out or there is no connection
                        binding.linearNointernet.setVisibility(View.VISIBLE);
                    } else if (error instanceof ServerError) {
                        //Indicates that the server responded with a error response
                        binding.linearApi.setVisibility(View.VISIBLE);

                    }
                    binding.linearLoading.setVisibility(View.GONE);
                });

        requestQueue = Volley.newRequestQueue(requireActivity());
        requestQueue.add(stringRequest);

    }

    public void viewStudentFacultyBorrowedBooks(boolean isBorrowed, String response) {
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
                String borrowStatus = jo.getString("borrowStatus");
                String reservationDate = jo.getString("reservationDate");

                ReservationDataModel reservationDataModel = new ReservationDataModel(
                        isBorrowed,
                        titleID, sectionID,
                        subSectionID, bookID,
                        bookTitle, bookAuthor,
                        sectionDewey, sectionName,
                        subSectionDewey, subSectionName,
                        bookAccessionNo, bookEdition,
                        bookVolume, bookPublisher,
                        bookPubDate, bookNumOfPages, bookStatus, borrowStatus,
                        reservationDate, "", "",
                        "", "", ""
                );
                reservationDataModelList.add(reservationDataModel);

            }

            binding.rvReservation.setLayoutManager(new LinearLayoutManager(getContext()));
            reservationAdapter = new ReservationBorrowedAdapter(getActivity(), reservationDataModelList);
            binding.rvReservation.setAdapter(reservationAdapter);
            binding.linearLoading.setVisibility(View.GONE);

            if (reservationAdapter.getItemCount() == 0) {
                binding.linearNohistory.setVisibility(View.VISIBLE);
            } else {
                binding.linearNohistory.setVisibility(View.GONE);
            }

        } catch (JSONException e) {
            Log.e("Error here", e.toString());
        }
    }

    private void getStudentReturnedBook(boolean isBorrowed) {

        String studentID = loginSharedPrefManager.getStudentId();
        String viewReservationURL = AppUtils.VIEW_STUDENT_RETURNED_BOOKS_ENDPOINT + "student_id=" + studentID;

        reservationDataModelList.clear();
        binding.rvReservation.setAdapter(null);
        binding.linearLoading.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, viewReservationURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                viewStudentFacultyReturnedBooks(isBorrowed, response);
            }
        },
                error -> {
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        //This indicates that the request has either time out or there is no connection
                        binding.linearNointernet.setVisibility(View.VISIBLE);
                    } else if (error instanceof ServerError) {
                        //Indicates that the server responded with a error response
                        binding.linearApi.setVisibility(View.VISIBLE);

                    }
                    binding.linearLoading.setVisibility(View.GONE);
                });

        requestQueue = Volley.newRequestQueue(requireActivity());
        requestQueue.add(stringRequest);

    }

    public void viewStudentFacultyReturnedBooks(boolean isBorrowed,String response) {
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
                String borrowStatus = jo.getString("borrowStatus");
                String reservationDate = jo.getString("reservationDate");
                String returnDueDate = jo.getString("returnDueDate");
                String returnDate = jo.getString("returnDate");
                String returnTotalHours = jo.getString("returnTotalHours");
                String returnPenalty = jo.getString("returnPenalty");
                String returnProcessedBy = jo.getString("returnProcessedBy");

                ReservationDataModel reservationDataModel = new ReservationDataModel(
                        isBorrowed,
                        titleID, sectionID,
                        subSectionID, bookID,
                        bookTitle, bookAuthor,
                        sectionDewey, sectionName,
                        subSectionDewey, subSectionName,
                        bookAccessionNo, bookEdition,
                        bookVolume, bookPublisher,
                        bookPubDate, bookNumOfPages, bookStatus, borrowStatus,
                        reservationDate, returnDueDate, returnDate,
                        returnTotalHours, returnPenalty, returnProcessedBy
                );
                reservationDataModelList.add(reservationDataModel);

            }

            binding.rvReservation.setLayoutManager(new LinearLayoutManager(getContext()));
            ReturnedAdapter returnedAdapter = new ReturnedAdapter(getActivity(), reservationDataModelList);
            binding.rvReservation.setAdapter(returnedAdapter);
            binding.linearLoading.setVisibility(View.GONE);

            if (returnedAdapter.getItemCount() == 0) {
                binding.linearNohistory.setVisibility(View.VISIBLE);
            } else {
                binding.linearNohistory.setVisibility(View.GONE);
            }

        } catch (JSONException e) {
            Log.e("Error here", e.toString());
        }

    }

    private void getFacultyReturnedBook(boolean isBorrowed) {

        String facultyId = loginSharedPrefManager.getFacultyId();
        String viewReservationURL = AppUtils.VIEW_FACULTY_RETURNED_BOOKS_ENDPOINT + "faculty_id=" + facultyId;

        reservationDataModelList.clear();
        binding.rvReservation.setAdapter(null);
        binding.linearLoading.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, viewReservationURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                viewStudentFacultyReturnedBooks(isBorrowed, response);
            }
        },
                error -> {
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        //This indicates that the request has either time out or there is no connection
                        binding.linearNointernet.setVisibility(View.VISIBLE);
                    } else if (error instanceof ServerError) {
                        //Indicates that the server responded with a error response
                        binding.linearApi.setVisibility(View.VISIBLE);

                    }
                    binding.linearLoading.setVisibility(View.GONE);
                });

        requestQueue = Volley.newRequestQueue(requireActivity());
        requestQueue.add(stringRequest);

    }


}