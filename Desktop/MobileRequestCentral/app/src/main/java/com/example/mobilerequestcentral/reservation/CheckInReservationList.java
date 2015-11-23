package com.example.mobilerequestcentral.reservation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.applidium.shutterbug.FetchableImageView;
import com.example.mobilerequestcentral.R;
import com.example.mobilerequestcentral.requestcentral;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import database.helper.DatabaseHandler;
import my.classes.reservation.myReservationClass;
import user.function.NewUserFunction;

/**
 * Created by amitk on 10/1/2015.
 */
public class CheckInReservationList extends Activity implements View.OnClickListener {
    static Context context;
    static ArrayList<Integer> positionStringArrayList;
    static String hide = "";
    static Toast toast;
    static ArrayList<Boolean> positionArray;
    ListView reservationListView;
    TextView textView;
    /*HashMap<String, String> data;*/
    /*ArrayList<String> reservationArrayList;*/
    /*HashMap<String, String> stringHashMap;*/
    Button backButton, /*checkinButton,*/
            checkinButton;
    CustomAdapter adapter;
    Activity activity;
    DatabaseHandler dh;
    ArrayList<myReservationClass> myReservationClassesArrayList;
    ArrayList<String> checkedinArrayList;
    ProgressDialog pDialog;
    NewUserFunction userFunction;

    public static void showToast(String msg) {
        if (toast != null)
            toast.cancel();
        toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.checkinreservation);
        reservationListView = (ListView) findViewById(R.id.checkinreservation_listview);
        backButton = (Button) findViewById(R.id.checkinreservation_button_back);
        /*checkinButton = (Button) findViewById(R.id.reservation_button_checkin);*/
        checkinButton = (Button) findViewById(R.id.checkinreservation_button_cancel);
        textView = (TextView) findViewById(R.id.checkinreservation_nodata);

        activity = this;
        context = this;
        positionStringArrayList = new ArrayList<>();
        dh = new DatabaseHandler(getApplicationContext());


