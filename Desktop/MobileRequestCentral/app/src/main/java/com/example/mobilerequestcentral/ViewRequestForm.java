package com.example.mobilerequestcentral;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import my.classes.MyRequestClass;

public class ViewRequestForm extends Activity {

    Button back;
    TextView tvReqType, tvOrgName, tvService, tvDescription, tvLocation, dueDate, woID, woStatus, requestdetailtitle;
    Bundle bndl = null;
    String ImageName, locationFromWhereItComes = "";
    ImageView iv;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        bndl = this.getIntent().getExtras();
        if (bndl != null) {
            MyRequestClass objClass = (MyRequestClass) bndl
                    .getSerializable(MyRequestClass.REQUESTCLASSEXTRA);
            locationFromWhereItComes = bndl.getString("location");

            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.view_request_from);

            back = (Button) findViewById(R.id.view_request_button_back);
            requestdetailtitle = (TextView) findViewById(R.id.requestdetail_detail_txt_title);
            tvReqType = (TextView) findViewById(R.id.tvRequestType);
            tvLocation = (TextView) findViewById(R.id.tv_LocationTv);
            tvOrgName = (TextView) findViewById(R.id.tvOrganisationName);
            tvService = (TextView) findViewById(R.id.tvServiceType);
            tvDescription = (TextView) findViewById(R.id.tvDescription);
            dueDate = (TextView) findViewById(R.id.tvWoDueDate);
            woID = (TextView) findViewById(R.id.tvWorkOrderId);
            iv = (ImageView) findViewById(R.id.imageName);
            woStatus = (TextView) findViewById(R.id.tvWoStatus);
            // ImageName = objClass.get_image();

            requestdetailtitle.setText("Request ID: " + objClass.getWORequestID());

            woID.setText(objClass.getWorkOrderID());

            tvReqType.setText(objClass.getRequesttype());
            woStatus.setText(objClass.getWOStatus());
            dueDate.setText(objClass.getDuedate());
            tvLocation.setText(objClass.getLocation());
            tvOrgName.setText(objClass.getOrganization());
            tvService.setText(objClass.getService());
            tvDescription.setText(objClass.getDescription());

            new LoadImageFromSdcard().execute();

        } else {

            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.view_request_from);
        }

    }

    public void goback(View v) {
        if (locationFromWhereItComes.equals("ViewRequestContainerNew")) {
            Intent intent = new Intent(ViewRequestForm.this, ViewRequestContainerNew.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.new_slide_right, R.anim.hold);

        } else if (locationFromWhereItComes.equals("ViewRequestContainer")) {
            Intent intent = new Intent(ViewRequestForm.this,
                    ViewRequestContainer.class);

            bndl.putString("Value", bndl.getString("Value"));
            bndl.putString("requestValue", bndl.getString("requestValue"));

            intent.putExtras(bndl);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.new_slide_right, R.anim.hold);
        }

    }

    @SuppressWarnings("unused")
    private void getImageFromSdCard() {
        String path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM).getAbsolutePath()
                + "/" + "RequestCentral" + "/" + ImageName;
        File file = new File(path);
        Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
        iv.setImageBitmap(bm);

    }

    @Override
    public void onBackPressed() {
        if (locationFromWhereItComes.equals("ViewRequestContainerNew")) {
            Intent intent = new Intent(ViewRequestForm.this, ViewRequestContainerNew.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.new_slide_right, R.anim.hold);

        } else if (locationFromWhereItComes.equals("ViewRequestContainer")) {
            Intent intent = new Intent(ViewRequestForm.this,
                    ViewRequestContainer.class);

            bndl.putString("Value", bndl.getString("Value"));
            bndl.putString("requestValue", bndl.getString("requestValue"));

            intent.putExtras(bndl);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.new_slide_right, R.anim.hold);
        }

    }

    public class LoadImageFromSdcard extends AsyncTask<Void, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Void... params) {
            String path = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DCIM).getAbsolutePath()
                    + "/" + "RequestCentral" + "/" + ImageName;
            File file = new File(path);

            Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());

            return bm;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            iv.setImageBitmap(result);
        }
    }
}
