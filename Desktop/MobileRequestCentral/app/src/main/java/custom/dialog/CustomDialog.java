package custom.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.example.locationfilterization.UpdateSpaceListFragment;
import com.example.mobilerequestcentral.R;

@SuppressLint("InflateParams")
public class CustomDialog {
    Activity ac;
    CustomDialogInterface cdi;
    CustomDialogInterface cdii;
    String building = null;
    String floor = null;
    String property = null;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    public CustomDialog(Activity activity) {
        ac = activity;
        cdi = null;
        cdii = null;
    }

    public CustomDialog(Activity activity, CustomDialogInterface ci) {
        ac = activity;
        cdii = ci;
    }

    public CustomDialog(Activity activity, CustomDialogInterface ci, String building) {
        ac = activity;
        cdii = ci;
    }


    public CustomDialog(Activity ac2, CustomDialogInterface ci, String floor,
                        String building, String property) {
        cdi = ci;
        cdii = null;
        ac = ac2;
        this.floor = floor;
        this.building = building;
        this.property = property;
    }

    public void showCustomDialog(final String title, final String message) {

        Button btn_dialog_ok;
        final Dialog dialog = new Dialog(ac, android.R.style.Theme_Translucent);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);

        final View view = ac.getLayoutInflater().inflate(
                R.layout.custom_dialog, null);
        dialog.setContentView(view);

        dialog.getWindow().getAttributes().windowAnimations = R.style.right_dialog_animation;
        TextView Dialog_Title = (TextView) dialog
                .findViewById(R.id.dialog_title);
        TextView Dialog_Message = (TextView) dialog
                .findViewById(R.id.dialogmessage);

        Dialog_Title.setText(title);
        Dialog_Message.setText(message);

        btn_dialog_ok = (Button) dialog.findViewById(R.id.dialog_btnok);

        btn_dialog_ok.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                v.startAnimation(buttonClick);
                dialog.dismiss();

                if (cdi != null) {
                    System.out
                            .println("=============helooo====================");
                    cdi.onCustomDialogClick(floor, building, property);
                }
                if (cdii != null) {
                    System.out.println("cdiii===========>" + cdii);
                    cdii.onCustomDialogFieldBlank();
                }
                if (message.equals("Space Updated Succesfully")) {
                    UpdateSpaceListFragment fragment = new UpdateSpaceListFragment();
                    fragment.updated(message, message, message);
                }
            }
        });

        dialog.show();
    }

}
