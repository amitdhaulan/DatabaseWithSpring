//package com.example.mobilerequestcentral;
//
//import java.util.ArrayList;
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentTransaction;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.ListView;
//import custom.dialog.CustomAdapter;
//import database.helper.DatabaseHandler;
//
//public class BuildingFragment extends Fragment {
//	ArrayList<String> buildingArraylist;
//	ListView lv;
//	CustomAdapter adpater;
//	DatabaseHandler dh;
//	// Bundle bundle=null;
//	View v;
//	String property;
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//							 Bundle savedInstanceState) {
//		// bundle = getArguments();
//		v = inflater.inflate(R.layout.building_fragment, null);
//        lv = (ListView) v.findViewById(R.id.building_listview);
//		dh = new DatabaseHandler(getActivity());
//		buildingArraylist = new ArrayList<String>();
//		// property = bundle.getString("property");
//		buildingArraylist = dh.getBuilding();
//
//		adpater = new CustomAdapter(buildingArraylist, getActivity());
//		System.out.println("======task id=============="
//				+ buildingArraylist.size());
//		lv.setAdapter(adpater);
//		lv.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> adapter, View v,
//					int position, long arg3) {
//				v.setSelected(true);
//				Bundle bundle = new Bundle();
//				bundle.putString("building", buildingArraylist.get(position));
//
//				FloorFragment frag = new FloorFragment();
//				SpaceFragment frag1 = new SpaceFragment();
//
//				frag.setArguments(bundle);
//
//				FragmentTransaction transaction = getFragmentManager()
//						.beginTransaction();
//				transaction.replace(R.id.space_fragment, frag1);
//				transaction.replace(R.id.floor_fragment, frag);
//				transaction.commit();
//			}
//		});
//		return v;
//	}
//}