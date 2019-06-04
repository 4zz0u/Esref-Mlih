package com.example.esrefmlih.Lifecycle.ViewPager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.esrefmlih.R;
import com.github.mikephil.charting.charts.BarChart;

public class StatisticsFragment extends Fragment {
    View view;
    private static BarChart mBarChart;
    public StatisticsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.statistics_fragment, container, false);


        return view;
    }
}
