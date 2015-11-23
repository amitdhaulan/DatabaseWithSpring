//package com.example.locationfilterization;
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
//
//import com.example.mobilerequestcentral.R;
//
//import custom.dialog.CustomAdapter;
//import database.helper.DatabaseHandler;
//
//public class BuildingFragment extends Fragment {
//	ArrayList<String> buildingArraylist;
//	ListView lv;
//	CustomAdapter adpater;
//	DatabaseHandler dh;
//	Bundle bundle = null;
//	View v;
//	String property;
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		bundle = getArguments();
//		v = inflater.inflate(R.layout.building_fragment, null);
//		if (bundle != null) {
//			lv = (ListView) v.findViewById(R.id.building_listview);
//			dh = new DatabaseHandler(getActivity());
//			buildingArraylist = new ArrayList<String>();
//			property = bundle.getString("property");
//			buildingArraylist = dh.getBuilding(property);
//
//
//
//			System.out.println("building------------------------>"
//					+ bundle.getString("property"));
//			adpater = new CustomAdapter(buildingArraylist, getActivity());
//			System.out.println("======task id=============="
//					+ buildingArraylist.size());
//			lv.setAdapter(adpater);
//			lv.setOnItemClickListener(new OnItemClickListener() {
//				@Override
//				public void onItemClick(AdapterView<?> adapter, View v,
//						int position, long arg3) {
//					v.setSelected(true);
//					Bundle bundle = new Bundle();
//					bundle.putString("building",
//							buildingArraylist.get(position));
//					bundle.putString("property", property);
//					FloorFragment frag = new FloorFragment();
//					frag.setArguments(bundle);
//					FragmentTransaction transaction = getFragmentManager()
//							.beginTransaction();
//					transaction.addToBackStack(null);
//					transaction.replace(R.id.floor_fragment, frag);
//					transaction.commit();
//				}
//			});
//		}
//		return v;
//	}
//
//}