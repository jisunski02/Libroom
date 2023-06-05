package edu.ucu.libraryapp.datamodels;

public class FacultyModel {

    private String facultyID;
    private String facultyEmpNo;
    private String facultyFirstName;
    private String facultyMiddleName;
    private String facultyLastName;
    private String facultyGender;
    private String facultyEmailAddress;
    private String facultyStatus;

    public FacultyModel(String facultyID, String facultyEmpNo, String facultyFirstName, String facultyMiddleName, String facultyLastName, String facultyGender, String facultyEmailAddress, String facultyStatus) {
        this.facultyID = facultyID;
        this.facultyEmpNo = facultyEmpNo;
        this.facultyFirstName = facultyFirstName;
        this.facultyMiddleName = facultyMiddleName;
        this.facultyLastName = facultyLastName;
        this.facultyGender = facultyGender;
        this.facultyEmailAddress = facultyEmailAddress;
        this.facultyStatus = facultyStatus;
    }

    public String getFacultyID() {
        return facultyID;
    }

    public String getFacultyEmpNo() {
        return facultyEmpNo;
    }

    public String getFacultyFirstName() {
        return facultyFirstName;
    }

    public String getFacultyMiddleName() {
        return facultyMiddleName;
    }

    public String getFacultyLastName() {
        return facultyLastName;
    }

    public String getFacultyGender() {
        return facultyGender;
    }

    public String getFacultyEmailAddress() {
        return facultyEmailAddress;
    }

    public String getFacultyStatus() {
        return facultyStatus;
    }
}
