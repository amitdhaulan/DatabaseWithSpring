package com.example.mobilerequestcentral.reservation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import java.util.Locale;

import database.helper.DatabaseHandler;
import my.classes.reservation.myReservationClass;
import user.function.NewUserFunction;

/**
 * Created by amitk on 8/7/2015.
 */
public class Reservation extends Activity implements View.OnClickListener {
    /*ListView reservationListView;*/
    static SwipeListView reservationListView;
    static CustomAdapter adapter;
    static Context context;
    static DatabaseHandler dh;
    static ArrayList<myReservationClass> myReservationClassesArrayList;
    static ArrayList<String> checkedinArrayList;
    /*HashMap<String, String> data;*/
    static ArrayList<Integer> positionStringArrayList;
    static String hide = "";
    static ArrayList<Boolean> positionArray;
    static Toast toast;
    /*ArrayList<String> reservationArrayList;*/
    /*HashMap<String, String> stringHashMap;*/
    Button backButton, checkinButton, cancelButton;
    Activity activity;
    ProgressDialog pDialog;
    NewUserFunction userFunction;
    Bundle bundle;
    String todayString;

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
        setContentView(R.layout.reservationlist);
        reservationListView = (SwipeListView) findViewById(R.id.example_swipe_lv_list);
        backButton = (Button) findViewById(R.id.reservation_button_back);
        checkinButton = (Button) findViewById(R.id.reservation_button_checkin);
        cancelButton = (Button) findViewById(R.id.reservation_button_cancel);

        activity = this;
        context = this;
        positionStringArrayList = new ArrayList<>();
        dh = new DatabaseHandler(getApplicationContext());


        bundle = getIntent().getExtras();
        todayString = bundle.getString("today");


