package custom.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

@SuppressLint("ValidFragment")
public class DateDialogFragment extends DialogFragment implements
        OnDateSetListener {
    CustomDialog cd;
    String tag;
    private int year, month, day;
    private dateDialogInterface dInterface;

    public DateDialogFragment(dateDialogInterface di, Activity activity) {
        dInterface = di;
        cd = new CustomDialog(activity);
    }

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        tag = this.getTag();
        System.out.println(tag);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int selected_year, int monthOfYear,
                          int dayOfMonth) {
        view.updateDate(selected_year, monthOfYear, dayOfMonth);
        dInterface.OnDatePickerClick(pad(dayOfMonth), pad(monthOfYear + 1), pad(selected_year), tag);


    }

}