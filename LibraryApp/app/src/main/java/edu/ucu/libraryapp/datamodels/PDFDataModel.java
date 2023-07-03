package edu.ucu.libraryapp.datamodels;

public class PDFDataModel {
    private String pdfID;
    private String pdfTitle;
    private String pdfFileName;

    public PDFDataModel(String pdfID, String pdfTitle, String pdfFileName) {
        this.pdfID = pdfID;
        this.pdfTitle = pdfTitle;
        this.pdfFileName = pdfFileName;
    }

    public String getPdfID() {
        return pdfID;
    }

    public String getPdfTitle() {
        return pdfTitle;
    }

    public String getPdfFileName() {
        return pdfFileName;
    }
}
