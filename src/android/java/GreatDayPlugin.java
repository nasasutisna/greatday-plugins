package com.greatday.plugins;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;

/**
 * This class echoes a string called from JavaScript.
 */
public class GreatDayPlugin extends CordovaPlugin {

    public CallbackContext context;
    private int REQUEST_CAMERA = 1;
    private int REQUEST_LOCATION = 2;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("getCamera")) {
            this.context = callbackContext;
            Context context = this.cordova.getActivity().getApplicationContext();
            String photoName = args.getString(0);
            this.getCamera(context, photoName);
            return true;
        } else if (action.equals("getLocation")) {
            this.context = callbackContext;
            Context context = this.cordova.getActivity().getApplicationContext();
            this.getLocation(context);
            return true;
        }
        return false;
    }

    private void getCamera(Context context, String photoName) {
        Intent intent = new Intent(context, com.greatday.plugins.activity.camera.CameraGreatdayActivity.class);
        intent.putExtra("name", photoName);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        cordova.startActivityForResult(this, intent, REQUEST_CAMERA);
    }

    private void getLocation(Context context) {
        Intent intent = new Intent(context, com.greatday.plugins.activity.location.LocationGreatdayActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        cordova.startActivityForResult(this, intent, REQUEST_LOCATION);
    }


    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if(requestCode == REQUEST_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle extras = data.getExtras(); // Get data sent by the Intent
                assert extras != null;
                String information = extras.getString("photo" );
                PluginResult result = new PluginResult(PluginResult.Status.OK, information);
                this.context.sendPluginResult(result);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                PluginResult result = new PluginResult(PluginResult.Status.ERROR, "canceled action, process this in javascript");
                this.context.sendPluginResult(result);
            }
        } else if(requestCode == REQUEST_LOCATION) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle extras = data.getExtras(); // Get data sent by the Intent
                assert extras != null;
                String latitude = extras.getString("latitude" );
                String longitude = extras.getString("longitude" );
                String address = extras.getString("address" );
                JSONObject json = new JSONObject();
                try {
                    json.put("latitude", latitude);
                    json.put("longitude", longitude);
                    json.put("address", address);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                PluginResult result = new PluginResult(PluginResult.Status.OK, json);
                this.context.sendPluginResult(result);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                PluginResult result = new PluginResult(PluginResult.Status.ERROR, "error location");
                this.context.sendPluginResult(result);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
