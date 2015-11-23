package com.example.mobilerequestcentral.reservation;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mobilerequestcentral.R;
import com.example.mobilerequestcentral.requestcentral;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import custom.dialog.CustomAlertAdapter;
import custom.dialog.CustomDatePickerValueInterface;
import custom.dialog.CustomDialog;
import custom.dialog.CustomDialogInterface;
import custom.dialog.CustomTimePickerDialog;
import custom.dialog.DateDialogFragment;
import custom.dialog.dateDialogInterface;
import database.helper.DatabaseHandler;
import my.classes.reservation.MyEquipmentClass;
import my.classes.reservation.ReservationSpaceClass;
import my.classes.reservation.myReservationClass;
import user.function.NewUserFunction;

/**
 * Created by amitk on 8/12/2015.
 */
public class MakeReservation extends FragmentActivity implements View.OnClickListener, CustomDatePickerValueInterface, dateDialogInterface,
        CustomDialogInterface {


    EditText locationEditText, descriptionEditText, numberofattendeesEditText;
    TextView spaceTextView, starttimeTextView, endtimeTextView, planneddurationTextView, starttime_timeTextView, endtime_timeTextView, titleTextView, reservationTitle;
    Spinner requestedlayoutSpinner;
    CheckBox foodserviceRequiredCheckBox, equipmentCheckBox, storageCheckBox, specialneedCheckBox;
    Button submitRequestButton, backButton, selectLocationButton;
    ProgressDialog pDialog;
    Calendar myCalendar;
    ArrayAdapter<String> statusadapter;
    JSONObject jsonObjectToPost;
    String[] userdetail;
    String labelString = "", startstr;
    Date startTimeDate, endtimeDate;
    dateDialogInterface di;
    CustomDatePickerValueInterface cdpInterface;
    Context context;
    Activity ac;
    Toast toast;
    DatabaseHandler dh;
    ArrayList<String> reservationBuildingList;
    ArrayList<String> reservationBuildingListFiltered;
    CharSequence[] reservationBuildingCharArray = {};
    ListView listview;
    int textlength = 0;
    NewUserFunction userFunction;
    ArrayList seletcedItems;
    CustomDialog cDialog;
    CustomTimePickerDialog timePickerDialog;
    HashMap<String, String> spaceRecordIdStringStringHashMap;
    ArrayList spaceRecordIdArrayList, strings;
    Bundle bundle;
    String locationText;
    long l, diffSeconds, diffMinutes, diffHours, diffDays;
    private AlertDialog myalertDialog = null;
    /*MyInterface myInterface;*/
    private CustomTimePickerDialog.OnTimeSetListener timeSetListener = new CustomTimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.make_reservation);
        context = this;
        ac = this;
        dh = new DatabaseHandler(context);
        myCalendar = Calendar.getInstance();

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");


        reservationBuildingList = new ArrayList<>();

        locationEditText = (EditText) findViewById(R.id.makereservation_location_edt);
        descriptionEditText = (EditText) findViewById(R.id.makereservation_description_edt);
        numberofattendeesEditText = (EditText) findViewById(R.id.makereseration_numberofattendees_edt);

        reservationTitle = (TextView) findViewById(R.id.reservationtitle);
        titleTextView = (TextView) findViewById(R.id.makereservation_txt_title);
        starttime_timeTextView = (TextView) findViewById(R.id.makereseration_starttime_time_txt);
        endtime_timeTextView = (TextView) findViewById(R.id.makereseration_endtime_time_txt);
        spaceTextView = (TextView) findViewById(R.id.makereseration_space_txt);
        starttimeTextView = (TextView) findViewById(R.id.makereseration_starttime_txt);
        endtimeTextView = (TextView) findViewById(R.id.makereseration_endtime_txt);
        planneddurationTextView = (TextView) findViewById(R.id.makereseration_planned_duration_txt);
        requestedlayoutSpinner = (Spinner) findViewById(R.id.makereseration_requestedlayout_spn);
        foodserviceRequiredCheckBox = (CheckBox) findViewById(R.id.makereseration_foodservice_cb);
        equipmentCheckBox = (CheckBox) findViewById(R.id.makereseration_equipment_cb);
        storageCheckBox = (CheckBox) findViewById(R.id.makereseration_storage_cb);
        specialneedCheckBox = (CheckBox) findViewById(R.id.makereseration_specialneeds_cb);
        submitRequestButton = (Button) findViewById(R.id.makereservation_submit_btn);
        backButton = (Button) findViewById(R.id.makereservation_button_back_btn);
        selectLocationButton = (Button) findViewById(R.id.makereservation_selectLocation);

        locationText = locationEditText.getText().toString();

        endtime_timeTextView.setTag("textView");
        starttime_timeTextView.setTag("starttimetextView");
        di = (dateDialogInterface) context;
        cdpInterface = (CustomDatePickerValueInterface) context;
        cDialog = new CustomDialog(MakeReservation.this);
        /*myInterface = (MyInterface) this;*/

        titleTextView.setText(title);

        if (title.contains("Equipment")) {
            String[] strings = title.split(" ");
            reservationTitle.setText(strings[0]);
        } else if (title.contains("Vehicle")) {
            String[] strings = title.split(" ");
            reservationTitle.setText(strings[0]);
        }

        spaceTextView.setOnClickListener(this);
        starttimeTextView.setOnClickListener(this);
        endtimeTextView.setOnClickListener(this);
        endtime_timeTextView.setOnClickListener(this);
        starttime_timeTextView.setOnClickListener(this);
        planneddurationTextView.setOnClickListener(this);
        submitRequestButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        selectLocationButton.setOnClickListener(this);

        ArrayList<String> requestedlayoutSpinnerArrayList = new ArrayList<>();
        requestedlayoutSpinnerArrayList.add("Auditorium");
        requestedlayoutSpinnerArrayList.add("Class Room");
        requestedlayoutSpinnerArrayList.add("Conference");
        requestedlayoutSpinnerArrayList.add("Dining");
        requestedlayoutSpinnerArrayList.add("Round Table");
        requestedlayoutSpinnerArrayList.add("Team Room");
        requestedlayoutSpinnerArrayList.add("Training");
        requestedlayoutSpinnerArrayList.add("Office");


        statusadapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, requestedlayoutSpinnerArrayList);

        statusadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        requestedlayoutSpinner.setAdapter(statusadapter);

    }

    @Override
    public void checkdatevalue(String time) {
        checkAndValidateTimeEntry(time);
    }

    @Override
    public void onCustomDialogClick(String floor, String building, String property) {

    }

    @Override
    public void onCustomDialogFieldBlank() {

    }

    @Override
    public void OnDatePickerClick(String day, String month, String year, String tag) {
        if (tag.equals("addtime_date")) {



            /*dateTextView.setText(month + "/" + day + "/" + year);*/
        } else if (tag.equals("starttime")) {
            starttimeTextView.setText(month + "/" + day + "/" + year);
        } else if (tag.equals("endtime")) {
            endtimeTextView.setText(month + "/" + day + "/" + year);
        }

    }

    public void checkAndValidateTimeEntry(String time) {

        /*try {

            if (CustomTimePicker.CalculateTimeDifference(StartTime, time)) {

            } else {
                timeEndTextView.setText("");
                cd.showCustomDialog("Invalid Time Entries!",
                        "Stop Time should be greater than Start Time.");

            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        } finally {
            IsEndTime = false;
        }*/

    }

    @Override
    public void onClick(View v) {
        if (v == submitRequestButton) {

           /* try{
                jsonObjectToPost = new JSONObject();


                if(endtimeTextView.getText().toString().length() == 0){
                    jsonObjectToPost.put("endtime", "");
                }else
                    jsonObjectToPost.put("endtime", endtimeTextView.getText().toString());

                if(starttimeTextView.getText().toString().length() == 0){
                    jsonObjectToPost.put("starttime", "");
                }else
                    jsonObjectToPost.put("starttime", starttimeTextView.getText().toString());

                if(numberofattendeesEditText.getText().toString() .length() == 0){
                    jsonObjectToPost.put("numberofemployee", "");
                }else
                    jsonObjectToPost.put("numberofemployee", numberofattendeesEditText.getText().toString());

                if(descriptionEditText.getText().toString().length() == 0){
                    jsonObjectToPost.put("description", "");
                }else
                    jsonObjectToPost.put("description", descriptionEditText.getText().toString());

                jsonObjectToPost.put("building", locationEditText.getText().toString());
                jsonObjectToPost.put("requestedlayout", requestedlayoutSpinner.getSelectedItem());

                String food = "", specialneed = "", equipment = "", storage = "";
                if(foodserviceRequiredCheckBox.isChecked()){
                    food = "true";
                }else
                    food = "false";
                if(specialneedCheckBox.isChecked()){
                    specialneed = "true";
                }else
                    specialneed = "false";
                if(equipmentCheckBox.isChecked()){
                    equipment = "true";
                }else
                    equipment = "false";
                if(storageCheckBox.isChecked()){
                    storage = "true";
                }else
                    storage = "false";

                jsonObjectToPost.put("foodservice", food);
                jsonObjectToPost.put("equipment", equipment);
                jsonObjectToPost.put("specialneeds", specialneed);
                jsonObjectToPost.put("storage", storage);

                jsonObjectToPost.put("spacerecordids", spaceRecordIdArrayList.toString().replace("[", "").replace("]", ""));


            }catch (Exception e){
                e.printStackTrace();
            }

            if(locationEditText.getText().toString().length() != 0){
                new PostReservation("", "").execute();
            }else{
                locationEditText.setError("Please provide the value");
            }*/

            if (locationEditText.getText().toString().length() != 0) {

                ArrayList<myReservationClass> myReservationClasses = new ArrayList<>();
                myReservationClass myReservationClass = new myReservationClass();
                long l = 0, l1 = 0;
                try {
                    Date date = new Date(starttimeTextView.getText().toString());
                    l = date.getTime();

                    Date date1 = new Date(endtimeTextView.getText().toString());
                    l1 = date1.getTime();
                } catch (Exception e) {
                    e.printStackTrace();
                }


                myReservationClass.setDate(starttimeTextView.getText().toString());
                myReservationClass.setStarttime(l + "");
                myReservationClass.setEndtime(l1 + "");
                myReservationClass.setBuildingname(locationEditText.getText().toString());
                myReservationClass.setFloorname("10-10-Tenth Floor");
                myReservationClass.setSpacename("Z404");
                myReservationClass.setCapacity("6");
                myReservationClass.setAda_available("TRUE");
                myReservationClass.setCatering_available("TRUE");
                myReservationClass.setNetwork_connection("TRUE");
                myReservationClass.setWhiteboard("TRUE");
                myReservationClass.setVideo_conference("TRUE");
                myReservationClass.setCheckedin("!checkedin");
                myReservationClass.setReservationId(getRandomNumber() + "");

                myReservationClasses.add(myReservationClass);
                dh.saveDemoReservationData(myReservationClasses);

                showToast("Reservation created successfully!");


            } else {
                locationEditText.setError("Please provide location!");
            }


        } else if (v == backButton) {
            Intent intent = new Intent(MakeReservation.this, requestcentral.class);
            startActivity(intent);
            overridePendingTransition(R.anim.new_slide_right, R.anim.hold);

        } else if (v == starttimeTextView) {

            planneddurationTextView.setText("");
            endtime_timeTextView.setText("");
            endtimeTextView.setText("");

            labelString = "starttime";

            DateDialogFragment dateDialogFragment = new DateDialogFragment(
                    di, ac);
            dateDialogFragment.show(getSupportFragmentManager(),
                    "starttime");


        } else if (v == endtimeTextView) {
            planneddurationTextView.setText("");
            endtime_timeTextView.setText("");

            /*planneddurationTextView.setText("");
            endtime_timeTextView.setText("");*/


            labelString = "endtime";

            DateDialogFragment dateDialogFragment = new DateDialogFragment(
                    di, ac);
            dateDialogFragment.show(getSupportFragmentManager(),
                    "endtime");

        } else if (v == starttime_timeTextView) {
            planneddurationTextView.setText("");
            endtime_timeTextView.setText("");
            endtimeTextView.setText("");

            if (starttimeTextView.getText().toString().length() != 0) {
                showTimePicker(starttime_timeTextView, starttimeTextView, endtimeTextView, planneddurationTextView, startstr/*, diffDays, diffHours, diffMinutes*/);
            } else showToast("Enter date first");

        } else if (v == endtime_timeTextView) {

            planneddurationTextView.setText("");
            if (starttime_timeTextView.getText().toString().length() != 0) {
                if (endtimeTextView.getText().toString().length() != 0) {
                    synchronized (this) {
                        startstr = starttimeTextView.getText().toString() + " " + starttime_timeTextView.getText().toString();
                        showTimePicker(endtime_timeTextView, starttimeTextView, endtimeTextView, planneddurationTextView, startstr);
                    }
                  /*  if(myInterface.ifFilled()) {
                        System.out.println("filled");
                    }*/
                } else showToast("Enter date first");
            } else {
                showToast("Enter start date and time!");
            }

        } else if (v == selectLocationButton) {
            reservationBuildingList = dh.getReservationBuildingList();
            if (reservationBuildingList.size() != 0) {


                reservationBuildingCharArray = reservationBuildingList
                        .toArray(new CharSequence[reservationBuildingList.size()]);


                System.out.println("=====>Clicked<=====");

                AlertDialog.Builder myDialog = new AlertDialog.Builder(MakeReservation.this);

                final EditText editText = new EditText(MakeReservation.this);
                listview = new ListView(MakeReservation.this);
                editText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.searchnew2, 0, 0, 0);
                LinearLayout layout = new LinearLayout(MakeReservation.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(editText);
                layout.addView(listview);
                myDialog.setView(layout);

                reservationBuildingListFiltered = new ArrayList<String>();
                reservationBuildingListFiltered.addAll(reservationBuildingList);
                Collections.sort(reservationBuildingListFiltered);
                CustomAlertAdapter arrayAdapter = new CustomAlertAdapter(MakeReservation.this, reservationBuildingListFiltered);
                listview.setAdapter(arrayAdapter);

                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String strName = reservationBuildingListFiltered.get(position);
                        locationEditText.setText("");
                        locationEditText.setText(strName);
                        myalertDialog.dismiss();


                        if (reservationTitle.getText().toString().contains("Equipment")) {
                            if (dh.getReservationEquipmentList_ViaBuildinhg(strName).size() == 0) {
                                new getEquipmentData().execute();
                                selectLocationButton.setText("");
                            }
                        }
                        if (reservationTitle.getText().toString().contains("Space")) {
                            if (dh.getReservationSpaceList_ViaBuildinhg(strName).size() == 0) {
                                new getReservationSpaces().execute();
                                selectLocationButton.setText("");
                            }
                        }
                        /*if(reservationTitle.getText().toString().contains("Vehicle")){
                            if(dh.getReservationVehicleList_ViaBuildinhg(strName).size()==0){
                                new getVehicleData().execute();
                                selectLocationButton.setText("");
                            }
                        }*/


                    }
                });

                editText.addTextChangedListener(new TextWatcher() {
                    public void afterTextChanged(Editable s) {

                    }

                    public void beforeTextChanged(CharSequence s,
                                                  int start, int count, int after) {

                    }

                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        editText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.searchnew2, 0, 0, 0);
                        textlength = editText.getText().length();
                        reservationBuildingListFiltered.clear();
                        for (int i = 0; i < reservationBuildingList.size(); i++) {
                            if (textlength <= reservationBuildingList.get(i).length()) {

                                if (reservationBuildingList.get(i).toLowerCase().contains(editText.getText().toString().toLowerCase().trim())) {
                                    reservationBuildingListFiltered.add(reservationBuildingList.get(i));
                                }
                            }
                        }

                        Collections.sort(reservationBuildingListFiltered);
                        listview.setAdapter(new CustomAlertAdapter(MakeReservation.this, reservationBuildingListFiltered));
                    }
                });
                myDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                myalertDialog = myDialog.show();
            } else {
                CustomDialog cDialog = new CustomDialog(MakeReservation.this);
                cDialog.showCustomDialog("Please wait...", "Preparing data.");
            }

        } else if (v == spaceTextView) {

            if (reservationTitle.getText().toString().equals("Space")) {
                final CharSequence[] items = dh.getReservationSpaceList_ViaBuildinhg(locationEditText.getText().toString()).toArray(new CharSequence[dh.getReservationSpaceList_ViaBuildinhg(locationEditText.getText().toString()).size()]);
                populateSpaceData(items, "Space");
            }
            if (reservationTitle.getText().toString().equals("Equipment")) {
                final CharSequence[] items = dh.getReservationEquipmentList_ViaBuildinhg(locationEditText.getText().toString()).toArray(new CharSequence[dh.getReservationEquipmentList_ViaBuildinhg(locationEditText.getText().toString()).size()]);
                populateSpaceData(items, "Equipment");
            }
            if (reservationTitle.getText().toString().equals("Vehicle")) {
                /*populateVehicleDada();*/
            }
        }
    }

    private String getRandomNumber() {
        long recordId = 0;
        Date date = new Date();
        recordId = date.getTime();
        return recordId + "";
    }

    private void populateSpaceData(CharSequence[] items, final String title) {
        /*showToast("Under Process");*/

        if (locationEditText.getText().toString().length() != 0) {


            //                    ***************************Changed at 8.48 9/9/15**************
            final ArrayList mSelectedItemsPosition = new ArrayList();


            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            /*alertDialogBuilder.setIcon(R.drawable.electricallighting);*/
            alertDialogBuilder.setTitle("Select " + title + "(s)")
                    .setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                            if (isChecked) {
                                mSelectedItemsPosition.add(which);

                            } else if (mSelectedItemsPosition.contains(which)) {
                                mSelectedItemsPosition.remove(Integer.valueOf(which));
                            }
                        }

                    }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    try {
                        strings = new ArrayList<String>();
                        seletcedItems = new ArrayList();

                        if (title.equals("Space")) {
                            seletcedItems = (dh.getReservationSpaceList_ViaBuildinhg(locationEditText.getText().toString()));
                        }
                        if (title.equals("Equipment")) {
                            seletcedItems = (dh.getReservationEquipmentList_ViaBuildinhg(locationEditText.getText().toString()));
                        }/*if(){
                            seletcedItems = (dh.getReservationSpaceList_ViaBuildinhg(locationEditText.getText().toString()));
                        }*/

                        for (int i = 0; i < mSelectedItemsPosition.size(); i++) {
                            strings.add(seletcedItems.get(Integer.parseInt(mSelectedItemsPosition.get(i).toString())).toString());
                        }
                        spaceTextView.setText(strings.toString().replace("[", "").replace("]", ""));

                        System.out.println("=============> " + strings);
                        spaceRecordIdArrayList = new ArrayList();
                        for (int i = 0; i < seletcedItems.size(); i++) {

                            try {

                                if (title.equals("Space")) {
                                    spaceRecordIdArrayList.add(dh.getReservationRecordId_ViaSpace(strings.get(i).toString()));
                                }
                                if (title.equals("Equipment")) {
                                    spaceRecordIdArrayList.add(dh.getReservationRecordId_ViaEquipment(strings.get(i).toString()));
                                }


                                System.out.println("RecordIdArrayList =============> :" + spaceRecordIdArrayList);
                            } catch (Exception e) {
                                System.out.println("localized message =========> " + e.getLocalizedMessage());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                }
            });

            alertDialogBuilder.create();
            ;
            alertDialogBuilder.show();
