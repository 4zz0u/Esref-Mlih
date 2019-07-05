package com.example.esrefmlih.Lifecycle.ViewPager.Statistics;



 class Statistics {

    private String mCategory;
    private float mMeanPerday;
    private float mMeanPerMonth;
    private float mTotal;
    private int mColorRessource;

    Statistics(String category, float meanPerDay, float meanPerMonth, float total, int colorRessource){
        mCategory = category;
        mMeanPerday = meanPerDay;
        mMeanPerMonth = meanPerMonth;
        mTotal = total;
        mColorRessource = colorRessource;
    }

     public String getmCategory() {
         return mCategory;
     }

     public int getmColorRessource() {
         return mColorRessource;
     }

     public void setmColorRessource(int mColorRessource) {
         this.mColorRessource = mColorRessource;
     }

     public void setmCategory(String mCategory) {
         this.mCategory = mCategory;
     }

     public float getmMeanPerday() {
         return mMeanPerday;
     }

     public void setmMeanPerday(float mMeanPerday) {
         this.mMeanPerday = mMeanPerday;
     }

     public float getmMeanPerMonth() {
         return mMeanPerMonth;
     }

     public void setmMeanPerMonth(float mMeanPerMonth) {
         this.mMeanPerMonth = mMeanPerMonth;
     }

     public float getmTotal() {
         return mTotal;
     }

     public void setmTotal(float mTotal) {
         this.mTotal = mTotal;
     }
 }
