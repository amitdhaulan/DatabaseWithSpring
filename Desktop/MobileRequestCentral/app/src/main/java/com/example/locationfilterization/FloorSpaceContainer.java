//package com.example.locationfilterization;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentTransaction;
//import android.view.View;
//import android.widget.RadioButton;
//
//import com.example.mobilerequestcentral.R;
//
//import custom.dialog.CustomDatePickerValueInterface;
//import custom.dialog.CustomDialogInterface;
//import custom.dialog.dateDialogInterface;
//
//@SuppressWarnings("serial")
//public class FloorSpaceContainer extends FragmentActivity implements
//		CustomDialogInterface, dateDialogInterface,
//		CustomDatePickerValueInterface {
//	Bundle bundle = null;
//
//	@Override
//	protected void onCreate(Bundle arg0) {
//		super.onCreate(arg0);
//		setContentView(R.layout.floor_space_container);
//		bundle = getIntent().getExtras();
//		String floor = bundle.getString("floor");
//		String property = bundle.getString("property");
//		String building = bundle.getString("building");
//		Intent intent = new Intent(this, FloorListFragment.class);
//		Bundle bundle2 = new Bundle();
//		bundle2.putString("floor", floor);
//		bundle2.putString("building", building);
//		bundle2.putString("property", property);
//		intent.putExtras(bundle2);
//		setIntent(intent);
//
//	}
//
//	public void back(View v) {
//		finish();
//	}
//
//	@Override
//	public void onBackPressed() {
//		super.onBackPressed();
//		finish();
//	}
//
//	@Override
//	public void onCustomDialogClick(String floor, String building,
//			String property) {
//		finish();
//		Bundle bundle = new Bundle();
//		bundle.putString("floor", floor);
//		bundle.putString("property", property);
//		bundle.putString("building", building);
//		Intent intent = new Intent(FloorSpaceContainer.this,
//				FloorSpaceContainer.class);
//		intent.putExtras(bundle);
//		startActivityForResult(intent, 0);
//
//	}
//
////	public void goback(View v) {
////
////
////		SpaceListFragment frag = new SpaceListFragment();
////		frag.setArguments(bundle);
////
////		FragmentTransaction transaction = getSupportFragmentManager()
////				.beginTransaction();
////		transaction.replace(R.id.floor_space_right_fragment, frag);
////		transaction.commit();
////	}
//
//	@Override
//	public void OnDatePickerClick(String day, String month, String year) {
//		UpdateSpaceListFragment obj = (UpdateSpaceListFragment) getSupportFragmentManager()
//				.findFragmentById(R.id.floor_space_right_fragment);
//		obj.setDate(day, month, year);
//
//	}
//
//	@Override
//	public void checkdatevalue(String time) {
//		UpdateSpaceListFragment at = (UpdateSpaceListFragment) getSupportFragmentManager()
//				.findFragmentById(R.id.floor_space_right_fragment);
//		at.checkAndValidateTimeEntry(time);
//
//	}
//
//	public void onFoodServiceClick(View view) {
//		// Is the button now checked?
//		boolean checked = ((RadioButton) view).isChecked();
//		UpdateSpaceListFragment at = (UpdateSpaceListFragment) getSupportFragmentManager()
//				.findFragmentById(R.id.floor_space_right_fragment);
//		;
//
//		// Check which radio button was clicked
//		switch (view.getId()) {
//		case R.id.FSRradioYes:
//			if (checked)
//
//				at.setFoodServicevalue("yes");
//
//			break;
//		case R.id.FSRradioNo:
//			if (checked)
//
//				at.setFoodServicevalue("no");
//			break;
//		}
//	}
//
//	public void onEquipmentRequiredClick(View view) {
//		// Is the button now checked?
//		boolean checked = ((RadioButton) view).isChecked();
//		UpdateSpaceListFragment at = (UpdateSpaceListFragment) getSupportFragmentManager()
//				.findFragmentById(R.id.floor_space_right_fragment);
//		// Check which radio button was clicked
//		switch (view.getId()) {
//		case R.id.ERradioYes:
//			if (checked)
//				at.setequipmentRequiredValue("yes");
//
//			break;
//		case R.id.ERradioNo:
//			if (checked)
//				at.setequipmentRequiredValue("no");
//
//			break;
//		}
//	}
//
//	public void onSpecialNeedClick(View view) {
//		// Is the button now checked?
//		boolean checked = ((RadioButton) view).isChecked();
//		UpdateSpaceListFragment at = (UpdateSpaceListFragment) getSupportFragmentManager()
//				.findFragmentById(R.id.floor_space_right_fragment);
//		// Check which radio button was clicked
//		switch (view.getId()) {
//		case R.id.SNradioYes:
//			if (checked)
//				at.setSpecialNeedValue("yes");
//			break;
//		case R.id.SNradioNo:
//			if (checked)
//				at.setSpecialNeedValue("no");
//			break;
//		}
//	}
//
//	public void onStorageNeedClick(View view) {
//		// Is the button now checked?
//		boolean checked = ((RadioButton) view).isChecked();
//		UpdateSpaceListFragment at = (UpdateSpaceListFragment) getSupportFragmentManager()
//				.findFragmentById(R.id.floor_space_right_fragment);
//		// Check which radio button was clicked
//		switch (view.getId()) {
//		case R.id.SRradioYes:
//			if (checked)
//
//				at.setStorageNeedValue("yes");
//			break;
//		case R.id.SRradioNo:
//			if (checked)
//				at.setStorageNeedValue("no");
//			break;
//		}
//	}
//
//	// @Override
//	// public void onCustomDialogClick() {
//	// SpaceListFragment frag = new SpaceListFragment();
//	// frag.setArguments(bundle);
//	//
//	// FragmentTransaction transaction = getSupportFragmentManager()
//	// .beginTransaction();
//	// transaction.replace(R.id.floor_space_right_fragment, frag);
//	// transaction.commit();
//	//
//	// }
//
//	@Override
//	public void onCustomDialogFieldBlank() {/*
//		SpaceListFragment frag = new SpaceListFragment();
//		frag.setArguments(bundle);
//
//		FragmentTransaction transaction = getSupportFragmentManager()
//				.beginTransaction();
//		transaction.replace(R.id.floor_space_right_fragment, frag);
//		transaction.commit();
//	*/}
// }