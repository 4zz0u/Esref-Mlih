package com.example.esrefmlih.Lifecycle.ViewPager.Statistics;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.esrefmlih.Database.Expenditure;
import com.example.esrefmlih.Lifecycle.ExpenditureViewModel;
import com.example.esrefmlih.R;

import java.util.ArrayList;
import java.util.List;

public class StatisticsFragment extends Fragment {
    View view;
    public StatisticsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.statistics_fragment, container, false);

        final String[] categories = {"Restaurant", "Cigarette", "Investissement", "Transport", "Santé", "Nourriture", "Taxes", "Voyage", "Voiture", "Sport", "Communication"};

        final int[] categoriesColors = {R.color.restaurant_color,
                R.color.smoke_color,
                R.color.business_color,
                R.color.transport_color,
                R.color.health_color,
                R.color.food_color,
                R.color.tax_color,
                R.color.trip_color,
                R.color.car_color,
                R.color.sport_color,
                R.color.communication_color
        };

        final ExpenditureViewModel expenditureViewModel = ViewModelProviders.of(this).get(ExpenditureViewModel.class);

        expenditureViewModel.getmAllExpenditures().observe(this, new Observer<List<Expenditure>>() {
            @Override
            public void onChanged(@Nullable List<Expenditure> expenditures) {

                ArrayList<Statistics> statistics = new ArrayList<>();
                for (int i = 0; i < categories.length; i++) {
                    float averagePerDay;
                    float averagePerMonth;
                    int totalExpenditure = 0;
                    for (Expenditure expenditure : expenditures){
                        if(expenditure.getCategory() == i) {
                            totalExpenditure += expenditure.getAmount();
                        }

                    }
                    averagePerDay = totalExpenditure/30;
                    averagePerMonth = totalExpenditure/12;

                    statistics.add(new Statistics(categories[i], averagePerDay, averagePerMonth, totalExpenditure, categoriesColors[i]));
                }

                StatisticsListAdapater listAdapater = new StatisticsListAdapater(getContext(), statistics);
                ListView statListview = view.findViewById(R.id.stat_listview);
                statListview.setAdapter(listAdapater);
            }
        });


        return view;
    }

}
