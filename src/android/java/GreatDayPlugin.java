package com.greatday.plugins;

import android.app.Activity;
import android.os.Bundle;

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
  private int REQUEST_TO_LOCATION = 3;
  private int REQUEST_TO_CAMERA = 4;

  private JSONObject jsonLocation = new JSONObject();
  private String photoCamera;
  private Context contextLocationCamera;

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    switch (action) {
      case "getCamera": {
        this.context = callbackContext;
        Context context = this.cordova.getActivity().getApplicationContext();
        String photoName = args.getString(0);
        this.getCamera(context, photoName);
        return true;
      }
      case "getLocation": {
        this.context = callbackContext;
        Context context = this.cordova.getActivity().getApplicationContext();
        this.getLocation(context);
        return true;
      }
      case "getLocationCamera": {
        this.context = callbackContext;
        contextLocationCamera = this.cordova.getActivity().getApplicationContext();
        photoCamera = args.getString(0);
        this.getLocationCamera(contextLocationCamera);
        return true;
      }
    }
    return false;
  }

  private void getCamera(Context context, String photoName) {
    Intent intent = new Intent(context, com.greatday.plugins.activity.camera.CameraGreatdayActivity.class);
    intent.putExtra("name", photoName);
    cordova.startActivityForResult(this, intent, REQUEST_CAMERA);
  }

  private void getLocation(Context context) {
    Intent intent = new Intent(context, com.greatday.plugins.activity.location.LocationGreatdayActivity.class);
    cordova.startActivityForResult(this, intent, REQUEST_LOCATION);
  }

  private void getLocationCamera(Context context) {
    Intent intent = new Intent(context, com.greatday.plugins.activity.location.LocationGreatdayActivity.class);
    cordova.startActivityForResult(this, intent, REQUEST_TO_LOCATION);
  }

  @Override
  public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
    if (requestCode == REQUEST_CAMERA) {
      if (resultCode == Activity.RESULT_OK) {
        Bundle extras = data.getExtras(); // Get data sent by the Intent
        assert extras != null;
        String information = extras.getString("photo");
        PluginResult result = new PluginResult(PluginResult.Status.OK, information);
        this.context.sendPluginResult(result);
      } else if (resultCode == Activity.RESULT_CANCELED) {
        PluginResult result = new PluginResult(PluginResult.Status.ERROR, "cancelled");
        this.context.sendPluginResult(result);
      }
    } else if (requestCode == REQUEST_LOCATION) {
      if (resultCode == Activity.RESULT_OK) {
        Bundle extras = data.getExtras(); // Get data sent by the Intent
        assert extras != null;
        String latitude = extras.getString("latitude");
        String longitude = extras.getString("longitude");
        String address = extras.getString("address");
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
    } else if(requestCode == REQUEST_TO_LOCATION) {
      if (resultCode == Activity.RESULT_OK) {
        Bundle extras = data.getExtras(); // Get data sent by the Intent
        assert extras != null;
        String latitude = extras.getString("latitude" );
        String longitude = extras.getString("longitude" );
        String address = extras.getString("address" );

        jsonLocation = new JSONObject();
        try {
          jsonLocation.put("latitude", latitude);
          jsonLocation.put("longitude", longitude);
          jsonLocation.put("address", address);
        } catch (JSONException e) {
          e.printStackTrace();
        }

        Intent intent = new Intent(contextLocationCamera, com.greatday.plugins.activity.camera.CameraGreatdayActivity.class);
        intent.putExtra("name", photoCamera);
        cordova.startActivityForResult(this, intent, REQUEST_TO_CAMERA);

      } else if (resultCode == Activity.RESULT_CANCELED) {
        PluginResult result = new PluginResult(PluginResult.Status.ERROR, "error location");
        this.context.sendPluginResult(result);
      }
    } else if(requestCode == REQUEST_TO_CAMERA) {
      if (resultCode == Activity.RESULT_OK) {
        Bundle extras = data.getExtras(); // Get data sent by the Intent
        assert extras != null;

        String path = extras.getString("photo");

        try {
          jsonLocation.put("path", path);
        } catch (JSONException e) {
          e.printStackTrace();
        }

        PluginResult result = new PluginResult(PluginResult.Status.OK, jsonLocation);
        this.context.sendPluginResult(result);

      } else if (resultCode == Activity.RESULT_CANCELED) {
        PluginResult result = new PluginResult(PluginResult.Status.ERROR, "cancelled");
        this.context.sendPluginResult(result);
      }
    }
    super.onActivityResult(requestCode, resultCode, data);
  }

}
