package com.example.mobilerequestcentral;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import custom.dialog.CustomAdapter;
import custom.dialog.CustomDialog;
import database.helper.DatabaseHandler;
import my.classes.MyLocationClass;
import user.function.NewUserFunction;

public class SpaceFragment extends Fragment {
    static String building;
    Bundle bundle = null;
    ListView lv;
    DatabaseHandler dh;
    ArrayList<String> spaceArrayList;
    CustomAdapter adapter;
    String floor;
    ProgressDialog pDialog;
    String hierarchyPathFloor = "", hierarchypath, floorpath;
    int spaceArrayPosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bundle = getArguments();
        spaceArrayList = new ArrayList<String>();

        View v = inflater.inflate(R.layout.space_fragment, null);
        if (bundle != null) {
            lv = (ListView) v.findViewById(R.id.space_listview);
            dh = new DatabaseHandler(getActivity());
            floor = bundle.getString("floor");
            building = bundle.getString("building");

            hierarchypath = bundle.getString("hierarchypath");
            floorpath = bundle.getString("floorpath");


            System.out.println("building------------------------>" + building);
            hierarchyPathFloor = dh.getHierarchyPathForSpace(floor);
            /*new getSpaces().execute();*/
            if (dh.getSpacenew(floor, building).size() == 0 && dh.getSpaceAvailability(floor, building).size() == 0) {
                new getSpaces().execute();
            } else if (dh.getSpacenew(floor, building).size() != 0) {
                spaceArrayList = dh.getSpacenew(floor, building);
                adapter = new CustomAdapter(spaceArrayList, getActivity());
                System.out.println("Space Array List ==>>>>>>>> " + spaceArrayList);
                lv.setAdapter(adapter);
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("Value", RequestCentralForm.requestValue);
                bundle.putString("building", building);
                bundle.putString("floor", floor);

                Intent intent = new Intent(getActivity(),
                        RequestCentralForm.class);
                intent.putExtras(bundle);
                startActivity(intent);
                getActivity().finish();
                getActivity().overridePendingTransition(
                        R.anim.new_slide_right_dialog, R.anim.hold);


            }

            lv.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int arg2, long arg3) {
                    spaceArrayPosition = arg2;
                    arg1.setSelected(true);
                    Bundle bundle = new Bundle();
                    bundle.putString("Value", RequestCentralForm.requestValue);
                    bundle.putString("space", spaceArrayList.get(arg2));
                    bundle.putString("building", building);
                    bundle.putString("floor", floor);

                    Intent intent = new Intent(getActivity(),
                            RequestCentralForm.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    getActivity().finish();
                    getActivity().overridePendingTransition(
                            R.anim.new_slide_right_dialog, R.anim.hold);


                }
            });

        }
        return v;
    }

    public class getSpaces extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pDialog = ProgressDialog.show(getActivity(), "", "");
            pDialog.setContentView(R.layout.progresslayout);
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
            String response = "";
            String[] userdetail;
            userdetail = dh.getuserDetail();

            HashMap<String, String> param = new HashMap<>();
            param.put("name", userdetail[0]);
            param.put("pass", userdetail[1]);
            param.put("path", hierarchyPathFloor);

            NewUserFunction userFunction = new NewUserFunction();

            try {
                response = userFunction.getSpaceData(param);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            System.out.print(response);

            if (!response.contains("space not available")) {
                DatabaseHandler dh = new DatabaseHandler(getActivity());
                dh.emptyTable(DatabaseHandler.MY_LOCATION_TABLE, DatabaseHandler.KEY_LOCATION_MYFLOORNAME, floor);
                /*dh.emptyTableWithCondition(DtabaseHandler.MY_LOCATION_TABLE,DatabaseHandler.KEY_LOCATION_MYFLOORNAME, floor);*/
                ArrayList<MyLocationClass> myLocationClassArrayList = new ArrayList<MyLocationClass>();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Iterator<String> stringIterator = jsonObject.keys();
                    while (stringIterator.hasNext()) {
                        MyLocationClass myLocationClassObj = new MyLocationClass();
                        String key = stringIterator.next();
                        // building
                        myLocationClassObj.setPath(hierarchypath);
                        myLocationClassObj.setLocationName(building);
                        myLocationClassObj.setFloorpath(floorpath);
                        myLocationClassObj.setFloor(floor);
                        myLocationClassObj.setDefaultLocation(MainActivity.default_location);
                        myLocationClassObj.setSpace(jsonObject.getString(key));

                        myLocationClassArrayList.add(myLocationClassObj);
                    }
                    dh.updateLocationWithSpace(myLocationClassArrayList);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                spaceArrayList = dh.getSpacenew(floor, building);
                pDialog.dismiss();
            } else {

                dh.updateLocationWithSpaceAvailability(floor);
                CustomDialog cDialog = new CustomDialog(getActivity());
                pDialog.dismiss();


                Bundle bundle = new Bundle();
                bundle.putString("Value", RequestCentralForm.requestValue);
                bundle.putString("building", building);
                bundle.putString("floor", floor);

                Intent intent = new Intent(getActivity(),
                        RequestCentralForm.class);
                intent.putExtras(bundle);
                startActivity(intent);
                getActivity().finish();
                getActivity().overridePendingTransition(
                        R.anim.new_slide_right_dialog, R.anim.hold);

            }
            spaceArrayList = dh.getSpacenew(floor, building);
            adapter = new CustomAdapter(spaceArrayList, getActivity());
            System.out.println("Space Array List ==>>>>>>>> " + spaceArrayList);
            lv.setAdapter(adapter);
            pDialog.dismiss();
        }
    }
}