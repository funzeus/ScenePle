package com.example.sceneple.sceneple;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/*
 * Created by p on 2015-08-07.
 */
public class DisplayLocationBaseActivity extends Activity {
    private Button btnShowLocation;
    private TextView txtLat;
    private TextView txtlon;

    private GPSInfo gps;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_loc_photo);

        btnShowLocation = (Button)findViewById(R.id.btn_start);
        txtLat = (TextView)findViewById(R.id.latitude);
        txtlon = (TextView)findViewById(R.id.longitude);

        btnShowLocation.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0){
                gps = new GPSInfo(DisplayLocationBaseActivity.this);

                if(gps.isGetLocation()){
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    txtLat.setText(String.valueOf(latitude));
                    txtlon.setText(String.valueOf(longitude));
                }
                else{
                    gps.showSettingsAlert();
                }
            }
        });
    }
}
