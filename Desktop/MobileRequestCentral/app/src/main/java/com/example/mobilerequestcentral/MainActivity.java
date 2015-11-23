package com.example.mobilerequestcentral;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.locationfilterization.FloorListCustomAdpter;
import com.example.locationfilterization.locationNewDialogProp;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import custom.dialog.CustomDialog;
import database.helper.DatabaseHandler;
import my.classes.FacilityClass;
import my.classes.MyLocationClass;
import my.classes.OrgClass;
import my.classes.ServiceClass;
import user.function.NewUserFunction;

public class MainActivity extends Activity {
    public static String default_location = "", default_org = "";
    // locationNewDialog dialog;
    Button btnLocation, updatelistButton;
    DatabaseHandler dh;
    ProgressDialog pDialog;
    ArrayList<MyLocationClass> myLocationClassArrayList;
    ArrayList<FacilityClass> myFacilityClassArrayList;
    ArrayList<ServiceClass> serviceClassArrayList;
    ArrayList<OrgClass> orgClassArrayList;
    // LocationClass objLocationClass;
    MyLocationClass myLocationClassObj;
    FacilityClass myFacilityClassObj;
    OrgClass myOrgCLassObj;
    ServiceClass serviceClassObj;
    String response = "";
    CustomDialog cDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


    }

    @Override
    protected void onResume() {
        super.onResume();
       /* DBH dbh = new DBH(getApplicationContext());
        dbh.read();*/
        FloorListCustomAdpter.back = false;
        cDialog = new CustomDialog(MainActivity.this);
        btnLocation = (Button) findViewById(R.id.locationButton);
        updatelistButton = (Button) findViewById(R.id.menuscreen_button_upload);
        dh = new DatabaseHandler(getApplicationContext());
        int rowCount = dh.getRowCount(DatabaseHandler.MY_LOCATION_TABLE);

        if (rowCount <= 0 && (dh.getRowCount(DatabaseHandler.MY_FACILITY_TABLE) <= 0)) {
            System.out.println("=====>Tables empty<=====\n =====>downloading locations and facilities<=====");
            new loadLocationFromServer().execute();
            new facilities().execute();
        }

        updatelistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Lists updated successfully", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void selectLocation(View v) {
        if (btnLocation.getText().toString().equalsIgnoreCase("Service Requester")) {

            if (dh.getBuilding().size() != 0 /*&& dh.getOrg().size() != 0*/) {
                Intent intent = new Intent(MainActivity.this, requestcentral.class);
                startActivityForResult(intent, 0);
                overridePendingTransition(R.anim.new_slide_left, R.anim.hold);
            } else {
                Toast.makeText(getApplicationContext(), "Please wait!", Toast.LENGTH_SHORT).show();
                new loadLocationFromServer().execute();
                new facilities().execute();
            }

        }
    }

    public void logout(View v) {
        new Logout().execute();
    }

    public void DownloadRequest(View v) {
		/*if (dh.getRowCount(DatabaseHandler.TABLE_REQUESTDATA) <= 0)
			new downloadrequests().execute();
		else{
			dh.emptyTable(DatabaseHandler.TABLE_REQUESTDATA, "blank", "blank");
			new downloadrequests().execute();
		}*/
        Intent intent = new Intent(MainActivity.this, ViewRequestHome.class);
        startActivity(intent);

        overridePendingTransition(R.anim.new_slide_left, R.anim.hold);


    }

    public void reserveSpace(View v) {
        Intent intent = new Intent(MainActivity.this,
                locationNewDialogProp.class);
        startActivityForResult(intent, 0);
        overridePendingTransition(R.anim.new_slide_left, R.anim.hold);

    }

    // ======================= back pressed event =======================//
    @Override
    public void onBackPressed() {

        Toast.makeText(MainActivity.this,
                getResources().getString(R.string.logout_exit),
                Toast.LENGTH_LONG).show();

    }

    private class loadLocationFromServer extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            myLocationClassArrayList = new ArrayList<MyLocationClass>();
            serviceClassArrayList = new ArrayList<ServiceClass>();
            orgClassArrayList = new ArrayList<OrgClass>();
            pDialog = ProgressDialog.show(MainActivity.this, "", "");
            pDialog.setContentView(R.layout.progresslayout);
            TextView titletextView = (TextView) pDialog
                    .findViewById(R.id.title);
            TextView messageTextView = (TextView) pDialog
                    .findViewById(R.id.message);
            titletextView.setText("Please Wait...");
            messageTextView.setText("Location data is being downloaded...");
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.show();
        }

        @SuppressWarnings("unchecked")
        @Override
        protected String doInBackground(Void... params) {
            try {
                String[] userdetail = new String[3];
                userdetail = dh.getuserDetail();
                NewUserFunction userFunction = new NewUserFunction();
            /*	ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
				param.add(new BasicNameValuePair("name", userdetail[0]));
				// param.add(new BasicNameValuePair("token", userdetail[2]));
				param.add(new BasicNameValuePair("pass", userdetail[1]));*/


                HashMap<String, String> param = new HashMap<>();
                param.put("name", userdetail[0]);
                param.put("pass", userdetail[1]);

                response = userFunction.getLocationRecord(param);

                if (!response.equalsIgnoreCase("Invalidate User")) {
                    JSONObject obj = new JSONObject(response);
                    Iterator<String> iterator = obj.keys();
                    while (iterator.hasNext()) {
                        myLocationClassObj = new MyLocationClass();
                        String key = iterator.next();
                        if ((!key.equals("default location") && !key.equals("default organization"))) {
                            myLocationClassObj.setPath(key);
                            myLocationClassObj.setLocationName(obj.getString(key));
                            myLocationClassObj.setDefaultLocation(obj.getString("default location"));
                            default_location = obj.getString("default location");
                            default_org = obj.getString("default organization");

                            /*myLocationClassArrayList.add(myLocationClassObj);*/
                        } else {
                            if (!key.equals("default organization")) {
                                myLocationClassObj.setPath(key);
                                String[] locationNameArray = obj.getString(key).split("\\\\");
                                myLocationClassObj.setLocationName(locationNameArray[locationNameArray.length - 3]);
                                myLocationClassObj.setDefaultLocation(obj.getString("default location"));
                                default_location = obj.getString("default location");
                                default_org = obj.getString("default organization");

                                /*myLocationClassArrayList.add(myLocationClassObj);*/
                            }
                        }
                        if (!key.equals("default organization")) {
                            myLocationClassArrayList.add(myLocationClassObj);
                        }

                    }
                    dh.emptyTable(DatabaseHandler.MY_LOCATION_TABLE, "blank", "blank");
                    dh.saveLocation(myLocationClassArrayList);


                    try {
                        String serviceString = userFunction.getRequestList(param);
                        JSONObject jsonObject = new JSONObject(serviceString);
                        Iterator<String> iterator2 = jsonObject.keys();
                        while (iterator2.hasNext()) {
                            serviceClassObj = new ServiceClass();
                            String key = iterator2.next();

                            JSONObject jsonObject2 = jsonObject.getJSONObject(key);
                            serviceClassObj.setRecordId(jsonObject2
                                    .getString("Record Id"));
                            serviceClassObj.setServiceName(jsonObject2
                                    .getString("Name"));
                            serviceClassObj.setHierarchyPath(jsonObject2
                                    .getString("Hierarchy Path"));
                            serviceClassArrayList.add(serviceClassObj);
                        }
                        dh.emptyTable(DatabaseHandler.MY_SERVICE_TABLE, "blank", "blank");
                        dh.saveServiceType(serviceClassArrayList);
                    } catch (Exception e) {

                    }
                    if (!default_location.equals("")) {
                        String[] str = default_location.split("\\\\");
                        int len = str.length;
                        String building = str[len - 3];
                        /*param.put("building", building);*/
                        param.put("building", "");
                        String orgString = userFunction.getOrganization(param);
                        try {
                            if (!orgString.contains("No Records Available")) {
                                JSONObject jsonObject1 = new JSONObject(orgString);
                                Iterator<String> stringIterator = jsonObject1.keys();
                                while (stringIterator.hasNext()) {
                                    myOrgCLassObj = new OrgClass();
                                    String outerkey = stringIterator.next();
                                    JSONObject innerJsonObject = jsonObject1.getJSONObject(outerkey);
                                    Iterator<String> stringIterator1 = innerJsonObject.keys();
                                    while (stringIterator1.hasNext()) {
                                        String innerKey = stringIterator1.next();
                                        if (innerKey.equals("Building")) {
                                            myOrgCLassObj.setOrg_building(innerJsonObject.getString("Building"));
                                        }
                                        if (innerKey.equals("Floor")) {
                                            myOrgCLassObj.setOrg_floor(innerJsonObject.getString("Floor"));
                                        }
                                        if (innerKey.equals("ID")) {
                                            myOrgCLassObj.setOrg_id(innerJsonObject.getString("ID"));
                                        }
                                        if (innerKey.equals("Location Lookup")) {
                                            myOrgCLassObj.setOrg_location_lookup(innerJsonObject.getString("Location Lookup"));
                                        }
                                        if (innerKey.equals("Organization Name")) {
                                   /* String orgNameToShow = "";
                                    try{
                                        orgNameToShow = innerJsonObject.getString("Organization Name");
                                        String[] string =orgNameToShow.split("\\\\");
                                        int length =string.length;
                                        orgNameToShow = string[length-1];
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }*/

                                            myOrgCLassObj.setOrg_organization_name(innerJsonObject.getString("Organization Name"));
                                        }

                                        if (innerKey.equals("Organization Path")) {
                                            myOrgCLassObj.setOrg_organization_path(innerJsonObject.getString("Organization Path"));
                                        }


                                        if (innerKey.equals("Property")) {
                                            myOrgCLassObj.setOrg_property(innerJsonObject.getString("Property"));
                                        }
                                        if (innerKey.equals("Space")) {
                                            myOrgCLassObj.setOrg_space(innerJsonObject.getString("Space"));
                                        }
                                        myOrgCLassObj.setDef_org(default_org);

                                    }
                                    orgClassArrayList.add(myOrgCLassObj);
                                }

                                dh.emptyTable(DatabaseHandler.MY_ORG_TABLE, "blank", "blank");
                                dh.saveOrg(orgClassArrayList);
                            } else {

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }

            if (response.equals("")) {
                cDialog.showCustomDialog("Attention!",
                        "Server not respond , try again");
            } else {
                btnLocation.setText("Service Requester");
            }
        }

    }

    public class Logout extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = ProgressDialog.show(MainActivity.this, "", "");
            pDialog.setContentView(R.layout.progresslayout);
            TextView titletextView = (TextView) pDialog
                    .findViewById(R.id.title);
            TextView messageTextView = (TextView) pDialog
                    .findViewById(R.id.message);
            titletextView.setText("Please Wait...");
            messageTextView.setText("You are being logged out...");
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.show();

        }

        @Override
        protected String doInBackground(Void... param) {
            for (int i = 0; i <= 1000; i++) {
                response = response + i;
            }

			/*try {
				String[] userdetail = new String[3];
				userdetail = dh.getuserDetail();
				NewUserFunction userFunction = new NewUserFunction();
				*//*ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("name", userdetail[0]));
				params.add(new BasicNameValuePair("token", userdetail[2]));*//*

                HashMap<String, String> params = new HashMap<>();
                params.put("name", userdetail[0]);
                params.put("token", userdetail[2]);


				String res = userFunction.logout(params);
				JSONObject obj = new JSONObject(res);
				response = obj.getString("logout");

			} catch (Exception e) {
				e.printStackTrace();
			}*/

            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            System.out.println(result);
			/*if (response.equals("true")) {
				*//*dh.emptyTable(DatabaseHandler.TABLE_LOCATION);*//*
				*//*dh.emptyTable(DatabaseHandler.TABLE_USER);*//*
				Intent intent = new Intent(MainActivity.this, LogIn.class);
				startActivityForResult(intent, 0);
				overridePendingTransition(R.anim.new_slide_right, R.anim.hold);
				finishAffinity();
			} else if (response.equals("false")) {
				*//*dh.emptyTable(DatabaseHandler.TABLE_LOCATION);*//*
				*//*dh.emptyTable(DatabaseHandler.TABLE_USER);*//*
				Intent intent = new Intent(MainActivity.this, LogIn.class);
				startActivityForResult(intent, 0);
				overridePendingTransition(R.anim.new_slide_right, R.anim.hold);
				finishAffinity();
			} else {
				*//*dh.emptyTable(DatabaseHandler.TABLE_LOCATION);*//*
				*//*dh.emptyTable(DatabaseHandler.TABLE_USER);*//*
				Intent intent = new Intent(MainActivity.this, LogIn.class);
				startActivityForResult(intent, 0);
				overridePendingTransition(R.anim.new_slide_right, R.anim.hold);
				finishAffinity();
			}*/

            Intent intent = new Intent(MainActivity.this, LogIn.class);
            startActivityForResult(intent, 0);
            overridePendingTransition(R.anim.new_slide_right, R.anim.hold);
            finishAffinity();
        }

    }

    // =============================================================================================//

    private class facilities extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            myFacilityClassArrayList = new ArrayList<FacilityClass>();
        }

        @SuppressWarnings("unchecked")
        @Override
        protected String doInBackground(Void... params) {
            try {
                String[] userdetail = new String[3];
                userdetail = dh.getuserDetail();
                NewUserFunction userFunction = new NewUserFunction();
				/*ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
				param.add(new BasicNameValuePair("name", userdetail[0]));
				// param.add(new BasicNameValuePair("token", userdetail[2]));
				param.add(new BasicNameValuePair("pass", userdetail[1]));*/

                HashMap<String, String> param = new HashMap<>();
                param.put("name", userdetail[0]);
                param.put("pass", userdetail[1]);
                response = userFunction.getFacilityData(param);


                JSONObject obj = new JSONObject(response);
                Iterator<String> iterator = obj.keys();
                while (iterator.hasNext()) {
                    myFacilityClassObj = new FacilityClass();
                    String key = iterator.next();

                    String[] parentKey = key.split("\\.");
                    String[] splited = obj.getString(key).toString().split("\\s+");
                    ArrayList<String> strings = new ArrayList<String>();
                    strings.add(obj.getString(key));
                    strings.remove(splited[0]);

                    String reqClass = "";
                    for (int i = 0; i < strings.size(); i++) {
                        reqClass = reqClass + strings.get(i) + " ";
                    }

                    String ReqClass_string = reqClass.replace(splited[0], "");


                    myFacilityClassObj.setParentType(parentKey[0]);
                    myFacilityClassObj.setGUI_ID(splited[0]);
                    myFacilityClassObj.setRequestClass(ReqClass_string.trim());
                    myFacilityClassObj.setRequestType(parentKey[1]);
                    myFacilityClassArrayList.add(myFacilityClassObj);
                }
                dh.emptyTable(DatabaseHandler.MY_FACILITY_TABLE, "blank", "blank");
                dh.saveFacilityData(myFacilityClassArrayList);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

        }

    }

}
