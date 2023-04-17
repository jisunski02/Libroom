package edu.ucu.libraryapp.datamodels;

public class StudentDataModel {
    private String studentID;
    private String studentNo;
    private String studentFirstName;
    private String studentMiddleName;
    private String studentLastName;
    private String studentGender;
    private String studentBirthdate;
    private String studentCourse;
    private String studentContactNo;
    private String studentEmailAddress;
    private String studentAddress;
    private String studentStatus;

    public StudentDataModel(String studentID, String studentNo, String studentFirstName, String studentMiddleName, String studentLastName, String studentGender, String studentBirthdate, String studentCourse, String studentContactNo, String studentEmailAddress, String studentAddress, String studentStatus) {
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
    }

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
