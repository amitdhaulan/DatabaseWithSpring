package com.example.mobilerequestcentral;
//Make appropriate activity call..............in place of CheckInReservationList at line number 241

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.locationfilterization.locationNewDialogProp;
import com.example.mobilerequestcentral.incidents.ReportIncident;
import com.example.mobilerequestcentral.reservation.CancelReservationList;
import com.example.mobilerequestcentral.reservation.CheckInReservationList;
import com.example.mobilerequestcentral.reservation.MakeReservation;
import com.example.mobilerequestcentral.reservation.Reservation;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import custom.dialog.CustomAlertAdapter;
import database.helper.DatabaseHandler;
import my.classes.FacilityClass;
import my.classes.reservation.ReservationBuildingClass;
import user.function.NewUserFunction;

public class requestcentral extends Activity {

    static Toast toast;
    Context context;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    Button back, refreshButton;
    ArrayList<FacilityClass> myFacilityClassArrayList;
    FacilityClass myFacilityClassObj;
    DatabaseHandler dh;
    ArrayList<String> reservationListArrayList;
    ListView listView;
    @SuppressWarnings("unused")
    private int previousGroup = -1, lastExpandedGroupPosition;
    private AlertDialog myalertDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.requestcentral);
        context = this;
        dh = new DatabaseHandler(this);

        back = (Button) findViewById(R.id.request_button_back);
        refreshButton = (Button) findViewById(R.id.refresh);
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.request_listview);
        reservationListArrayList = new ArrayList<>();
        reservationListArrayList = dh.fillReservationLists();

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(context, listDataHeader,
                listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(requestcentral.this,
                        MainActivity.class);
                startActivityForResult(intent, 0);
                overridePendingTransition(R.anim.new_slide_right, R.anim.hold);
            }
        });

        refreshButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                new facilities().execute();
                prepareListData();
                listAdapter = new ExpandableListAdapter(context, listDataHeader,
                        listDataChild);
                expListView.invalidate();
                expListView.setAdapter(listAdapter);
                showToast("List Refreshed!");
            }
        });


        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView arg0, View arg1,
                                        int groupPosition, long arg3) {
                if (listDataHeader.get(groupPosition).equals("Reserve a Space")) {
                    Intent intent = new Intent(requestcentral.this,
                            locationNewDialogProp.class);
                    startActivityForResult(intent, 0);
                }
                return false;
            }
        });
        expListView.setOnChildClickListener(new OnChildClickListener() {
            View _lastColored;

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                if (_lastColored != null) {
                    _lastColored.setBackgroundColor(Color.TRANSPARENT);
                    _lastColored.invalidate();
                }
                _lastColored = v;
                /*v.setBackgroundColor(Color.parseColor("#4F6DC3"));*/

                if (listDataHeader.get(groupPosition).equals("Facilities")) {
                    String requestValue = listDataChild.get(
                            listDataHeader.get(groupPosition)).get(childPosition);
                    Bundle bundle = new Bundle();
                    bundle.putString("Value", requestValue);

                    Intent intent = new Intent(requestcentral.this,
                            RequestCentralForm.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    overridePendingTransition(R.anim.new_slide_left,
                            R.anim.hold);
                } else if (listDataHeader.get(groupPosition).equals("Reservation")) {
                    if (listDataChild.get(
                            listDataHeader.get(groupPosition)).get(childPosition).equals("My reservations")) {

                        if (dh.getDemoReservationData().size() != 0) {
                            Intent intent = new Intent(requestcentral.this,
                                    Reservation.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("today", "false");

                            intent.putExtras(bundle);
                            startActivity(intent);
                            overridePendingTransition(R.anim.new_slide_left,
                                    R.anim.hold);
                        } else {
                            showToast("No reservation record to show!");
                        }

                    } else if ((listDataChild.get(
                            listDataHeader.get(groupPosition)).get(childPosition).equals("Make a new reservation"))) {

                        populateReservationlist();


                    } else if ((listDataChild.get(
                            listDataHeader.get(groupPosition)).get(childPosition).equals("Request a reservation"))) {

                        populateReservationlist();


                    } else if ((listDataChild.get(
                            listDataHeader.get(groupPosition)).get(childPosition).equals("Cancel a reservation"))) {

                        if (dh.getDemoReservationData().size() != 0) {
                            Intent intent = new Intent(requestcentral.this,
                                    CancelReservationList.class);
                        /*intent.putExtra("hide","hidecheckinbutton");*/
                            startActivity(intent);
                            overridePendingTransition(R.anim.new_slide_left,
                                    R.anim.hold);
                        } else {
                            showToast("No reservation record to show!");
                        }

                    } else if ((listDataChild.get(
                            listDataHeader.get(groupPosition)).get(childPosition).equals("Check in"))) {
                        if (dh.getDemoReservationData().size() != 0) {
                            Intent intent = new Intent(requestcentral.this,
                                    CheckInReservationList.class);
                        /*intent.putExtra("hide","hidecancelbutton");*/
                            startActivity(intent);
                            overridePendingTransition(R.anim.new_slide_left,
                                    R.anim.hold);
                        } else {
                            showToast("No reservation record to show!");
                        }

                    } else if ((listDataChild.get(
                            listDataHeader.get(groupPosition)).get(childPosition).equals("Today's reservation"))) {

                        if (dh.getDemoReservationDataForParticularDay("09/30/2015").size() != 0) {
                            Intent intent = new Intent(requestcentral.this,
                                    Reservation.class);


                            Bundle bundle = new Bundle();
                            bundle.putString("today", "true");

                            intent.putExtras(bundle);
                            startActivity(intent);
                            overridePendingTransition(R.anim.new_slide_left,
                                    R.anim.hold);
                        } else {
                            showToast("No reservation record to show!");
                        }


                    } else {
                        if (toast != null)
                            toast.cancel();
                        toast = Toast.makeText(getApplicationContext(), "Under Process!", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } else if (listDataHeader.get(groupPosition).equals("Incidents")) {

                    String title = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
                    Intent intent = new Intent(requestcentral.this,
                            ReportIncident.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("title", title);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    overridePendingTransition(R.anim.new_slide_left,
                            R.anim.hold);
                }
                return false;
            }
        });
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != lastExpandedGroupPosition) {
                    expListView.collapseGroup(lastExpandedGroupPosition);
                    System.out.println("lastExpandedGroupPosition: " + lastExpandedGroupPosition);
                }

                lastExpandedGroupPosition = groupPosition;
            }
        });
    }

    private void showToast(String msg) {
        if (toast != null)
            toast.cancel();
        toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void populateReservationlist() {
        /*Intent intent = new Intent(requestcentral.this,
								MakeReservation.class);
						startActivity(intent);
						overridePendingTransition(R.anim.new_slide_left,
                                R.anim.hold);

                        if(dh.getReservationBuildingList().size() == 0){
                            new getBuildingForReservation().execute();
                        }*/


        AlertDialog.Builder myDialog = new AlertDialog.Builder(requestcentral.this);

                        /*final EditText editText = new EditText(requestcentral.this);*/
        listView = new ListView(requestcentral.this);
                        /*editText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.search, 0, 0, 0);*/
        LinearLayout layout = new LinearLayout(requestcentral.this);
        layout.setOrientation(LinearLayout.VERTICAL);
                        /*layout.addView(editText);*/
        layout.addView(listView);
        myDialog.setView(layout);

                       /* serviceArrayListFiltered = new ArrayList<String>();
                        serviceArrayListFiltered.addAll(serviceArrayList);
                        Collections.sort(serviceArrayListFiltered);*/
        CustomAlertAdapter arrayAdapter = new CustomAlertAdapter(requestcentral.this, reservationListArrayList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                               /* String strName = serviceArrayListFiltered.get(position);
                                service.setText("");
                                service.setText(strName);*/
                /*Toast.makeText(getApplicationContext(), reservationListArrayList.get(position), Toast.LENGTH_SHORT).show();*/
                Intent intent = new Intent(requestcentral.this,
                        MakeReservation.class);
                intent.putExtra("title", reservationListArrayList.get(position));
                startActivity(intent);
                overridePendingTransition(R.anim.new_slide_left,
                        R.anim.hold);

                if (dh.getReservationBuildingList().size() == 0) {
                    new getBuildingForReservation().execute();
                }
                myalertDialog.dismiss();

            }
        });

                       /* editText.addTextChangedListener(new TextWatcher()
                        {
                            public void afterTextChanged(Editable s){

                            }
                            public void beforeTextChanged(CharSequence s,
                                                          int start, int count, int after){

                            }
                            public void onTextChanged(CharSequence s, int start, int before, int count)
                            {
                                editText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.search, 0, 0, 0);
                                textlength = editText.getText().length();
                                serviceArrayListFiltered.clear();
                                for (int i = 0; i < serviceArrayList.size(); i++)
                                {
                                    if (textlength <= serviceArrayList.get(i).length())
                                    {

                                        if(serviceArrayList.get(i).toLowerCase().contains(editText.getText().toString().toLowerCase().trim()))
                                        {
                                            serviceArrayListFiltered.add(serviceArrayList.get(i));
                                        }
                                    }
                                }

                                Collections.sort(serviceArrayListFiltered);
                                listview.setAdapter(new CustomAlertAdapter(RequestCentralForm.this, serviceArrayListFiltered));
                            }
                        });*/
        myDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        myalertDialog = myDialog.show();
    }

    private void prepareListData() {
        List<String> Facilities = new ArrayList<String>();
        List<String> Reservations = new ArrayList<String>();
        List<String> Incidents = new ArrayList<String>();

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

		/*DatabaseHandler dh = new DatabaseHandler(getApplicationContext());*/
        Facilities.addAll(dh.fillRequestLists());


        // Adding parent data
        listDataHeader.add("Facilities");
        listDataHeader.add("Reservation");
        listDataHeader.add("Incidents");
		/*listDataHeader.add("Climate Control");*/
		/*listDataHeader.add("Reserve a Space");*/
        // listDataHeader.add("Coming Soon..");

        // Adding child data

		/*Facilities.add("Electrical & Lighting");*/
		/*Facilities.add("General Repair");*/
		/*Facilities.add("House Keeping");*/

		/*List<String> IT = new ArrayList<String>();
		IT.add("Room Too Cold");
		IT.add("HVAC Service");
		IT.add("Room Too Warm");
		IT.add("BMS Alert - HVAC");
		IT.add("Service - New");
		IT.add("Service - Repair");*/

        List<String> Reserve = new ArrayList<String>();
        Reserve.add("");

		/*Reservations.addAll(dh.fillReservationLists());*/


        Reservations.add("Cancel a reservation");
        Reservations.add("Check in");
        Reservations.add("Make a new reservation");
        Reservations.add("My reservations");
        Reservations.add("Request a reservation");
        Reservations.add("Today's reservation");

        Incidents.add("Building Evacuation");
        Incidents.add("Damage / Vandalism");
        Incidents.add("E-911");
        Incidents.add("External Incident Monitoring");
        Incidents.add("Fire Alarm Activation");
        Incidents.add("HR / Legal Assistance");
        Incidents.add("Insurance Claims");
        Incidents.add("iSOS");
        Incidents.add("Lost, Stolen or Found");
        Incidents.add("Nuisance Calls / Phishing / Information Security Issue");
        Incidents.add("Other / General Security Concern");
        Incidents.add("Page Critical");
        Incidents.add("Pandemic / Contagious Illness");
        Incidents.add("Transportation");
        Incidents.add("Unauthorized Access");
        Incidents.add("Workplace Injury or Illness");
        Incidents.add("Workplace Violence or Threat of Violence");

        listDataChild.put(listDataHeader.get(0), Facilities); // Header, Child
        listDataChild.put(listDataHeader.get(1), Reservations);
        listDataChild.put(listDataHeader.get(2), Incidents);
        // data
		/*listDataChild.put(listDataHeader.get(1), IT);
		listDataChild.put(listDataHeader.get(2), Reserve);*/
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(requestcentral.this, MainActivity.class);
        startActivityForResult(intent, 0);
        overridePendingTransition(R.anim.new_slide_right, R.anim.hold);
    }

    private class facilities extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            myFacilityClassArrayList = new ArrayList<FacilityClass>();
        }

        @SuppressWarnings("unchecked")
        @Override
        protected String doInBackground(Void... params) {
            String response;
			/*DatabaseHandler dh = new DatabaseHandler(getApplicationContext());*/
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

                dh.emptyTable(DatabaseHandler.MY_FACILITY_TABLE, "blank", "blank");

                response = userFunction.getFacilityData(param);

                JSONObject obj = new JSONObject(response);
                Iterator<String> iterator = obj.keys();
                while (iterator.hasNext()) {
                    myFacilityClassObj = new FacilityClass();
                    String key = iterator.next();

                    String[] splited = obj.getString(key).toString().split("\\s+");
                    ArrayList<String> strings = new ArrayList<String>();
                    strings.add(obj.getString(key));
                    strings.remove(splited[0]);

                    String reqClass = "";
                    for (int i = 0; i < strings.size(); i++) {
                        reqClass = reqClass + strings.get(i) + " ";
                    }

                    String ReqClass_string = reqClass.replace(splited[0], "");
                    String[] parentKey = key.split("\\.");

                    myFacilityClassObj.setParentType(parentKey[0]);
                    myFacilityClassObj.setGUI_ID(splited[0]);
                    myFacilityClassObj.setRequestClass(ReqClass_string);
                    myFacilityClassObj.setRequestType(parentKey[1]);
                    myFacilityClassArrayList.add(myFacilityClassObj);
                }

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


    public class getBuildingForReservation extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... params) {
            String response = "";
            String[] userdetail;
            userdetail = dh.getuserDetail();
            HashMap<String, String> param = new HashMap<>();
            param.put("name", userdetail[0]);
            param.put("pass", userdetail[1]);

            NewUserFunction userFunction = new NewUserFunction();

            try {
                response = userFunction.getBuildingForReservation(param);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            System.out.print(response);
            ArrayList<ReservationBuildingClass> reservationBuildingClassesArrayList = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(response);
                Iterator<String> iterator = jsonObject.keys();
                while (iterator.hasNext()) {
                    ReservationBuildingClass reservationBuildingClass = new ReservationBuildingClass();

                    String buildingId = iterator.next();
                    reservationBuildingClass.setBuildingId(buildingId);
                    reservationBuildingClass.setBuildingName(jsonObject.getString(buildingId));
                    reservationBuildingClassesArrayList.add(reservationBuildingClass);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            dh.savereservertationBuildingData(reservationBuildingClassesArrayList);

        }
    }
}
