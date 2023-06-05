package edu.ucu.libraryapp.datamodels;

import com.google.gson.annotations.SerializedName;

public class StudentDataModel {

    @SerializedName("studentID")
    private String studentID;
    @SerializedName("studentNo")
    private String studentNo;
    @SerializedName("studentFirstName")
    private String studentFirstName;
    @SerializedName("studentMiddleName")
    private String studentMiddleName;
    @SerializedName("studentLastName")
    private String studentLastName;
    @SerializedName("studentGender")
    private String studentGender;
    @SerializedName("studentBirthdate")
    private String studentBirthdate;
    @SerializedName("studentCourse")
    private String studentCourse;
    @SerializedName("studentContactNo")
    private String studentContactNo;
    @SerializedName("studentEmailAddress")
    private String studentEmailAddress;
    @SerializedName("studentAddress")
    private String studentAddress;
    @SerializedName("studentStatus")
    private String studentStatus;

   /* public StudentDataModel(String studentID, String studentNo, String studentFirstName, String studentMiddleName, String studentLastName, String studentGender, String studentBirthdate, String studentCourse, String studentContactNo, String studentEmailAddress, String studentAddress, String studentStatus) {
        this.studentID = studentID;
        this.studentNo = studentNo;
        this.studentFirstName = studentFirstName;
        this.studentMiddleName = studentMiddleName;
        this.studentLastName = studentLastName;
        this.studentGender = studentGender;
        this.studentBirthdate = studentBirthdate;
        this.studentCourse = studentCourse;
        this.studentContactNo = studentContactNo;
        this.studentEmailAddress = studentEmailAddress;
        this.studentAddress = studentAddress;
        this.studentStatus = studentStatus;
    } */

    public String getStudentID() {
        return studentID;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public String getStudentFirstName() {
        return studentFirstName;
    }

    public String getStudentMiddleName() {
        return studentMiddleName;
    }

    public String getStudentLastName() {
        return studentLastName;
    }

    public String getStudentGender() {
        return studentGender;
    }

    public String getStudentBirthdate() {
        return studentBirthdate;
    }

    public String getStudentCourse() {
        return studentCourse;
    }

    public String getStudentContactNo() {
        return studentContactNo;
    }

    public String getStudentEmailAddress() {
        return studentEmailAddress;
    }

    public String getStudentAddress() {
        return studentAddress;
    }

    public String getStudentStatus() {
        return studentStatus;
    }
}
