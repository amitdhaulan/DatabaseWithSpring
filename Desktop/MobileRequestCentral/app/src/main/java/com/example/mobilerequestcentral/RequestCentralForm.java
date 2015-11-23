package com.example.mobilerequestcentral;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.CursorLoader;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import custom.dialog.CustomAlertAdapter;
import custom.dialog.CustomDialog;
import custom.dialog.CustomDialogInterface;
import database.helper.DatabaseHandler;
import get.real.path.GetRealPath;
import my.classes.MyRequestClass;
import my.classes.OrgClass;
import user.function.NewUserFunction;

public class RequestCentralForm extends FragmentActivity {


    private static final int SELECT_PHOTO_APISDKG19 = 12;
    /*private static final int SELECT_PHOTO_APISDKL19 = 11;*/
    private static final int CAMERA_REQUEST = 1000;
    static String requestValue;
    static String selectService = "";
    LinearLayout locationrequestedForLinearLayout;
    Context context;
    Bundle bundle = null, valueBundle = null;
    String floor = "";
    String space = "";
    String building = "";
    EditText locName;
    EditText orgName, service, description;
    TextView txtHeader/*, txtRequestCounter*//*, txtCreatedBy*/;
    Button back;
    Button btnViewRequest, selectorg;
    /*ListView orgListView;*/
    Button submit;
    ImageView imageView;
    ProgressDialog pDialog;
    String usernameString, passwordString;
    JSONObject jsonObjectToPost;
    String[] userdetail;
    ArrayList<String> serviceArrayList;
    CharSequence[] serviceArray;
    ArrayList<String> serviceArrayListFiltered;
    ArrayList<String> buildingArrayList;
    CharSequence[] buildingArray;
    LinearLayout linearLayout;
    ArrayAdapter<String> itemsAdapter;
    ArrayList<String> orgArrayList;
    ArrayList<String> orgArrayListFiltered;
    CharSequence[] orgnaisationArray = {};
    CustomDialog cDialog, customdialog;
    MyRequestClass objClass;
    DatabaseHandler dh;
    CustomDialogInterface cd;
    Bitmap photoBitmap = null;
    String ImageName;
    AlertDialog alert;
    String location = "";
    int textlength = 0;
    ListView listview;
    Uri selectedImage;
    OrgClass myOrgCLassObj;
    ArrayList<OrgClass> orgClassArrayList;
    String orgNameToShow = "";
    private AlertDialog myalertDialog = null;
    private RadioGroup radioGroup;
    private RadioButton radioButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        context = this;
        valueBundle = this.getIntent().getExtras();
        bundle = this.getIntent().getExtras();
        dh = new DatabaseHandler(context);
        // cd = (CustomDialogInterface);

        customdialog = new CustomDialog(RequestCentralForm.this);
        System.out.println("bundle===>" + bundle);

