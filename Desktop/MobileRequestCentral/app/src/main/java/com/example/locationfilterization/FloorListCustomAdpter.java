package com.example.locationfilterization;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.mobilerequestcentral.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FloorListCustomAdpter extends BaseAdapter {
    public static boolean back = false;
    Context context;
    LayoutInflater inflater;
    Activity activity;
    String floor;
    String property;
    String building;

    // FragmentManager fm;

    @SuppressWarnings("static-access")
    public FloorListCustomAdpter(Activity ac, String floor, String property,
                                 String building/* , FragmentManager fm */) {
        context = ac;
        activity = ac;
        this.floor = floor;
        this.property = property;
        this.building = building;
        // this.fm = fm;
        inflater = (LayoutInflater) activity
                .getSystemService(activity.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int arg0, View convertView, ViewGroup arg2) {
        View v = convertView;
        if (convertView == null) {
            v = inflater.inflate(R.layout.floor_row, null);
        }
        TextView txtid = (TextView) v.findViewById(R.id.floorId);
        TextView txtName = (TextView) v.findViewById(R.id.floorName);

        txtid.setText(floor);
        txtName.setText("Floor-" + floor);
        Button viewSpace = (Button) v.findViewById(R.id.viewSpaces);
        viewSpace.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("floor", floor);
                bundle.putString("property", property);
                bundle.putString("building", building);

                Intent i = new Intent(context, SpaceListFragment.class);
                i.putExtras(bundle);
                context.startActivity(i);
                ((Activity) context).overridePendingTransition(
                        R.anim.new_slide_left, R.anim.hold);

                // SpaceListFragment frag = new SpaceListFragment();
                // frag.setArguments(bundle);
                // FragmentTransaction transaction = fm.beginTransaction();
                // transaction.replace(R.id.floor_space_right_fragment, frag);
                // transaction.commit();

                CopyReadAssets(floor);

            }
        });
        return v;
    }

    private void CopyReadAssets(String floor2) {
        AssetManager assetManager = activity.getAssets();
        InputStream in = null;
        OutputStream out = null;
        File file = null;
        try {
            if (floor2.equals("01-First Floor")) {
                in = assetManager.open("drawing.dwg");
                file = new File(Environment.getExternalStorageDirectory(),
                        "drawing.dwg");
            } else {
                in = assetManager
                        .open("3000 BRIDGE PARKWAY 2ND FLOOR - Copy.dwg");
                file = new File(Environment.getExternalStorageDirectory(),
                        "3000 BRIDGE PARKWAY 2ND FLOOR - Copy.dwg");
            }
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

        if (!back) {
            showAlert(floor2);
        }

    }

    private void showAlert(final String floor2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Image Viewer");
        builder.setMessage("Are you sure to display drawing image?");
        builder.setPositiveButton("Yes, Please",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        showPdfReader(floor2);
                    }

                });
        builder.setNegativeButton("No, Thanks", null);
        builder.create().show();

    }

    private void showPdfReader(String floor2) {
        boolean installed = isAppInstalled("com.dwgsee.dwgviewer");
        if (installed) {
            Intent intent;
            intent = new Intent(Intent.ACTION_VIEW);
            if (floor2.equals("01-First Floor")) {
                intent.setDataAndType(
                        Uri.parse("file://"
                                + Environment.getExternalStorageDirectory()
                                + "/drawing.dwg"), "application/dwg");
            } else {
                intent.setDataAndType(
                        Uri.parse("file://"
                                + Environment.getExternalStorageDirectory()
                                + "/3000 BRIDGE PARKWAY 2ND FLOOR - Copy.dwg"),
                        "application/dwg");
            }
            try {
                activity.startActivity(intent);
            } catch (ActivityNotFoundException e) {

            }
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("No Application Found");
            builder.setMessage("Download one from Android Market?");
            builder.setPositiveButton("Yes, Please",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent marketIntent = new Intent(Intent.ACTION_VIEW);
                            marketIntent.setData(Uri
                                    .parse("https://play.google.com/store/apps/details?id=com.dwgsee.dwgviewer&hl=en"));
                            activity.startActivity(marketIntent);
                        }
                    });
            builder.setNegativeButton("No, Thanks", null);
            builder.create().show();
        }

    }

    private boolean isAppInstalled(String packageName) {
        PackageManager pm = activity.getPackageManager();
        boolean installed = false;
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            installed = false;
        }
        return installed;
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }
}