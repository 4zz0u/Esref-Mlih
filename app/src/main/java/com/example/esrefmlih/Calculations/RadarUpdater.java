package com.example.esrefmlih.Calculations;

import android.content.Context;
import android.graphics.Color;

import com.example.esrefmlih.Database.Expenditure;
import com.example.esrefmlih.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class RadarUpdater {
    private List<Expenditure> mAllExpenditures;

    // Constructors
    public RadarUpdater(List<Expenditure> allExpenditures) {
        mAllExpenditures = allExpenditures;
    }

    public RadarUpdater() {
    }

    /**
     * This mehtod calculates the sum of expenditure's amounts per month and then updates
     * the BarChart's yValues to return it at the end.
     * @return yValues
     */
    public RadarData update() {

        int[] mExpendituresPerCategories = new int[11];
        ArrayList<RadarEntry> radarEntries = new ArrayList<>();
        int i;

        for (Expenditure expenditure : mAllExpenditures) {
        // gets the expenditures from database
            i = expenditure.getCategory();
            mExpendituresPerCategories[i] += expenditure.getAmount();
        }
        for (i = 0; i < 11; i++) {
            radarEntries.add(new RadarEntry(mExpendituresPerCategories[i]));
        }

        RadarDataSet radarDataset = new RadarDataSet(radarEntries, "");
        radarDataset.setColors(ColorTemplate.COLORFUL_COLORS);

        return new RadarData(radarDataset);
    }

    public void radarDrawer(RadarChart radarChart) {
        //Radar chart drawer

        radarChart.setDrawWeb(true);
        //radarChart.setWebLineWidth(1.65f);
        radarChart.setWebColor(R.color.colorRedDark);
        radarChart.animateXY(4000, 5000, Easing.EaseInElastic);
        radarChart.getDescription().setEnabled(false);




        String[] categories = new String[]{"Restaurant", "Cigarette", "Investissement", "Transport", "Santé", "Nourriture", "Taxes", "Voyage", "Voiture", "Sport", "Communication"};
        XAxis xAxis = radarChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(categories));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(false);
        xAxis.setTextColor(R.color.colorDarkBlue);
        xAxis.setTextSize(12f);

    }
}
