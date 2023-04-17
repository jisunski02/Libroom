package edu.ucu.libraryapp.datamodels;

public class ReservationDataModel {
    private String titleID;
    private String sectionID;
    private String subSectionID;
    private String bookID;
    private String title;
    private String titleAuthor;
    private String sectionDewey;
    private String sectionName;
    private String subSectionDewey;
    private String subsectionName;
    private String accessionNo;
    private String edition;
    private String volume;
    private String publisher;
    private String publicationDate;
    private String numberOfPages;
    private String status;
    private String reservation_date;

    public ReservationDataModel(String titleID, String sectionID, String subSectionID, String bookID, String title, String titleAuthor, String sectionDewey, String sectionName, String subSectionDewey, String subsectionName, String accessionNo, String edition, String volume, String publisher, String publicationDate, String numberOfPages, String status, String reservation_date) {
        this.titleID = titleID;
        this.sectionID = sectionID;
        this.subSectionID = subSectionID;
        this.bookID = bookID;
        this.title = title;
        this.titleAuthor = titleAuthor;
        this.sectionDewey = sectionDewey;
        this.sectionName = sectionName;
        this.subSectionDewey = subSectionDewey;
        this.subsectionName = subsectionName;
        this.accessionNo = accessionNo;
        this.edition = edition;
        this.volume = volume;
        this.publisher = publisher;
        this.publicationDate = publicationDate;
        this.numberOfPages = numberOfPages;
        this.status = status;
        this.reservation_date = reservation_date;
    }

    public String getTitleID() {
        return titleID;
    }

    public String getSectionID() {
        return sectionID;
    }

    public String getSubSectionID() {
        return subSectionID;
    }

    public String getBookID() {
        return bookID;
    }

    public String getTitle() {
        return title;
    }

    public String getTitleAuthor() {
        return titleAuthor;
    }

    public String getSectionDewey() {
        return sectionDewey;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getSubSectionDewey() {
        return subSectionDewey;
    }

    public String getSubsectionName() {
        return subsectionName;
    }

    public String getAccessionNo() {
        return accessionNo;
    }

    public String getEdition() {
        return edition;
    }

    public String getVolume() {
        return volume;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public String getNumberOfPages() {
        return numberOfPages;
    }

    public String getStatus() {
        return status;
    }

    public String getReservation_date() {
        return reservation_date;
    }
}
