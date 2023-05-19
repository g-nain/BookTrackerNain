package com.example.librarybooktracker_nain;

public class RegularBook extends Book{

    public RegularBook(String bookCode, String title, String author, int numOfDays, boolean isBorrowed) {
        super(bookCode, title, author, numOfDays, isBorrowed);
    }

    @Override
    public double calculatePrice() {
        return getNumOfDays() * 20.00;
    }
}
