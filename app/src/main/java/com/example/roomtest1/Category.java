package com.example.roomtest1;


public class Category {

    private int mCategoryId;
    private String mCategoryName;
    private int mIconRessourceId;
    private int mBackgroundColor;

    public Category(int categoryId, int backgroundColor, String categoryName, int iconRessourceId) {
        mCategoryId = categoryId;
        mCategoryName = categoryName;
        mIconRessourceId = iconRessourceId;
        mBackgroundColor = backgroundColor;
    }

    public String getCategoryName() {
        return mCategoryName;
    }

    public void setCategoryName(String mCategoryName) {
        this.mCategoryName = mCategoryName;
    }

    public int getIconRessourceId() {
        return mIconRessourceId;
    }

    public void setIconRessourceId(int mIconRessourceId) {
        this.mIconRessourceId = mIconRessourceId;
    }

    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    public int getmCategoryId() {
        return mCategoryId;
    }

    public void setmCategoryId(int mCategoryId) {
        this.mCategoryId = mCategoryId;
    }

    public void setBackgroundColor(int mBackgroundColor) {
        this.mBackgroundColor = mBackgroundColor;
    }

    @Override
    public String toString() {
        return "Category{" +
                "mCategoryName='" + mCategoryName + '\'' +
                ", mIconRessourceId=" + mIconRessourceId +
                '}';
    }
}