        cancelButton.setOnClickListener(this);
        checkinButton.setOnClickListener(this);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Reservation.this, requestcentral.class);
                startActivity(intent);
                overridePendingTransition(R.anim.new_slide_right,
                        R.anim.hold);
            }
        });


        if (bundle != null && todayString.equals("true")) {
            myReservationClassesArrayList = dh.getDemoReservationDataForParticularDay("09/30/2015");
            checkedinArrayList = new ArrayList<>();
            for (int i = 0; i < myReservationClassesArrayList.size(); i++) {
                checkedinArrayList.add(myReservationClassesArrayList.get(i).getCheckedin());
            }
        } else if (bundle != null && todayString.equals("false")) {
            myReservationClassesArrayList = dh.getDemoReservationData();
            checkedinArrayList = new ArrayList<>();
            for (int i = 0; i < myReservationClassesArrayList.size(); i++) {
                checkedinArrayList.add(myReservationClassesArrayList.get(i).getCheckedin());
            }
        }

        Collections.sort(myReservationClassesArrayList);

        adapter = new CustomAdapter(activity, myReservationClassesArrayList);


        reservationListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*showToast("Hello");*/

                Intent intent = new Intent(Reservation.this, ReservationDetail.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("reservationClassObject", myReservationClassesArrayList.get(position));
                bundle.putString("today", todayString);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.new_slide_left,
                        R.anim.hold);
            }
        });

        //Listview Swipe Listener
        reservationListView.setSwipeListViewListener(new BaseSwipeListViewListener() {
            @Override
            public void onOpened(int position, boolean toRight) {
            }

            @Override
            public void onClosed(int position, boolean fromRight) {
            }

            @Override
            public void onListChanged() {
            }

            @Override
            public void onMove(int position, float x) {
            }

            @Override
            public void onStartOpen(int position, int action, boolean right) {
                Log.d("swipe", String.format("onStartOpen %d - action %d", position, action));
            }

            @Override
            public void onStartClose(int position, boolean right) {
                Log.d("swipe", String.format("onStartClose %d", position));
            }

            @Override
            public void onClickFrontView(int position) {
                Log.d("swipe", String.format("onClickFrontView %d", position));
                reservationListView.openAnimate(position); //when you touch front view it will open

            }

            @Override
            public void onClickBackView(int position) {
                Log.d("swipe", String.format("onClickBackView %d", position));
                reservationListView.closeAnimate(position);//when you touch back view it will close
            }

            @Override
            public void onDismiss(int[] reverseSortedPositions) {

            }

        });
        reservationListView.setSwipeMode(SwipeListView.SWIPE_MODE_DEFAULT); // there are five swiping modes
        reservationListView.setSwipeActionLeft(SwipeListView.SWIPE_ACTION_REVEAL); //there are four swipe actions
        reservationListView.setSwipeActionRight(SwipeListView.SWIPE_ACTION_REVEAL);
        reservationListView.setOffsetLeft(convertDpToPixel(0f)); // left side offset
        reservationListView.setOffsetRight(convertDpToPixel(0f)); // right side offset
        reservationListView.setAnimationTime(50); // Animation time
        reservationListView.setSwipeOpenOnLongPress(true);

        reservationListView.setAdapter(adapter);

        //List view scroll listener
        /*reservationListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                System.out.println("scrollState: " + scrollState + "<==============\n");
                showToast("scrollState: " + scrollState + "<==============\n");
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });*/
    }

    public int convertDpToPixel(float dp) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }

    @Override
    public void onClick(View v) {
        if (v == cancelButton) {

            if (!checkedinArrayList.contains("!checkedin")) {
                showToast("No Reservation to cancel!");
            } else {
                if (positionStringArrayList.size() == 0) {
                    showToast("Please make a selection!");
                } else {
                    try {
                        dh.deleteReservationRecord(myReservationClassesArrayList.get(positionStringArrayList.get(0)));
                        showToast("Reservation cancelled!");
                        myReservationClassesArrayList = new ArrayList<>();
                        myReservationClassesArrayList = dh.getDemoReservationData();
                        adapter = new CustomAdapter(activity, myReservationClassesArrayList);
                        reservationListView.setAdapter(adapter);
                        positionStringArrayList = new ArrayList<>();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        if (v == checkinButton) {

            if (!checkedinArrayList.contains("!checkedin")) {
                showToast("No Reservation to check in!");
            } else {
                if (positionStringArrayList.size() == 0) {
                    showToast("Please make a selection!");
                } else {
                    try {
                        dh.checkinReservationRecord(myReservationClassesArrayList.get(positionStringArrayList.get(0)));
                        showToast("Reservation checked in!");
                        myReservationClassesArrayList = new ArrayList<>();
                        myReservationClassesArrayList = dh.getDemoReservationData();
                        adapter = new CustomAdapter(activity, myReservationClassesArrayList);
                        reservationListView.setAdapter(adapter);
                        positionStringArrayList = new ArrayList<>();
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
        Intent intent = new Intent(Reservation.this, requestcentral.class);
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

        public View getView(final int position, View convertView, final ViewGroup parent) {

            View vi = convertView;

            try {
                final ViewHolder holder;
                inflater = activity.getLayoutInflater();
                if (convertView == null) {
                    LayoutInflater inflater = (LayoutInflater) activity
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    holder = new ViewHolder();

                    vi = inflater.inflate(R.layout.myreservations, null);
                    holder.header = (TextView) vi.findViewById(R.id.list_header);

                    vi.setTag(holder);

                } else {
                    holder = (ViewHolder) vi.getTag();
                    holder.checkBox.setOnCheckedChangeListener(null);
                    holder.checkedinImageButton.setVisibility(View.GONE);
                }


                holder.dateTextView = (TextView) vi.findViewById(R.id.date);
                holder.starttimeTextView = (TextView) vi.findViewById(R.id.starttime);
                holder.endtimeTextView = (TextView) vi.findViewById(R.id.endtime);
                holder.buildingTextView = (TextView) vi.findViewById(R.id.building);
                holder.floorTextView = (TextView) vi.findViewById(R.id.floor);
                holder.spaceTextView = (TextView) vi.findViewById(R.id.space);
                holder.checkBox = (CheckBox) vi.findViewById(R.id.reservationcheckbox);
                holder.checkedinImageButton = (FetchableImageView) vi.findViewById(R.id.checkedintag);
                /*holder.checkedinButton =(Button)vi.findViewById(R.id.swipe_button1);*/
                holder.cancelButton = (Button) vi.findViewById(R.id.swipe_button2);

                /*holder.checkedinButton.setTag(position);*/
                holder.cancelButton.setTag(position);

                holder.checkBox.setFocusable(false);
                holder.checkBox.setChecked(positionArray.get(position));

                Date startdate = new Date(Long.parseLong(myReservationClassArrayList.get(position).getStarttime()));
                DateFormat formatter = new SimpleDateFormat("HH:mm:ss", Locale.CANADA);
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
                } else if (!myReservationClassArrayList.get(position).getCheckedin().equals("checkedin")) {
                    holder.checkedinImageButton.setVisibility(View.GONE);
                    holder.checkBox.setVisibility(View.VISIBLE);
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

                /*final String buttonText1;*/
                /*buttonText1 = holder.checkedinButton.getText().toString();*/

                final String buttonText2;
                buttonText2 = holder.cancelButton.getText().toString();

               /* holder.checkedinButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        showToast(buttonText1 + " " + holder.checkedinButton.getTag());
                        //Do checked in related work here.........
                    }
                });*/

                holder.cancelButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        showToast(buttonText2 + " " + holder.cancelButton.getTag());
                        //cancelled related work
                        if (!checkedinArrayList.contains("!checkedin")) {
                            showToast("No Reservation to cancel!");
                        } else {
                            try {
                                dh.deleteReservationRecord(myReservationClassesArrayList.get(position));
                                showToast("Reservation Cancelled!");
                                myReservationClassesArrayList = new ArrayList<>();
                                myReservationClassesArrayList = dh.getDemoReservationData();
                                adapter = new CustomAdapter(activity, myReservationClassesArrayList);
                                reservationListView.setAdapter(adapter);
                                positionStringArrayList = new ArrayList<>();
                            } catch (Exception e) {
                                e.printStackTrace();
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
            FetchableImageView checkedinImageButton;
            /*Button checkedinButton;*/
            Button cancelButton;

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
            pDialog = ProgressDialog.show(Reservation.this, "", "");
            pDialog.setContentView(R.layout.progresslayout);
            // progressBar = (ProgressBar)
            // pDialog.findViewById(R.id.progressBar1);
            TextView titletextView = (TextView) pDialog
                    .findViewById(R.id.title);
            TextView messageTextView = (TextView) pDialog
                    .findViewById(R.id.message);
            titletextView.setText("Please Wait...");
            messageTextView.setText("Reservations are being downloaded...");
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

                            //can use this too, every time instead of static keys :-)
                            /*myReservationClass.setBlahBlah(jsonObject1.getString(internalKey));*/

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
                                myReservationClass.setReservation_manager_type(jsonObject1.getString("Reservation Manager Type"));
                            }
                            if (internalKey.equals("Start Date/Time")) {
                                myReservationClass.setStarttime(jsonObject1.getString("Start Date/Time"));
                            }
                            if (internalKey.equals("Start Date")) {
                                myReservationClass.setDate(jsonObject1.getString("Start Date"));
                            }
                            if (internalKey.equals("Control Number")) {
                                myReservationClass.setControl_number(jsonObject1.getString("Control Number"));
                            }
                            if (internalKey.equals("Floor")) {
                                myReservationClass.setFloorname(jsonObject1.getString("Floor"));
                            }
                            if (internalKey.equals("City")) {
                                myReservationClass.setCity(jsonObject1.getString("City"));
                            }
                            if (internalKey.equals("Network Connection?")) {
                                myReservationClass.setNetwork_connection(jsonObject1.getString("Network Connection?"));
                            }
                            if (internalKey.equals("ADA Available?")) {
                                myReservationClass.setAda_available(jsonObject1.getString("ADA Available?"));
                            }
                            if (internalKey.equals("In-Room Projector?")) {
                                myReservationClass.setIn_room_projector(jsonObject1.getString("In-Room Projector?"));
                            }
                            if (internalKey.equals("Whiteboard?")) {
                                myReservationClass.setWhiteboard(jsonObject1.getString("Whiteboard?"));
                            }
                            if (internalKey.equals("Video Conference?")) {
                                myReservationClass.setVideo_conference(jsonObject1.getString("Video Conference?"));
                            }
                            if (internalKey.equals("Teleconference Phone?")) {
                                myReservationClass.setTeleconference_phone(jsonObject1.getString("Teleconference Phone?"));
                            }
                            if (internalKey.equals("Room Phone")) {
                                myReservationClass.setRoomtype(jsonObject1.getString("Room Phone"));
                            }
                            if (internalKey.equals("Catering Available?")) {
                                myReservationClass.setCatering_available(jsonObject1.getString("Catering Available?"));
                            }
                            if (internalKey.equals("Control Number")) {
                                myReservationClass.setControl_number(jsonObject1.getString("Control Number"));
                                myReservationClass.setCheckedin("!checkedin");
                            }
                        }
                        myReservationClassesArrayList.add(myReservationClass);
                    }
                    dh.emptyTable(DatabaseHandler.MY_DEMORESERVATION_TABLE, "blank", "blank");
                    dh.saveDemoReservationData(myReservationClassesArrayList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            pDialog.dismiss();

            if (bundle != null)
                myReservationClassesArrayList = dh.getDemoReservationDataForParticularDay("09/30/2015");
            else
                myReservationClassesArrayList = dh.getDemoReservationData();


            adapter = new CustomAdapter(activity, myReservationClassesArrayList);
            reservationListView.setAdapter(adapter);

        }
    }
}