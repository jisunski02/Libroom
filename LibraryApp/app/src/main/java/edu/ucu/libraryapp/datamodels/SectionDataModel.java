package edu.ucu.libraryapp.datamodels;

public class SectionDataModel {

    private String sectionID;
    private String deweyDecimal;
    private String sectionName;

    public SectionDataModel(String sectionID, String deweyDecimal, String sectionName) {
        this.sectionID = sectionID;
        this.deweyDecimal = deweyDecimal;
        this.sectionName = sectionName;
    }

    public String getSectionID() {
        return sectionID;
    }

    public String getDeweyDecimal() {
        return deweyDecimal;
    }

    public String getSectionName() {
        return sectionName;
    }
}
