package com.greatday.plugins.activity.camera;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.senjuid.camera.CameraActivity;

public class CameraGreatdayActivity extends CameraActivity {

    @Override
    protected void onStart() {
        super.onStart();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            photo = extras.getString("name");
        }
    }

    public CameraGreatdayActivity() {
        
    }

    @Override
    public void onYesButtonPressed(String photo) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("photo", photo);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void onSetNamePhoto() {
        new CameraGreatdayActivity();
    }

}
