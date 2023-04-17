package edu.ucu.libraryapp.datamodels;

public class PDFDataModel {
    private String pdfID;
    private String pdfName;
    private String pdfFile;

    public PDFDataModel(String pdfID, String pdfName, String pdfFile) {
        this.pdfID = pdfID;
        this.pdfName = pdfName;
        this.pdfFile = pdfFile;
    }

    public String getPdfID() {
        return pdfID;
    }

    public String getPdfName() {
        return pdfName;
    }

    public String getPdfFile() {
        return pdfFile;
    }
}
