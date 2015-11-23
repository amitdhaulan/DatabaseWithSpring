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
//public class PropertyFragment extends Fragment {
//	ArrayList<String> propertyArraylist;
//	ListView lv;
//	CustomAdapter adpater;
//	DatabaseHandler dh;
//	View v1;
//
//	@Override
//	public View onCreateView(LayoutInflater inflater,
//			 ViewGroup container,  Bundle savedInstanceState) {
//		View v = inflater.inflate(R.layout.property_fragment, null);
//		lv = (ListView) v.findViewById(R.id.property_listview);
//		dh = new DatabaseHandler(getActivity());
//		propertyArraylist = new ArrayList<String>();
//		propertyArraylist = dh.getProperty();
//		adpater = new CustomAdapter(propertyArraylist, getActivity());
//		System.out.println("======task id=============="
//				+ propertyArraylist.size());
//		lv.setAdapter(adpater);
//		lv.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				arg1.setSelected(true);
//				Bundle bundle = new Bundle();
//				bundle.putString("property", propertyArraylist.get(arg2));
//				BuildingFragment frag = new BuildingFragment();
//				FloorFragment frag1 = new FloorFragment();
//				frag.setArguments(bundle);
//				FragmentTransaction transaction = getFragmentManager()
//						.beginTransaction();
//				transaction.replace(R.id.building_fragment, frag);
//				transaction.replace(R.id.floor_fragment, frag1);
//				transaction.addToBackStack(null);
//				transaction.commit();
//			}
//		});
//		return v;
//	}
//}
