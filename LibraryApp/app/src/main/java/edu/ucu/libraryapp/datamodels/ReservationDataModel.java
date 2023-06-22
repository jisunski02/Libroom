package edu.ucu.libraryapp.datamodels;

public class ReservationDataModel {
    boolean isBorrowed;
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
    private String borrowStatus;
    private String reservation_date;
    private String returnDueDate;
    private String returnDate;
    private String returnTotalHours;
    private String returnPenalty;
    private String returnProcessedBy;

    public ReservationDataModel(boolean isBorrowed, String titleID, String sectionID, String subSectionID, String bookID, String title, String titleAuthor, String sectionDewey, String sectionName, String subSectionDewey, String subsectionName, String accessionNo, String edition, String volume, String publisher, String publicationDate, String numberOfPages, String status, String borrowStatus, String reservation_date, String returnDueDate, String returnDate, String returnTotalHours, String returnPenalty, String returnProcessedBy) {
        this.isBorrowed = isBorrowed;
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
        this.borrowStatus = borrowStatus;
        this.reservation_date = reservation_date;
        this.returnDueDate = returnDueDate;
        this.returnDate = returnDate;
        this.returnTotalHours = returnTotalHours;
        this.returnPenalty = returnPenalty;
        this.returnProcessedBy = returnProcessedBy;
    }

    public boolean isBorrowed() {
        return isBorrowed;
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

    public String getBorrowStatus() {
        return borrowStatus;
    }

    public String getReservation_date() {
        return reservation_date;
    }

    public String getReturnDueDate() {
        return returnDueDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public String getReturnTotalHours() {
        return returnTotalHours;
    }

    public String getReturnPenalty() {
        return returnPenalty;
    }

    public String getReturnProcessedBy() {
        return returnProcessedBy;
    }
}