        checkinButton.setOnClickListener(this);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckInReservationList.this, requestcentral.class);
                startActivity(intent);
                overridePendingTransition(R.anim.new_slide_right,
                        R.anim.hold);
            }
        });


        /*****************************Data for my reservations++++++++++++++++++++
         */

        if (dh.getDemoReservationData().size() == 0) {
            new getReservationSpaces().execute();
        }

        myReservationClassesArrayList = dh.getDemoReservationData();
        checkedinArrayList = new ArrayList<>();
        for (int i = 0; i < myReservationClassesArrayList.size(); i++) {
            checkedinArrayList.add(myReservationClassesArrayList.get(i).getCheckedin());
        }

        Collections.sort(myReservationClassesArrayList);
        adapter = new CustomAdapter(activity, myReservationClassesArrayList);
        reservationListView.setAdapter(adapter);


        reservationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String checkedinstatus = myReservationClassesArrayList.get(position).getCheckedin();
                if (checkedinstatus.equals("checkedin")) {
                    showToast("This Reservation is already checked in!");
                }

            }
        });


    }

    @Override
    public void onClick(View v) {
        if (v == checkinButton) {

            if (!checkedinArrayList.contains("!checkedin")) {
                showToast("All the reservations are checked in!");
            } else {
                if (positionStringArrayList.size() == 0) {
                    showToast("Please make a selection!");
                } else {
                    try {
                        dh.checkinReservationRecord(myReservationClassesArrayList.get(positionStringArrayList.get(0)));
                        checkedinArrayList.set(positionStringArrayList.get(0), "checkedin");
                        positionStringArrayList.clear();
                        myReservationClassesArrayList = new ArrayList<>();
                        myReservationClassesArrayList = dh.getDemoReservationData();
                        adapter = new CustomAdapter(activity, myReservationClassesArrayList);
                        reservationListView.setAdapter(adapter);
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }
            }

        }
    }

    public long getRandomNumber() {
        long randomNumber = 0;
        Date date = new Date();
        randomNumber = date.getTime();
        return randomNumber;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CheckInReservationList.this, requestcentral.class);
        startActivity(intent);
        overridePendingTransition(R.anim.new_slide_right,
                R.anim.hold);
    }

    public static class CustomAdapter extends BaseAdapter {

        public Resources res;
        ArrayList<myReservationClass> myReservationClassArrayList;
        String prevDate = "";
        private Activity activity;
        private LayoutInflater inflater = null;


        public CustomAdapter(Activity a, ArrayList<myReservationClass> myReservationClassArrayList) {
            activity = a;
            this.myReservationClassArrayList = myReservationClassArrayList;
            inflater = (LayoutInflater) activity.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            positionArray = new ArrayList<Boolean>(myReservationClassArrayList.size());
            for (int i = 0; i < myReservationClassArrayList.size(); i++) {
                positionArray.add(false);
            }

        }

        public int getCount() {
            return myReservationClassArrayList.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, final View convertView, ViewGroup parent) {
            View vi = convertView;
            try {
                final ViewHolder holder;
                if (convertView == null) {
                    vi = inflater.inflate(R.layout.myreservations, null);

                    holder = new ViewHolder();

                    holder.header = (TextView) vi.findViewById(R.id.list_header);
                    holder.dateTextView = (TextView) vi.findViewById(R.id.date);
                    holder.starttimeTextView = (TextView) vi.findViewById(R.id.starttime);
                    holder.endtimeTextView = (TextView) vi.findViewById(R.id.endtime);
                    holder.buildingTextView = (TextView) vi.findViewById(R.id.building);
                    holder.floorTextView = (TextView) vi.findViewById(R.id.floor);
                    holder.spaceTextView = (TextView) vi.findViewById(R.id.space);
                    holder.checkBox = (CheckBox) vi.findViewById(R.id.reservationcheckbox);
                    holder.checkedinImageButton = (FetchableImageView) vi.findViewById(R.id.checkedintag);

                    holder.header.setTag(position);
                    vi.setTag(holder);
                } else {
                    holder = (ViewHolder) vi.getTag();
                    holder.checkBox.setOnCheckedChangeListener(null);
                    holder.checkedinImageButton.setVisibility(View.GONE);
                    holder.header.setVisibility(View.GONE);
                }

                holder.checkBox.setFocusable(false);
                holder.checkBox.setChecked(positionArray.get(position));

                Date startdate = new Date(Long.parseLong(myReservationClassArrayList.get(position).getStarttime()));
                DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                String starttime = formatter.format(startdate);

                Date enddate = new Date(Long.parseLong(myReservationClassArrayList.get(position).getStarttime()));
                String endtime = formatter.format(enddate);


                holder.header.setText(myReservationClassArrayList.get(position).getDate());
                holder.header.setOnClickListener(null);
                holder.dateTextView.setText(myReservationClassArrayList.get(position).getDate());
                holder.starttimeTextView.setText(starttime);
                holder.endtimeTextView.setText(endtime);
                holder.buildingTextView.setText(myReservationClassArrayList.get(position).getBuildingname());
                holder.floorTextView.setText(myReservationClassArrayList.get(position).getFloorname());
                holder.spaceTextView.setText(myReservationClassArrayList.get(position).getSpacename());

                if (myReservationClassArrayList.get(position).getCheckedin().equals("checkedin")) {
                    holder.checkedinImageButton.setVisibility(View.VISIBLE);
                    holder.checkBox.setVisibility(View.GONE);

                }
                if (position > 0) {
                    prevDate = myReservationClassArrayList.get(position - 1).getDate();
                }
                if (prevDate == "" || !prevDate.equals(myReservationClassArrayList.get(position).getDate())) {
                    holder.header.setVisibility(View.VISIBLE);
                } else if (prevDate != "" || prevDate.equals(myReservationClassArrayList.get(position).getDate())) {
                    holder.header.setVisibility(View.GONE);
                    if (position == 0)
                        holder.header.setVisibility(View.VISIBLE);
                }

                holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            System.out.println(position + "--- :)");
                            positionArray.set(position, true);
                            positionStringArrayList.add(position);

                        } else {
                            positionArray.set(position, false);
                            if (positionStringArrayList.contains(Integer.valueOf(position))) {
                                positionStringArrayList.remove(Integer.valueOf(position));
                            }
                        }

                    }
                });

                return vi;
            } catch (Exception e) {

            }
            return vi;
        }

        public static class ViewHolder {

            public TextView header;
            public TextView dateTextView;
            public TextView starttimeTextView;
            public TextView endtimeTextView;
            public TextView buildingTextView;
            public TextView floorTextView;
            public TextView spaceTextView;
            public CheckBox checkBox;
            public FetchableImageView checkedinImageButton;
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
            pDialog = ProgressDialog.show(CheckInReservationList.this, "", "");
            pDialog.setContentView(R.layout.progresslayout);
            // progressBar = (ProgressBar)
            // pDialog.findViewById(R.id.progressBar1);
            TextView titletextView = (TextView) pDialog
                    .findViewById(R.id.title);
            TextView messageTextView = (TextView) pDialog
                    .findViewById(R.id.message);
            titletextView.setText("Please Wait...");
            messageTextView.setText("Reservation are being downloaded...");
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            myReservationClassesArrayList = new ArrayList<>();
            userFunction = new NewUserFunction();
            String response = "";

            HashMap<String, String> postDataParams = new HashMap<>();
            postDataParams.put("name", username);
            postDataParams.put("pass", password);

            try {
                response = userFunction.getReservations(postDataParams);
            } catch (Exception e) {
                e.printStackTrace();
            }


            if (response.contains("Date") && response.contains("Reservation ID")) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Iterator<String> stringIterator = jsonObject.keys();
                    while (stringIterator.hasNext()) {
                        myReservationClass myReservationClass = new myReservationClass();
                        String key = stringIterator.next();
                        System.out.print("=============> : " + key);
                        JSONObject jsonObject1 = jsonObject.getJSONObject(key);
                        Iterator<String> stringIterator1 = jsonObject1.keys();
                        while (stringIterator1.hasNext()) {
                            String internalKey = stringIterator1.next();
                            if (internalKey.equals("Description")) {
                                myReservationClass.setDescription(jsonObject1.getString("Description"));
                            }
                            if (internalKey.equals("Room")) {
                                myReservationClass.setSpacename(jsonObject1.getString("Room"));
                            }
                            if (internalKey.equals("End Date/Time")) {
                                myReservationClass.setEndtime(jsonObject1.getString("End Date/Time"));
                            }
                            if (internalKey.equals("Reservation ID")) {
                                myReservationClass.setReservationId(jsonObject1.getString("Reservation ID"));
                            }
                            if (internalKey.equals("Building")) {
                                myReservationClass.setBuildingname(jsonObject1.getString("Building"));
                            }
                            if (internalKey.equals("Reservation Manager Type")) {
                                /*myReservationClass.setReservation_manager_type(jsonObject1.getString("Reservation Manager Type"));*/
                            }
                            if (internalKey.equals("Start Date/Time")) {
                                myReservationClass.setStarttime(jsonObject1.getString("Start Date/Time"));
                            }
                            if (internalKey.equals("Start Date")) {
                                myReservationClass.setDate(jsonObject1.getString("Start Date"));
                            }
                            if (internalKey.equals("Control Number")) {
                                /*myReservationClass.setControl_number(jsonObject1.getString("Control Number"));*/
                            }
                            if (internalKey.equals("Floor")) {
                                myReservationClass.setFloorname(jsonObject1.getString("Floor"));
                            }
                            if (internalKey.equals("City")) {
                                /*myReservationClass.setCity(jsonObject1.getString("City"));*/
                            }
                            if (internalKey.equals("Network Connection?")) {
                                /*myReservationClass.setNetwork_connection(jsonObject1.getString("Network Connection?"));*/
                            }
                            if (internalKey.equals("ADA Available?")) {
                                /*myReservationClass.setAda_available(jsonObject1.getString("ADA Available?"));*/
                            }
                            if (internalKey.equals("In-Room Projector?")) {
                                /*myReservationClass.setIn_room_projector(jsonObject1.getString("In-Room Projector?"));*/
                            }
                            if (internalKey.equals("Whiteboard?")) {
                                /*myReservationClass.setWhiteboard(jsonObject1.getString("Whiteboard?"));*/
                            }
                            if (internalKey.equals("Video Conference?")) {
                                /*myReservationClass.setVideo_conference(jsonObject1.getString("Video Conference?"));*/
                            }
                            if (internalKey.equals("Teleconference Phone?")) {
                                /*myReservationClass.setConference_number(jsonObject1.getString("Teleconference Phone?"));*/
                            }
                            if (internalKey.equals("Room Phone")) {
                                /*myReservationClass.setRoomtype(jsonObject1.getString("Room Phone"));*/
                            }
                            if (internalKey.equals("Catering Available?")) {
                                /*myReservationClass.setCatering_available(jsonObject1.getString("Catering Available?"));*/
                            }
                            if (internalKey.equals("Control Number")) {
                                /*myReservationClass.setControl_number(jsonObject1.getString("Control Number"));*/
                            }
                        }

                        myReservationClassesArrayList.add(myReservationClass);

                    }
                    dh.emptyTable(DatabaseHandler.MY_DEMORESERVATION_TABLE, "blank", "blank");
                    dh.saveDemoReservationData(myReservationClassesArrayList);


                } catch (Exception e) {

                }


            }

            return response;
        }


        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            pDialog.dismiss();

            myReservationClassesArrayList = dh.getDemoReservationData();
            adapter = new CustomAdapter(activity, myReservationClassesArrayList);
            reservationListView.setAdapter(adapter);

        }
    }
}
