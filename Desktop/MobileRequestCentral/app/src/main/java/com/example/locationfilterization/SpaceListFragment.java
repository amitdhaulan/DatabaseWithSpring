package com.example.locationfilterization;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mobilerequestcentral.R;

import java.util.ArrayList;

import custom.dialog.CustomDialog;
import database.helper.DatabaseHandler;
import my.classes.MyLocationClass;

public class SpaceListFragment extends Activity {
    Bundle bundle = null;
    DatabaseHandler dh;
    String floor;
    String property;
    String building;
    ArrayList<MyLocationClass> spaceArrayList;
    ListView lv;
    SpaceListCustomAdapter adapter;
    TextView txtCounter;
    MyLocationClass objClass;
    CustomDialog cd;
    OnItemClickListener click;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        bundle = getIntent().getExtras();
        spaceArrayList = new ArrayList<MyLocationClass>();
        if (bundle != null) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.spacelist);

            cd = new CustomDialog(this);
            txtCounter = (TextView) findViewById(R.id.txtCounter);
            lv = (ListView) findViewById(R.id.space_listview);
            dh = new DatabaseHandler(this);

            floor = bundle.getString("floor");
            property = bundle.getString("property");
            building = bundle.getString("building");

//			spaceArrayList = dh.getSpace(floor, property, building);
            txtCounter.setText("Total Record : " + spaceArrayList.size());
            adapter = new SpaceListCustomAdapter(this, spaceArrayList);
            lv.setAdapter(adapter);

            click = new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int arg2, long arg3) {

                    System.out
                            .println("<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>");
                    // arg1.setSelected(true);
                    objClass = new MyLocationClass();
                    objClass = spaceArrayList.get(arg2);
                    int rowcount = dh.getRowCount(
//							DatabaseHandler.TABLE_RESERVED_SPACE,
//							objClass.get_spaceId());

                            DatabaseHandler.TABLE_RESERVED_SPACE,
//							objClass.getLocationId());
                            objClass.getPath());
                    if (rowcount <= 0) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(MyLocationClass.EXTRA, objClass);
                        bundle.putString("floor", floor);
                        bundle.putString("property", property);
                        bundle.putString("building", building);

                        Intent intent = new Intent(SpaceListFragment.this,
                                UpdateSpaceListFragment.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.new_slide_left,
                                R.anim.hold);

                        // UpdateSpaceListFragment frag = new
                        // UpdateSpaceListFragment();
                        // frag.setArguments(bundle);
                        // FragmentTransaction transaction =
                        // getFragmentManager()
                        // .beginTransaction();
                        // transaction.replace(R.id.floor_space_right_fragment,
                        // frag);
                        // transaction.commit();
                    } else {
                        cd.showCustomDialog("Attention",
                                "This space is already reserved");
                    }

                }
            };
            System.out.println("clicklistner------------>" + click);
            lv.setOnItemClickListener(click);
        } else {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.spacelist);
        }
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(SpaceListFragment.this, FloorListFragment.class);
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(R.anim.new_slide_right, R.anim.hold);
        // super.onBackPressed();
    }
}
