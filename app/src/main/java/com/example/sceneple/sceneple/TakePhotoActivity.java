package com.example.sceneple.sceneple;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class TakePhotoActivity extends Activity {
    Button mShutter;
    MyCameraSurface mSurface;
    String mRootPath;
    static final String PICFOLDER = "ScenePle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_camera);

        mSurface = (MyCameraSurface)findViewById(R.id.previewFrame);

        mShutter = (Button)findViewById(R.id.button1);
        mShutter.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mSurface.mCamera.autoFocus(mAutoFocus);

            }
        });

        mRootPath = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/" + PICFOLDER;
        File fRoot = new File(mRootPath);
        if (fRoot.exists() == false) {
            if (fRoot.mkdir() == false) {
                Toast.makeText(this, "사진을 저장할 폴더가 없습니다.", Toast.LENGTH_LONG).show();
                finish();
                return;
            }
        }
    }

    // 포커싱 성공하면 촬영 허가
    AutoFocusCallback mAutoFocus = new AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {
            mShutter.setEnabled(success);
            mSurface.mCamera.takePicture(null, null, mPicture);

        }
    };

    // 사진 저장.
    PictureCallback mPicture = new PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {

            Calendar calendar = Calendar.getInstance();
            String FileName = String.format("SP%02d%02d%02d-%02d%02d%02d.jpg",
                    calendar.get(Calendar.YEAR) % 100, calendar.get(Calendar.MONTH)+1,
                    calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
            String path = mRootPath + "/" + FileName;

            File file = new File(path);
            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(data);
                fos.flush();
                fos.close();
            } catch (Exception e) {

                return;
            }

            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.parse("file://" + path);
            intent.setData(uri);
            sendBroadcast(intent);

            Toast.makeText(getApplicationContext(), "Photo saved", Toast.LENGTH_SHORT).show();
            camera.startPreview();
        }
    };

}


class MyCameraSurface extends SurfaceView implements SurfaceHolder.Callback {
    SurfaceHolder mHolder;
    Camera mCamera;

    public MyCameraSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    // 표면 생성시 카메라 오픈하고 미리보기 설정
    public void surfaceCreated(SurfaceHolder holder) {
        mCamera = Camera.open();
        //camera display angle
        mCamera.setDisplayOrientation(90);
        try {
            mCamera.setPreviewDisplay(mHolder);
        } catch (IOException e) {
            mCamera.release();
            mCamera = null;
        }
    }

    // 표면 파괴시 카메라도 파괴한다.
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    /*
     * Get Optimized Preview Size.
     */
    private Camera.Size getBestPreviewSize(int width, int height) {
        Camera.Size result=null;
        Camera.Parameters p = mCamera.getParameters();
        for (Camera.Size size : p.getSupportedPreviewSizes()) {
            if (size.width<=width && size.height<=height) {
                if (result==null) {
                    result=size;
                } else {
                    int resultArea=result.width*result.height;
                    int newArea=size.width*size.height;

                    if (newArea>resultArea) {
                        result=size;
                    }
                }
            }
        }
        return result;
    }
    // 표면의 크기가 결정될 때 최적의 미리보기 크기를 구해 설정한다.
    public void surfaceChanged(SurfaceHolder holder, int format, int width,	int height) {
        /*
         * If there is no preview
         */
        if(mHolder.getSurface() == null){
            return;
        }

        try{
            mCamera.stopPreview();
        }catch(Exception e){

        }

        Camera.Parameters parameters = mCamera.getParameters();
        Camera.Size size = getBestPreviewSize(width, height);
        /*
         * 1280, 720  --> optimized
         *  640, 360
         */
        parameters.setPreviewSize(size.width, size.height);
        parameters.setRotation(0);
        mCamera.setParameters(parameters);

        // 새로 변경된 설정으로 프리뷰를 재생성한다
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (Exception e){
        };
    }

}
