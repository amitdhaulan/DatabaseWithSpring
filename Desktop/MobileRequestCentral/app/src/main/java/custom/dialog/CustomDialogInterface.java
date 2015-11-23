package custom.dialog;

import java.io.Serializable;

public interface CustomDialogInterface extends Serializable {

    public void onCustomDialogClick(String floor, String building, String property);

    public void onCustomDialogFieldBlank();
}
