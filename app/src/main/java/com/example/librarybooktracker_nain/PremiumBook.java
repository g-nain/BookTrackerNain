package com.example.librarybooktracker_nain;

public class PremiumBook extends Book{
    public PremiumBook(String bookCode, String title, String author, int numOfDays, boolean isBorrowed) {
        super(bookCode, title, author, numOfDays, isBorrowed);
    }

    @Override
    public double calculatePrice() {
        if (getNumOfDays() > 7){
            return ((getNumOfDays() - 7) * 75.00) + (7 * 50.00);
        }else{
            return getNumOfDays() * 50.00;
        }
    }
}
