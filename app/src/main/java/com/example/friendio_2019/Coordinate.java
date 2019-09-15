package com.example.friendio_2019;

public class Coordinate {
    private double mLatitude;
    private double mLongitude;
    public Coordinate()
    {

    }
    public Coordinate(double latitude, double longitude)
    {
        this.mLatitude = latitude;
        this.mLongitude = longitude;
    }

    public void setLatitude(double latitude)
    {
        mLatitude = latitude;
    }
    public double getLatitude()
    {
        return mLatitude;
    }

    public void setLongitude(double longitude)
    {
        mLongitude = longitude;
    }
    public double getLongitude()
    {
        return mLongitude;
    }
}
