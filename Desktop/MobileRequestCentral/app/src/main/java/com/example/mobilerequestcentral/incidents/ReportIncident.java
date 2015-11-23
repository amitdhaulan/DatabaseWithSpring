package com.example.mobilerequestcentral.incidents;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilerequestcentral.R;
import com.example.mobilerequestcentral.requestcentral;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import custom.dialog.CustomDialog;

/**
 * Created by amitk on 10/27/2015.
 */
public class ReportIncident extends Activity {
    Spinner buildingSpinner, floorSpinner, roomSpinner, organizaionSpinner, reportingSpinner, serviceRequestSpinner, typeOfItemSpinner;
    EditText additionalocationEditText, descriptionOfItemEditText, descriptionOfRequestEditText;
    TextView dateTimeTextView, titleTextView;
    CustomDialog cDialog;
    Toast toast;
    ArrayList<String> buildingSpinnerArray, floorSpinnerArray, roomSpinnerArray, organizationSpinnerArray, reportingSpinnerArray, serviceRequestSpinnerArray, typeOfItemSpinnerArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.incident);

        Bundle bundle = getIntent().getExtras();

        cDialog = new CustomDialog(ReportIncident.this);
        buildingSpinner = (Spinner) findViewById(R.id.incident_building_spn);
        floorSpinner = (Spinner) findViewById(R.id.incident_floor_spn);
        roomSpinner = (Spinner) findViewById(R.id.incident_room_spn);
        organizaionSpinner = (Spinner) findViewById(R.id.incident_organization_spn);
        reportingSpinner = (Spinner) findViewById(R.id.incident_report_spn);
        serviceRequestSpinner = (Spinner) findViewById(R.id.incident_servicereq_spn);

        dateTimeTextView = (TextView) findViewById(R.id.incident_Incident_date_time_txt);
        titleTextView = (TextView) findViewById(R.id.incident_txt_title);

        additionalocationEditText = (EditText) findViewById(R.id.incident_additional_location_edt);
        typeOfItemSpinner = (Spinner) findViewById(R.id.incident_typeOfItem_spn);
        descriptionOfItemEditText = (EditText) findViewById(R.id.incident_desofitem_edt);
        descriptionOfRequestEditText = (EditText) findViewById(R.id.incident_description_edt);


        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.CANADA);
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        long dateTime = date.getTime();
        calendar.setTimeInMillis(dateTime);
        dateTimeTextView.setText(formatter.format(calendar.getTime()));

       /* String title = bundle.getString("title");
        titleTextView.setText(title);*/

        buildingSpinnerArray = new ArrayList<>();
        floorSpinnerArray = new ArrayList<>();
        roomSpinnerArray = new ArrayList<>();
        organizationSpinnerArray = new ArrayList<>();
        reportingSpinnerArray = new ArrayList<>();
        serviceRequestSpinnerArray = new ArrayList<>();
        typeOfItemSpinnerArray = new ArrayList<>();

        buildingSpinnerArray.add("Building One");
        buildingSpinnerArray.add("Building Two");
        buildingSpinnerArray.add("Building Three");
        buildingSpinnerArray.add("Building Four");
        buildingSpinnerArray.add("Building Five");

        floorSpinnerArray.add("Floor One");
        floorSpinnerArray.add("Floor Two");
        floorSpinnerArray.add("Floor Three");
        floorSpinnerArray.add("Floor Four");
        floorSpinnerArray.add("Floor Five");

        roomSpinnerArray.add("Room One");
        roomSpinnerArray.add("Room Two");
        roomSpinnerArray.add("Room Three");
        roomSpinnerArray.add("Room Four");
        roomSpinnerArray.add("Room Five");

        organizationSpinnerArray.add("Organization One");
        organizationSpinnerArray.add("Organization Two");
        organizationSpinnerArray.add("Organization Three");
        organizationSpinnerArray.add("Organization Four");
        organizationSpinnerArray.add("Organization Five");

        reportingSpinnerArray.add("Lost/Stolen");
        reportingSpinnerArray.add("Found");

        serviceRequestSpinnerArray.add("Provided Electronics");
        serviceRequestSpinnerArray.add("Clothing/Accessories");
        serviceRequestSpinnerArray.add("Jewellary");
        serviceRequestSpinnerArray.add("Money");
        serviceRequestSpinnerArray.add("Other");

        typeOfItemSpinnerArray.add("Jewellary");
        typeOfItemSpinnerArray.add("Money");
        typeOfItemSpinnerArray.add("Other");

        /*Building spinner*/
        ArrayAdapter<String> buildingSpinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, buildingSpinnerArray);
        buildingSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        buildingSpinner.setAdapter(buildingSpinnerArrayAdapter);


        /*Floor Spinner*/
        ArrayAdapter<String> floorSpinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, floorSpinnerArray);
        floorSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        floorSpinner.setAdapter(floorSpinnerArrayAdapter);

        /*Room Spinner*/
        ArrayAdapter<String> roomSpinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roomSpinnerArray);
        roomSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomSpinner.setAdapter(roomSpinnerArrayAdapter);


        /*Organization Spinner*/
        ArrayAdapter<String> organizationSpinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, organizationSpinnerArray);
        organizationSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        organizaionSpinner.setAdapter(organizationSpinnerArrayAdapter);

        /*Reporting Spinner*/
        ArrayAdapter<String> reportingSpinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, reportingSpinnerArray);
        reportingSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reportingSpinner.setAdapter(reportingSpinnerArrayAdapter);

        /*Service Request Spinner*/
        ArrayAdapter<String> serviceRequestSpinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, serviceRequestSpinnerArray);
        serviceRequestSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        serviceRequestSpinner.setAdapter(serviceRequestSpinnerArrayAdapter);

          /*Type of Item Spinner*/
        ArrayAdapter<String> typeOfItemSpinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, typeOfItemSpinnerArray);
        typeOfItemSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeOfItemSpinner.setAdapter(typeOfItemSpinnerArrayAdapter);
    }

    public void goback(View view) {
        Intent intent = new Intent(ReportIncident.this,
                requestcentral.class);
        startActivityForResult(intent, 0);
        overridePendingTransition(R.anim.new_slide_right, R.anim.hold);
    }

    public void submit(View view) {

        if (dateTimeTextView.getText().toString().length() != 0) {
            cDialog.showCustomDialog("Attention!",
                    "This is for demo only!");
        } else {
            showToast("Please enter date and time!");
        }

    }

    public void showToast(String msg) {
        if (toast != null)
            toast.cancel();
        toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ReportIncident.this,
                requestcentral.class);
        startActivityForResult(intent, 0);
        overridePendingTransition(R.anim.new_slide_right, R.anim.hold);
    }
}
