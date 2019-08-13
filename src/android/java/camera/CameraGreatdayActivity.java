package com.greatday.plugins.activity.camera;

import android.content.Intent;
import android.widget.Toast;

import com.senjuid.camera.CameraActivity;

public class CameraGreatdayActivity extends CameraActivity {

    public CameraGreatdayActivity() {
        Intent intent = getIntent();
        if(intent != null) {
            photo = intent.getStringExtra("name");
        }
    }

    @Override
    public void onYesButtonPressed(String photo) {
        Toast.makeText(this, photo, Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onSetNamePhoto() {
        new CameraGreatdayActivity();
    }

}
