package com.example.esrefmlih.Calculations;


import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import com.example.esrefmlih.Database.Expenditure;
import com.example.esrefmlih.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class PieUpdater {

    private List<Expenditure> mAllExpenditures;

    // Constructors
    public PieUpdater(List<Expenditure> allExpenditures) {
        mAllExpenditures = allExpenditures;
    }

    public PieUpdater() {

    }


    /**
     * This mehtod calculates the sum of expenditure's amounts per category and then updates
     * the PieChart's yValues and returns the data that will be fetched to the chart's instance at the end.
     *
     * @return pieData
     */

    public PieData update(Context context) {

        int[] mExpendituresPerMonths = new int[11];

        ArrayList<PieEntry> yValues = new ArrayList<>();
        String[] categories = new String[]{"Restaurant", "Cigarette", "Investissement", "Transport", "Santé", "Nourriture", "Taxes", "Voyage", "Voiture", "Sport", "Communication"};

        int i;


        for (Expenditure expenditure : mAllExpenditures) {
            i = expenditure.getCategory();
            mExpendituresPerMonths[i] = mExpendituresPerMonths[i] + expenditure.getAmount();
        }
        for (i = 0; i < mExpendituresPerMonths.length; i++) {
            if (mExpendituresPerMonths[i] != 0) {
                yValues.add(new PieEntry(mExpendituresPerMonths[i], categories[i]));
            }
        }

        PieDataSet dataSet = new PieDataSet(yValues, "");

        // Setting categories colors
        int[] categoriesColors = {ContextCompat.getColor(context, R.color.restaurant_color),
                ContextCompat.getColor(context, R.color.smoke_color),
                ContextCompat.getColor(context, R.color.business_color),
                ContextCompat.getColor(context, R.color.transport_color),
                ContextCompat.getColor(context, R.color.health_color),
                ContextCompat.getColor(context, R.color.food_color),
                ContextCompat.getColor(context, R.color.tax_color),
                ContextCompat.getColor(context, R.color.trip_color),
                ContextCompat.getColor(context, R.color.car_color),
                ContextCompat.getColor(context, R.color.sport_color),
                ContextCompat.getColor(context, R.color.communication_color)
        };
        dataSet.setColors(categoriesColors);
        dataSet.setSliceSpace(0f);
        dataSet.setSelectionShift(10f);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(20f);
        data.setValueTextColor(Color.WHITE);

        return data;
    }


    public void pieDrawer(PieChart pieChart, Context context) {
        // Enable center hole to appear
        pieChart.setDrawHoleEnabled(true);

        // Enable percentage
        pieChart.setUsePercentValues(true);

        // Disable description
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(10, 2, 10, 15);

        //Friction parameter for dragging the pieChart
        pieChart.setDragDecelerationFrictionCoef(0.15f);

        //The hole's parameter
        pieChart.setDrawHoleEnabled(true);
        int holeColor = ContextCompat.getColor(context, R.color.colorAccent);
        pieChart.setHoleColor(holeColor);
        pieChart.setTransparentCircleRadius(57f);
        pieChart.setTransparentCircleAlpha(110);
        pieChart.getLegend().setEnabled(false);

        pieChart.animateY(4000, Easing.EaseInOutBack);

    }
}
