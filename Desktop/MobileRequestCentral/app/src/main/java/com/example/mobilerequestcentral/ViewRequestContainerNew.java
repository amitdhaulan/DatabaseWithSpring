package com.example.mobilerequestcentral;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import database.helper.DatabaseHandler;
import my.classes.MyRequestClass;

/**
 * Created by amitk on 10/1/2015.
 */
public class ViewRequestContainerNew extends Activity implements AdapterView.OnItemSelectedListener {

    static String value, floor, building, space;
    /*Spinner statusSpinner*//*, requestTypeSpinner*/;
    DatabaseHandler dh;
    String statusString, reqtypeString;
    TextView request_textview, request_tv_title;
    Button back;
    ListView reqListView;
    RequestListCustomAdapter radapter;
    ArrayList<MyRequestClass> objarrayList;
    ArrayList<String> status, reqType;
    MyRequestClass objClass;
    /*Bundle bundle;*/
    ArrayAdapter<String> statusadapter;
    private AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.viewrequestlistnew);
		/*statusSpinner = (Spinner)findViewById(R.id.filter_reqStatus);*/
        request_textview = (TextView) findViewById(R.id.requestnew_textview);
        request_tv_title = (TextView) findViewById(R.id.requestnew_tv_title);
		/*requestTypeSpinner = (Spinner)findViewById(R.id.filter_reqType);*/
        /*bundle = this.getIntent().getExtras();*/

        /*if (bundle != null) {
            value = bundle.getString("Value");
            reqtypeString = bundle.getString("requestValue");
            request_tv_title.setText(reqtypeString);
        }*/

        reqListView = (ListView) findViewById(R.id.requestnew_listview);
        dh = new DatabaseHandler(this);
        back = (Button) findViewById(R.id.requestnew_button_back);


        reqType = new ArrayList<String>();
        reqType = dh.getReqType();
        reqType.add(0, "All");
        Collections.sort(reqType);
        ArrayAdapter<String> reqadapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, reqType);
        reqadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        /*requestTypeSpinner.setAdapter(reqadapter);*/


        status = new ArrayList<String>();
        status = dh.getStatus();
        status.add(0, "All");


        try {
            Collections.sort(status);
            statusadapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, status);

            statusadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


            objarrayList = new ArrayList<>();
            objarrayList = dh.getUnFilteredRequestData();

            if (objarrayList.size() == 0) {
                new loadFilteredRecordFromDatabase().execute();
            } else {
                radapter = new RequestListCustomAdapter(ViewRequestContainerNew.this,
                        objarrayList);
                reqListView.setAdapter(radapter);

            }


            if (objarrayList.size() == 0) {
                reqListView.setVisibility(View.GONE);
                request_textview.setVisibility(View.VISIBLE);


            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(ViewRequestContainerNew.this,
                        ViewRequestHome.class);

                startActivity(intent);
                overridePendingTransition(R.anim.new_slide_right, R.anim.hold);
            }
        });

        reqListView.setTextFilterEnabled(true);
        reqListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                arg1.setSelected(true);
                objClass = new MyRequestClass();
                objClass = objarrayList.get(arg2);

                // ViewRequestForm vform = new ViewRequestForm();
                Bundle bndl = new Bundle();
                bndl.putSerializable(MyRequestClass.REQUESTCLASSEXTRA, objClass);
                /*bndl.putString("Value", bundle.getString("Value"));*/
                bndl.putString("requestValue", reqtypeString);
                String ViewRequestContainer = "ViewRequestContainerNew";
                bndl.putString("location", ViewRequestContainer);

                Intent intent = new Intent(ViewRequestContainerNew.this,
                        ViewRequestForm.class);
                intent.putExtras(bndl);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.new_slide_left, R.anim.hold);

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        System.out.println(statusString + reqtypeString);
        new loadFilteredRecordFromDatabase().execute();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ViewRequestContainerNew.this,
                ViewRequestHome.class);

        startActivity(intent);
        overridePendingTransition(R.anim.new_slide_right, R.anim.hold);
    }

    private class loadFilteredRecordFromDatabase extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... arg0) {
            objarrayList = dh.getUnFilteredRequestData();
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            radapter = new RequestListCustomAdapter(ViewRequestContainerNew.this,
                    objarrayList);
            reqListView.setAdapter(radapter);

        }
    }
}

