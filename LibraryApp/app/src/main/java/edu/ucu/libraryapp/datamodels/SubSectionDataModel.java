package edu.ucu.libraryapp.datamodels;

public class SubSectionDataModel {

    private String subSectionID;
    private String deweyDecimal;
    private String sectionID;
    private String subSectionName;

    public SubSectionDataModel(String subSectionID, String deweyDecimal, String sectionID, String sectionName) {
        this.subSectionID = subSectionID;
        this.deweyDecimal = deweyDecimal;
        this.sectionID = sectionID;
        this.subSectionName = sectionName;
    }

    public String getSubSectionID() {
        return subSectionID;
    }

    public String getDeweyDecimal() {
        return deweyDecimal;
    }

    public String getSectionID() {
        return sectionID;
    }

    public String getSubSectionName() {
        return subSectionName;
    }
}
