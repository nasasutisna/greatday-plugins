package com.greatday.plugins;

import android.os.Bundle;
import android.util.Log;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.content.Intent;

import com.greatday.plugins.activity.camera.CameraGreatdayActivity;

/**
 * This class echoes a string called from JavaScript.
 */
public class GreatDayPlugin extends CordovaPlugin {
    public CallbackContext context;
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("getLocation")) {
            this.context = callbackContext;
            Context context = this.cordova.getActivity().getApplicationContext();
            this.getLocation(context);
            return true;
        }
        return false;
    }

    private void getLocation(Context context) {
        Intent intent = new Intent(context, com.greatday.plugins.activity.camera.CameraGreatdayActivity.class);
        cordova.startActivityForResult((CordovaPlugin) this, intent, 0);
    }


    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (resultCode == cordova.getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();// Get data sent by the Intent
            String information = extras.getString("photo" );
            Log.d("on Result ", information);
            PluginResult result = new PluginResult(PluginResult.Status.OK, information);
            this.context.sendPluginResult(result);
        } else if (resultCode == cordova.getActivity().RESULT_CANCELED) {
            PluginResult resultado = new PluginResult(PluginResult.Status.OK, "canceled action, process this in javascript");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