        if (valueBundle != null || bundle != null) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.requestcentralform_updated);

            linearLayout = (LinearLayout) findViewById(R.id.linearlayout_forgettingweight);
            locationrequestedForLinearLayout = (LinearLayout) findViewById(R.id.locationRequestedForLinearLayout);
            btnViewRequest = (Button) findViewById(R.id.btn_Vrequest);
            back = (Button) findViewById(R.id.requestform_button_back);
            locName = (EditText) findViewById(R.id.location);
            submit = (Button) findViewById(R.id.submit_button_done);
            orgName = (EditText) findViewById(R.id.organisation);
            service = (EditText) findViewById(R.id.sevice);
            imageView = (ImageView) findViewById(R.id.uploadimage_imgview_id);
            selectorg = (Button) findViewById(R.id.selectOrg);
            radioGroup = (RadioGroup) findViewById(R.id.radioSex);
            /*txtRequestCounter = (TextView) findViewById(R.id.requestCounter);*/
			/*txtCreatedBy = (TextView) findViewById(R.id.createdby);*/
            description = (EditText) findViewById(R.id.description);
            txtHeader = (TextView) findViewById(R.id.timelog_detail_txt_title);


            String locationNameString = dh.getDefaultLocation();
            try {
                locName.setText(locationNameString.replaceFirst("\\\\", "").replace("\\\\", "/").replace("Locations", "").replaceFirst("\\\\", ""));
            } catch (Exception e) {
                e.printStackTrace();
            }


          /*  String orgNameString = dh.getDefaultOrganization();
            String[] strOrg = orgNameString.split("\\\\");
            orgName.setText(strOrg[strOrg.length-1]);*/

            orgName.setText(dh.getDefaultOrganization());


            location = locName.getText().toString();
            System.out.print("==> loc" + location);

            userdetail = dh.getuserDetail();

            try {
                requestValue = null;
                requestValue = valueBundle.getString("Value");
            } catch (Exception e) {
                e.printStackTrace();
            }

            /*if(requestValue.equals("Equipment Service")){
                locationrequestedForLinearLayout.setVisibility(View.VISIBLE);
            }*/
            txtHeader.setText(/* "Create Request : " + */requestValue);

            buildingArrayList = dh.getBuildingNames();

            buildingArray = buildingArrayList
                    .toArray(new CharSequence[buildingArrayList.size()]);


            serviceArrayList = dh.getService1(dh.getRequestClass(requestValue),
                    dh.getHierarchyPath1(dh.getRequestClass(requestValue)));

            serviceArray = serviceArrayList
                    .toArray(new CharSequence[serviceArrayList.size()]);


            try {
                if (bundle.getString("floor") != null && bundle.getString("space") != null) {
                    floor = bundle.getString("floor");
                    space = bundle.getString("space");
                    building = bundle.getString("building");

                    locName.setText(building + "/" + floor + "/" + space);

                    if (dh.getOrg_building(building).size() == 0) {
                        new getOrg(userdetail[0], userdetail[1], building).execute();
                    }


                } else if (bundle.getString("building") != null && bundle.getString("floor") != null) {
                    floor = bundle.getString("floor");
                    building = bundle.getString("building");
                    locName.setText(building + "/" + floor);
                    if (dh.getOrg_building(building).size() == 0) {
                        new getOrg(userdetail[0], userdetail[1], building).execute();
                    }
                } else if (bundle.getString("building") != null) {
                    building = bundle.getString("building");
                    locName.setText(building);
                    if (dh.getOrg_building(building).size() == 0) {
                        new getOrg(userdetail[0], userdetail[1], building).execute();
                    }
                } else {

                    if (!location.equals("")) {
                        String[] str = location.split("\\\\");
                        int len = str.length;

                        if(locationNameString.split("\\\\").length >= 5){
                            floor = str[len - 2];
                            space = str[len - 1];
                            building = str[len - 3];
                        }else{
                            building = str[len - 1];
                        }

                        if (dh.getOrg_building(building).size() == 0) {
                            new getOrg(userdetail[0], userdetail[1], building).execute();
                        }
                    } else {
                        building = locName.getText().toString();

                    }


                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            selectorg.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {

                        /*orgArrayList = dh.getOrg();*/
                    orgArrayList = dh.getOrg_building(building);
                    orgArrayListFiltered = new ArrayList<String>();

                    orgnaisationArray = orgArrayList
                            .toArray(new CharSequence[orgArrayList.size()]);


                    if (dh.getOrg_building(building).size() != 0) {
                        System.out.println("=====>Clicked<=====");

                        AlertDialog.Builder myDialog = new AlertDialog.Builder(RequestCentralForm.this);

                        final EditText editText = new EditText(RequestCentralForm.this);
                        listview = new ListView(RequestCentralForm.this);
                        editText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.searchnew2, 0, 0, 0);
                        LinearLayout layout = new LinearLayout(RequestCentralForm.this);
                        layout.setOrientation(LinearLayout.VERTICAL);
                        layout.addView(editText);
                        layout.addView(listview);
                        myDialog.setView(layout);

                        orgArrayListFiltered = new ArrayList<String>();
                        orgArrayListFiltered.addAll(orgArrayList);
                        Collections.sort(orgArrayListFiltered);
                        CustomAlertAdapter arrayAdapter = new CustomAlertAdapter(RequestCentralForm.this, orgArrayListFiltered);
                        listview.setAdapter(arrayAdapter);

                        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String strName = orgArrayListFiltered.get(position);
                                orgName.setText("");
                                orgName.setText(strName);
                                myalertDialog.dismiss();
                                /*Toast.makeText(getApplicationContext(), orgNameToShow, Toast.LENGTH_SHORT).show();*/

                            }
                        });

                        editText.addTextChangedListener(new TextWatcher() {
                            public void afterTextChanged(Editable s) {

                            }

                            public void beforeTextChanged(CharSequence s,
                                                          int start, int count, int after) {

                            }

                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                editText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.searchnew2, 0, 0, 0);
                                textlength = editText.getText().length();
                                orgArrayListFiltered.clear();
                                for (int i = 0; i < orgArrayList.size(); i++) {
                                    if (textlength <= orgArrayList.get(i).length()) {

                                        if (orgArrayList.get(i).toLowerCase().contains(editText.getText().toString().toLowerCase().trim())) {
                                            orgArrayListFiltered.add(orgArrayList.get(i));
                                        }
                                    }
                                }

                                Collections.sort(orgArrayListFiltered);
                                listview.setAdapter(new CustomAlertAdapter(RequestCentralForm.this, orgArrayListFiltered));
                            }
                        });
                        myDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        myalertDialog = myDialog.show();
                    } else {
                        Toast.makeText(getApplicationContext(), "This location has no organization", Toast.LENGTH_SHORT).show();
                    }

                }
            });


            btnViewRequest.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    if (dh.getStatus().size() != 0) {
                        System.out
                                .println("View Req ==> ");
                        Intent intent = new Intent(RequestCentralForm.this,
                                ViewRequestContainer.class);

                        Bundle bundle = new Bundle();
                        bundle.putString("Value", valueBundle.getString("Value"));
                        bundle.putString("requestValue", requestValue);
                        bundle.putString("floor", bundle.getString("floor"));
                        bundle.putString("space", bundle.getString("space"));
                        bundle.putString("building",
                                bundle.getString("building"));
                        intent.putExtras(bundle);
                        startActivity(intent);
                        overridePendingTransition(R.anim.new_slide_left,
                                R.anim.hold);
                    } else
                        Toast.makeText(context, "Download requests first.", Toast.LENGTH_SHORT).show();
                }
            });

            back.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Intent intent = new Intent(RequestCentralForm.this,
                            requestcentral.class);
                    startActivityForResult(intent, 0);
                    overridePendingTransition(R.anim.new_slide_right, R.anim.hold);

                }
            });


            service.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    AlertDialog.Builder myDialog = new AlertDialog.Builder(RequestCentralForm.this);

                    final EditText editText = new EditText(RequestCentralForm.this);
                    listview = new ListView(RequestCentralForm.this);
                    editText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.searchnew2, 0, 0, 0);
                    LinearLayout layout = new LinearLayout(RequestCentralForm.this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.addView(editText);
                    layout.addView(listview);
                    myDialog.setView(layout);

                    serviceArrayListFiltered = new ArrayList<String>();
                    serviceArrayListFiltered.addAll(serviceArrayList);
                    Collections.sort(serviceArrayListFiltered);
                    CustomAlertAdapter arrayAdapter = new CustomAlertAdapter(RequestCentralForm.this, serviceArrayListFiltered);
                    listview.setAdapter(arrayAdapter);

                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String strName = serviceArrayListFiltered.get(position);
                            service.setText("");
                            service.setText(strName);
                            myalertDialog.dismiss();

                        }
                    });

                    editText.addTextChangedListener(new TextWatcher() {
                        public void afterTextChanged(Editable s) {

                        }

                        public void beforeTextChanged(CharSequence s,
                                                      int start, int count, int after) {

                        }

                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            editText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.searchnew2, 0, 0, 0);
                            textlength = editText.getText().length();
                            serviceArrayListFiltered.clear();
                            for (int i = 0; i < serviceArrayList.size(); i++) {
                                if (textlength <= serviceArrayList.get(i).length()) {

                                    if (serviceArrayList.get(i).toLowerCase().contains(editText.getText().toString().toLowerCase().trim())) {
                                        serviceArrayListFiltered.add(serviceArrayList.get(i));
                                    }
                                }
                            }

                            Collections.sort(serviceArrayListFiltered);
                            listview.setAdapter(new CustomAlertAdapter(RequestCentralForm.this, serviceArrayListFiltered));
                        }
                    });
                    myDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    myalertDialog = myDialog.show();

                }
            });

            submit.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {


                    if (locName.getText().length() > 0
                            && orgName.getText().length() > 0
                            && service.getText().length() > 0) {
                        String buildingId = "";

                        dh = new DatabaseHandler(getApplicationContext());

                        ArrayList<String> strings = dh.getBuildingWithId(locName
                                .getText().toString());

                        jsonObjectToPost = new JSONObject();
                        try {

                            if(building.length() != 0)
                                jsonObjectToPost.put("buildingName", building);
                            else
                                jsonObjectToPost.put("buildingName", "BESC - MAIN");

                            if (floor.length() != 0)
                                jsonObjectToPost.put("floor", floor);
                            else
                                jsonObjectToPost.put("floor", "");

                            if (space.length() != 0)
                                jsonObjectToPost.put("space", space);
                            else
                                jsonObjectToPost.put("space", "");


                            jsonObjectToPost.put("buildingId", buildingId);
                            jsonObjectToPost.put("gui_id", dh.getGUIID(requestValue));
                            jsonObjectToPost.put("type", service.getText()
                                    .toString());
                            jsonObjectToPost.put("typeId",
                                    dh.serviceId(service.getText().toString()));

                            String orgNameString = "";
                            try {
                                /*String[] str =orgName.getText().toString().split("\\\\");
                                orgNameString  = str[str.length-2];*/
                                orgNameString = orgName.getText().toString();
                            } catch (Exception e) {
                                orgNameString = orgName.getText().toString();
                                e.printStackTrace();
                            }


							/*jsonObjectToPost.put("organizationName","01811 NF RM West - 01811" );*/
                            jsonObjectToPost.put("organizationName", orgNameString);
                            jsonObjectToPost.put("description", description
                                    .getText().toString());
                            jsonObjectToPost.put("serviceType",
                                    getServiceData());
                            jsonObjectToPost.put("serviceId", getServiceId());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        objClass = new MyRequestClass();

                        objClass.setDescription(description.getText()
                                .toString());
                        objClass.setLocation(locName.getText().toString());
                        objClass.setOrganization(orgName.getText().toString());
                        objClass.setRequesttype(txtHeader.getText().toString());
                        objClass.setService(service.getText().toString());

                        new UploadData().execute();
                    } else {
                        showAlert();
                    }
                }

            });

        } else {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.requestcentralform);
        }
    }

    public void goback(View view) {
        Intent intent = new Intent(RequestCentralForm.this,
                requestcentral.class);
        startActivityForResult(intent, 0);
        overridePendingTransition(R.anim.new_slide_right, R.anim.hold);
    }

    protected JSONObject getServiceId() throws JSONException {
        dh = new DatabaseHandler(RequestCentralForm.this);
        JSONObject jsonObject = dh.getServiceId(dh.getRequestClass(requestValue));
        dh.close();
        return jsonObject;
    }

    protected JSONObject getServiceData() throws JSONException {
        dh = new DatabaseHandler(RequestCentralForm.this);
        JSONObject jsonObject = dh.ServiceIdWithName_Parent(dh.getRequestClass(requestValue));
        dh.close();
        return jsonObject;
    }

    private void showAlert() {
        customdialog.showCustomDialog("Attention",
                "Please fill all mandatory field");

    }

    public void setImage(Bitmap photo, String imagename) {
        photoBitmap = photo;
        imageView.setImageBitmap(photoBitmap);
        ImageName = imagename;

    }

    public void selectLocation(View v) {
        try {
            if (building.length() != 0) {
                Intent intent = new Intent(RequestCentralForm.this, locationNewDialogBuild.class);
                startActivityForResult(intent, 0);
                overridePendingTransition(R.anim.new_slide_left, R.anim.hold);
            } else {
                System.out.println("in else of select location");
            }
        } catch (Exception e) {
        }
    }

    public void BrowseImage(View v) {
        if (Build.VERSION.SDK_INT < 19) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivity(intent.createChooser(intent, getResources().getString(R.string.supplyconstant)));

        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, SELECT_PHOTO_APISDKG19);
        }
    }

    @SuppressWarnings("unused")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView imageView = (ImageView) findViewById(R.id.uploadimage_imgview_id);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            overridePendingTransition(R.anim.new_slide_right, R.anim.hold);
            Date d = new Date();
            CharSequence date = DateFormat.format("MM-dd-yyyy", d.getTime());
            long time = (long) d.getTime();

            File exportDir = new File(Environment
                    .getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_DCIM).getAbsolutePath()
                    + "/" + "RequestCentral" + "/", "");

            if (!exportDir.exists()) {
                exportDir.mkdirs();
            }
            Bitmap bitmap = photo;
            File image = new File(exportDir, date + " " + time + ".png");

            File file = image;
            FileOutputStream outStream;
            try {
                outStream = new FileOutputStream(image);
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, outStream);
                outStream.flush();
                outStream.close();

                String path = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DCIM).getAbsolutePath()
                        + "/" + "RequestCentral" + "/";

                String IMAGENAME = file.toString().replace(path, "");
                String imagename = IMAGENAME;

                setImage((Bitmap) data.getExtras().get("data"), path);

                sendBroadcast(new Intent(
                        Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                        Uri.parse("file://" + path + date + " " + time + ".png")));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (requestCode == SELECT_PHOTO_APISDKG19
                && resultCode == RESULT_OK && null != data) {
            overridePendingTransition(R.anim.new_slide_right, R.anim.hold);

            selectedImage = data.getData();
            imageView
                    .setImageBitmap(BitmapFactory.decodeFile(GetRealPath
                            .checkAPIAndGetPath(getApplicationContext(),
                                    selectedImage)));
        }
    }

    public void CaptureImage(View v) {
        Intent cameraIntent = new Intent(
                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
            overridePendingTransition(R.anim.new_slide_left, R.anim.hold);
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        Cursor cursor = null;
        int column_index = 0;
        try {
            String[] projection = {MediaStore.Images.Media.DATA};
            CursorLoader cursorLoader = new CursorLoader(context, contentUri,
                    projection, null, null, null);
            cursor = cursorLoader.loadInBackground();
            column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception exception) {
            return cursor.getString(column_index);
        } finally {
            if (!(cursor.isClosed())) {
                cursor.close();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RequestCentralForm.this,
                requestcentral.class);
        startActivityForResult(intent, 0);
        overridePendingTransition(R.anim.new_slide_right, R.anim.hold);
    }

    public class UploadData extends AsyncTask<Void, Integer, String> {
        String username, password;

        public UploadData(/* String userName, String passsword */) {
			/*
			 * this.username = userName; this.password = passsword;
			 */
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pDialog = ProgressDialog.show(RequestCentralForm.this, "", "");
            pDialog.setContentView(R.layout.progresslayout);
            // progressBar = (ProgressBar)
            // pDialog.findViewById(R.id.progressBar1);
            TextView titletextView = (TextView) pDialog
                    .findViewById(R.id.title);
            TextView messageTextView = (TextView) pDialog
                    .findViewById(R.id.message);
            titletextView.setText("Please Wait...");
            messageTextView.setText("Creating Request...");
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            NewUserFunction userFunction = new NewUserFunction();
            String response = "";
			/*ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("name", userdetail[0]));
			param.add(new BasicNameValuePair("pass", userdetail[1]));
			param.add(new BasicNameValuePair("data", jsonObjectToPost
					.toString()));*/


            HashMap<String, String> param = new HashMap<>();
            param.put("name", userdetail[0]);
            param.put("pass", userdetail[1]);
            param.put("data", jsonObjectToPost
                    .toString());

            System.out.println("=====>jsonObjectToPost.toString()<=====" + jsonObjectToPost.toString());

            try {
                response = userFunction.uploadRequestData(param);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            pDialog.dismiss();
            cDialog = new CustomDialog(RequestCentralForm.this, cd);

            if (null != response && response.contains("message")) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    long l = Long.parseLong(jsonObject.getString("message"));

                    cDialog.showCustomDialog("Attention",
                            "Record Submitted Sucessfully");
                    service.setText("");
                    description.setText("");
                    imageView.setImageBitmap(null);

                } catch (Exception e) {
                    cDialog.showCustomDialog(
                            getResources().getString(R.string.Creationtitle),
                            getResources().getString(R.string.creationErrorMsg));
                }

            }

			/*if (null != response && !response.contains("error") && !response.equals("")) {
				*//*dh.saveRequestRecord(objClass);*//*

				cDialog.showCustomDialog("Attention",
                        "Record Submitted Sucessfully");
				service.setText("");
				description.setText("");
				imageView.setImageBitmap(null);

			} else {
				cDialog.showCustomDialog(
						getResources().getString(R.string.Creationtitle),
						getResources().getString(R.string.creationErrorMsg));
			}*/
        }
    }


    public class getOrg extends AsyncTask<Void, Integer, String> {
        String username, password, buildingname;

        public getOrg(String userName, String passsword, String buildingname) {
            this.username = userName;
            this.password = passsword;
            this.buildingname = buildingname;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            orgClassArrayList = new ArrayList<>();
            pDialog = ProgressDialog.show(RequestCentralForm.this, "", "");
            pDialog.setContentView(R.layout.progresslayout);
            // progressBar = (ProgressBar)
            // pDialog.findViewById(R.id.progressBar1);
            TextView titletextView = (TextView) pDialog
                    .findViewById(R.id.title);
            TextView messageTextView = (TextView) pDialog
                    .findViewById(R.id.message);
            titletextView.setText("Please Wait...");
            messageTextView.setText("Downloading organizations...");
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.show();
            orgName.setText("");
        }

        @Override
        protected String doInBackground(Void... params) {
            NewUserFunction userFunction = new NewUserFunction();
            String orgString = "";

            HashMap<String, String> param = new HashMap<>();
            param.put("name", this.username);
            param.put("pass", this.password);
            param.put("building", this.buildingname);

            try {
                /*response = userFunction.getOrganization(param);*/

            /*String */
                orgString = userFunction.getOrganization(param);

                if (!orgString.contains("No Records Available")) {
                    JSONObject jsonObject1 = new JSONObject(orgString);
                    Iterator<String> stringIterator = jsonObject1.keys();
                    while (stringIterator.hasNext()) {
                        myOrgCLassObj = new OrgClass();
                        String outerkey = stringIterator.next();
                        JSONObject innerJsonObject = jsonObject1.getJSONObject(outerkey);
                        Iterator<String> stringIterator1 = innerJsonObject.keys();
                        while (stringIterator1.hasNext()) {
                            String innerKey = stringIterator1.next();
                            if (innerKey.equals("Building")) {
                                myOrgCLassObj.setOrg_building(innerJsonObject.getString("Building"));
                            }
                            if (innerKey.equals("Floor")) {
                                myOrgCLassObj.setOrg_floor(innerJsonObject.getString("Floor"));
                            }
                            if (innerKey.equals("ID")) {
                                myOrgCLassObj.setOrg_id(innerJsonObject.getString("ID"));
                            }
                            if (innerKey.equals("Location Lookup")) {
                                myOrgCLassObj.setOrg_location_lookup(innerJsonObject.getString("Location Lookup"));
                            }
                            if (innerKey.equals("Organization Name")) {

                       /* try{
                            orgNameToShow = innerJsonObject.getString("Organization Name");
                            String[] string =orgNameToShow.split("\\\\");
                            int length =string.length;
                            orgNameToShow = string[length-1];
                        }catch (Exception e){
                            e.printStackTrace();
                        }*/
                                myOrgCLassObj.setOrg_organization_name(innerJsonObject.getString("Organization Name"));
                            }

                            if (innerKey.equals("Organization Path")) {

                       /* try{
                            orgNameToShow = innerJsonObject.getString("Organization Path");
                            String[] string =orgNameToShow.split("\\\\");
                            int length =string.length;
                            orgNameToShow = string[length-1];
                        }catch (Exception e){
                            e.printStackTrace();
                        }*/
                                myOrgCLassObj.setOrg_organization_path(innerJsonObject.getString("Organization Path"));
                            }
                            if (innerKey.equals("Property")) {
                                myOrgCLassObj.setOrg_property(innerJsonObject.getString("Property"));
                            }
                            if (innerKey.equals("Space")) {
                                myOrgCLassObj.setOrg_space(innerJsonObject.getString("Space"));
                            }
                            myOrgCLassObj.setDef_org(MainActivity.default_org);

                        }
                        orgClassArrayList.add(myOrgCLassObj);
                    }

                    dh.emptyTable(DatabaseHandler.MY_ORG_TABLE, "blank", "blank");
                    dh.saveOrg(orgClassArrayList);
                } else {

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return orgString;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            /*Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();*/
            pDialog.dismiss();

            if (response.equals("No Records Available")) {
                dh.emptyTable(DatabaseHandler.MY_ORG_TABLE, "blank", "blank");
            }

        }
    }
}
