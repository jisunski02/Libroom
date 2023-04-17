package edu.ucu.libraryapp;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentController;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class AppUtils {

    public static String BASE_URL = "https://lib-room.online/includes/";
    public static String VIEW_SECTION_ENDPOINT = BASE_URL+ "viewsection.php";
    public static String VIEW_SUB_SECTION_ENDPOINT = BASE_URL+ "viewsubsection.php";
    public static String VIEW_BOOKS_BY_SECTIONID_ENDPOINT = BASE_URL+ "viewbook.php";
    public static String LOGIN_STUDENT_ENDPOINT = BASE_URL+ "loginuser.php";
    public static String VIEW_STUDENT_PROFILE_ENDPOINT = BASE_URL+ "viewstudentprofile.php?";
    public static String RESERVE_BOOK_ENDPOINT = BASE_URL+ "borrowbook.php";
    public static String VIEW_RESERVATION_ENDPOINT = BASE_URL+ "viewreservation.php?";

    public static void gotoActivity(Activity fromActivity, Class<?> toActivity){
        Intent intent = new Intent(fromActivity, toActivity);
        fromActivity.startActivity(intent);
    }

    public static void openFragment(Fragment fragment, String tag, Activity activity) {
        FragmentManager fm = ((FragmentActivity)activity).getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragment_wrapper, fragment);
        transaction.commit();
        fm.executePendingTransactions();
        fm.findFragmentByTag(tag);
    }

    public static void toastMessage(Activity activity, String message){

        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();

    }

}
