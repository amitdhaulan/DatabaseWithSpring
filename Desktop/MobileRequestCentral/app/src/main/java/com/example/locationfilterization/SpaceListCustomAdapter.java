package com.example.locationfilterization;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.applidium.shutterbug.FetchableImageView;
import com.example.mobilerequestcentral.R;

import java.util.ArrayList;

import database.helper.DatabaseHandler;
import my.classes.MyLocationClass;

public class SpaceListCustomAdapter extends BaseAdapter {
    // LayoutInflater inflater;
    Activity activity;
    ArrayList<MyLocationClass> spaceArraylist;
    MyLocationClass objClass;
    DatabaseHandler dh;
    ArrayList<String> spaceId;
    int counter;

    public SpaceListCustomAdapter(Activity ac,
                                  ArrayList<MyLocationClass> spaceArrayList) {
        spaceId = new ArrayList<String>();
        dh = new DatabaseHandler(ac);
        activity = ac;
        spaceArraylist = spaceArrayList;
        // inflater = (LayoutInflater) activity
        // .getSystemService(activity.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return spaceArraylist.size();
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
        ViewHolder holder = null;
        Drawable greendr = activity.getResources().getDrawable(
                R.drawable.onlineindicators);
        Drawable reddr = activity.getResources().getDrawable(R.drawable.red);
        System.out.println("Convert View:>>>>>>>>>>>>>>>>>> " + v);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            holder = new ViewHolder();

            v = inflater.inflate(R.layout.space_row, null);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        objClass = spaceArraylist.get(arg0);
        holder.txtid = (TextView) v.findViewById(R.id.spaceId);
        holder.txtName = (TextView) v.findViewById(R.id.spaceName);
        holder.txtorgaId = (TextView) v.findViewById(R.id.organisationId);
        holder.txtorgName = (TextView) v.findViewById(R.id.organisationName);
        holder.txtPeoplename = (TextView) v.findViewById(R.id.peopleName);
        holder.imgIndicator = (FetchableImageView) v
                .findViewById(R.id.imageindicator);

		/*holder.txtid.setText(objClass.get_spaceId());
		holder.txtName.setText(objClass.get_spaceName());
		holder.txtorgaId.setText(objClass.get_organisationId());
		holder.txtorgName.setText(objClass.get_organisationName());
		holder.txtPeoplename.setText(objClass.get_peopleName());

		spaceId = dh.spaceid();
		if (spaceId.contains(objClass.get_spaceId())) {
			counter = counter + 1;
			System.out.println("Counter: " + counter);
			holder.imgIndicator.setImage(null, reddr);

		}
		if (!(spaceId.contains(objClass.get_spaceId()))) {
			holder.imgIndicator.setImage(null, greendr);
		}*/

        return v;
    }

    public static class ViewHolder {
        TextView txtid, txtName, txtorgaId, txtorgName, txtPeoplename;
        FetchableImageView imgIndicator;
    }

}
