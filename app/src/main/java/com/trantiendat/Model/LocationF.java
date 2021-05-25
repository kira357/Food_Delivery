package com.trantiendat.Model;

public class LocationF {
    private String mNameLocation;
    private String mPhotoLocation;
    private String mAddLocation;

    public LocationF() {
    }

    public LocationF(String mNameLocation, String mPhotoLocation, String mAddLocation) {
        this.mNameLocation = mNameLocation;
        this.mPhotoLocation = mPhotoLocation;
        this.mAddLocation = mAddLocation;

    }

    public String getmNameLocation() {
        return mNameLocation;
    }

    public void setmNameLocation(String mNameLocation) {
        this.mNameLocation = mNameLocation;
    }

    public String getmPhotoLocation() {
        return mPhotoLocation;
    }

    public void setmPhotoLocation(String mPhotoLocation) {
        this.mPhotoLocation = mPhotoLocation;
    }

    public String getmAddLocation() {
        return mAddLocation;
    }

    public void setmAddLocation(String mAddLocation) {
        this.mAddLocation = mAddLocation;
    }


}
