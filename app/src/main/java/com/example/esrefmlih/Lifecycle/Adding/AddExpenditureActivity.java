package com.example.esrefmlih.Lifecycle.Adding;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.esrefmlih.Database.Expenditure;
import com.example.esrefmlih.Lifecycle.ExpenditureViewModel;
import com.example.esrefmlih.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class AddExpenditureActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    //Declaring the dialog's components
    private Dialog mEpicDialog;
    private Button mConfirmBtn;
    private ImageView mClosePopup;
    private EditText mAmountEditText;
    private TextView mDatePicker;

    //Declare an instance of ViewModel, that will take the store the Expenditure data and store it into the database
    ExpenditureViewModel mExpenditureViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expenditure);

        // Initializing the ViewModel
        mExpenditureViewModel = ViewModelProviders.of(this).get(ExpenditureViewModel.class);

        // Initializing the Dialog
        mEpicDialog = new Dialog(this);

        final ArrayList<Category> categories = new ArrayList<Category>();

        categories.add(new Category(1, R.color.restaurant_color, "Restaurant", R.drawable.baseline_restaurant_24));
        categories.add(new Category(2, R.color.smoke_color, "Smoke", R.drawable.twotone_smoking_rooms_24));
        categories.add(new Category(3, R.color.business_color, "Business", R.drawable.twotone_business_center_24));
        categories.add(new Category(4, R.color.transport_color, "Transport", R.drawable.twotone_directions_transit_24));
        categories.add(new Category(5, R.color.health_color, "Health", R.drawable.baseline_local_hospital_24));
        categories.add(new Category(6, R.color.food_color, "Food", R.drawable.twotone_fastfood_24));
        categories.add(new Category(7, R.color.tax_color, "Taxes", R.drawable.baseline_account_balance_24));
        categories.add(new Category(8, R.color.trip_color, "Trips", R.drawable.baseline_flight_24));
        categories.add(new Category(9, R.color.car_color, "Car", R.drawable.baseline_directions_car_24));
        categories.add(new Category(10, R.color.sport_color, "Sport", R.drawable.baseline_fitness_center_24));
        categories.add(new Category(11, R.color.communication_color, "Communication", R.drawable.baseline_call_24));


        final CategoryArrayAdapter itemAdapter = new CategoryArrayAdapter(this, categories);

        ListView categoryListView = (ListView) findViewById(R.id.listView);
        categoryListView.setAdapter(itemAdapter);

        //setting a click listener on ListView's ITEMS
        categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //When the item is clicked, the Dialog view should popup to enter the expenditure's amount and pick a date for it
                ShowEpicDialog(position);
            }
        });
    }

    /**
     * This method pops up the Dialog's view
     */
    private void ShowEpicDialog(final int itemPosition) {
        // Finding the dialog's component's Ids
        mEpicDialog.setContentView(R.layout.epic_popup_add_expenditure);
        mClosePopup = (ImageView) mEpicDialog.findViewById(R.id.closePopup);
        mAmountEditText = (EditText) mEpicDialog.findViewById(R.id.amountEditText);
        mDatePicker = (TextView) mEpicDialog.findViewById(R.id.datePicker);
        mConfirmBtn = (Button) mEpicDialog.findViewById(R.id.confirmBtn);

        // Set a listener in the Closing cross
        mClosePopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEpicDialog.dismiss();
            }
        });

        // Set a date picker listener
        mDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        // THE REAL SHIT : When you click on this button, the Category position, the Amount and the Date
        // Should get stored in the database
        mConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast toast;

                if(TextUtils.isEmpty(mAmountEditText.getText())) {
                    // Showing this toast after confirming the transaction
                    toast = Toast.makeText(getApplicationContext(), "Select an amount", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.setMargin(0, 0.05f);
                    toast.show();
                } else if (mDatePicker.getText().toString().equals("Date picker")){
                    toast = Toast.makeText(getApplicationContext(), "Select a date", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.setMargin(0, 0.05f);
                    toast.show();
                }
                else {
                    String amountString = mAmountEditText.getText().toString();
                    int amount = Integer.parseInt(amountString);
                    try {
                        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(mDatePicker.getText().toString());
                        Expenditure expenditure = new Expenditure(itemPosition, amount, date);
                        mExpenditureViewModel.insert(expenditure);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    // Showing this toast after confirming the transaction
                    toast = Toast.makeText(getApplicationContext(), "Expenditure added", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.setMargin(0, 0.05f);
                    toast.show();

                    mEpicDialog.dismiss();
                }




            }
        });

        mEpicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mEpicDialog.show();
    }


    /**
     * This method shows the Date Picker dialog
     */
    private void showDateDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000); // gets the current day
        datePickerDialog.show();
    }

    // This will be triggered when a date is selected
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = dayOfMonth + "/" + month + "/" + year;
        mDatePicker.setText(date);

        // Showing this toast after picking the date
        Toast dateToast = Toast.makeText(this, "Date has been selected", Toast.LENGTH_SHORT);
        dateToast.setGravity(Gravity.BOTTOM, 0, 0);
        dateToast.setMargin(0, 0.05f);
        dateToast.show();
    }

}
