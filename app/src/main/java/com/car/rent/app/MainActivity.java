package com.car.rent.app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private Spinner carlist;
    private EditText dailyRent, totalAmount, totalAmountWithTax;
    private SeekBar chooseDays;
    private TextView rentDays;
    private RadioGroup driversAge;
    private RadioButton driversAgeSelected;
    private CheckBox gps, childSeat, unlimitedMileage;
    private Button viewDetails, calculate;
    private ArrayAdapter<String> carlistAdapter;
    private String[] carListArray = new String[]{"---Please Choose a car---","BMW","Audi","Volks Wagon","Mercedes","Ferrari","Mahendra Thar","Dodge Viper","Fortuner","Range Rover","Rolls Royce"};

    //daily rent for cars (first field will be 0 for default option
    private int[] fixedRate = new int[]{0,50,60,80,100,110,120, 140, 150, 170, 200};


    public static String currency = "$";
    private int SELECTED_DAYS = 1;
    private int CAR_SELECTED=0;
    private int DRIVERS_AGE_TAX = 0;
    private int TOTAL_AMOUNT = 0;
    private double TOTAL_AMOUNT_WITH_TAX = 0.0;
    private int PASS =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //initilizing all edittexts
         dailyRent = findViewById(R.id.dailyRent);
         totalAmount = findViewById(R.id.totalAmount);
         totalAmountWithTax = findViewById(R.id.totalAmountWithTax);


        carlistAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, carListArray);
        carlist = findViewById(R.id.cars);
        carlist.setAdapter(carlistAdapter);
        carlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

               dailyRent.setText(""+currency+" " +fixedRate[i]);
                CAR_SELECTED = i;

                if(CAR_SELECTED == 0)
                dailyRent.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });


        //seekbar for selecting no. of days to rent a car
        chooseDays = findViewById(R.id.seekDays);
        rentDays = findViewById(R.id.rentDays);
        //default is 1 day
        rentDays.setText(String.format(getResources().getString(R.string.days),1));

        chooseDays.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                 rentDays.setText(String.format(getResources().getString(R.string.days),i));
                 SELECTED_DAYS = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //other features needed
        gps = findViewById(R.id.gps);
        childSeat = findViewById(R.id.childSeat);
        unlimitedMileage = findViewById(R.id.mileage);

        calculate = findViewById(R.id.calculate);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              calculateChanges();

            }
        });

        viewDetails = findViewById(R.id.viewDetails);
        viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //calling this to update all fields before switching activity
                calculateChanges();

                //used to check if all required fields have been fill by user
                if(PASS == 1)
                showFullDetails();


            }
        });



    }

    private void showFullDetails() {

        DecimalFormat df = new DecimalFormat("0.00");

        Intent i = new Intent(this, FullDetails.class);
        i.putExtra("carName",""+carListArray[CAR_SELECTED]);
        i.putExtra("dayRent",""+fixedRate[CAR_SELECTED]);
        i.putExtra("Days", ""+SELECTED_DAYS);
        i.putExtra("DriverAge", ""+getTax(driversAgeSelected.getText().toString()));
        i.putExtra("gps", gps.isChecked()?"5":"0");
        i.putExtra("childSeat", childSeat.isChecked()?"7":"0");
        i.putExtra("mileage", unlimitedMileage.isChecked()?"10":"0");
        i.putExtra("tax",""+df.format(TOTAL_AMOUNT*0.13));
        i.putExtra("totalAmountWithTax",""+df.format(TOTAL_AMOUNT_WITH_TAX));
        startActivity(i);



    }

    private void calculateChanges() {

        //getting drivers age
        driversAge = findViewById(R.id.driversAge);
        //getting the selected radiobutton using it's id from its view
        driversAgeSelected = findViewById(driversAge.getCheckedRadioButtonId());



        if(CAR_SELECTED<1)
            showT("Please select a car first!");
        else if(driversAgeSelected == null)
            showT("Please choose driver's age");
        else {

            PASS = 1;
            DRIVERS_AGE_TAX = getTax(driversAgeSelected.getText().toString());
            TOTAL_AMOUNT = (fixedRate[CAR_SELECTED] * SELECTED_DAYS) + getOptions() + DRIVERS_AGE_TAX;
            totalAmount.setText(currency + " " + TOTAL_AMOUNT);

            //13% tax
            double tax = TOTAL_AMOUNT * 0.13;
            DecimalFormat df = new DecimalFormat("0.00");

            TOTAL_AMOUNT_WITH_TAX = (TOTAL_AMOUNT + tax);
            totalAmountWithTax.setText(currency + " " + df.format(TOTAL_AMOUNT_WITH_TAX));

        }

    }

    private void showT(String s) {
        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();

    }

    // if drivers age is less than 20, add $5, if greater than 60, discount $10.
    private int getTax(String toString) {

        int ret = 0;

        if(toString.contains("20"))
            ret  = 5;
        else if (toString.contains("21"))
            ret = 0;
        else if(toString.contains("Above"))
            ret = -10;

        return ret;
    }

    //calculating extra option cost
    private int getOptions() {

        int ret = 0;
        if(gps.isChecked())
            ret +=5;
        if(childSeat.isChecked())
            ret+=7;
        if(unlimitedMileage.isChecked())
            ret+=10;

        return ret;
    }


}