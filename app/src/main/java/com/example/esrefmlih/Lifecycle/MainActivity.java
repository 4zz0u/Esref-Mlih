package com.example.esrefmlih.Lifecycle;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.esrefmlih.Lifecycle.Adding.AddExpenditureActivity;
import com.example.esrefmlih.Lifecycle.ViewPager.StatisticsFragment;
import com.example.esrefmlih.Lifecycle.ViewPager.GraphsFragment;
import com.example.esrefmlih.Lifecycle.ViewPager.Transactions.TransactionsFragment;
import com.example.esrefmlih.Lifecycle.ViewPager.ViewPagerAdapter;
import com.example.esrefmlih.R;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private ExpenditureViewModel mExpenditureViewModel;

    //Dialog components
    private Button mConfirmBtn;
    private ImageView mClosePopup;
    private Dialog mEpicDeleteDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Finding the views
        TabLayout mTabLayout = findViewById(R.id.tabLayout);
        Toolbar mToolBar =  findViewById(R.id.toolBar);
        setSupportActionBar(mToolBar);
        ViewPager mViewPager = findViewById(R.id.viewPager);
        mDrawer = findViewById(R.id.drawer_layout);
        mEpicDeleteDialog = new Dialog(this);


        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        // Adding fragments
        viewPagerAdapter.addFragment(new GraphsFragment(), getString(R.string.graphs_tab));
        viewPagerAdapter.addFragment(new StatisticsFragment(), getString(R.string.statistics_tab));
        viewPagerAdapter.addFragment(new TransactionsFragment(), getString(R.string.transactions_tab));
        // Adapter setup
        mViewPager.setAdapter(viewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        // Adds a listener on the expenditure-add ImageView => takes you to the AddExpenditureActivity
        FloatingActionButton ic_add = findViewById(R.id.ic_add);
        ic_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addExpenditureIntent = new Intent(getApplicationContext(), AddExpenditureActivity.class);
                startActivity(addExpenditureIntent);
            }
        });

        // Initializing the ViewModel
        mExpenditureViewModel = ViewModelProviders.of(this).get(ExpenditureViewModel.class);
        // Adds a listener to the Navigation View items
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.export_to_excel:
                        break;
                    case R.id.switch_language:
                        break;
                    case R.id.delete_data:
                        showDeleteConfirmationPopup();
                        break;
                }
                return true;
            }
        });

        // Adds a hamburger toggle on the up right corner of the toolbar that opens and closes the navigation drawer when dragged
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, mToolBar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        mDrawer.addDrawerListener(toggle);
        // This will take care of rotating the hamburger icon to get it lift the draw itself
        toggle.syncState();

    }

    @Override
    public void onBackPressed() {

        // checks if the Navigation drawer is open, then when we press the back button, it will closes the navigation
        // NOT THE ACTIVITY
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    /**
     * This method shows a confirmation pops up dialog that assures the user's decision
     */
    private void showDeleteConfirmationPopup() {
        mEpicDeleteDialog.setContentView(R.layout.epic_popup_delete_data);
        mEpicDeleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mClosePopup = mEpicDeleteDialog.findViewById(R.id.closeDeletePopup);
        mConfirmBtn = mEpicDeleteDialog.findViewById(R.id.confirmDeleteBtn);

        mClosePopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEpicDeleteDialog.dismiss();
            }
        });

        mConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpenditureViewModel.deleteAll();
                mEpicDeleteDialog.dismiss();
            }
        });

        mEpicDeleteDialog.show();
    }
}
