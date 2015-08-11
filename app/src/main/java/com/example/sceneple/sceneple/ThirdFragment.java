package com.example.sceneple.sceneple;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/*
 * Created by JW on 2015-08-09.
 * This fragment will be best photo activity
 */
public class ThirdFragment extends Fragment{
    private String title;
    private int page;

    public static ThirdFragment newInstance(int page, String title){
        ThirdFragment fragmenThird = new ThirdFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmenThird.setArguments(args);
        return fragmenThird;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.display_bestphoto, container, false);
        TextView tvLabel = (TextView) view.findViewById(R.id.bestPhotoView);
        tvLabel.setText(page + " -- " + title);
        return view;
    }

}
