package com.example.librarybooktracker_nain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseFirestore.getInstance();

        TextView bookCodeTxt = findViewById(R.id.bookCodeTxt);
        TextView numOfDaysTxt = findViewById(R.id.numOfDaysTxt);
        TextView authorTxt = findViewById(R.id.authorTxt);
        TextView titleTxt = findViewById(R.id.titleTxt);
        TextView availableDisplayTxt = findViewById(R.id.isBorrowedTxt);
        TextView totalPriceTxt = findViewById(R.id.totalPriceTxt);
        Button borrowBtn = findViewById(R.id.borrowBtn);

        borrowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalPriceTxt.setVisibility(View.VISIBLE);
                totalPriceTxt.setText("Retrieving information...");
                totalPriceTxt.setTextColor(Color.GRAY);
                titleTxt.setVisibility(View.VISIBLE);
                titleTxt.setText("Retrieving information...");
                titleTxt.setTextColor(Color.GRAY);
                authorTxt.setVisibility(View.VISIBLE);
                authorTxt.setText("Retrieving information...");
                authorTxt.setTextColor(Color.GRAY);
                availableDisplayTxt.setVisibility(View.VISIBLE);
                availableDisplayTxt.setText("Retrieving information...");
                availableDisplayTxt.setTextColor(Color.GRAY);
                String bookCode = bookCodeTxt.getText().toString();
                int numOfDays = Integer.parseInt(numOfDaysTxt.getText().toString());
                db.collection("RegularBooks").whereEqualTo("bookCode", bookCode)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()) {
                                    QuerySnapshot regularBooksSnapshot = task.getResult();

                                    // Check if RegularBooks collection has a matching document
                                    if (!regularBooksSnapshot.isEmpty()) {
                                        for (QueryDocumentSnapshot document : task.getResult()){
                                            String title = (String) document.getData().get("title");
                                            String author = (String) document.getData().get("author");

                                            boolean isBorrowed = (boolean) document.getData().get("isBorrowed");
                                            RegularBook regular = new RegularBook(bookCode, title, author, numOfDays, isBorrowed);
                                            if (isBorrowed == false){
                                                double totalPrice = regular.calculatePrice();
                                                totalPriceTxt.setVisibility(View.VISIBLE);
                                                titleTxt.setVisibility(View.VISIBLE);
                                                authorTxt.setVisibility(View.VISIBLE);
                                                totalPriceTxt.setText(String.valueOf(totalPrice));
                                                titleTxt.setText(title);
                                                authorTxt.setText(author);
                                                availableDisplayTxt.setVisibility(View.VISIBLE);
                                                availableDisplayTxt.setText("Book available!");
                                                availableDisplayTxt.setTextColor(Color.GREEN);

                                            }else{
                                                totalPriceTxt.setVisibility(View.INVISIBLE);
                                                titleTxt.setVisibility(View.VISIBLE);
                                                authorTxt.setVisibility(View.VISIBLE);
                                                titleTxt.setText(title);
                                                authorTxt.setText(author);
                                                availableDisplayTxt.setVisibility(View.VISIBLE);
                                                availableDisplayTxt.setText("Book NOT available!");
                                                availableDisplayTxt.setTextColor(Color.RED);
                                            }

                                        }
                                    }else{
                                        //regular books unsuccessful
                                        checkPremiumBooks();
                                    }

                                }
                        }
                });

            }
        });
    }
    public void checkPremiumBooks(){
        TextView bookCodeTxt = findViewById(R.id.bookCodeTxt);
        TextView numOfDaysTxt = findViewById(R.id.numOfDaysTxt);
        TextView authorTxt = findViewById(R.id.authorTxt);
        TextView titleTxt = findViewById(R.id.titleTxt);
        TextView availableDisplayTxt = findViewById(R.id.isBorrowedTxt);
        TextView totalPriceTxt = findViewById(R.id.totalPriceTxt);
        String bookCode = bookCodeTxt.getText().toString();
        int numOfDays = Integer.parseInt(numOfDaysTxt.getText().toString());
        db.collection("PremiumBooks").whereEqualTo("bookCode", bookCode)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            QuerySnapshot premiumBooksSnapshot = task.getResult();

                            // Check if RegularBooks collection has a matching document
                            if (!premiumBooksSnapshot.isEmpty()) {
                                for (QueryDocumentSnapshot document : task.getResult()){
                                    String title = (String) document.getData().get("title");
                                    String author = (String) document.getData().get("author");

                                    boolean isBorrowed = (boolean) document.getData().get("isBorrowed");
                                    PremiumBook premium = new PremiumBook(bookCode, title, author, numOfDays, isBorrowed);
                                    if (isBorrowed == false){
                                        double totalPrice = premium.calculatePrice();
                                        totalPriceTxt.setVisibility(View.VISIBLE);
                                        titleTxt.setVisibility(View.VISIBLE);
                                        authorTxt.setVisibility(View.VISIBLE);
                                        totalPriceTxt.setText(String.valueOf(totalPrice));
                                        titleTxt.setText(title);
                                        authorTxt.setText(author);
                                        availableDisplayTxt.setVisibility(View.VISIBLE);
                                        availableDisplayTxt.setText("Book available!");
                                        availableDisplayTxt.setTextColor(Color.GREEN);

                                    }else{
                                        totalPriceTxt.setVisibility(View.INVISIBLE);
                                        titleTxt.setVisibility(View.VISIBLE);
                                        authorTxt.setVisibility(View.VISIBLE);
                                        titleTxt.setText(title);
                                        authorTxt.setText(author);
                                        availableDisplayTxt.setVisibility(View.VISIBLE);
                                        availableDisplayTxt.setText("Book NOT available!");
                                        availableDisplayTxt.setTextColor(Color.RED);
                                    }

                                }

                            }else{
                                totalPriceTxt.setVisibility(View.INVISIBLE);
                                totalPriceTxt.setText("");
                                titleTxt.setVisibility(View.VISIBLE);
                                titleTxt.setText("NOT FOUND");
                                titleTxt.setTextColor(Color.RED);
                                authorTxt.setVisibility(View.VISIBLE);
                                authorTxt.setText("NOT FOUND");
                                authorTxt.setTextColor(Color.RED);
                                availableDisplayTxt.setVisibility(View.VISIBLE);
                                availableDisplayTxt.setText("NOT FOUND");
                                availableDisplayTxt.setTextColor(Color.RED);
                            }

                        }

                    }
                });
    }
}


