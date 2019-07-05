package com.example.esrefmlih.Lifecycle;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.esrefmlih.Database.Expenditure;

import java.util.List;

public class ExpenditureViewModel extends AndroidViewModel {

    private Repository mRepository;
    private LiveData<List<Expenditure>> mAllExpenditures;

    public ExpenditureViewModel(@NonNull Application application) {
        super(application);

        mRepository = new Repository(application);
        mAllExpenditures = mRepository.getmAllExpenditures();
    }

    public LiveData<List<Expenditure>> getmAllExpenditures() {
        return mAllExpenditures;
    }

    public void insert(Expenditure expenditure) {
        mRepository.insert(expenditure);
    }

    public void update(Expenditure expenditure) { mRepository.update(expenditure); }

    public void deleteAll() { mRepository.deleteAll(); }

    public void delete(Expenditure expenditure) { mRepository.delete(expenditure); }

    public float averageAmount(int category) { return mRepository.averageAmount(category); }
}
