package com.example.esrefmlih.Calculations;

import android.content.Context;

import com.example.esrefmlih.Database.Expenditure;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class HorizontalBarUpdater {
    private List<Expenditure> mAllExpenditures;

    // Constructors
    public HorizontalBarUpdater(List<Expenditure> allExpenditures) {
        mAllExpenditures = allExpenditures;
    }

    public HorizontalBarUpdater() {
    }

    /**
     * This mehtod calculates the sum of expenditure's amounts per month and then updates
     * the BarChart's yValues to return it at the end.
     *
     * @return yValues
     */
    public BarData update() {

        int[] mExpendituresPerCategories = new int[12];
        ArrayList<BarEntry> barEntries = new ArrayList<>();

        for (Expenditure expenditure : mAllExpenditures) {
            mExpendituresPerCategories[expenditure.getCategory()] += expenditure.getAmount();

        }
        for (int i = 0; i < 12; i++) {
            barEntries.add(new BarEntry(i, mExpendituresPerCategories[i]));
        }

        BarDataSet barDataSet = new BarDataSet(barEntries, "");
        barDataSet.setColors(ColorTemplate.JOYFUL_COLORS);


        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.9f);

        return barData;
    }

    public void horizontalBarDrawer(HorizontalBarChart horizontalBarChart) {
        horizontalBarChart.setElevation(50f);
        horizontalBarChart.setFitBars(true);
        horizontalBarChart.setMaxVisibleValueCount(50);
        horizontalBarChart.setPinchZoom(true);
        horizontalBarChart.getLegend().setEnabled(false);

        horizontalBarChart.animateXY(4000, 4000, Easing.EaseInBounce);

        String[] categories = new String[]{"Restaurant", "Cigarette", "Investissement", "Transport", "Santé", "Nourriture", "Taxes", "Voyage", "Voiture", "Sport", "Communication"};

        XAxis xAxis = horizontalBarChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(categories));
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        xAxis.setGranularity(1);
        xAxis.setTextSize(2f);
    }
}
