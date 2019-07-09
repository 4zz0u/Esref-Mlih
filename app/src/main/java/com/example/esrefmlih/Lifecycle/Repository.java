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
    private float average;

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


    // Add a wrapper for the insert() method. You must call this on a non-UI thread (the insertAsyncTask we declared) or your app will crash.
    // Room ensures that you don't do any long-running operations on the main thread, blocking the UI.

    public void insert(Expenditure expenditure) {
        new insertAsyncTask(mExpenditureDao).execute(expenditure);
    }

    public void update(Expenditure expenditure) {
        new updateAsyncTask(mExpenditureDao).execute(expenditure);
    }


    // Declaring an inner class for the Insert asynchronous task to the database

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
    // Declaring an inner class for the Insert asynchronous task to the database

    private static class updateAsyncTask extends AsyncTask<Expenditure, Void, Void> {

        private ExpenditureDao mAsyncTaskDao;

        updateAsyncTask(ExpenditureDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Expenditure... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }

    // Add a wrapper for the deleteAll() method for the same reasons as we had for insert()

    public void deleteAll() {
        new deleteAsyncTask(mExpenditureDao).execute();
    }

    // Declaring an inner class for the deletion asynchronous task to the database, since deleting is a writing query
    private static class deleteAsyncTask extends AsyncTask<Void, Void, Void> {
        private ExpenditureDao mAsyncTaskDao;

        deleteAsyncTask(ExpenditureDao dao) { mAsyncTaskDao = dao; }
        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    // Add a wrapper to the delete expenditure query so it works on an non-UI thread (deleteExpenditureAsyncTask) so the app won't crash.
    public void delete(Expenditure expenditure) {
        new deleteExpenditureAsyncTask(mExpenditureDao).execute(expenditure);
    }


    // Declaring an inner class for the expenditure deletion asynchronous task to the database, since deleting is a writing query
    private static class deleteExpenditureAsyncTask extends AsyncTask<Expenditure, Void, Void>{
        private ExpenditureDao mAsyncTascDao;
        deleteExpenditureAsyncTask(ExpenditureDao dao) { mAsyncTascDao = dao; }
        @Override
        protected Void doInBackground(Expenditure... expenditures) {
            mAsyncTascDao.delete(expenditures[0]);
            return null;
        }
    }

}
