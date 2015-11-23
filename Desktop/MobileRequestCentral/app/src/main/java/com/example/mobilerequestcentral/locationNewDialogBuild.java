package com.example.mobilerequestcentral;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;

import custom.dialog.CustomAdapter;
import database.helper.DatabaseHandler;

public class locationNewDialogBuild extends FragmentActivity {
    FrameLayout buildingFrameLayout, floorFrameLayout, spaceFrameLayout;
    ListView buildingListView, floorListView, spaceListView;
    DatabaseHandler dh;
    ArrayList<String> buildingArraylist;
    Activity activity;
    CustomAdapter adpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_building_floor_space_new);

        activity = this;

        buildingFrameLayout = (FrameLayout) findViewById(R.id.building_fragment);
        floorFrameLayout = (FrameLayout) findViewById(R.id.floor_fragment);
        spaceFrameLayout = (FrameLayout) findViewById(R.id.space_fragment);

        buildingListView = (ListView) findViewById(R.id.building_listview);
        floorListView = (ListView) findViewById(R.id.floor_listview);
        spaceListView = (ListView) findViewById(R.id.space_listview);

        dh = new DatabaseHandler(activity);
        buildingArraylist = new ArrayList<String>();
        buildingArraylist = dh.getBuilding();

        adpater = new CustomAdapter(buildingArraylist, activity);
        System.out.println("======task id=============="
                + buildingArraylist.size());
        buildingListView.setAdapter(adpater);

        buildingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v,
                                    int position, long arg3) {
                v.setSelected(true);
                Bundle bundle = new Bundle();
                bundle.putString("building", buildingArraylist.get(position));

                FloorFragment frag = new FloorFragment();
                SpaceFragment frag1 = new SpaceFragment();

                frag.setArguments(bundle);

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.space_fragment, frag1);
                transaction.replace(R.id.floor_fragment, frag);
                transaction.commit();
            }
        });

    }

    @Override
    public void onBackPressed() {

        overridePendingTransition(R.anim.new_slide_right, R.anim.hold);
        finish();
        overridePendingTransition(R.anim.new_slide_right, R.anim.hold);
    }
}
