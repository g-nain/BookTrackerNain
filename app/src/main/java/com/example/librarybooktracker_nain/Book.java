package com.example.librarybooktracker_nain;

public abstract class Book {
    private String bookCode;
    private String title;
    private String author;
    private int numOfDays;
    private boolean isBorrowed;

    public Book(String bookCode, String title, String author, int numOfDays, boolean isBorrowed){
        this.bookCode = bookCode;
        this.title = title;
        this.author = author;
        this.numOfDays = numOfDays;
        this.isBorrowed = isBorrowed;
    }

    public int getNumOfDays() {
        return numOfDays;
    }

    public abstract double calculatePrice ();
}
