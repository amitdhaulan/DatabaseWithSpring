package com.example.mobilerequestcentral;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

public class FloorFragment extends Fragment {
    Bundle bundle = null;
    ListView lv;
    DatabaseHandler dh;
    ArrayList<String> floorArrayList;
    CustomAdapter adapter;
    //	String property;
    String hierarchyPath = "", floorpath = "";
    String building;
    ProgressDialog pDialog;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        bundle = getArguments();

        View v = inflater.inflate(R.layout.floor_fragment, null);
        if (bundle != null) {
            lv = (ListView) v.findViewById(R.id.floor_listview);
            dh = new DatabaseHandler(getActivity());

//			property = bundle.getString("property");
            building = bundle.getString("building");

            hierarchyPath = dh.getHierarchyPathForFloor(building);

            if (dh.getFloor(building).size() == 0 && dh.getFloorAvailability(building).size() == 0) {
                new getFloors().execute();
            } else {
                floorArrayList = new ArrayList<String>();
                floorArrayList = dh.getFloor(building);

                if (floorArrayList.size() != 0) {
                    if (floorArrayList.size() != 0 && !floorArrayList.toString().contains("Invalidate User")) {
                        System.out.println("In Else table has values");
                        adapter = new CustomAdapter(floorArrayList, getActivity());
                        System.out.println("Floor Array List ==>>>>>>>> " + floorArrayList);
                        lv.setAdapter(adapter);
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putString("Value", RequestCentralForm.requestValue);
                        bundle.putString("building", building);

                        Intent intent = new Intent(getActivity(),
                                RequestCentralForm.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        getActivity().finish();
                        getActivity().overridePendingTransition(
                                R.anim.new_slide_right_dialog, R.anim.hold);
                    }
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("Value", RequestCentralForm.requestValue);
                    bundle.putString("building", building);

                    Intent intent = new Intent(getActivity(),
                            RequestCentralForm.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    getActivity().finish();
                    getActivity().overridePendingTransition(
                            R.anim.new_slide_right_dialog, R.anim.hold);
                }

            }

            lv.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    arg1.setSelected(true);
                    Bundle bundle = new Bundle();
                    bundle.putString("Value", RequestCentralForm.requestValue);
                    bundle.putString("floor", floorArrayList.get(arg2));
                    bundle.putString("building", building);
                    bundle.putString("hierarchypath", hierarchyPath);
                    bundle.putString("floorpath", floorpath);

                    SpaceFragment frag = new SpaceFragment();
                    frag.setArguments(bundle);

                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.addToBackStack(null);


                    transaction.replace(R.id.space_fragment, frag);
                    transaction.commit();
                }
            });

        }
        return v;
    }

    public class getFloors extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pDialog = ProgressDialog.show(getActivity(), "", "");
            pDialog.setContentView(R.layout.progresslayout);
            // progressBar = (ProgressBar)
            // pDialog.findViewById(R.id.progressBar1);
            TextView titletextView = (TextView) pDialog
                    .findViewById(R.id.title);
            TextView messageTextView = (TextView) pDialog
                    .findViewById(R.id.message);
            titletextView.setText("Please Wait...");
            messageTextView.setText("Floors are being downloaded...");
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            String response = "";
            String[] userdetail = dh.getuserDetail();

			/*ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
            param.add(new BasicNameValuePair("name", userdetail[0]));
			param.add(new BasicNameValuePair("pass", userdetail[1]));
            param.add(new BasicNameValuePair("path", hierarchyPath));*/

            HashMap<String, String> param = new HashMap<>();
            param.put("name", userdetail[0]);
            param.put("pass", userdetail[1]);
            param.put("path", hierarchyPath);

            NewUserFunction userFunction = new NewUserFunction();

            try {
                response = userFunction.getFloorsData(param);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            System.out.print(response);
            if (!response.contains("floor not available")) {
                ArrayList<MyLocationClass> myLocationClassArrayList = new ArrayList<MyLocationClass>();
                DatabaseHandler dh = new DatabaseHandler(getActivity());
                dh.emptyTable(DatabaseHandler.MY_LOCATION_TABLE, DatabaseHandler.KEY_LOCATION_NAME, building);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Iterator<String> stringIterator = jsonObject.keys();
                    while (stringIterator.hasNext()) {
                        MyLocationClass myLocationClassObj = new MyLocationClass();
                        String key = stringIterator.next();

                        myLocationClassObj.setPath(hierarchyPath);
                        myLocationClassObj.setLocationName(building);
                        myLocationClassObj.setFloorpath(key);
                        floorpath = myLocationClassObj.getFloorpath();
                        myLocationClassObj.setDefaultLocation(MainActivity.default_location);
                        myLocationClassObj.setFloor(jsonObject.getString(key));
                        myLocationClassArrayList.add(myLocationClassObj);
                    }
                    dh.updateLocationWithFloor(myLocationClassArrayList);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                floorArrayList = new ArrayList<String>();
                floorArrayList = dh.getFloor(building);
                adapter = new CustomAdapter(floorArrayList, getActivity());
                System.out.println("Floor Array List ==>>>>>>>> " + floorArrayList);
                lv.setAdapter(adapter);
                pDialog.dismiss();
            } else {
                dh.updateLocationWithFloorAvailability(building);
                CustomDialog cDialog = new CustomDialog(getActivity());
                pDialog.dismiss();

				/*cDialog.showCustomDialog("Attention!",
						"Floors are not available for this location!");
                */
                Bundle bundle = new Bundle();
                bundle.putString("Value", RequestCentralForm.requestValue);
                bundle.putString("building", building);

                Intent intent = new Intent(getActivity(),
                        RequestCentralForm.class);
                intent.putExtras(bundle);
                startActivity(intent);
                getActivity().finish();
                getActivity().overridePendingTransition(
                        R.anim.new_slide_right_dialog, R.anim.hold);
            }
        }
    }
}
