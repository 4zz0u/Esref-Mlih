package com.example.roomtest1;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class ExpenditureViewModel extends AndroidViewModel {

    private Repository mRepository;
    private LiveData<List<Expenditure>> mAllExpenditures;

    public ExpenditureViewModel(@NonNull Application application) {
        super(application);

        mRepository = new Repository(application);
        mAllExpenditures = mRepository.getmAllExpenditures();
    }

    LiveData<List<Expenditure>> getmAllExpenditures() {
        return mAllExpenditures;
    }

    public void insert(Expenditure expenditure) {
        mRepository.insert(expenditure);
    }

}
