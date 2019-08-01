package br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JoHN on 30/04/2018.
 */

public class MyLocationListener implements LocationListener {

    private Context mContext;
    private GoogleMap mmap;
    private mapFragment mapfrag;
    private SharedPreferences pref;
    private String file_pref = "map";
    private List<Marker> mapkill = new ArrayList<>();
    private boolean isShow = false;

    public MyLocationListener(Context mContext,mapFragment mapfragment) {
        this.mContext = mContext;
        this.mmap = mapfragment.mMap;
        this.mapfrag = mapfragment;
    }

    @Override
    public void onLocationChanged(Location location) {
        /*pref = mContext.getSharedPreferences(file_pref,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("lat",String.valueOf(location.getLatitude()));
        editor.putString("lng",String.valueOf(location.getLongitude()));
        editor.apply();*/

        for (int i=0;i<mapkill.size();i++){
            mapkill.get(i).remove();
            mapkill.clear();
        }

       // Log.d("gps",String.valueOf(mapkill.size()));

        mapkill.add(mapfrag.initMapGps(mmap,location.getLatitude(),location.getLongitude()));
        if(!isShow){
            mmap.moveCamera(CameraUpdateFactory.newLatLng(mapkill.get(0).getPosition()));
            mmap.animateCamera(CameraUpdateFactory.zoomTo(18));
            isShow = true;
        }


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("gps","status -> "+provider.toString());
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("gps","enabled -> "+provider.toString());
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(mContext,"porfavor ative o seu GPS",Toast.LENGTH_LONG).show();
        Intent i = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(i);
    }
}
