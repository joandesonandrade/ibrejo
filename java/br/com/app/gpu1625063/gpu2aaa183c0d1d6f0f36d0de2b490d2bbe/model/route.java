package br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe.model;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by JoHN on 03/05/2018.
 */

public class route {
    public String endAddress;
    public LatLng endLocation;
    public String startAddress;
    public LatLng startLocation;

    public List<LatLng> points;

    private Context mContext;

    public route() {
    }

    public route(String endAddress, LatLng endLocation, String startAddress, LatLng startLocation, List<LatLng> points, Context mContext) {
        this.endAddress = endAddress;
        this.endLocation = endLocation;
        this.startAddress = startAddress;
        this.startLocation = startLocation;
        this.points = points;
        this.mContext = mContext;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public void setEndLocation(LatLng endLocation) {
        this.endLocation = endLocation;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public void setStartLocation(LatLng startLocation) {
        this.startLocation = startLocation;
    }

    public void setPoints(List<LatLng> points) {
        this.points = points;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public LatLng getEndLocation() {
        return endLocation;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public LatLng getStartLocation() {
        return startLocation;
    }

    public List<LatLng> getPoints() {
        return points;
    }
}
