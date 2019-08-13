package com.greatday.plugins;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.content.Intent;

import com.greatday.plugins.activity.camera.CameraGreatdayActivity;

/**
 * This class echoes a string called from JavaScript.
 */
public class GreatDayPlugin extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("getLocation")) {
            Context context = this.cordova.getActivity().getApplicationContext();
            this.getLocation(context);
            return true;
        }
        return false;
    }

    private void getLocation(Context context) {
       Intent intent = new Intent(context, CameraGreatdayActivity.class);
       this.cordova.getActivity().startActivity(intent);
    }

}
