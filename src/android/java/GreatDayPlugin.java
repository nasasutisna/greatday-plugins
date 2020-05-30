package com.greatday.plugins;

import android.content.Intent;

import com.senjuid.camera.CameraPlugin;
import com.senjuid.camera.CameraPluginListener;
import com.senjuid.camera.CameraPluginOptions;
import com.senjuid.location.LocationPlugin;
import com.senjuid.location.LocationPluginOptions;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class echoes a string called from JavaScript.
 */
public class GreatDayPlugin extends CordovaPlugin {

  private CallbackContext context;
  private CameraPlugin cameraPlugin;
  private LocationPlugin locationPlugin;

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    this.context = callbackContext;

    switch (action) {
      case "getCamera": {
        String photoName = args.getString(0);
        this.takePhoto(photoName, true);
        return true;
      }
      case "getCameraSwap": {
        String photoName = args.getString(0);
        this.takePhoto(photoName, false);
        return true;
      }
      case "getLocation": {
        this.getLocation(null);
        return true;
      }
      case "getLocationRadius": {
        String workLocationData = args.getString(0);
        this.getLocation(workLocationData);
        return true;
      }
      case "getLocationCamera": {
        String photoCamera = args.getString(0);
        this.getLocationAndTakePhoto(photoCamera, null, true);
        return true;
      }
      case "getLocationCameraSwap": {
        String photoCamera = args.getString(0);
        this.getLocationAndTakePhoto(photoCamera, null, false);
        return true;
      }
      case "getLocationRadiusCamera": {
        JSONObject data = args.getJSONObject(0);
        String photoCamera = data.getString("photo");
        String location = data.getString("location");
        this.getLocationAndTakePhoto(photoCamera, location, true);
        return true;
      }
      case "getLocationRadiusCameraSwap": {
        JSONObject data = args.getJSONObject(0);
        String photoCamera = data.getString("photo");
        String location = data.getString("location");
        this.getLocationAndTakePhoto(photoCamera, location, false);
        return true;
      }
      case "getLocationLabelLanguage": {
        JSONObject data = args.getJSONObject(0);
        String label1 = data.getString("label1");
        String label2 = data.getString("label2");
        String language = data.getString("language");
        this.getLocationWithLanguage(null, label1, label2, language);
        return true;
      }
      case "getLocationLabelLanguageRadius": {
        this.context = callbackContext;
        JSONObject data = args.getJSONObject(0);
        String label1 = data.getString("label1");
        String label2 = data.getString("label2");
        String language = data.getString("language");
        String location = data.getString("location");
        this.getLocationWithLanguage(location, label1, label2, language);
        return true;
      }
    }
    return false;
  }

  private void takePhoto(String photoName, boolean disableBackCamera) {
    CameraPluginListener cameraPluginListener = new CameraPluginListener() {
      @Override
      public void onSuccess(@NotNull String s) {
        PluginResult result = new PluginResult(PluginResult.Status.OK, s);
        GreatDayPlugin.this.context.sendPluginResult(result);
      }

      @Override
      public void onCancel() {
        PluginResult result = new PluginResult(PluginResult.Status.ERROR, "cancelled");
        GreatDayPlugin.this.context.sendPluginResult(result);
      }
    };
    cameraPlugin = new CameraPlugin(GreatDayPlugin.this.cordova.getActivity());
    cameraPlugin.setCameraPluginListener(cameraPluginListener);

    CameraPluginOptions options = new CameraPluginOptions.Builder()
      .setDisableFacingBack(disableBackCamera)
      .setName(photoName)
      .build();

    GreatDayPlugin.this.cordova.startActivityForResult(GreatDayPlugin.this,
      cameraPlugin.getIntent(options), CameraPlugin.REQUEST);
  }

  private void getLocation(String location) throws JSONException {
    LocationPlugin.LocationPluginListener locationPluginListener = new LocationPlugin.LocationPluginListener() {
      @Override
      public void onLocationRetrieved(Double lon, Double lat) {
        JSONObject jsonLocation = new JSONObject();
        try {
          jsonLocation.put("latitude", String.valueOf(lat));
          jsonLocation.put("longitude", String.valueOf(lon));
          jsonLocation.put("address", "");
        } catch (JSONException e) {
          e.printStackTrace();
        }
        PluginResult result = new PluginResult(PluginResult.Status.OK, jsonLocation);
        GreatDayPlugin.this.context.sendPluginResult(result);
      }

      @Override
      public void onCanceled() {
        PluginResult result = new PluginResult(PluginResult.Status.ERROR, "error location");
        GreatDayPlugin.this.context.sendPluginResult(result);
      }
    };

    locationPlugin = new LocationPlugin(this.cordova.getActivity());
    locationPlugin.setLocationPluginListener(locationPluginListener);
    LocationPluginOptions options = new LocationPluginOptions.Builder()
      .setData(location)
      .build();
    Intent intent = locationPlugin.getIntent(options);
    this.cordova.startActivityForResult(this, intent, LocationPlugin.REQUEST);
  }

  private void getLocationAndTakePhoto(String photoCamera, String location, boolean disableBackCamera) throws JSONException {
    JSONObject jsonLocation = new JSONObject();
    CameraPluginListener cameraPluginListener = new CameraPluginListener() {
      @Override
      public void onSuccess(@NotNull String s) {
        try {
          jsonLocation.put("path", s);
        } catch (JSONException e) {
          e.printStackTrace();
        }

        PluginResult result = new PluginResult(PluginResult.Status.OK, jsonLocation);
        GreatDayPlugin.this.context.sendPluginResult(result);
      }

      @Override
      public void onCancel() {
        PluginResult result = new PluginResult(PluginResult.Status.ERROR, "cancelled");
        GreatDayPlugin.this.context.sendPluginResult(result);
      }
    };
    LocationPlugin.LocationPluginListener locationPluginListener = new LocationPlugin.LocationPluginListener() {
      @Override
      public void onLocationRetrieved(Double lon, Double lat) {
        try {
          jsonLocation.put("latitude", String.valueOf(lat));
          jsonLocation.put("longitude", String.valueOf(lon));
          jsonLocation.put("address", "");
        } catch (JSONException e) {
          e.printStackTrace();
        }

        cameraPlugin = new CameraPlugin(GreatDayPlugin.this.cordova.getActivity());
        cameraPlugin.setCameraPluginListener(cameraPluginListener);

        CameraPluginOptions options = new CameraPluginOptions.Builder()
          .setDisableFacingBack(disableBackCamera)
          .setName(photoCamera)
          .build();

        GreatDayPlugin.this.cordova.startActivityForResult(GreatDayPlugin.this,
          cameraPlugin.getIntent(options), CameraPlugin.REQUEST);
      }

      @Override
      public void onCanceled() {
        PluginResult result = new PluginResult(PluginResult.Status.ERROR, "error location");
        GreatDayPlugin.this.context.sendPluginResult(result);
      }
    };

    locationPlugin = new LocationPlugin(this.cordova.getActivity());
    locationPlugin.setLocationPluginListener(locationPluginListener);
    LocationPluginOptions options = new LocationPluginOptions.Builder()
      .setData(location)
      .build();
    Intent intent = locationPlugin.getIntent(options);
    this.cordova.startActivityForResult(this, intent, LocationPlugin.REQUEST);
  }

  private void getLocationWithLanguage(String location, String message1, String message2, String language) throws JSONException {
    if (language != null) {
      com.senjuid.location.util.LocaleHelper.setLocale(this.cordova.getContext(), language);
    }

    LocationPlugin.LocationPluginListener locationPluginListener = new LocationPlugin.LocationPluginListener() {
      @Override
      public void onLocationRetrieved(Double lon, Double lat) {
        JSONObject jsonLocation = new JSONObject();
        try {
          jsonLocation.put("latitude", String.valueOf(lat));
          jsonLocation.put("longitude", String.valueOf(lon));
          jsonLocation.put("address", "");
        } catch (JSONException e) {
          e.printStackTrace();
        }
        PluginResult result = new PluginResult(PluginResult.Status.OK, jsonLocation);
        GreatDayPlugin.this.context.sendPluginResult(result);
      }

      @Override
      public void onCanceled() {
        PluginResult result = new PluginResult(PluginResult.Status.ERROR, "error location");
        GreatDayPlugin.this.context.sendPluginResult(result);
      }
    };

    locationPlugin = new LocationPlugin(this.cordova.getActivity());
    locationPlugin.setLocationPluginListener(locationPluginListener);
    LocationPluginOptions options = new LocationPluginOptions.Builder()
      .setData(location)
      .setMessage(message1, message2)
      .build();
    Intent intent = locationPlugin.getIntent(options);
    this.cordova.startActivityForResult(this, intent, LocationPlugin.REQUEST);
  }

  @Override
  public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
    if (cameraPlugin != null) {
      cameraPlugin.onActivityResult(requestCode, resultCode, data);
    }
    if (locationPlugin != null) {
      locationPlugin.onActivityResult(requestCode, resultCode, data);
    }
    super.onActivityResult(requestCode, resultCode, data);
  }
}