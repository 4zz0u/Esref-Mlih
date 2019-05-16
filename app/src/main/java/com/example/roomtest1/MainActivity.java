package com.example.roomtest1;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    public static PieChart mPieChart;
    private String[] mCategories = {"Restaurant", "Smoke", "Business", "Transport", "Health", "Food", "Taxes", "Car", "Sport", "Communication"};
    private static int mRestaurantAmount = 0;
    private static int mSmokeAmount = 0;
    private static int mBusinessAmount = 0;
    private static int mTransportAmount = 0;
    private static int mHealthAmount = 0;
    private static int mFoodAmount = 0;
    private static int mTaxesAmount = 0;
    private static int mCarAmount = 0;
    private static int mSportAmount = 0;
    private static int mCommunicationAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        /* Initiate the ViewModel to an observer so it updates the data on the pie chart
                            whenever a new expenditure is added */
        final ExpenditureViewModel mExpenditureViewModel = ViewModelProviders.of(this).get(ExpenditureViewModel.class);

        // Adding an observer to the list of expenditures
        mExpenditureViewModel.getmAllExpenditures().observe(this, new Observer<List<Expenditure>>() {
            @Override
            public void onChanged(@Nullable List<Expenditure> expenditures) {

                // yValues : contains the values of each expenditure per category
                final ArrayList<PieEntry> yValues = new ArrayList<>();

                // Gets all the expenditures from the ViewModel and store them in a variable of type LiveData<List<Expenditure>>
                LiveData<List<Expenditure>> observedExpenditures = mExpenditureViewModel.getmAllExpenditures();

                // G
                List<Expenditure> allExpenditures = observedExpenditures.getValue();

                try {
                    for (Expenditure expenditure : allExpenditures) {
                        if (expenditure.getCategory() == 0) {
                            mRestaurantAmount = mRestaurantAmount + expenditure.getAmount();
                        }
                    }
                    for (Expenditure expenditure : allExpenditures) {
                        if (expenditure.getCategory() == 1) {
                            mSmokeAmount = mSmokeAmount + expenditure.getAmount();
                        }
                    }
                    for (Expenditure expenditure : allExpenditures) {
                        if (expenditure.getCategory() == 2) {
                            mBusinessAmount = mBusinessAmount + expenditure.getAmount();
                        }
                    }
                    for (Expenditure expenditure : allExpenditures) {
                        if (expenditure.getCategory() == 3) {
                            mTransportAmount = mTransportAmount + expenditure.getAmount();
                        }
                    }
                    for (Expenditure expenditure : allExpenditures) {
                        if (expenditure.getCategory() == 4) {
                            mHealthAmount = mHealthAmount + expenditure.getAmount();
                        }
                    }
                    for (Expenditure expenditure : allExpenditures) {
                        if (expenditure.getCategory() == 5) {
                            mFoodAmount = mFoodAmount + expenditure.getAmount();
                        }
                    }
                    for (Expenditure expenditure : allExpenditures) {
                        if (expenditure.getCategory() == 6) {
                            mTaxesAmount = mTaxesAmount + expenditure.getAmount();
                        }
                    }
                    for (Expenditure expenditure : allExpenditures) {
                        if (expenditure.getCategory() == 7) {
                            mCarAmount = mCarAmount + expenditure.getAmount();
                        }
                    }
                    for (Expenditure expenditure : allExpenditures) {
                        if (expenditure.getCategory() == 8) {
                            mSportAmount = mSportAmount + expenditure.getAmount();
                        }
                    }
                    for (Expenditure expenditure : allExpenditures) {
                        if (expenditure.getCategory() == 9) {
                            mCommunicationAmount = mCommunicationAmount + expenditure.getAmount();
                        }
                    }

                    TextView textView = (TextView) findViewById(R.id.textView);
                    String string = Integer.toString(mRestaurantAmount);
                    textView.setHint(string);
                    yValues.add(new PieEntry(mRestaurantAmount, "Restaurant"));
                    yValues.add(new PieEntry(mSmokeAmount, "Smoke"));
                    yValues.add(new PieEntry(mBusinessAmount, "Business"));
                    yValues.add(new PieEntry(mTransportAmount, "Transport"));
                    yValues.add(new PieEntry(mHealthAmount, "Health"));
                    yValues.add(new PieEntry(mFoodAmount, "Food"));
                    yValues.add(new PieEntry(mTaxesAmount, "Taxes"));
                    yValues.add(new PieEntry(mCarAmount, "Car"));
                    yValues.add(new PieEntry(mSportAmount, "Sport"));
                    yValues.add(new PieEntry(mCommunicationAmount, "Communication"));

                } catch (NullPointerException e) {
                    yValues.add(new PieEntry(0f, "Restaurant"));
                    yValues.add(new PieEntry(0f, "Smoke"));
                    yValues.add(new PieEntry(0f, "Business"));
                    yValues.add(new PieEntry(0f, "Transport"));
                    yValues.add(new PieEntry(0f, "Health"));
                    yValues.add(new PieEntry(0f, "Food"));
                    yValues.add(new PieEntry(0f, "Taxes"));
                    yValues.add(new PieEntry(0f, "Car"));
                    yValues.add(new PieEntry(0f, "Sport"));
                    yValues.add(new PieEntry(0f, "Communication"));
                }

                PieDataSet dataSet = new PieDataSet(yValues, "");

                dataSet.setSliceSpace(3f);
                dataSet.setSelectionShift(10f);
                dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

                PieData data = new PieData((dataSet));
                data.setValueTextSize(20f);
                data.setValueTextColor(Color.WHITE);

                mPieChart.setData(data);
                mPieChart.invalidate(); //refresh
            }
        });

        //add a listener on the expenditure-add ImageView => takes you to the AddExpenditureActivity
        ImageView ic_add = findViewById(R.id.ic_add);

        ic_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addExpenditureIntent = new Intent(getApplicationContext(), AddExpenditureActivity.class);
                startActivity(addExpenditureIntent);
            }
        });



        mPieChart = (PieChart) findViewById(R.id.pieChart);

        // Enable middle hole to appear
        mPieChart.setDrawHoleEnabled(true);

        // Enable percentage
        mPieChart.setUsePercentValues(true);

        // Disable description
        mPieChart.getDescription().setEnabled(false);
        mPieChart.setExtraOffsets(10, 2, 10, 15);

        //Friction parameter for dragging the mPieChart
        mPieChart.setDragDecelerationFrictionCoef(0.15f);

        //The hole's parameter
        mPieChart.setDrawHoleEnabled(true);
        mPieChart.setHoleColor(Color.WHITE);
        mPieChart.setTransparentCircleRadius(61f);



        mPieChart.animateY(10000, Easing.EaseInOutBack);



    }
}
