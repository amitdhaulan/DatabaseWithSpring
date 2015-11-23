package com.example.mobilerequestcentral;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import custom.dialog.CustomDialog;
import database.helper.DatabaseHandler;
import user.function.NewUserFunction;

public class LogIn extends Activity {
    String UserName, Passsword;
    EditText edtUsername, edtPswd;
    DatabaseHandler dh;
    NewUserFunction userFunction;
    ProgressDialog pDialog;
    CustomDialog cDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);

      /*  try {
            copyDataBase("Location1.db");
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        dh = new DatabaseHandler(getApplicationContext());
       /* try {
            dh.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            dh.openDataBase();
        } catch (SQLException sqle) {

            sqle.printStackTrace();

        }*/


        cDialog = new CustomDialog(LogIn.this);
        edtUsername = (EditText) findViewById(R.id.userid_et);
        edtPswd = (EditText) findViewById(R.id.password_et);

        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        String[] user_detail = databaseHandler.getuserDetail();
        /*edtUsername.setText(user_detail[0]);*/
        /*edtPswd.setText(user_detail[1]);*/

    }

    public void login(View v) {
        UserName = edtUsername.getText().toString();
        Passsword = edtPswd.getText().toString();
        if (UserName.length() == 0) {
            edtUsername.setError("Please Enter Username");
        } else if (Passsword.length() == 0) {
            edtPswd.setError("Please Enter Password");
        } else {
            String nm, pswd;
            String[] userDetail = new String[3];
            System.out.println("checkinlocaldatabase");
            int n = dh.getRowCount(DatabaseHandler.TABLE_USER);
            if (n > 0) {
                userDetail = dh.getuserDetail();
                nm = userDetail[0];
                pswd = userDetail[1];

                if (UserName.equals(nm) && Passsword.equals(pswd)) {
                    System.out.println("user data is in local db");

                    startActivity(new Intent(LogIn.this, MainActivity.class));
                    overridePendingTransition(R.anim.new_slide_left,
                            R.anim.hold);
                } else {
                    /*loginFromServer loginFromServer = new loginFromServer(UserName, Passsword);
                    loginFromServer.run();*/

                    new loginFromServer(UserName, Passsword).execute();
                }

            } else {
                new loginFromServer(UserName, Passsword).execute();
            }

        }
    }


    public class loginFromServer extends AsyncTask<Void, Integer, String> {
        String username, password;

        public loginFromServer(String userName, String passsword) {
            this.username = userName;
            this.password = passsword;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pDialog = ProgressDialog.show(LogIn.this, "", "");
            pDialog.setContentView(R.layout.progresslayout);
            // progressBar = (ProgressBar)
            // pDialog.findViewById(R.id.progressBar1);
            TextView titletextView = (TextView) pDialog
                    .findViewById(R.id.title);
            TextView messageTextView = (TextView) pDialog
                    .findViewById(R.id.message);
            titletextView.setText("Please Wait...");
            messageTextView.setText("You are being logged in...");
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            userFunction = new NewUserFunction();
            String loginResponse = "";
            String response = "";

            HashMap<String, String> postDataParams = new HashMap<>();
            postDataParams.put("name", username);
            postDataParams.put("pass", password);


            try {
                try {
                    response = userFunction.login(postDataParams);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                JSONObject jsonObj = new JSONObject(response);
                JSONObject jsonMsg = jsonObj.getJSONObject("message");
                loginResponse = jsonMsg.getString("success");
                System.out.println("loginResponse ==>" + loginResponse);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return loginResponse;
        }


        @Override
        protected void onPostExecute(String token) {
            super.onPostExecute(token);
            pDialog.dismiss();

            try {
                long _token = Long.parseLong(token);

                System.out.println("Token ========================> " + _token);
                dh.emptyTable(DatabaseHandler.MY_ORG_TABLE, "blank", "blank");
                dh.emptyTable(DatabaseHandler.MY_SERVICE_TABLE, "blank", "blank");
                dh.emptyTable(DatabaseHandler.MY_LOCATION_TABLE, "blank", "blank");
                dh.emptyTable(DatabaseHandler.MY_FACILITY_TABLE, "blank", "blank");
                dh.emptyTable(DatabaseHandler.TABLE_USER, "blank", "blank");
                dh.emptyTable(DatabaseHandler.TABLE_REQUESTDATA, "blank", "blank");
                dh.emptyTable(DatabaseHandler.MY_RESERVATIONBUILDING_TABLE, "blank", "blank");
                /*dh.emptyTable(DatabaseHandler.MY_RESERVATION_SPACE_TABLE, "blank", "blank");*/

                dh.saveUserDetail(username, token, password);
                System.out.println("username ==>" + username);
                Intent intent = new Intent(LogIn.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.new_slide_left, R.anim.hold);

            } catch (Exception e) {
                e.printStackTrace();
                cDialog.showCustomDialog(
                        getResources().getString(R.string.logintitle),
                        getResources().getString(R.string.serverNotRespond));
            }
        }

    }

    public class loginFromServerRunnable implements Runnable {
        String username, password;

        public loginFromServerRunnable(String userName, String passsword) {
            this.username = userName;
            this.password = passsword;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " User Name ===>" + username);
            processCommand();
            System.out.println(Thread.currentThread().getName() + " End.");
        }

        private void processCommand() {
            try {
                /*Thread.sleep(5000);*/
                userFunction = new NewUserFunction();
                String loginResponse = "";
                String response = "";

                HashMap<String, String> postDataParams = new HashMap<>();
                postDataParams.put("name", username);
                postDataParams.put("pass", password);


                try {
                    try {
                        response = userFunction.login(postDataParams);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    JSONObject jsonObj = new JSONObject(response);
                    JSONObject jsonMsg = jsonObj.getJSONObject("message");
                    loginResponse = jsonMsg.getString("success");
                    System.out.println("loginResponse ==>" + loginResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
