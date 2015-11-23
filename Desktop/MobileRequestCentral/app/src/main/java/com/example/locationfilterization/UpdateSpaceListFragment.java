package com.example.locationfilterization;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mobilerequestcentral.R;

import java.util.Calendar;

import custom.dialog.CustomDatePickerValueInterface;
import custom.dialog.CustomDialog;
import custom.dialog.CustomDialogInterface;
import custom.dialog.CustomTimePicker;
import custom.dialog.DateDialogFragment;
import custom.dialog.dateDialogInterface;
import database.helper.DatabaseHandler;
import my.classes.MyLocationClass;

@SuppressWarnings("serial")
public class UpdateSpaceListFragment extends FragmentActivity implements
        CustomDatePickerValueInterface, dateDialogInterface,
        CustomDialogInterface {
    public static String StartTime = "";
    public static Boolean IsEndTime = false;
    // View v;
    Bundle bundle = null;
    Activity context;
    CustomDialog cd, cdd;
    CustomDialogInterface ci, cddi;
    CustomDatePickerValueInterface cdpInterface;
    TextView txtSpaceID, txtOrganisationId, txtSpaceName;
    EditText edtPeopleName;
    Spinner spnPeopleName, spnOrganisationName;
    MyLocationClass objclass;
    Button btnUpdate;
    DatabaseHandler dh;
    String floor, building, property;
    String foodservice = "yes";
    String equipmentRequired = "yes";
    String specialneed = "yes";
    String storageneed = "yes";
    dateDialogInterface di;
    String pplspecid;
    ;
    String[] classRoomArray = {"Confrence", "Dining", "Round Table",
            "Team Room", "Training", "Office", "WorkSpace"};
    ArrayAdapter<String> classRoomAdapter;
    // ************************Changed*************************************
    TextView dateTextView, timeStartTextView, timeEndTextView;
    EditText nameTextView, contactInfoTextView, commentsTextView,
            attendeesTextView, contactinfoEmailTextView;
    Spinner roomLayoutSpinner;

    RadioGroup foodServicesGroup, equipmentReqGroup, specialNeedGroup,
            storageGroup;

    /*
     * RadioButton foodServicesButton, equipmwntButton, specialNeedButton,
     * storageButton;
     */
    // *********************************************************************
    @Override
    public void onCreate(Bundle savedInstanceState) {
        bundle = getIntent().getExtras();
        if (bundle != null) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.update_space_record);
            context = this;
            ci = (CustomDialogInterface) context;
            cddi = (CustomDialogInterface) context;
            di = (dateDialogInterface) context;
            cdpInterface = (CustomDatePickerValueInterface) context;
            txtSpaceID = (TextView) findViewById(R.id.spaceId);
            btnUpdate = (Button) findViewById(R.id.update_button_done);
            objclass = (MyLocationClass) bundle
                    .getSerializable(MyLocationClass.EXTRA);
            dh = new DatabaseHandler(this);

            floor = bundle.getString("floor");
            building = bundle.getString("building");
            property = bundle.getString("property");

            cd = new CustomDialog(this);
            cdd = new CustomDialog(this, cddi);
            // *************************Changed*******************************************
            nameTextView = (EditText) findViewById(R.id.update_space_tv_name);
            dateTextView = (TextView) findViewById(R.id.update_space_tv_reservationDate);
            timeStartTextView = (TextView) findViewById(R.id.update_space_tv_timeStart);
            timeEndTextView = (TextView) findViewById(R.id.update_space_tv_timeEnd);
            contactInfoTextView = (EditText) findViewById(R.id.update_space_tv_contactInformationPhone);
            commentsTextView = (EditText) findViewById(R.id.update_space_tv_additionalComments);
            attendeesTextView = (EditText) findViewById(R.id.update_space_tv_numberOfAttendees);
            roomLayoutSpinner = (Spinner) findViewById(R.id.update_space_sp_roomLayout);
            foodServicesGroup = (RadioGroup) findViewById(R.id.update_space_rg_foodServicesRequired);
            equipmentReqGroup = (RadioGroup) findViewById(R.id.update_space_rg_equipmentRequired);
            specialNeedGroup = (RadioGroup) findViewById(R.id.update_space_rg_specialNeed);
            storageGroup = (RadioGroup) findViewById(R.id.update_space_rg_storageRequired);
            contactinfoEmailTextView = (EditText) findViewById(R.id.update_space_tv_contactInformation);
            classRoomAdapter = new ArrayAdapter<String>(this,
                    R.layout.custom_spinner, classRoomArray);
            roomLayoutSpinner.setAdapter(classRoomAdapter);
            // *****************************************************************************
            // setRecordOnView(objclass);
            // date================================================//

            dateTextView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    // DatePickerDialogFragment datePickerDialogFragment = new
                    // DatePickerDialogFragment();

                    DateDialogFragment dateDialogFragment = new DateDialogFragment(
                            di, context);
                    dateDialogFragment.show(getSupportFragmentManager(),
                            "addtime_date");

                }
            });
            // ====================================Start
            // Time==============================================//
            timeStartTextView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    new CustomTimePicker(context, timeStartTextView,
                            cdpInterface);
                }

            });

            // ====================================End
            // Time==================================================//
            timeEndTextView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (timeStartTextView.getText().length() != 0) {

                        StartTime = timeStartTextView.getText().toString();
                        IsEndTime = true;
                        new CustomTimePicker(context, timeEndTextView,
                                cdpInterface);

                    } else {
                        cd.showCustomDialog("Invalid Time Entry!",
                                "Enter start time first.");
                    }
                }
            });
            btnUpdate.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    if (dateTextView.getText().length() > 0
                            && nameTextView.getText().length() > 0
                            && timeStartTextView.getText().length() > 0
                            && timeEndTextView.getText().length() > 0
                            && contactInfoTextView.getText().length() > 0
                            && attendeesTextView.getText().length() > 0
                            && contactinfoEmailTextView.getText().length() > 0) {

						/*UpdateRecord();*/

                    } else {
                        cd.showCustomDialog("Attention!",
                                "Please fill all mandatory field");
                    }

                }
            });
        } else {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.update_space_record);
        }
        // ===============================================================================//

    }

	/*private void UpdateRecord() {
        dh = new DatabaseHandler(this);
		ReservationClass obj = new ReservationClass();
		obj.set_spaceId(objclass.get_spaceId());
		obj.set_classRoom(roomLayoutSpinner.getSelectedItem().toString());
		obj.set_comment(commentsTextView.getText().toString());
		obj.set_foodServiceRequired(foodservice);
		obj.set_numberOfAttendes(attendeesTextView.getText().toString());
		obj.set_reservationDate(dateTextView.getText().toString());
		obj.set_reservationEmail(contactinfoEmailTextView.getText().toString());
		obj.set_reservationName(nameTextView.getText().toString());
		obj.set_reservationPhoneNo(contactInfoTextView.getText().toString());
		obj.set_reservationStartTime(timeStartTextView.getText().toString());
		obj.set_resrevationEndTime(timeEndTextView.getText().toString());
		obj.set_specialNeed(specialneed);
		obj.set_storageNeeded(storageneed);
		obj.set_equipmentRequired(equipmentRequired);
		dh.UpdateRecord(obj);

		cdd.showCustomDialog("Congrats!", "Space Updated Succesfully");
	}*/

    public void setDate(String day, String month, String year) {
        dateTextView.setText(month + "/" + day + "/" + year);

    }

    public void checkAndValidateTimeEntry(String time) {

        try {

            if (CustomTimePicker.CalculateTimeDifference(StartTime, time)) {

            } else {
                timeEndTextView.setText("");
                cd.showCustomDialog("Invalid Time Entries!",
                        "Stop Time should be greater than Start Time.");

            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        } finally {
            IsEndTime = false;
        }

    }

    public void onFoodServiceClick(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // UpdateSpaceListFragment at = (UpdateSpaceListFragment)
        // getSupportFragmentManager()
        // .findFragmentById(R.id.floor_space_right_fragment);

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.FSRradioYes:
                if (checked)

                    setFoodServicevalue("yes");

                break;
            case R.id.FSRradioNo:
                if (checked)

                    setFoodServicevalue("no");
                break;
        }
    }

    public void onEquipmentRequiredClick(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // UpdateSpaceListFragment at= (UpdateSpaceListFragment)
        // getSupportFragmentManager().findFragmentById(R.id.floor_space_right_fragment);
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.ERradioYes:
                if (checked)
                    setequipmentRequiredValue("yes");

                break;
            case R.id.ERradioNo:
                if (checked)
                    setequipmentRequiredValue("no");

                break;
        }
    }

    public void onSpecialNeedClick(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // UpdateSpaceListFragment at= (UpdateSpaceListFragment)
        // getSupportFragmentManager().findFragmentById(R.id.floor_space_right_fragment);
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.SNradioYes:
                if (checked)
                    setSpecialNeedValue("yes");
                break;
            case R.id.SNradioNo:
                if (checked)
                    setSpecialNeedValue("no");
                break;
        }
    }

    public void onStorageNeedClick(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // UpdateSpaceListFragment at= (UpdateSpaceListFragment)
        // getSupportFragmentManager().findFragmentById(R.id.floor_space_right_fragment);
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.SRradioYes:
                if (checked)

                    setStorageNeedValue("yes");
                break;
            case R.id.SRradioNo:
                if (checked)
                    setStorageNeedValue("no");
                break;
        }
    }

    public void setFoodServicevalue(String string) {
        foodservice = string;

    }

    public void setequipmentRequiredValue(String string) {
        equipmentRequired = string;

    }

    public void setSpecialNeedValue(String string) {
        specialneed = string;

    }

    public void setStorageNeedValue(String string) {
        storageneed = string;

    }

    @Override
    public void onCustomDialogClick(String floor, String building,
                                    String property) {

        Intent intent = new Intent(UpdateSpaceListFragment.this,
                SpaceListFragment.class);
        intent.putExtras(bundle);
        startActivity(intent);

        // SpaceListFragment frag = new SpaceListFragment();
        // frag.setArguments(bundle);
        //
        // FragmentTransaction transaction = getSupportFragmentManager()
        // .beginTransaction();
        // transaction.replace(R.id.floor_space_right_fragment, frag);
        // transaction.commit();

    }

    @Override
    public void onCustomDialogFieldBlank() {
        // getSupportFragmentManager().beginTransaction()
        // .replace(R.id.worktask_right_fragment, new No()).commit();
        // /*
        // * System.out.println("HELOOOOOOOOOOOOOOOOOO"); RequestCentralForm obj
        // =
        // * (RequestCentralForm)
        // * getSupportFragmentManager().findFragmentById(R.id
        // * .worktask_right_fragment); obj.setFieldBlank();
        // */
    }

    @Override
    public void OnDatePickerClick(String day, String month, String year, String tag) {
		/*
		 * UpdateSpaceListFragment obj = (UpdateSpaceListFragment)
		 * getSupportFragmentManager()
		 * .findFragmentById(R.id.floor_space_right_fragment); obj.
		 */
        setDate(day, month, year);

    }

    @Override
    public void checkdatevalue(String time) {
		/*
		 * UpdateSpaceListFragment at = (UpdateSpaceListFragment)
		 * getSupportFragmentManager()
		 * .findFragmentById(R.id.floor_space_right_fragment); at.
		 */
        checkAndValidateTimeEntry(time);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UpdateSpaceListFragment.this,
                SpaceListFragment.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.new_slide_right, R.anim.hold);
    }

    public void updated(String property, String building, String floor) {
        try {
            bundle.putString("floor", floor);
            bundle.putString("property", property);
            bundle.putString("building", building);
            Intent intent = new Intent(UpdateSpaceListFragment.this,
                    SpaceListFragment.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } catch (Exception exception) {
            System.out.println(exception.getLocalizedMessage());
        }

    }

    @SuppressLint("ValidFragment")
    public class DatePickerDialogFragment extends DialogFragment {

        private OnDateSetListener mDateSetListener;

        public DatePickerDialogFragment() {
            // nothing to see here, move along
        }

        public DatePickerDialogFragment(OnDateSetListener callback) {
            mDateSetListener = (OnDateSetListener) callback;
        }

        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar cal = Calendar.getInstance();

            return new DatePickerDialog(UpdateSpaceListFragment.this,
                    mDateSetListener, cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        }

    }
}