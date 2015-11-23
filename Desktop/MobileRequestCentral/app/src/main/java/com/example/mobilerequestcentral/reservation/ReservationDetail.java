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
 * Created by amitk on 8/7/2015.
 */
public class ReservationDetail extends Activity {

    TextView dateTextView, starttimeTextView, endtimeTextView, buildingTextView,
            floorTextView, roomTextView, /*reservationclassTextView,*/
            roomcapacityTextView, /*roomtypeTextView,*/
            adaavailabilityTextView, cateringavailabilityTextView, networkavailabiltiyTextView,
    /*inroomprojectorTextView, */whiteboardTextView/*, conferencephoneTextView*//*, conferencephonenumberTextView*/,
            videoconferenceTextView/*, videoconferenceinfoTextView*/;


    Button backButton;
    CustomAdapter customAdapter;
    myReservationClass myReservationClass;
    Bundle bundle;
    String todayString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.myreservationdetail);

        bundle = getIntent().getExtras();
        todayString = bundle.getString("today");

        dateTextView = (TextView) findViewById(R.id.reservationdetail_date);
        starttimeTextView = (TextView) findViewById(R.id.reservationdetail_starttime);
        endtimeTextView = (TextView) findViewById(R.id.reservationdetail_endtime);
        buildingTextView = (TextView) findViewById(R.id.reservationdetail_building);
        floorTextView = (TextView) findViewById(R.id.reservationdetail_floor);
        roomTextView = (TextView) findViewById(R.id.reservationdetail_space);
        /*reservationclassTextView = (TextView)findViewById(R.id.reservationdetail_reservationclass);*/
        roomcapacityTextView = (TextView) findViewById(R.id.reservationdetail_roomcapacity);
        /*roomtypeTextView = (TextView)findViewById(R.id.reservationdetail_roomtype);*/
        adaavailabilityTextView = (TextView) findViewById(R.id.reservationdetail_adaavailable);
        cateringavailabilityTextView = (TextView) findViewById(R.id.reservationdetail_cateringavailable);
        networkavailabiltiyTextView = (TextView) findViewById(R.id.reservationdetail_networkconnection);
        /*inroomprojectorTextView = (TextView)findViewById(R.id.reservationdetail_inroomprojector);*/
        whiteboardTextView = (TextView) findViewById(R.id.reservationdetail_whiteboard);
        /*conferencephoneTextView = (TextView)findViewById(R.id.reservationdetail_conferencephone);*/
        /*conferencephonenumberTextView = (TextView)findViewById(R.id.reservationdetail_conferencephonenumber);*/
        videoconferenceTextView = (TextView) findViewById(R.id.reservationdetail_videoconference);
        /*videoconferenceinfoTextView = (TextView)findViewById(R.id.reservationdetail_videoconferenceinfo);*/

        myReservationClass = new myReservationClass();

/*        bundle = getIntent().getExtras();*/

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
        endtimeTextView.setText(endtime);
        buildingTextView.setText(myReservationClass.getBuildingname());
        floorTextView.setText(myReservationClass.getFloorname());
        roomTextView.setText(myReservationClass.getSpacename());

        /*reservationclassTextView.setText(myReservationClass.getReservationclass());*/
        roomcapacityTextView.setText(myReservationClass.getCapacity());
        /*roomtypeTextView.setText(myReservationClass.getRoomtype());*/
        adaavailabilityTextView.setText(myReservationClass.getAda_available());
        cateringavailabilityTextView.setText(myReservationClass.getCatering_available());
        networkavailabiltiyTextView.setText(myReservationClass.getNetwork_connection());
        /*inroomprojectorTextView.setText(myReservationClass.getIn_room_projector());*/
        whiteboardTextView.setText(myReservationClass.getWhiteboard());
        /*conferencephoneTextView.setText(myReservationClass.getTeleconference_phone());*/
        /*conferencephonenumberTextView.setText(myReservationClass.getConference_number());*/
        videoconferenceTextView.setText(myReservationClass.getVideo_conference());
        /*videoconferenceinfoTextView.setText(myReservationClass.getConference_number());*/

        /*Toast.makeText(getApplicationContext(), myReservationClass.getDate(), Toast.LENGTH_SHORT).show();*/


        backButton = (Button) findViewById(R.id.reservationdetail_button_back);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReservationDetail.this, Reservation.class);

                Bundle bundle = new Bundle();
                bundle.putString("today", todayString);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.new_slide_right, R.anim.hold);

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ReservationDetail.this, Reservation.class);

        Bundle bundle = new Bundle();
        bundle.putString("today", todayString);
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(R.anim.new_slide_right, R.anim.hold);
    }
}
