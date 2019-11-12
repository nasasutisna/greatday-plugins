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

import javax.xml.transform.Source;

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
  private Boolean isDisabled;
  private Context contextGlobal;

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
      case "getCameraSwap": {
        this.context = callbackContext;
        Context context = this.cordova.getActivity().getApplicationContext();
        String photoName = args.getString(0);
        this.getCameraSwap(context, photoName);
        return true;
      }
      case "getLocation": {
        this.context = callbackContext;
        Context context = this.cordova.getActivity().getApplicationContext();
        this.getLocation(context);
        return true;
      }
      case "getLocationRadius": {
        this.context = callbackContext;
        Context context = this.cordova.getActivity().getApplicationContext();
        String data = args.getString(0);
        this.getLocationRadius(context, data);
        return true;
      }
      case "getLocationCamera": {
        this.context = callbackContext;
        contextGlobal = this.cordova.getActivity().getApplicationContext();
        photoCamera = args.getString(0);
        isDisabled = true;
        this.getLocationCamera(contextGlobal);
        return true;
      }
      case "getLocationCameraSwap": {
        this.context = callbackContext;
        contextGlobal = this.cordova.getActivity().getApplicationContext();
        photoCamera = args.getString(0);
        isDisabled = false;
        this.getLocationCameraSwap(contextGlobal);
        return true;
      }
      case "getLocationRadiusCamera": {
        this.context = callbackContext;
        contextGlobal = this.cordova.getActivity().getApplicationContext();
        JSONObject data = args.getJSONObject(0);
        photoCamera = data.getString("photo");
        isDisabled = true;
        String location = data.getString("location");
        this.getLocationRadiusCamera(contextGlobal, location);
        return true;
      }
      case "getLocationRadiusCameraSwap": {
        this.context = callbackContext;
        contextGlobal = this.cordova.getActivity().getApplicationContext();
        JSONObject data = args.getJSONObject(0);
        photoCamera = data.getString("photo");
        isDisabled = false;
        String location = data.getString("location");
        this.getLocationRadiusCameraSwap(contextGlobal, location);
        return true;
      }
      case "getLocationLabelLanguage": {
        this.context = callbackContext;
        Context context = this.cordova.getActivity().getApplicationContext();
        JSONObject data = args.getJSONObject(0);
        String label1 = data.getString("label1");
        String label2 = data.getString("label2");
        String language = data.getString("language");
        this.getLocationLabelLanguage(context, label1, label2, language);
        return true;
      }
      case "getLocationLabelLanguageRadius": {
        this.context = callbackContext;
        Context context = this.cordova.getActivity().getApplicationContext();
        JSONObject data = args.getJSONObject(0);
        String label1 = data.getString("label1");
        String label2 = data.getString("label2");
        String language = data.getString("language");
        String location = data.getString("location");
        this.getLocationLabelLanguageRadius(context, label1, label2, language, location);
        return true;
      }
    }
    return false;
  }

  private void getCamera(Context context, String photoName) {
    Intent intent = new Intent(context, com.senjuid.camera.CaptureActivity.class);
    intent.putExtra("name", photoName);
    intent.putExtra("disable_back", true);
    cordova.startActivityForResult(this, intent, REQUEST_CAMERA);
  }

  private void getCameraSwap(Context context, String photoName) {
    Intent intent = new Intent(context, com.senjuid.camera.CaptureActivity.class);
    intent.putExtra("name", photoName);
    intent.putExtra("disable_back", false);
    cordova.startActivityForResult(this, intent, REQUEST_CAMERA);
  }

  private void getLocation(Context context) {
    Intent intent = new Intent(context, com.greatday.plugins.activity.location.LocationGreatdayActivity.class);
    cordova.startActivityForResult(this, intent, REQUEST_LOCATION);
  }

  //get location with radius
  private void getLocationRadius(Context context, String data) {
    Intent intent = new Intent(context, com.greatday.plugins.activity.location.LocationGreatdayActivity.class);
    intent.putExtra("data", data);
    cordova.startActivityForResult(this, intent, REQUEST_LOCATION);
  }

  private void getLocationCamera(Context context) {
    Intent intent = new Intent(context, com.greatday.plugins.activity.location.LocationGreatdayActivity.class);
    cordova.startActivityForResult(this, intent, REQUEST_TO_LOCATION);
  }

  private void getLocationCameraSwap(Context context) {
    Intent intent = new Intent(context, com.greatday.plugins.activity.location.LocationGreatdayActivity.class);
    cordova.startActivityForResult(this, intent, REQUEST_TO_LOCATION);
  }

  //  location radius Camera
  private void getLocationRadiusCamera(Context context, String data) {
    Intent intent = new Intent(context, com.greatday.plugins.activity.location.LocationGreatdayActivity.class);
    intent.putExtra("data", data);
    cordova.startActivityForResult(this, intent, REQUEST_TO_LOCATION);
  }

  //  location radius Camera Swap
  private void getLocationRadiusCameraSwap(Context context, String data) {
    Intent intent = new Intent(context, com.greatday.plugins.activity.location.LocationGreatdayActivity.class);
    intent.putExtra("data", data);
    cordova.startActivityForResult(this, intent, REQUEST_TO_LOCATION);
  }

  // get location with label and language
  private void getLocationLabelLanguage(Context context, String label1, String label2, String language) {
    if(language != null) {
      com.senjuid.location.util.LocaleHelper.setLocale(context, language);
    }
    Intent intent = new Intent(context, com.greatday.plugins.activity.location.LocationGreatdayActivity.class);
    intent.putExtra("message1", label1);
    intent.putExtra("message2", label2);
    cordova.startActivityForResult(this, intent, REQUEST_LOCATION);
  }

  // get location with radius, label and language
  private void getLocationLabelLanguageRadius(Context context, String label1, String label2, String language, String data) {
    if(language != null) {
      com.senjuid.location.util.LocaleHelper.setLocale(context, language);
    }
    Intent intent = new Intent(context, com.greatday.plugins.activity.location.LocationGreatdayActivity.class);
    intent.putExtra("message1", label1);
    intent.putExtra("message2", label2);
    intent.putExtra("data", data);
    cordova.startActivityForResult(this, intent, REQUEST_LOCATION);
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
    } else if (requestCode == REQUEST_TO_LOCATION) {
      if (resultCode == Activity.RESULT_OK) {
        Bundle extras = data.getExtras(); // Get data sent by the Intent
        assert extras != null;
        String latitude = extras.getString("latitude");
        String longitude = extras.getString("longitude");
        String address = extras.getString("address");

        jsonLocation = new JSONObject();
        try {
          jsonLocation.put("latitude", latitude);
          jsonLocation.put("longitude", longitude);
          jsonLocation.put("address", address);
        } catch (JSONException e) {
          e.printStackTrace();
        }

        Intent intent = new Intent(contextGlobal, com.senjuid.camera.CaptureActivity.class);
        intent.putExtra("name", photoCamera);
        intent.putExtra("disable_back", isDisabled);
        cordova.startActivityForResult(this, intent, REQUEST_TO_CAMERA);

      } else if (resultCode == Activity.RESULT_CANCELED) {
        PluginResult result = new PluginResult(PluginResult.Status.ERROR, "error location");
        this.context.sendPluginResult(result);
      }
    } else if (requestCode == REQUEST_TO_CAMERA) {
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
