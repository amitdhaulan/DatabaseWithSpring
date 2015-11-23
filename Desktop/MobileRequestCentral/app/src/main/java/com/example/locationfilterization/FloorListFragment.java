package com.example.locationfilterization;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.example.mobilerequestcentral.R;
import com.example.mobilerequestcentral.requestcentral;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FloorListFragment extends Activity {
    ListView lv;
    FloorListCustomAdpter adapter;
    String floor;
    String property;
    String building;
    Button btnShowImage;
    Bundle bundle;

    // Button btnViewSpace;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.floorlist);
        // btnShowImage = (Button) v.findViewById(R.id.showImage);
        bundle = getIntent().getExtras();
        floor = bundle.getString("floor");
        property = bundle.getString("property");
        building = bundle.getString("building");
        lv = (ListView) findViewById(R.id.floor_listview);

        // FragmentManager fm = getFragmentManager();
        adapter = new FloorListCustomAdpter(this, floor, property, building/*
                                                                             * ,
																			 * fm
																			 */);

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                arg1.setSelected(true);

                System.out.println("Helo");
				/*
				 * Bundle bundle = new Bundle(); bundle.putString("floor",
				 * floor); bundle.putString("property", property);
				 * bundle.putString("building", building); SpaceListFragment
				 * frag = new SpaceListFragment(); frag.setArguments(bundle);
				 * FragmentTransaction transaction =
				 * getFragmentManager().beginTransaction();
				 * transaction.replace(R.id.floor_space_right_fragment, frag);
				 * transaction.commit();
				 */

            }
        });
    }

    @SuppressWarnings("unused")
    private void CopyReadAssets() {
        AssetManager assetManager = this.getAssets();

        InputStream in = null;
        OutputStream out = null;

        try {
            in = assetManager.open("test.pdf");
            File file = new File(Environment.getExternalStorageDirectory(),
                    "test.pdf");
            out = new FileOutputStream(file);

            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
        Intent intent;
        intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(
                Uri.parse("file://" + Environment.getExternalStorageDirectory()
                        + "/test.pdf"), "application/pdf");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // No application to view, ask to download one
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("No Application Found");
            builder.setMessage("Download one from Android Market?");
            builder.setPositiveButton("Yes, Please",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent marketIntent = new Intent(Intent.ACTION_VIEW);
                            marketIntent.setData(Uri
                                    .parse("market://details?id=com.adobe.reader"));
                            startActivity(marketIntent);
                        }
                    });
            builder.setNegativeButton("No, Thanks", null);
            builder.create().show();
        }
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(FloorListFragment.this, requestcentral.class);
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(R.anim.new_slide_right, R.anim.hold);
    }
}
