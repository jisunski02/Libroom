package edu.ucu.libraryapp.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

public class LoginSharedPrefManager {

    SharedPreferences login_preferences;
    SharedPreferences.Editor login_preferences_editor;
    Context context_login_preferences;
    final int Private_Mode_LOGIN = 0;

    @NonNull
    private static final String login_pref_name = "newLogin";

    @NonNull
    private static final String login_IS_LOGIN = "IsLoggedIn";

    //Student
    private final String KEY_STUDENT_ID = "KEY_STUDENT_ID";
    private final String KEY_STUDENT_NO = "KEY_STUDENT_NO";
    private final String KEY_STUDENT_PASSWORD = "KEY_STUDENT_PASSWORD";
    private final String KEY_STUDENT_FIRSTNAME = "KEY_STUDENT_FIRSTNAME";
    private final String KEY_STUDENT_MIDDLENAME = "KEY_STUDENT_MIDDLENAME";
    private final String KEY_STUDENT_LASTNAME = "KEY_STUDENT_LASTNAME";
    private final String KEY_STUDENT_GENDER = "KEY_STUDENT_GENDER";
    private final String KEY_STUDENT_BIRTHDATE = "KEY_STUDENT_BIRTHDATE";
    private final String KEY_STUDENT_COURSE = "KEY_STUDENT_COURSE";
    private final String KEY_STUDENT_CONTACTNO = "KEY_STUDENT_CONTACTNO";
    private final String KEY_STUDENT_EMAILADDRESS = "KEY_STUDENT_EMAILADDRESS";
    private final String KEY_STUDENT_ADDRESS = "KEY_STUDENT_ADDRESS";
    private final String KEY_STUDENT_STATUS = "KEY_STUDENT_STATUS";


    //Faculty
    private final String KEY_FACULTY_ID = "KEY_FACULTY_ID";
    private final String KEY_FACULTY_PASSWORD = "KEY_FACULTY_PASSWORD";
    private final String KEY_FACULTY_NO = "KEY_FACULTY_NO";
    private final String KEY_FACULTY_FIRSTNAME = "KEY_FACULTY_FIRSTNAME";
    private final String KEY_FACULTY_MIDDLENAME = "KEY_FACULTY_MIDDLENAME";
    private final String KEY_FACULTY_LASTNAME = "KEY_FACULTY_LASTNAME";
    private final String KEY_FACULTY_GENDER = "KEY_FACULTY__GENDER";
    private final String KEY_FACULTY_EMAILADDRESS = "KEY_FACULTY_EMAILADDRESS";
    private final String KEY_FACULTY_STATUS = "KEY_FACULTY_STATUS";

    //User Type
    private final String KEY_USER_TYPE = "KEY_USER_TYPE";

    public LoginSharedPrefManager(Context context){
        this.context_login_preferences = context;
        login_preferences = context_login_preferences.getSharedPreferences(login_pref_name, Private_Mode_LOGIN);
        login_preferences_editor = login_preferences.edit();
    }

    public void createStudentLoginSession(String id_no){
        login_preferences_editor.putBoolean(login_IS_LOGIN, true);
        login_preferences_editor.putString(KEY_STUDENT_NO, id_no);
        login_preferences_editor.commit();
    }

    public void createFacultyLoginSession(String emp_no){
        login_preferences_editor.putBoolean(login_IS_LOGIN, true);
        login_preferences_editor.putString(KEY_FACULTY_NO, emp_no);
        login_preferences_editor.commit();
    }

    public void setStudentId(String id){
        login_preferences_editor.putString(KEY_STUDENT_ID, id);
        login_preferences_editor.commit();
    }

    public String getStudentId() {
        String id = login_preferences.getString(KEY_STUDENT_ID, null);
        if(id != null) {
            return id;
        }
        return null;
    }

    public void setStudentPassword(String password){
        login_preferences_editor.putString(KEY_STUDENT_PASSWORD, password);
        login_preferences_editor.commit();
    }

    public String getStudentPassword() {
        String password = login_preferences.getString(KEY_STUDENT_PASSWORD, null);
        if(password != null) {
            return password;
        }
        return null;
    }

    public void setStudentNo(String id_no){
        login_preferences_editor.putString(KEY_STUDENT_NO, id_no);
        login_preferences_editor.commit();
    }

    public String getStudentNo() {
        String id_no = login_preferences.getString(KEY_STUDENT_NO, null);
        if(id_no != null) {
            return id_no;
        }
        return null;
    }

    public void setStudentFirstName(String first_name){
        login_preferences_editor.putString(KEY_STUDENT_FIRSTNAME, first_name);
        login_preferences_editor.commit();
    }

