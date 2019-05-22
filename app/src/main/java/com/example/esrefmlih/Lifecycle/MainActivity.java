package com.example.esrefmlih.Lifecycle;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.esrefmlih.Lifecycle.Adding.AddExpenditureActivity;
import com.example.esrefmlih.Database.Expenditure;
import com.example.esrefmlih.Calculations.PieUpdater;
import com.example.esrefmlih.Lifecycle.ViewPager.BarFragment;
import com.example.esrefmlih.Lifecycle.ViewPager.PieFragment;
import com.example.esrefmlih.Lifecycle.ViewPager.TransactionsFragment;
import com.example.esrefmlih.Lifecycle.ViewPager.ViewPagerAdapter;
import com.example.esrefmlih.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private AppBarLayout mAppBarLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        // Adding fragments
        viewPagerAdapter.addFragment(new PieFragment(), "Statistics");
        viewPagerAdapter.addFragment(new BarFragment(), "Monthly");
        viewPagerAdapter.addFragment(new TransactionsFragment(), "Transactions");
        // Adapter setup
        mViewPager.setAdapter(viewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);


    }
}
