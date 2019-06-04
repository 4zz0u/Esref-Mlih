package com.example.esrefmlih.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;


@Entity(tableName = "expenditure_table")
public class Expenditure {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "category")
    private int mCategory;

    @ColumnInfo(name = "amount")
    private int mAmount;

    @ColumnInfo(name = "date")
    private Date mDate;


    public Expenditure(int category, int amount, Date date) {
        mCategory = category;
        mAmount = amount;
        mDate = date;
    }



    // Getters

    public Date getDate() {
            return mDate;
    }

    public int getId() {
        return id;
    }

    public int getCategory() {
        return mCategory;
    }

    public int getAmount() {
        return mAmount;
    }


    // Setters

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategory(int category) {
        this.mCategory = category;
    }

    public void setAmount(int amount) {
        this.mAmount = amount;
    }


}
