package com.example.esrefmlih.Calculations;


import com.example.esrefmlih.Database.Expenditure;
import com.github.mikephil.charting.data.PieEntry;
import java.util.ArrayList;
import java.util.List;

public class PieUpdater {

    private List<Expenditure> mAllExpenditures;

    // Constructor
    public PieUpdater(List<Expenditure> allExpenditures) {
        mAllExpenditures = allExpenditures;
    }


    /**
     * This mehtod calculates the sum of expenditure's amounts per category and then updates
     * the PieChart's yValues to return it at the end.
     * @return yValues
     */

    public ArrayList<PieEntry> update() {

        int[] mExpendituresPerCategory = new int[11];
        ArrayList<PieEntry> yValues = new ArrayList<>();
        String[] categories = {"Restaurant", "Smoke", "Business", "Transport", "Health", "Food", "Taxes", "Trips", "Car", "Sport", "Communication"};
        int i;

        try {
            for (Expenditure expenditure : mAllExpenditures) {
                i = expenditure.getCategory();
                mExpendituresPerCategory[i] = mExpendituresPerCategory[i] + expenditure.getAmount();
            }
            for(i = 0; i < mExpendituresPerCategory.length; i++) {
                yValues.add(new PieEntry(mExpendituresPerCategory[i], categories[i]));
            }

        } catch (NullPointerException e) {
            for(i = 0; i < categories.length; i++) {
                yValues.add(new PieEntry(0f, categories[i]));
            }
        }
        return yValues;
    }
}
