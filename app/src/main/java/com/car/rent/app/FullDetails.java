package com.car.rent.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class FullDetails extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_details);

        Intent in = getIntent();
        String carName = in.getStringExtra("carName");
        String dayRent = MainActivity.currency+in.getStringExtra("dayRent");
        String Days = in.getStringExtra("Days");
        String DriverAge = in.getStringExtra("DriverAge").contains("-") ?"-$10" : MainActivity.currency+in.getStringExtra("DriverAge");
        String gps = MainActivity.currency+in.getStringExtra("gps");
        String childSeat = MainActivity.currency+in.getStringExtra("childSeat");
        String mileage = MainActivity.currency+in.getStringExtra("mileage");
        String tax = MainActivity.currency+in.getStringExtra("tax");
        String totalAmountWithTax = MainActivity.currency+in.getStringExtra("totalAmountWithTax");


        TextView full = findViewById(R.id.full);
        full.setText(
                "Car Name : "+ carName +"\n\n"+
                "Daily Rent : "+ dayRent +"\n\n"+
                "Needed For : "+ Days +" day(s)\n\n"+
                "Driver's Age Charges : "+ DriverAge+"\n\n"+
                "GPS Charges: "+ gps +"\n\n"+
                "Child Seat: "+ childSeat +"\n\n"+
                "Unlimited Mileage : "+ mileage +"\n\n"+
                "Tax (13%) : "+ tax +"\n\n"+
                "Final Amount : "+ totalAmountWithTax


        );


    }
}
