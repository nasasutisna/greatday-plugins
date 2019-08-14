package com.greatday.plugins.activity.location;

import android.content.Intent;

import com.senjuid.location.GeolocationActivity;

public class LocationGreatdayActivity extends GeolocationActivity {

    @Override
    public void onYesButtonPressed(Double latitude, Double longitude, String address) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("latitude", latitude.toString());
        returnIntent.putExtra("longitude", longitude.toString());
        returnIntent.putExtra("address", address);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

}
