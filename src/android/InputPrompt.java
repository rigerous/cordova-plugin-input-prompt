package com.inputPrompt;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class InputPrompt extends CordovaPlugin {
    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {

        try {
            if ("input-prompt".equals(action)) {

                JSONObject arg_object = args.getJSONObject(0);
                final String title = arg_object.getString("title");
                final String placeholder = arg_object.getString("placeholder");
                final String okbuttontext = arg_object.getString("okbuttontext");
                final String cancelbuttontext = arg_object.getString("cancelbuttontext");

                // final View viewInflated = LayoutInflater.from(cordova.getActivity()).inflate(
                //     cordova.getActivity().getResources().getIdentifier("editTextDialogUserInput", "id", cordova.getActivity().getPackageName()),
                //     (ViewGroup) findViewById(
                //         cordova.getActivity().getResources().getIdentifier("window", "layout", cordova.getActivity().getPackageName())
                //     ),
                //     false
                // );

                cordova.getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        AlertDialog.Builder dlg = new AlertDialog.Builder(cordova.getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                        dlg.setTitle(title);

                        LayoutInflater li = LayoutInflater.from(cordova.getActivity());
                        View windowView = li.inflate(
                            cordova.getActivity().getResources().getIdentifier("window", "layout", cordova.getActivity().getPackageName()),
                            null
                        );

                        final EditText userInput = (EditText) windowView
                                .findViewById(cordova.getActivity().getResources().getIdentifier("editTextDialogUserInput", "id", cordova.getActivity().getPackageName()));

                        userInput.setHint(placeholder);

                        dlg.setView(windowView);
                        dlg.setPositiveButton(okbuttontext, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                String userInputText = userInput.getText().toString();
                                callbackContext.success(userInputText);
                            }
                        });
                        dlg.setNegativeButton(cancelbuttontext, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        dlg.create();
                        dlg.show();
                    }
                });
                return true;
            }
            callbackContext.error("Invalid action");
            return false;
        } catch(Exception e) {
            System.err.println("Exception: " + e.getMessage());
            callbackContext.error(e.getMessage());
            return false;
        }
    }
}
