package custom.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mobilerequestcentral.R;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    ArrayList<String> arraylist = new ArrayList<String>();
    LayoutInflater inflater;
    Activity activity;

    @SuppressWarnings("static-access")
    public CustomAdapter(ArrayList<String> properetyArraylist, Activity ac) {
        activity = ac;
        arraylist = properetyArraylist;
        inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return arraylist.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder = null;
        if (convertView == null) {
            v = inflater.inflate(R.layout.property_listview, null);
            holder = new ViewHolder();
            holder.txtpropertyname = (TextView) v.findViewById(R.id.textView1);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.txtpropertyname.setText(arraylist.get(position));
        return v;
    }

    static class ViewHolder {
        TextView txtpropertyname;

    }
}
