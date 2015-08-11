package com.example.sceneple.sceneple;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

/*
 * Created by p on 2015-08-05.
 *
 * Open Album in Device external storage.
 * Take the selected images to the preview.
 */
public class TakeAlbumActivity extends SelectImageInAlbum {

    private TextView mTextTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_album);

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //setImageSizeBoundary(400); // optional. default is 500.
                // setCropOption(1, 1);  // optional. default is no crop.
                //setCustomButtons(btnGallery, btnCamera, btnCancel); // you can set these buttons.
                startSelectImage();
            }
        });

        getSelectedImageFile(); // extract selected & saved image file.

        mTextTest = (TextView)findViewById(R.id.textView1);
        mTextTest.setText("This is test");
    }

}
