package com.personal.jsontocsv;

import android.os.Environment;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhufengli 2020/03/06
 */

public class JsonToCsv extends CordovaPlugin {

    public static int JSON_TO_CSV = 0;
    public static int CSV_TO_JSON = 1;

    public static int actionType = JSON_TO_CSV;

    public static String fileName = "";

    public List<String> dataArray = new ArrayList<String>();

    private CallbackContext callbackContext;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        this.callbackContext = callbackContext;
        if(action.equals("coolMethod")){
            if(args.length() > 0){
                actionType = args.getInt(0);
            }
            if(args.length() > 1){
                dataArray = (List<String>) args.getJSONArray(1);
            }
            if(args.length() > 2){
                fileName = args.getString(2);
            }
            if(actionType == JSON_TO_CSV){
                transJsonToFile();
            }else if (actionType == CSV_TO_JSON) {
                transFileToJson();
            }
            callbackContext.success(args.getString(0));

            return true;
        }
        return super.execute(action, args, callbackContext);
    }

    private void transJsonToFile() {
        File fPath = new File(getFilePath());
        if(!fPath.exists()){
            fPath.mkdirs();
        }
        File file = new File(getFilePath() + "/" + fileName);
        if(file.exists()){
            file.delete();
        }


    }

    private void transFileToJson() {

    }

    public static String getFilePath(){
        return Environment.getExternalStorageDirectory() + "/CSV";
    }

    public static void setFileName(String fileName) {
        JsonToCsv.fileName = fileName;
    }

    public static String getFileName() {
        return fileName;
    }
}