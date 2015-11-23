package com.example.mobilerequestcentral;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // list_header titles
    // child data in format of list_header title, child title
    private HashMap<String, List<String>> _listDataChild;

    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.image);
        if (childText.equals("Plumbing and Leaks")) {
            imageView.setImageResource(R.drawable.plumbingleaks);
        } else if (childText.equals("Security")) {
            imageView.setImageResource(R.drawable.security);
        } else if (childText.equals("Fixture and Furniture")) {
            imageView.setImageResource(R.drawable.fixturefurniture);
        } else if (childText.equals("General Repairs")) {
            imageView.setImageResource(R.drawable.generalrepair);
        } else if (childText.equals("Temperature")) {
            imageView.setImageResource(R.drawable.temperature);
        } else if (childText.equals("Interior Services")) {
            imageView.setImageResource(R.drawable.interiorservices);
        } else if (childText.equals("Key Request")) {
            imageView.setImageResource(R.drawable.keyrequest);
        } else if (childText.equals("Electrical & Lighting")) {
            imageView.setImageResource(R.drawable.electricallighting);
        } else if (childText.equals("Equipment Service")) {
            imageView.setImageResource(R.drawable.equipmentservices);
        } else if (childText.equals("Exterior Service")) {
            imageView.setImageResource(R.drawable.exteriorservices);
        } else if (childText.equals("House Keeping")) {
            imageView.setImageResource(R.drawable.housekeeping);
        } else if (childText.equals("Today's reservation")) {
            imageView.setImageResource(R.drawable.todayres);
        } else if (childText.equals("Check in")) {
            imageView.setImageResource(R.drawable.checkin);
        } else if (childText.equals("Make a new reservation")) {
            imageView.setImageResource(R.drawable.newreservation);
        } else if (childText.equals("Request a reservation")) {
            imageView.setImageResource(R.drawable.requestreservaation);
        } else if (childText.equals("Cancel a reservation")) {
            imageView.setImageResource(R.drawable.cancelreservation);
        } else if (childText.equals("My reservations")) {
            imageView.setImageResource(R.drawable.myreservation);
        } else if (childText.equals("Building Evacuation")) {
            imageView.setImageResource(R.drawable.buildingevacuation);
        } else if (childText.equals("Damage / Vandalism")) {
            imageView.setImageResource(R.drawable.damagevandalism);
        } else if (childText.equals("E-911")) {
            imageView.setImageResource(R.drawable.e);
        } else if (childText.equals("Lost, Stolen or Found")) {
            imageView.setImageResource(R.drawable.loststolenorfound);
        } else if (childText.equals("Nuisance Calls / Phishing / Information Security Issue")) {
            imageView.setImageResource(R.drawable.nuisancecallsphishinginformationsecurityissue);
        } else if (childText.equals("Workplace Injury or Illness")) {
            imageView.setImageResource(R.drawable.workplaceinjuryorillness);
        } else if (childText.equals("Workplace Violence or Threat of Violence")) {
            imageView.setImageResource(R.drawable.workplaceviolenceorthreatofviolence);
        } else if (childText.equals("Other / General Security Concern")) {
            imageView.setImageResource(R.drawable.othergeneralsecurityconcern);
        } else if (childText.equals("Fire Alarm Activation")) {
            imageView.setImageResource(R.drawable.firealarmactivation);
        } else if (childText.equals("Page Critical")) {
            imageView.setImageResource(R.drawable.pagecritical);
        } else if (childText.equals("Transportation")) {
            imageView.setImageResource(R.drawable.transportation);
        } else if (childText.equals("Unauthorized Access")) {
            imageView.setImageResource(R.drawable.unauthorizedaccess);
        } else if (childText.equals("Insurance Claims")) {
            imageView.setImageResource(R.drawable.insuranceclaims);
        } else if (childText.equals("HR / Legal Assistance")) {
            imageView.setImageResource(R.drawable.hrlegalassistance);
        } else if (childText.equals("Pandemic / Contagious Illness")) {
            imageView.setImageResource(R.drawable.pandemiccontagiousillness);
        } else if (childText.equals("External Incident Monitoring")) {
            imageView.setImageResource(R.drawable.externalincidentmonitoring);
        } else if (childText.equals("iSOS")) {
            imageView.setImageResource(R.drawable.isos);
        }


        txtListChild.setText(childText);
        /*convertView.setBackgroundColor(Color.parseColor("#8499FE"));*/

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageHeader);
        if (headerTitle.equals("Facilities")) {
            imageView.setImageResource(R.drawable.facilities);
            ;
        } else if (headerTitle.equals("Reservation")) {
            imageView.setImageResource(R.drawable.reservation);
        } else if (headerTitle.equals("Incidents")) {
            imageView.setImageResource(R.drawable.reservation);
        }

        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
