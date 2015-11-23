package com.example.mobilerequestcentral;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import my.classes.MyRequestClass;

public class RequestListCustomAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Activity activity;

    ArrayList<MyRequestClass> ArrLst = new ArrayList<MyRequestClass>();

    @SuppressWarnings("static-access")
    public RequestListCustomAdapter(Activity ac, ArrayList<MyRequestClass> ArrLst) {
        activity = ac;
        this.ArrLst = ArrLst;

        inflater = (LayoutInflater) activity
                .getSystemService(activity.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return ArrLst.size();
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
            v = inflater.inflate(R.layout.view_request_row, null);
        }
        MyRequestClass obj = new MyRequestClass();
        obj = ArrLst.get(arg0);
        TextView txtid = (TextView) v.findViewById(R.id.woid);
        TextView duedate = (TextView) v.findViewById(R.id.duedate);
        TextView txtStatus = (TextView) v.findViewById(R.id.status);
        TextView txtReqType = (TextView) v.findViewById(R.id.ReqType);

        txtid.setText(obj.getWORequestID());
        duedate.setText(obj.getDuedate());
        txtStatus.setText(obj.getWOStatus());
        txtReqType.setText(obj.getRequesttype());
        return v;
    }

}
