package org.example.ooplibrary.Object;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BookLoan  {
    private StringProperty bookLoanID;
    private StringProperty bookName;
    private StringProperty fullName;
    private StringProperty dueDate;
    private StringProperty returnDate;
    private StringProperty note;

    public BookLoan(String bookLoanID, String bookName, String fullName, String dueDate, String returnDate, String note) {
        this.bookLoanID = new SimpleStringProperty(bookLoanID);
        this.bookName = new SimpleStringProperty(bookName);
        this.fullName = new SimpleStringProperty(fullName);
        this.dueDate = new SimpleStringProperty(dueDate);
        this.returnDate = new SimpleStringProperty(returnDate);
        this.note = new SimpleStringProperty(note);
    }

    public BookLoan() {
        this.bookLoanID = new SimpleStringProperty("");
        this.bookName = new SimpleStringProperty("");
        this.fullName = new SimpleStringProperty("");
        this.dueDate = new SimpleStringProperty("");
        this.returnDate = new SimpleStringProperty("");
        this.note = new SimpleStringProperty("");
    }

    public String getBookLoanID() {
        return bookLoanID.get();
    }

    public StringProperty bookLoanIDProperty() {
        return bookLoanID;
    }

    public void setBookLoanID(String bookLoanID) {
        this.bookLoanID.set(bookLoanID);
    }

    public String getBookName() {
        return bookName.get();
    }

    public StringProperty bookNameProperty() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName.set(bookName);
    }

    public String getUsername() {
        return fullName.get();
    }

    public StringProperty fullNameProperty() {
        return fullName;
    }

    public void setUsername(String fullName) {
        this.fullName.set(fullName);
    }

    public String getDueDate() {
        return dueDate.get();
    }

    public StringProperty dueDateProperty() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate.set(dueDate);
    }

    public String getReturnDate() {
        return returnDate.get();
    }

    public StringProperty returnDateProperty() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate.set(returnDate);
    }

    public String getNote() {
        return note.get();
    }

    public StringProperty noteProperty() {
        return note;
    }

    public void setNote(String note) {
        this.note.set(note);
    }
}
