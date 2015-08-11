package com.example.sceneple.sceneple;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;


/**
 * Created by p on 2015-08-08.
 */
public class GPSInfo extends Service implements LocationListener{
    private final Context mContext;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean isGetLocation = false;

    Location location;
    double lat; //위도
    double lon; //경도

    /*
     * GPS 정보를 받아올 간격
     * 최소 거리: 10m
     * 최소 시간 간격: 1s.
     */
    private final static long MIN_DISTANCE_FOR_UPDATES = 10;
    private final static long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;

    protected LocationManager locationManager;

    public  GPSInfo(Context context){
        this.mContext = context;
        getLocation();
    }

    public Location getLocation(){
        try{
            locationManager = (LocationManager)mContext.getSystemService(LOCATION_SERVICE);

            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if(!isGPSEnabled && !isNetworkEnabled){

            }
            else{
                this.isGetLocation = true;
                if(isNetworkEnabled){
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MIN_TIME_BW_UPDATES, MIN_DISTANCE_FOR_UPDATES,this);

                    if(locationManager!=null){
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if(location != null){
                            lat = location.getLatitude();
                            lon = location.getLongitude();
                        }
                    }
                }
                if(isGPSEnabled){
                    if(location ==null){
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_FOR_UPDATES,this);
                        if(locationManager!=null){
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if(location!=null){
                                lat = location.getLatitude();
                                lon = location.getLongitude();
                            }
                        }
                    }
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return location;
    }

    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(GPSInfo.this);
        }
    }
    public double getLatitude(){
        if(location!=null){
            lat = location.getLatitude();
        }
        return lat;
    }
    public double getLongitude(){
        if(location != null){
           lon = location.getLongitude();
        }
        return lon;
    }
    public boolean isGetLocation() {
        return this.isGetLocation;
    }

    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        alertDialog.setTitle("GPS 사용유무셋팅");
        alertDialog.setMessage("GPS 셋팅이 되지 않았을수도 있습니\n 설정창으로 가시겠습니까?");

        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
                }
            });

        alertDialog.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                }
        });

        alertDialog.show();
        }

@Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

        public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub

        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

        }

            public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

        }

            public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

        }
}
