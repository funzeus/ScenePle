package com.example.sceneple.sceneple;

import android.app.Activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.PagerTabStrip;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class MainActivity extends FragmentActivity implements OnClickListener{
    Button mShutter;
    MyCameraSurface mSurface;
    String mRootPath;
    static final String PICFOLDER = "ScenePle";

    /*
     * Sliding menu interface
     * btn1: Open camera
     * btn2: Open Device Album
     *
     * ViewPager Interfae ----- Deprecated
     * mMyPhotoBtn:
     * mLocBtn:
     * mBestBtn:
     *
     * ViewPager with fragments / indicator
     * Date: 2015.08. 08
     * modified by JW
     */
    private DisplayMetrics metrics;
    private LinearLayout ll_mainLayout;
    private LinearLayout ll_menuLayout;
    private FrameLayout.LayoutParams leftMenuLayoutPrams;
    private int leftMenuWidth;
    private static boolean isLeftExpanded;
    private Button bt_left, btn1, btn2, btn3, mMyPhotoBtn, mLocBtn, mBestBtn;

    private static Typeface mTypeface = null;
    private String mTextColor = "#87CEFA"; // Color for pager tap strip.
    private TextView mTextView = null;

    private ViewPager mPager;
    FragmentPagerAdapter adapterViewPager;
    PagerTabStrip mPagerTapStrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSlideMenu(); //slide menu

        /*
         * view pager adapter
         */
        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        // Set the custom color for PagerTapStrip.
        mPagerTapStrip = (PagerTabStrip)findViewById(R.id.pager_head);
        mPagerTapStrip.setTabIndicatorColor(Color.parseColor(mTextColor));



        mTypeface = Typeface.createFromAsset(this.getAssets(), "font/helvetica_extended_light.ttf");
        setGlobalFont(vpPager);
        mTextView = (TextView)findViewById(R.id.titleText);
        mTextView.setTypeface(mTypeface);
    }
    /*
     * Set font all view Pager.
     * Date: 2015. 08. 08
     * modified by JW
     */
    private void setGlobalFont(View view){
        if (view != null) {
            if(view instanceof ViewGroup){
               ViewGroup vg = (ViewGroup)view;
                int vgCnt = vg.getChildCount();
                for(int i=0; i < vgCnt; i++){
                   View v = vg.getChildAt(i);
                   if(v instanceof TextView){
                        ((TextView) v).setTypeface(mTypeface);
                        ((TextView) v).setTextColor(Color.parseColor(mTextColor));
                        }
                    setGlobalFont(v);
                    }
                }
            }
    }

    /*
     * Viewpager with fragments and indicator interface.
     * Date: 2015. 08. 08
     * modified by JW
     */
    public static class MyPagerAdapter extends FragmentPagerAdapter{
        //Number of Fragments.
        private static int NUM_ITEMS = 3;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }
        @Override
        public int getCount(){
            return NUM_ITEMS;
        }
        // Returns the fragment to display for that page
        /*
         * FirstFragment: My Photo
         * SecondFragment: Location
         * ThirdFragment: BestPhoto
         */
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - 내가 올린 사진
                    return FirstFragment.newInstance(0, "My Photo");
                case 1: // Fragment # 1 - 내 위치 주변 사진
                    return SecondFragment.newInstance(1, "Location");
                case 2: // Fragment # 2 - Weekly? Monthly? best photo.
                    return ThirdFragment.newInstance(2, "Best");
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            String title = "default";
            switch (position){
                case 0:
                    title = "MyPhoto";
                    break;
                case 1:
                    title = "Location";
                    break;
                case 2:
                    title = "Best Photo";
                    break;
                default:
                    break;
            }
            return title;
        }
    }


    /*
     * View pager interface -- deprecated
     */
