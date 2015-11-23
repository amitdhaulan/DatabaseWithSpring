package com.example.mobilerequestcentral;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import database.helper.DatabaseHandler;
import user.function.NewUserFunction;

/**
 * Created by amitk on 10/1/2015.
 */
public class ViewRequestHome extends Activity implements View.OnClickListener {
    Button homeButton, viewrequestButton, downloadrequestButton;
    DatabaseHandler dh;
    ProgressDialog pDialog;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.viewrequesthome);

        homeButton = (Button) findViewById(R.id.viewrequesthome_home);
        viewrequestButton = (Button) findViewById(R.id.viewrequesthome_viewrequest);
        downloadrequestButton = (Button) findViewById(R.id.viewrequesthome_downloadrequest);

        downloadrequestButton.setOnClickListener(this);
        viewrequestButton.setOnClickListener(this);

        dh = new DatabaseHandler(getApplicationContext());
    }

    public void home(View view) {
        Intent intent = new Intent(ViewRequestHome.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.new_slide_right, R.anim.hold);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ViewRequestHome.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.new_slide_right, R.anim.hold);
    }

    @Override
    public void onClick(View v) {
        if (v == downloadrequestButton) {
            if (dh.getRowCount(DatabaseHandler.TABLE_REQUESTDATA) <= 0)
                new downloadrequests().execute();
            else {
                dh.emptyTable(DatabaseHandler.TABLE_REQUESTDATA, "blank", "blank");
                new downloadrequests().execute();
            }
        }
        if (v == viewrequestButton) {
            if (dh.getStatus().size() != 0) {
                System.out
                        .println("View Req ==> ");
                Intent intent = new Intent(ViewRequestHome.this,
                        ViewRequestContainerNew.class);


                startActivity(intent);
                overridePendingTransition(R.anim.new_slide_left,
                        R.anim.hold);
            } else {
                showToast("Download requests first.");
            }
        }

    }

    private void showToast(String s) {
        if (toast != null)
            toast.cancel();
        toast = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT);
        toast.show();
    }

    private class downloadrequests extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = ProgressDialog.show(ViewRequestHome.this, "", "");
            pDialog.setContentView(R.layout.progresslayout);
            TextView titletextView = (TextView) pDialog
                    .findViewById(R.id.title);
            TextView messageTextView = (TextView) pDialog
                    .findViewById(R.id.message);
            titletextView.setText("Please Wait...");
            messageTextView.setText("Requests are being downloaded...");
            ((ProgressDialog) pDialog)
                    .setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.show();
        }

        @SuppressWarnings("unchecked")
        @Override
        protected String doInBackground(Void... params) {
            Map<String, String> map = null;
            String response = "";
            try {
                String[] userdetail = new String[3];
                userdetail = dh.getuserDetail();
                NewUserFunction userFunction = new NewUserFunction();
                /*ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
				param.add(new BasicNameValuePair("name", userdetail[0]));
				param.add(new BasicNameValuePair("pass", userdetail[1]));*/

                HashMap<String, String> param = new HashMap<>();
                param.put("name", userdetail[0]);
                param.put("pass", userdetail[1]);

                response = userFunction.downloadWorkRequest(param);

                if (!response.contains("Internal Server Error")) {
                    JSONObject obj = new JSONObject(response);
                    Iterator<String> iterator = obj.keys();
                    while (iterator.hasNext()) {
                        map = new HashMap<String, String>();
                        String key = iterator.next();
                        JSONObject jsonObject = obj.getJSONObject(key);
                        Iterator<String> iterator2 = jsonObject.keys();
                        while (iterator2.hasNext()) {
                            String string = iterator2.next();
                            map.put(string, jsonObject.getString(string));
                        }
                        dh.addDownloadedRequest(map);
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pDialog.dismiss();
        }

    }
}
