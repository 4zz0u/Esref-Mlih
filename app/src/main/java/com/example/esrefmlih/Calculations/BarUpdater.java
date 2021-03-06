package com.example.esrefmlih.Calculations;


import com.example.esrefmlih.Database.Expenditure;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BarUpdater {

    private List<Expenditure> mAllExpenditures;

    // Constructors
    public BarUpdater(List<Expenditure> allExpenditures) {
        mAllExpenditures = allExpenditures;
    }

    public BarUpdater() {
    }

    /**
     * This mehtod calculates the sum of expenditure's amounts per month and then updates
     * the BarChart's yValues to return it at the end.
     * @return yValues
     */
    public BarData update() {

        int[] mExpendituresPerMonth = new int[12];
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        Calendar cal = Calendar.getInstance();

        for (Expenditure expenditure : mAllExpenditures) {

            cal.setTime(expenditure.getDate());
            int month = cal.get(Calendar.MONTH);
            mExpendituresPerMonth[month] = mExpendituresPerMonth[month] + expenditure.getAmount();

        }
        for (int i = 0; i < 12; i++) {
            barEntries.add(new BarEntry(i, mExpendituresPerMonth[i]));
        }

        BarDataSet barDataSet = new BarDataSet(barEntries, "");
        barDataSet.setColors(ColorTemplate.JOYFUL_COLORS);


        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.9f);

        return barData;
    }

    public void barDrawer(BarChart barChart) {
        barChart.setElevation(4f);
        barChart.setFitBars(true);
        barChart.setDrawValueAboveBar(true);
        barChart.setMaxVisibleValueCount(50);
        barChart.setPinchZoom(true);
        barChart.getLegend().setEnabled(false);
        barChart.getDescription().setEnabled(false);

        barChart.animateXY(4000, 4000, Easing.EaseInBounce);

        String[] months = new String[]{"Jan", "Fev", "Mars", "Avril", "Mai", "Juin", "Juil", "Aout", "Sep", "Oct", "Nov", "Dec"};

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(months));
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);
    }
}
