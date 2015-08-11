package com.example.sceneple.sceneple;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/*
 * Created by JW on 2015-08-08.
 * This Fragment will be Location Photo Activity.
 */
public class SecondFragment extends Fragment{
    private String title;
    private int page;
    /*
     * Test for GPS information
     */
    private GPSInfo gps;
    private Button btnShowLocation,test;
    private TextView txtLat;
    private TextView txtlon;
    /*
     * Constructor for
     */
    public static SecondFragment newInstance(int page, String title){
        SecondFragment fragmenSecond = new SecondFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmenSecond.setArguments(args);
        return fragmenSecond;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");

    }
    /*
     * Fragment의 경우 layout은 onCreateView에서 정의한다.
     * Date: 2015. 08. 09
     * modified by JW
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //view에 대한 layout만 정의 가능.. -> activity는 결국 main에서?
        View view = inflater.inflate(R.layout.display_loc_photo, container, false);
        TextView tvLabel = (TextView) view.findViewById(R.id.locTest1);

        btnShowLocation = (Button) view.findViewById(R.id.btn_start);
        txtLat = (TextView) view.findViewById(R.id.latitude);
        txtlon = (TextView) view.findViewById(R.id.longitude);

        btnShowLocation.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0){
                System.out.println("test");
                txtlon.setText("longitude");
                Toast.makeText(getActivity(),"test",Toast.LENGTH_LONG).show();
                //Testing for GPS information.
                gps = new GPSInfo(getActivity());

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

        return view;
    }

}
