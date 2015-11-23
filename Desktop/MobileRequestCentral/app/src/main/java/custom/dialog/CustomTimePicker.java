package custom.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.locationfilterization.UpdateSpaceListFragment;
import com.example.mobilerequestcentral.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("ValidFragment")
public class CustomTimePicker extends DialogFragment {
    static Context _context;
    /*static final int DIALOG_STARTTIME_PICKER = 2;*/
    /*static final int DIALOG_STOPTIME_PICKER = 3;*/
    final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
    /*int i = 1;*/
    String time = "";
    CustomDatePickerValueInterface cdpInterface;
	/*Boolean rvalue = true;*/

    public CustomTimePicker(Context context, TextView timeEndTextView,
                            CustomDatePickerValueInterface cdpInterface2) {
        cdpInterface = cdpInterface2;
        _context = context;
        customTimePickerDialog(timeEndTextView);
    }

    @SuppressLint("SimpleDateFormat")
    public static Boolean CalculateTimeDifference(String dateStart,
                                                  String dateStop) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (d2.getTime() > d1.getTime()) {
            return true;
        } else {
            return false;
        }

    }

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    protected void customTimePickerDialog(final TextView timeEndTextView) {
        Button btn_dialog_set, btn_dialog_cancel;

        final Dialog dialog = new Dialog(_context,
                android.R.style.Theme_Translucent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        final View view = ((Activity) _context).getLayoutInflater().inflate(
                R.layout.customtimepicker, null);
        dialog.setContentView(view);
        final TimePicker tPicker = (TimePicker) dialog
                .findViewById(R.id.customtimePicker);
        TextView customTimePickerDialog_Title = (TextView) dialog
                .findViewById(R.id.timepicker_dialog_title);
        customTimePickerDialog_Title.setText("Current Time : "
                + pad(tPicker.getCurrentHour()) + ":"
                + pad(tPicker.getCurrentMinute()));
        btn_dialog_set = (Button) dialog
                .findViewById(R.id.timepicker_dialog_btn_set);
        btn_dialog_set.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                time = pad(tPicker.getCurrentHour()) + ":"
                        + pad(tPicker.getCurrentMinute());
                if (UpdateSpaceListFragment.IsEndTime) {
                    // String strtTime=UpdateSpaceListFragment.StartTime;
                    timeEndTextView.setText(time);
                    System.out.println(time);
                    dialog.dismiss();

                    cdpInterface.checkdatevalue(time);

                } else {
                    timeEndTextView.setText(time);
                    System.out.println(time);
                    dialog.dismiss();
                }

            }
        });
        btn_dialog_cancel = (Button) dialog
                .findViewById(R.id.timepicker_dialog_btn_cancel);
        btn_dialog_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                dialog.dismiss();
                time = "";
            }
        });
        dialog.show();

    }

}