//class BooksValidationThread extends Thread{
//    TextView availableDisplayTxt;
//    boolean isBorrowed;
//    Handler handler;
//
//    BooksValidationThread(TextView availableDisplayTxt, Handler handler, boolean isBorrowed){
//        this.availableDisplayTxt = availableDisplayTxt;
//        this.handler = handler;
//        this.isBorrowed = isBorrowed;
//    }
//
//    @Override
//    public void run() {
//        checkBooksExist(isBorrowed, availableDisplayTxt);
//        new Handler(Looper.getMainLooper()).post(new Runnable() {
//            @Override
//            public void run() {
//                availableDisplayTxt.setVisibility(View.VISIBLE);
//                availableDisplayTxt.setText("Checking availability...");
//            }
//        });
//    }
//
//    public void checkBooksExist(boolean isBorrowed, TextView availableDisplayTxt) {
//        if(isBorrowed){
//            new Handler(Looper.getMainLooper()).post(new Runnable() {
//                @Override
//                public void run() {
//                    availableDisplayTxt.setVisibility(View.VISIBLE);
//                    availableDisplayTxt.setText("Book NOT available!");
//                    availableDisplayTxt.setTextColor(Color.RED);
//                }
//            });
//        }else{
//            new Handler(Looper.getMainLooper()).post(new Runnable() {
//                @Override
//                public void run() {
//                    availableDisplayTxt.setVisibility(View.VISIBLE);
//                    availableDisplayTxt.setText("Book available!");
//                    availableDisplayTxt.setTextColor(Color.GREEN);
//                }
//            });
//        }
//    }
//
//}
