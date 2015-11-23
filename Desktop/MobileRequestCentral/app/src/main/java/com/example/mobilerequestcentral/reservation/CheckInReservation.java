package com.example.mobilerequestcentral.reservation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.mobilerequestcentral.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import custom.dialog.CustomAdapter;
import my.classes.reservation.myReservationClass;

/**
 * Created by amitk on 10/1/2015.
 */
public class CheckInReservation extends Activity {

    TextView dateTextView, starttimeTextView, endtimeTextView, buildingTextView,
            floorTextView, roomTextView, /*reservationclassTextView,*/
            roomcapacityTextView, /*roomtypeTextView,*/
            adaavailabilityTextView, cateringavailabilityTextView, networkavailabiltiyTextView,
    /*inroomprojectorTextView, */whiteboardTextView, conferencephoneTextView, /*conferencephonenumberTextView,*/
            videoconferenceTextView/*, videoconferenceinfoTextView*/;


    Button backButton;
    CustomAdapter customAdapter;
    my.classes.reservation.myReservationClass myReservationClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.checkinreservationdetail);


        backButton = (Button) findViewById(R.id.checkinreservationdetail_button_back);
        dateTextView = (TextView) findViewById(R.id.checkinreservationdetail_date);
        starttimeTextView = (TextView) findViewById(R.id.checkinreservationdetail_starttime);
        endtimeTextView = (TextView) findViewById(R.id.checkinreservationdetail_endtime);
        buildingTextView = (TextView) findViewById(R.id.checkinreservationdetail_building);
        floorTextView = (TextView) findViewById(R.id.checkinreservationdetail_floor);
        roomTextView = (TextView) findViewById(R.id.checkinreservationdetail_space);
        /*reservationclassTextView = (TextView)findViewById(R.id.cancelreservationdetail_reservationclass);*/
        roomcapacityTextView = (TextView) findViewById(R.id.checkinreservationdetail_roomcapacity);
        /*roomtypeTextView = (TextView)findViewById(R.id.cancelreservationdetail_roomtype);*/
        adaavailabilityTextView = (TextView) findViewById(R.id.checkinreservationdetail_adaavailable);
        cateringavailabilityTextView = (TextView) findViewById(R.id.checkinreservationdetail_cateringavailable);
        networkavailabiltiyTextView = (TextView) findViewById(R.id.checkinreservationdetail_networkconnection);
        /*inroomprojectorTextView = (TextView)findViewById(R.id.cancelreservationdetail_inroomprojector);*/
        whiteboardTextView = (TextView) findViewById(R.id.checkinreservationdetail_whiteboard);
        conferencephoneTextView = (TextView) findViewById(R.id.checkinreservationdetail_conferencephone);
        /*conferencephonenumberTextView = (TextView)findViewById(R.id.cancelreservationdetail_conferencephonenumber);*/
        videoconferenceTextView = (TextView) findViewById(R.id.cancelreservationdetail_videoconference);
        /*videoconferenceinfoTextView = (TextView)findViewById(R.id.cancelreservationdetail_videoconferenceinfo);*/

        myReservationClass = new myReservationClass();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            myReservationClass = (myReservationClass) bundle.getSerializable("reservationClassObject");

        }

        Date startdate = new Date(Long.parseLong(myReservationClass.getStarttime()));
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String starttime = formatter.format(startdate);

        Date enddate = new Date(Long.parseLong(myReservationClass.getStarttime()));
        String endtime = formatter.format(enddate);


        dateTextView.setText(myReservationClass.getDate());
        starttimeTextView.setText(starttime);
        endtimeTextView.setText(starttime);
        buildingTextView.setText(myReservationClass.getBuildingname());
        floorTextView.setText(myReservationClass.getFloorname());
        roomTextView.setText(myReservationClass.getSpacename());

        /*reservationclassTextView.setText(myReservationClass.getReservationclass());
        roomcapacityTextView.setText(myReservationClass.getCapacity());
        roomtypeTextView.setText(myReservationClass.getRoomtype());
        adaavailabilityTextView.setText(myReservationClass.getAda_available());
        cateringavailabilityTextView.setText(myReservationClass.getCatering_available());
        networkavailabiltiyTextView.setText(myReservationClass.getNetwork_connection());
        inroomprojectorTextView.setText(myReservationClass.getIn_room_projector());
        whiteboardTextView.setText(myReservationClass.getWhiteboard());
        conferencephoneTextView.setText(myReservationClass.getConference_number());
        conferencephonenumberTextView.setText(myReservationClass.getConference_number());
        videoconferenceTextView.setText(myReservationClass.getVideo_conference());
        videoconferenceinfoTextView.setText(myReservationClass.getConference_number());*/

        /*Toast.makeText(getApplicationContext(), myReservationClass.getDate(), Toast.LENGTH_SHORT).show();*/


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckInReservation.this, CheckInReservationList.class);
                startActivity(intent);
                overridePendingTransition(R.anim.new_slide_right, R.anim.hold);

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CheckInReservation.this, CheckInReservationList.class);
        startActivity(intent);
        overridePendingTransition(R.anim.new_slide_right, R.anim.hold);
    }
}