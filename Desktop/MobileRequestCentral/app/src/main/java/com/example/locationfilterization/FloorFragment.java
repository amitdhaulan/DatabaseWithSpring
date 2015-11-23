//package com.example.locationfilterization;
//
//import java.util.ArrayList;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.ListView;
//
//import com.example.mobilerequestcentral.R;
//
//import custom.dialog.CustomAdapter;
//import database.helper.DatabaseHandler;
//
//public class FloorFragment extends Fragment {
//	Bundle bundle = null;
//	ListView lv;
//	DatabaseHandler dh;
//	ArrayList<String> floorArrayList;
//	CustomAdapter adapter;
//	String property;
//	String building;
//
//	@Override
//	public View onCreateView(LayoutInflater inflater,
//			 ViewGroup container,  Bundle savedInstanceState) {
//		bundle = getArguments();
//		floorArrayList = new ArrayList<String>();
//
//		View v = inflater.inflate(R.layout.floor_fragment, null);
//		if (bundle != null) {
//			lv = (ListView) v.findViewById(R.id.floor_listview);
//			dh = new DatabaseHandler(getActivity());
//			property = bundle.getString("property");
//			building = bundle.getString("building");
//			floorArrayList = dh.getFloor(building, property);
//			System.out.println("building------------------------>" + building);
//			adapter = new CustomAdapter(floorArrayList, getActivity());
//			System.out.println("======task id=============="
//					+ floorArrayList.size());
//			lv.setAdapter(adapter);
//			lv.setOnItemClickListener(new OnItemClickListener() {
//				@Override
//				public void onItemClick(AdapterView<?> arg0, View arg1,
//						int arg2, long arg3) {
//					getActivity().finish();
//					arg1.setSelected(true);
//					Bundle bundle = new Bundle();
//					bundle.putString("floor", floorArrayList.get(arg2));
//					bundle.putString("property", property);
//					bundle.putString("building", building);
//					Intent intent = new Intent(getActivity(),
//							FloorListFragment.class);
//					intent.putExtras(bundle);
//					startActivityForResult(intent, 0);
//					getActivity().overridePendingTransition(R.anim.new_slide_left, R.anim.hold);
//				}
//			});
//
//		}
//		return v;
//	}
//
//}
