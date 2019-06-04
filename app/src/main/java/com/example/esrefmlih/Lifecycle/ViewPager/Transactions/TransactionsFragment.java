package com.example.esrefmlih.Lifecycle.ViewPager.Transactions;

import android.arch.lifecycle.LiveData;
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

public class TransactionsFragment extends Fragment {
    View view;
    public TransactionsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.transactions_fragment, container, false);

        /* Initiate the ViewModel to an observer so it updates the data on the transactions history
                            whenever a new expenditure is added */

        final ExpenditureViewModel expenditureViewModel = ViewModelProviders.of(this).get(ExpenditureViewModel.class);

        expenditureViewModel.getmAllExpenditures().observe(this, new Observer<List<Expenditure>>() {
            @Override
            public void onChanged(@Nullable List<Expenditure> expenditures) {

                LiveData<List<Expenditure>> liveDataExpenditures = expenditureViewModel.getmAllExpenditures();
                List<Expenditure> allExpenditures =  liveDataExpenditures.getValue();

                TransactionListAdapter listAdapter = new TransactionListAdapter(getContext(), allExpenditures);
                ListView transactionListView = view.findViewById(R.id.transaction_listview);
                transactionListView.setAdapter(listAdapter);
            }
        });

        return view;
    }
}