    public String getStudentFirstName() {
        String first_name = login_preferences.getString(KEY_STUDENT_FIRSTNAME, null);
        if(first_name != null) {
            return first_name;
        }
        return null;
    }

    public void setStudentMiddleName(String middle_name){
        login_preferences_editor.putString(KEY_STUDENT_MIDDLENAME, middle_name);
        login_preferences_editor.commit();
    }

    public String getStudentMiddleName() {
        String middle_name = login_preferences.getString(KEY_STUDENT_MIDDLENAME, null);
        if(middle_name != null) {
            return middle_name;
        }
        return null;
    }

    public void setStudentLastName(String last_name){
        login_preferences_editor.putString(KEY_STUDENT_LASTNAME, last_name);
        login_preferences_editor.commit();
    }

    public String getStudentLastName() {
        String last_name = login_preferences.getString(KEY_STUDENT_LASTNAME, null);
        if(last_name != null) {
            return last_name;
        }
        return null;
    }

    public void setStudentGender(String gender){
        login_preferences_editor.putString(KEY_STUDENT_GENDER, gender);
        login_preferences_editor.commit();
    }

    public String getStudentGender() {
        String gender = login_preferences.getString(KEY_STUDENT_GENDER, null);
        if(gender != null) {
            return gender;
        }
        return null;
    }

    public void setStudentBirthDate(String birth_date){
        login_preferences_editor.putString(KEY_STUDENT_BIRTHDATE, birth_date);
        login_preferences_editor.commit();
    }

    public String getStudentBirthDate() {
        String birth_date = login_preferences.getString(KEY_STUDENT_BIRTHDATE, null);
        if(birth_date != null) {
            return birth_date;
        }
        return null;
    }

    public void setStudentCourse(String course){
        login_preferences_editor.putString(KEY_STUDENT_COURSE, course);
        login_preferences_editor.commit();
    }

    public String getStudentCourse() {
        String course = login_preferences.getString(KEY_STUDENT_COURSE, null);
        if(course != null) {
            return course;
        }
        return null;
    }

    public void setStudentContactNo(String contact_no){
        login_preferences_editor.putString(KEY_STUDENT_CONTACTNO, contact_no);
        login_preferences_editor.commit();
    }

    public String getStudentContactNo() {
        String contact_no = login_preferences.getString(KEY_STUDENT_CONTACTNO, null);
        if(contact_no != null) {
            return contact_no;
        }
        return null;
    }

    public void setStudentEmailAddress(String email_address){
        login_preferences_editor.putString(KEY_STUDENT_EMAILADDRESS, email_address);
        login_preferences_editor.commit();
    }

    public String getStudentEmailAddress() {
        String email_address = login_preferences.getString(KEY_STUDENT_EMAILADDRESS, null);
        if(email_address != null) {
            return email_address;
        }
        return null;
    }

    public void setStudentAddress(String address){
        login_preferences_editor.putString(KEY_STUDENT_ADDRESS, address);
        login_preferences_editor.commit();
    }

    public String getStudentAddress() {
        String address = login_preferences.getString(KEY_STUDENT_ADDRESS, null);
        if(address != null) {
            return address;
        }
        return null;
    }

    public void setStudentStatus(String status){
        login_preferences_editor.putString(KEY_STUDENT_STATUS, status);
        login_preferences_editor.commit();
    }

    public String getStudentStatus() {
        String status = login_preferences.getString(KEY_STUDENT_STATUS, null);
        if(status != null) {
            return status;
        }
        return null;
    }

    public void setFacultyId(String id){
        login_preferences_editor.putString(KEY_FACULTY_ID, id);
        login_preferences_editor.commit();
    }

    public String getFacultyId() {
        String id = login_preferences.getString(KEY_FACULTY_ID, null);
        if(id != null) {
            return id;
        }
        return null;
    }

    public void setFacultyNo(String id_no){
        login_preferences_editor.putString(KEY_FACULTY_NO, id_no);
        login_preferences_editor.commit();
    }

    public String getFacultyNo() {
        String id_no = login_preferences.getString(KEY_FACULTY_NO, null);
        if(id_no != null) {
            return id_no;
        }
        return null;
    }


    public void setFacultyPassword(String password){
        login_preferences_editor.putString(KEY_FACULTY_PASSWORD, password);
        login_preferences_editor.commit();
    }

    public String getFacultyPassword() {
        String password = login_preferences.getString(KEY_FACULTY_PASSWORD, null);
        if(password != null) {
            return password;
        }
        return null;
    }

    public void setFacultyFirstName(String first_name){
        login_preferences_editor.putString(KEY_FACULTY_FIRSTNAME, first_name);
        login_preferences_editor.commit();
    }

