package com.example.esrefmlih.Lifecycle.ViewPager;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.esrefmlih.Calculations.PieUpdater;
import com.example.esrefmlih.Database.Expenditure;
import com.example.esrefmlih.Lifecycle.ExpenditureViewModel;
import com.example.esrefmlih.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class GraphsFragment extends Fragment {
    View view;
    public static PieChart mPieChart;
    public GraphsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.graphs_fragment, container, false);


        /* Initiate the ViewModel to an observer so it updates the data on the pie chart
                            whenever a new expenditure is added */
        final ExpenditureViewModel mExpenditureViewModel = ViewModelProviders.of(this).get(ExpenditureViewModel.class);

        // Adding an observer to the list of expenditures
        mExpenditureViewModel.getmAllExpenditures().observe(this, new Observer<List<Expenditure>>() {
            @Override
            public void onChanged(@Nullable List<Expenditure> expenditures) {


                // Gets all the expenditures from the ViewModel and store them in a variable of type LiveData<List<Expenditure>>
                LiveData<List<Expenditure>> observedExpenditures = mExpenditureViewModel.getmAllExpenditures();

                // Gets a list of the expenditures by unwrapping it from live data
                List<Expenditure> allExpenditures = observedExpenditures.getValue();

                PieUpdater pieUpdater = new PieUpdater(allExpenditures);

                // yValues : contains the values of each expenditure per category
                ArrayList<PieEntry> yValues;
                yValues = pieUpdater.update();


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


        mPieChart = (PieChart) view.findViewById(R.id.pieChart);

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
        int holeColor = ContextCompat.getColor(getContext(), R.color.colorAccent);
        mPieChart.setHoleColor(holeColor);
        mPieChart.setTransparentCircleRadius(61f);
        mPieChart.setTransparentCircleAlpha(110);
        mPieChart.getLegend().setEnabled(false);


        // Setting categories colors
        Context context = getContext();
        int [] categoriesColors = {ContextCompat.getColor(context, R.color.restaurant_color),
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
        View pieChild = mPieChart.getChildAt(0);


        mPieChart.animateY(4000, Easing.EaseInOutBack);

        return view;
    }


}
