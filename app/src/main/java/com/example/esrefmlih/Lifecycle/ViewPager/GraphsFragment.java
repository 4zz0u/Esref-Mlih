package com.example.esrefmlih.Lifecycle.ViewPager;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.esrefmlih.Calculations.BarUpdater;
import com.example.esrefmlih.Calculations.HorizontalBarUpdater;
import com.example.esrefmlih.Calculations.PieUpdater;
import com.example.esrefmlih.Calculations.RadarUpdater;
import com.example.esrefmlih.Database.Expenditure;
import com.example.esrefmlih.Lifecycle.ExpenditureViewModel;
import com.example.esrefmlih.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.RadarData;

import java.util.List;

public class GraphsFragment extends Fragment {

    private BarUpdater barUpdater;
    private PieUpdater pieUpdater;
    private RadarUpdater radarUpdater;
    private HorizontalBarUpdater horizontalBarUpdater;
    private static PieChart mPieChart;
    private static BarChart mBarChart;
    private static RadarChart mRadarChart;
    private static HorizontalBarChart mHorizontalBarChart;
    public GraphsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.graphs_fragment, container, false);


        /* Initiate the ViewModel to an observer so it updates the data on the pie chart
                            whenever a new expenditure is added */
        final ExpenditureViewModel mExpenditureViewModel = ViewModelProviders.of(this).get(ExpenditureViewModel.class);

        // Adding an observer to the list of expenditures
        mExpenditureViewModel.getmAllExpenditures().observe(this, new Observer<List<Expenditure>>() {
            @Override
            public void onChanged(@Nullable List<Expenditure> expenditures) {
                //expenditures are a list of expenditures fetched from the database with the query request made by the view model

                pieUpdater = new PieUpdater(expenditures);
                barUpdater = new BarUpdater(expenditures);
                radarUpdater = new RadarUpdater(expenditures);
                horizontalBarUpdater = new HorizontalBarUpdater(expenditures);


                PieData pieData = pieUpdater.update(getContext());
                BarData barData = barUpdater.update();
                RadarData radarData = radarUpdater.update();
                BarData horizontalBarData = horizontalBarUpdater.update();


                mPieChart.setData(pieData);
                mPieChart.invalidate(); //refresh
                mPieChart.notifyDataSetChanged();

                mBarChart.setData(barData);
                mBarChart.invalidate(); //refresh
                mBarChart.notifyDataSetChanged();

                mRadarChart.setData(radarData);
                mRadarChart.invalidate(); //refresh
                mRadarChart.notifyDataSetChanged();

                mHorizontalBarChart.setData(horizontalBarData);
                mHorizontalBarChart.invalidate(); //refresh
                mHorizontalBarChart.notifyDataSetChanged();

            }
        });

        // finding views from the ressources
        mPieChart = view.findViewById(R.id.pieChart);
        mBarChart = view.findViewById(R.id.barChart);
        mRadarChart = view.findViewById(R.id.radarChart);
        mHorizontalBarChart = view.findViewById(R.id.horizontalBarChart);

        // gives the piechart the different parameters
        pieUpdater = new PieUpdater();
        pieUpdater.pieDrawer(mPieChart, getContext()); // the context is needed for slice coloring

        // gives the vertical bar chart the different parameters
        barUpdater = new BarUpdater();
        barUpdater.barDrawer(mBarChart);

        // gives the radar chart the different parameters
        radarUpdater = new RadarUpdater();
        radarUpdater.radarDrawer(mRadarChart);

        // gives the horizontal bar chart the different parameters
        horizontalBarUpdater = new HorizontalBarUpdater();
        horizontalBarUpdater.horizontalBarDrawer(mHorizontalBarChart);

        return view;
    }
}