    public String getFacultyFirstName() {
        String first_name = login_preferences.getString(KEY_FACULTY_FIRSTNAME, null);
        if(first_name != null) {
            return first_name;
        }
        return null;
    }

    public void setFacultyMiddleName(String middle_name){
        login_preferences_editor.putString(KEY_FACULTY_MIDDLENAME, middle_name);
        login_preferences_editor.commit();
    }

    public String getFacultyMiddleName() {
        String middle_name = login_preferences.getString(KEY_FACULTY_MIDDLENAME, null);
        if(middle_name != null) {
            return middle_name;
        }
        return null;
    }

    public void setFacultyLastName(String last_name){
        login_preferences_editor.putString(KEY_FACULTY_LASTNAME, last_name);
        login_preferences_editor.commit();
    }

    public String getFacultyLastName() {
        String last_name = login_preferences.getString(KEY_FACULTY_LASTNAME, null);
        if(last_name != null) {
            return last_name;
        }
        return null;
    }

    public void setFacultyGender(String gender){
        login_preferences_editor.putString(KEY_FACULTY_GENDER, gender);
        login_preferences_editor.commit();
    }

    public String getFacultyGender() {
        String gender = login_preferences.getString(KEY_FACULTY_GENDER, null);
        if(gender != null) {
            return gender;
        }
        return null;
    }

    public void setFacultyEmailAddress(String email_address){
        login_preferences_editor.putString(KEY_FACULTY_EMAILADDRESS, email_address);
        login_preferences_editor.commit();
    }

    public String getFacultyEmailAddress() {
        String email_address = login_preferences.getString(KEY_FACULTY_EMAILADDRESS, null);
        if(email_address != null) {
            return email_address;
        }
        return null;
    }

    public void setFacultyStatus(String status){
        login_preferences_editor.putString(KEY_FACULTY_STATUS, status);
        login_preferences_editor.commit();
    }

    public String getFacultyStatus() {
        String status = login_preferences.getString(KEY_FACULTY_STATUS, null);
        if(status != null) {
            return status;
        }
        return null;
    }

    public void setKeyUserType(String usertype){
        login_preferences_editor.putString(KEY_USER_TYPE, usertype);
        login_preferences_editor.commit();
    }

    public String getKeyUserType() {
        String status = login_preferences.getString(KEY_USER_TYPE, null);
        if(status != null) {
            return status;
        }
        return null;
    }


    public boolean isLoggedIn(){
        return login_preferences.getBoolean(login_IS_LOGIN, false);
    }

    public void logOutStudent(){
        login_preferences_editor.remove(KEY_STUDENT_ID);
        login_preferences_editor.remove(KEY_STUDENT_NO);
        login_preferences_editor.remove(KEY_STUDENT_FIRSTNAME);
        login_preferences_editor.remove(KEY_STUDENT_MIDDLENAME);
        login_preferences_editor.remove(KEY_STUDENT_LASTNAME);
        login_preferences_editor.remove(KEY_STUDENT_GENDER);
        login_preferences_editor.remove(KEY_STUDENT_BIRTHDATE   );
        login_preferences_editor.remove(KEY_STUDENT_COURSE);
        login_preferences_editor.remove(KEY_STUDENT_CONTACTNO);
        login_preferences_editor.remove(KEY_STUDENT_EMAILADDRESS);
        login_preferences_editor.remove(KEY_STUDENT_ADDRESS);
        login_preferences_editor.remove(KEY_STUDENT_STATUS);
        login_preferences_editor.remove(KEY_USER_TYPE);
        login_preferences_editor.remove(KEY_STUDENT_PASSWORD);
        login_preferences_editor.putBoolean(login_IS_LOGIN, false);
        login_preferences_editor.clear();
        login_preferences_editor.commit();
    }

    public void logOutFaculty(){
        login_preferences_editor.remove(KEY_FACULTY_ID);
        login_preferences_editor.remove(KEY_FACULTY_NO);
        login_preferences_editor.remove(KEY_FACULTY_FIRSTNAME);
        login_preferences_editor.remove(KEY_FACULTY_MIDDLENAME);
        login_preferences_editor.remove(KEY_FACULTY_LASTNAME);
        login_preferences_editor.remove(KEY_FACULTY_GENDER);
        login_preferences_editor.remove(KEY_FACULTY_EMAILADDRESS);
        login_preferences_editor.remove(KEY_FACULTY_STATUS);
        login_preferences_editor.remove(KEY_USER_TYPE);
        login_preferences_editor.remove(KEY_FACULTY_PASSWORD);
        login_preferences_editor.putBoolean(login_IS_LOGIN, false);
        login_preferences_editor.clear();
        login_preferences_editor.commit();
    }
}