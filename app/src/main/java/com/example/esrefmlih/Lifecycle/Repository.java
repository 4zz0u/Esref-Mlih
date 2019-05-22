package com.example.esrefmlih.Lifecycle;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.esrefmlih.Database.Expenditure;
import com.example.esrefmlih.Database.ExpenditureDao;
import com.example.esrefmlih.Database.ExpenditureRoomDatabase;

import java.util.List;

public class Repository {
    private ExpenditureDao mExpenditureDao;
    private LiveData<List<Expenditure>> mAllExpenditures;

    //Add a constructor that gets a handle to the database and initializes the member variables.
    Repository(Application application) {
        ExpenditureRoomDatabase db = ExpenditureRoomDatabase.getDatabase(application);
        mExpenditureDao = db.expenditureDao();
        mAllExpenditures = mExpenditureDao.getAllExpenditures();
    }


    //Add a wrapper for getAllWords().
    // Room executes all queries on a separate thread. Observed LiveData will notify the observer when the data has changed.

    LiveData<List<Expenditure>> getmAllExpenditures() {
        return mAllExpenditures;
    }


    //Add a wrapper for the insert() method. You must call this on a non-UI thread (the insertAsyncTask we declared) or your app will crash.
    // Room ensures that you don't do any long-running operations on the main thread, blocking the UI.

    public void insert(Expenditure expenditure) {
        new insertAsyncTask(mExpenditureDao).execute(expenditure);
    }

    // Declaring an nested class for the Insert asynchronous task to the database

    private static class insertAsyncTask extends AsyncTask<Expenditure, Void, Void> {

        private ExpenditureDao mAsyncTaskDao;

        insertAsyncTask(ExpenditureDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Expenditure... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

}
