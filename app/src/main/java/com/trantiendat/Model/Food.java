package com.trantiendat.Model;

public class Food {
    private String mNameFood;
    private String mPriceFood;
    private String mDesc;
    private int mImgFood;
    private String mAddress;
    private float mRating;

    public String getmNameFood() {
        return mNameFood;
    }

    public void setmNameFood(String mNameFood) {
        this.mNameFood = mNameFood;
    }

    public String getmPriceFood() {
        return mPriceFood;
    }

    public void setmPriceFood(String mPriceFood) {
        this.mPriceFood = mPriceFood;
    }

    public String getmDesc() {
        return mDesc;
    }

    public void setmDesc(String mDesc) {
        this.mDesc = mDesc;
    }

    public int getmImgFood() {
        return mImgFood;
    }

    public void setmImgFood(int mImgFood) {
        this.mImgFood = mImgFood;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public float getmRating() {
        return mRating;
    }

    public void setmRating(float mRating) {
        this.mRating = mRating;
    }

    public Food(String mNameFood, String mPriceFood, String mDesc, int mImgFood, String mAddress, float mRating) {
        this.mNameFood = mNameFood;
        this.mPriceFood = mPriceFood;
        this.mDesc = mDesc;
        this.mImgFood = mImgFood;
        this.mAddress = mAddress;
        this.mRating = mRating;
    }
}