//    private void setCurrentItem(int index){
//        if(index==0){
//            mPager.setCurrentItem(0);
//        }
//        else if(index == 1){
//            mPager.setCurrentItem(1);
//        }
//        else{
//            mPager.setCurrentItem(2);
//        }
//    }
//    private void initPagerLayout(){
//        mMyPhotoBtn = (Button)findViewById(R.id.myPhoto);
//        mLocBtn = (Button)findViewById(R.id.locPhoto);
//        mBestBtn = (Button)findViewById(R.id.best);
//
//        mMyPhotoBtn.setOnClickListener(this);
//        mLocBtn.setOnClickListener(this);
//        mBestBtn.setOnClickListener(this);
//
//        mPager = (ViewPager)findViewById(R.id.viewPager);
//        mPager.setAdapter(new ViewPagerAdapter(this));
//    }
//    private class ViewPagerAdapter extends PagerAdapter{
//        private LayoutInflater mLayoutInflater;
//
//        public ViewPagerAdapter(Context context){
//            super();
//            mLayoutInflater = LayoutInflater.from(context);
//        }
//
//        @Override
//        public int getCount(){
//            return 3;
//        }
//
//        @Override
//        public Object instantiateItem(View pager, int index){
//            View view = null;
//            if(index == 0){
//                view = mLayoutInflater.inflate(R.layout.display_myphoto,null);
//            }
//            else if(index == 1){
////                view = mLayoutInflater.inflate(R.layout.display_loc_photo,null);
//                view = mLayoutInflater.inflate(R.layout.core_test,null);
//
//            }
//            else{
//                view = mLayoutInflater.inflate(R.layout.display_bestphoto, null);
//            }
//
//            ((ViewPager)pager).addView(view, 0);
//            return view;
//        }
//        @Override
//        public void destroyItem(View pager, int position, Object view){
//            ((ViewPager)pager).removeView((View) view);
//        }
//        @Override
//        public boolean isViewFromObject(View pager, Object obj){
//            return pager == obj;
//        }
//
//        @Override public void restoreState(Parcelable arg0, ClassLoader arg1){}
//        @Override public Parcelable saveState(){return  null;}
//        @Override public void startUpdate(View arg0){}
//        @Override public void finishUpdate(View arg0){}
//
//    }


    /*
     * Slide menu Interface
     * Camera
     * Album
     * (Hash tag searching)
     * GPS information
     * User Option.
     */

    private void initSlideMenu() {

        // init left menu width
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        leftMenuWidth = (int) ((metrics.widthPixels) * 0.25);

        // init main view
        ll_mainLayout = (LinearLayout) findViewById(R.id.ll_mainlayout);

        // init left menu
        ll_menuLayout = (LinearLayout) findViewById(R.id.ll_menuLayout);
        leftMenuLayoutPrams = (FrameLayout.LayoutParams) ll_menuLayout
                .getLayoutParams();
        leftMenuLayoutPrams.width = leftMenuWidth;
        ll_menuLayout.setLayoutParams(leftMenuLayoutPrams);

        // init ui
        bt_left = (Button) findViewById(R.id.bt_left);
        bt_left.setOnClickListener(this);

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);

    }
    private void menuLeftSlideAnimationToggle() {

        if (!isLeftExpanded) {

            isLeftExpanded = true;

            // Expand
            new OpenAnimation(ll_mainLayout, leftMenuWidth,
                    Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0.25f, 0, 0.0f, 0, 0.0f);

            // disable all of main view
            FrameLayout viewGroup = (FrameLayout) findViewById(R.id.ll_fragment)
                    .getParent();
            enableDisableViewGroup(viewGroup, false);

            // enable empty view
            ((LinearLayout) findViewById(R.id.ll_empty))
                    .setVisibility(View.VISIBLE);
            findViewById(R.id.ll_empty).setEnabled(true);
            findViewById(R.id.ll_empty).setOnTouchListener(
                    new OnTouchListener() {

                        @Override
                        public boolean onTouch(View arg0, MotionEvent arg1) {
                            menuLeftSlideAnimationToggle();
                            return true;
                        }
                    });

        } else {
            isLeftExpanded = false;

            // close
            new CloseAnimation(ll_mainLayout, leftMenuWidth,
                    TranslateAnimation.RELATIVE_TO_SELF, 0.25f,
                    TranslateAnimation.RELATIVE_TO_SELF, 0.0f, 0, 0.0f, 0, 0.0f);

            // enable all of main view
            FrameLayout viewGroup = (FrameLayout) findViewById(R.id.ll_fragment)
                    .getParent();
            enableDisableViewGroup(viewGroup, true);

            // disable empty view
            ((LinearLayout) findViewById(R.id.ll_empty))
                    .setVisibility(View.GONE);
            findViewById(R.id.ll_empty).setEnabled(false);

        }
    }

    /*
     * 뷰의 동작을 제어한다. 하위 모든 뷰들이 enable 값으로 설정된다.
     * 슬라이드 메뉴 동작 시 하위 뷰에 대한 동작 제어.
     * @param viewGroup
     * @param enabled
     * modified by JW
     */
    public static void enableDisableViewGroup(ViewGroup viewGroup,
                                              boolean enabled) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            if (view.getId() != R.id.bt_left) {
                view.setEnabled(enabled);
                if (view instanceof ViewGroup) {
                    enableDisableViewGroup((ViewGroup) view, enabled);
                }
            }
        }
    }

    /*
     * Declare Button Action
     * Set All Button that used in main activity.
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            //Open slide menu
            case R.id.bt_left:
                menuLeftSlideAnimationToggle();
                break;
            //Camera
            case R.id.btn1:
                Intent intent = new Intent(MainActivity.this, TakePhotoActivity.class);
                startActivity(intent);
                break;
            //Open Device Album
            case R.id.btn2:
                Toast.makeText(this, "Album Button Clicked", Toast.LENGTH_LONG).show();
                Intent intent2 = new Intent(MainActivity.this, TakeAlbumActivity.class);
//                Intent intent2 = new Intent(MainActivity.this, CoreTestActivity.class);
                startActivity(intent2);
                break;
            //GPS information test.
            case R.id.btn3:
                Intent intent3 = new Intent(MainActivity.this, DisplayLocationBaseActivity.class);
                startActivity(intent3);
                break;
        }
    }
}