//                    ***************************************************************


        } else
            showToast("Please select building first!");
    }

    public void showTimePicker(TextView textView, TextView startdateView, TextView enddateTextview, TextView plannedEnd, String startdateandTimeString/*, long diffDays, long diffHours, long diffMinutes*/) {
        timePickerDialog = new CustomTimePickerDialog(context, timeSetListener,
                Calendar.getInstance().get(Calendar.HOUR), (Calendar.getInstance().get(Calendar.MINUTE) + CustomTimePickerDialog.TIME_PICKER_INTERVAL), true, textView, startdateView, enddateTextview, plannedEnd, startdateandTimeString/*, diffDays, diffHours, diffMinutes*/);
        timePickerDialog.setTitle("Set time");

        timePickerDialog.show();
    }

    public void showToast(String msg) {
        if (toast != null)
            toast.cancel();

        toast = Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MakeReservation.this, requestcentral.class);
        startActivity(intent);
        overridePendingTransition(R.anim.new_slide_right, R.anim.hold);

    }

    public class PostReservation extends AsyncTask<Void, Integer, String> {

        String _username, _password;

        PostReservation(String username, String password) {
            _username = username;
            _password = password;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            super.onPreExecute();
            pDialog = ProgressDialog.show(MakeReservation.this, "", "");
            pDialog.setContentView(R.layout.progresslayout);
            TextView titletextView = (TextView) pDialog
                    .findViewById(R.id.title);
            TextView messageTextView = (TextView) pDialog
                    .findViewById(R.id.message);
            titletextView.setText("Please Wait...");
            messageTextView.setText("Request is being created...");
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.show();


        }

        @Override
        protected String doInBackground(Void... params) {

            String response = "";
            DatabaseHandler dh = new DatabaseHandler(getApplicationContext());

            String[] userdetail;
            userdetail = dh.getuserDetail();

            HashMap<String, String> param = new HashMap<>();
            param.put("name", userdetail[0]);
            param.put("pass", userdetail[1]);
            /*try{
                jsonObjectToPost = new JSONObject();


                if(endtimeTextView.getText().toString().length() == 0){
                    jsonObjectToPost.put("endtime", "");
                }else
                    jsonObjectToPost.put("endtime", endtimeTextView.getText().toString());

                if(starttimeTextView.getText().toString().length() == 0){
                    jsonObjectToPost.put("starttime", "");
                }else
                    jsonObjectToPost.put("starttime", starttimeTextView.getText().toString());

                if(numberofattendeesEditText.getText().toString() .length() == 0){
                    jsonObjectToPost.put("numberofemployee", "");
                }else
                    jsonObjectToPost.put("numberofemployee", numberofattendeesEditText.getText().toString());

                if(descriptionEditText.getText().toString().length() == 0){
                    jsonObjectToPost.put("description", "");
                }else
                    jsonObjectToPost.put("description", descriptionEditText.getText().toString());

                jsonObjectToPost.put("building", locationEditText.getText().toString());
                jsonObjectToPost.put("requestedlayout", requestedlayoutSpinner.getSelectedItem());

                String food = "", specialneed = "", equipment = "", storage = "";
                if(foodserviceRequiredCheckBox.isChecked()){
                    food = "true";
                }else
                    food = "false";
                if(specialneedCheckBox.isChecked()){
                    specialneed = "true";
                }else
                    specialneed = "false";
                if(equipmentCheckBox.isChecked()){
                    equipment = "true";
                }else
                    equipment = "false";
                if(storageCheckBox.isChecked()){
                    storage = "true";
                }else
                    storage = "false";

                jsonObjectToPost.put("foodservice", food);
                jsonObjectToPost.put("equipment", equipment);
                jsonObjectToPost.put("specialneeds", specialneed);
                jsonObjectToPost.put("storage", storage);

                jsonObjectToPost.put("spacerecordids", spaceRecordIdArrayList.toString().replace("[", "").replace("]", ""));


            }catch (Exception e){
                e.printStackTrace();
            }*/

            param.put("data", jsonObjectToPost.toString());

            System.out.println("params ===========> " + param);

            NewUserFunction userFunction = new NewUserFunction();

            try {
//                response = userFunction.makeReservation(param);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (result != null && result.contains("message")) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String s = jsonObject.getString("message");
                    long l = Long.parseLong(s);
                    locationEditText.setText("");
                    descriptionEditText.setText("");
                    numberofattendeesEditText.setText("");
                    spaceTextView.setText("");
                    starttimeTextView.setText("");
                    endtimeTextView.setText("");
                    planneddurationTextView.setText("");
                    starttime_timeTextView.setText("");
                    endtime_timeTextView.setText("");
                    foodserviceRequiredCheckBox.setChecked(false);
                    equipmentCheckBox.setChecked(false);
                    storageCheckBox.setChecked(false);
                    specialneedCheckBox.setChecked(false);
                    requestedlayoutSpinner.setSelection(0);
                    cDialog.showCustomDialog("Attention!",
                            "Reservation created successfully");

                } catch (Exception e) {
                    cDialog.showCustomDialog("Attention!",
                            "Reservation not created , try again");
                }
            }

        }
    }

    public class getReservationSpaces extends AsyncTask<Void, Integer, String> {
        String username, password;

        public getReservationSpaces() {
            String userdetail[] = dh.getuserDetail();
            this.username = userdetail[0];
            this.password = userdetail[1];
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pDialog = ProgressDialog.show(MakeReservation.this, "", "");
            pDialog.setContentView(R.layout.progresslayout);
            // progressBar = (ProgressBar)
            // pDialog.findViewById(R.id.progressBar1);
            TextView titletextView = (TextView) pDialog
                    .findViewById(R.id.title);
            TextView messageTextView = (TextView) pDialog
                    .findViewById(R.id.message);
            titletextView.setText("Please Wait...");
            messageTextView.setText("Spaces are being downloaded...");
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            userFunction = new NewUserFunction();
            String spaceResponse = "";

            HashMap<String, String> postDataParams = new HashMap<>();
            postDataParams.put("name", username);
            postDataParams.put("pass", password);
            postDataParams.put("building", locationText);


            try {
                spaceResponse = userFunction.getSpaceForReservation(postDataParams);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return spaceResponse;
        }


        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            pDialog.dismiss();
            ArrayList<ReservationSpaceClass> reservationSpaceClassesArrayList = new ArrayList<>();

            if (!response.contains("no data")) {
                dh.emptyTable(DatabaseHandler.MY_RESERVATION_SPACE_TABLE, "blank", "blank");


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Iterator<String> iterator = jsonObject.keys();
                    while (iterator.hasNext()) {
                        ReservationSpaceClass reservationSpaceClass = new ReservationSpaceClass();
                        String key = iterator.next();
                        JSONObject innerJsonObject1 = jsonObject.getJSONObject(key);
                        Iterator<String> innerIterator = innerJsonObject1.keys();
                        while (innerIterator.hasNext()) {
                            String innerkey = innerIterator.next();
                            if (innerkey.equals("ID")) {
                                reservationSpaceClass.setSpace_id(innerJsonObject1.getString("ID"));
                            }
                            if (innerkey.equals("Hierarchy Path")) {
                                reservationSpaceClass.setSpace_hierarchyPath(innerJsonObject1.getString("Hierarchy Path"));
                            }
                            if (innerkey.equals("Name")) {
                                reservationSpaceClass.setSpace_name(innerJsonObject1.getString("Name"));
                            }
                            if (innerkey.equals("Parent Building")) {
                                reservationSpaceClass.setSpace_parentBuilding(innerJsonObject1.getString("Parent Building"));
                            }
                            if (innerkey.equals("Reservable")) {
                                reservationSpaceClass.setSpace_reservable(innerJsonObject1.getString("Reservable"));
                            }
                           /* if(innerkey.equals("Status")){
                                reservationSpaceClass.setSpace_status(innerJsonObject1.getString("Status"));
                            }*/
                            if (innerkey.equals("Room Type")) {
                                reservationSpaceClass.setSpace_room_type(innerJsonObject1.getString("Room Type"));
                            }
                            if (innerkey.equals("Layout Type")) {
                                reservationSpaceClass.setSpace_layout_type(innerJsonObject1.getString("Layout Type"));
                            }
                            if (innerkey.equals("Reserve Name")) {
                                reservationSpaceClass.setSpace_reservation_name(innerJsonObject1.getString("Reserve Name"));
                            }
                            if (innerkey.equals("Reservation Class Name")) {
                                reservationSpaceClass.setSpace_reservation_class_name(innerJsonObject1.getString("Reservation Class Name"));
                            }
                            if (innerkey.equals("Capacity")) {
                                reservationSpaceClass.setSpace_capacity(innerJsonObject1.getString("Capacity"));
                            }
                            if (innerkey.equals("Record ID")) {
                                reservationSpaceClass.setSpace_record_id(innerJsonObject1.getString("Record ID"));
                            }


                        }
                        reservationSpaceClassesArrayList.add(reservationSpaceClass);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dh.savereservertationSpaceData(reservationSpaceClassesArrayList);
            }


        }


    }

    public class getEquipmentData extends AsyncTask<Void, Integer, String> {
        String username, password;

        public getEquipmentData() {
            String userdetail[] = dh.getuserDetail();
            this.username = userdetail[0];
            this.password = userdetail[1];
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pDialog = ProgressDialog.show(MakeReservation.this, "", "");
            pDialog.setContentView(R.layout.progresslayout);
            // progressBar = (ProgressBar)
            // pDialog.findViewById(R.id.progressBar1);
            TextView titletextView = (TextView) pDialog
                    .findViewById(R.id.title);
            TextView messageTextView = (TextView) pDialog
                    .findViewById(R.id.message);
            titletextView.setText("Please Wait...");
            messageTextView.setText("Equipments are being downloaded...");
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            userFunction = new NewUserFunction();
            String equipmentesponse = "";

            HashMap<String, String> postDataParams = new HashMap<>();
            postDataParams.put("name", username);
            postDataParams.put("pass", password);
            postDataParams.put("building", locationText);


            try {
                equipmentesponse = userFunction.getEquipmentData(postDataParams);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return equipmentesponse;
        }


        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            pDialog.dismiss();
            ArrayList<MyEquipmentClass> reservationEquipmentClassesArrayList = new ArrayList<>();

            if (!response.contains("no data")) {
                dh.emptyTable(DatabaseHandler.MY_EQUIPMENT_TABLE, "blank", "blank");


                try {

                    JSONObject jsonObject = new JSONObject(response);
                    Iterator<String> stringIterator = jsonObject.keys();
                    while (stringIterator.hasNext()) {
                        MyEquipmentClass myEquipmentClass = new MyEquipmentClass();
                        String key = stringIterator.next();
                        JSONObject jsonObject1 = jsonObject.getJSONObject(key);
                        Iterator<String> stringIterator1 = jsonObject1.keys();
                        while (stringIterator1.hasNext()) {
                            String key1 = stringIterator1.next();
                            if (key1.contains("Name")) {
                                myEquipmentClass.setName(jsonObject1.getString(key1));
                            }
                            if (key1.contains("status")) {
                                myEquipmentClass.setStatus(jsonObject1.getString(key1));
                            }
                            if (key1.contains("Reservation Number")) {
                                myEquipmentClass.setReservation_number(jsonObject1.getString(key1));
                            }
                            if (key1.contains("System Record ID")) {
                                myEquipmentClass.setSystem_record_id(jsonObject1.getString(key1));
                            }
                            if (key1.contains("Building")) {
                                myEquipmentClass.setBuilding_name(jsonObject1.getString(key1));
                            }
                        }
                        reservationEquipmentClassesArrayList.add(myEquipmentClass);
                    }
                    dh.savereservertationEquipmentData(reservationEquipmentClassesArrayList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
