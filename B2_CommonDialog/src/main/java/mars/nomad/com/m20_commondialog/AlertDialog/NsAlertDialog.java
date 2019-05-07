package mars.nomad.com.m20_commondialog.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;

import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by SJH, NomadSoft.Inc, 2018-09-07
 */
public class NsAlertDialog {

    public static void showTwoChoiceDialog(Context context, String message, String confirmTxt, String cancelTxt, DialogInterface.OnClickListener confirmClickListener) {

        try {

            if (context != null) {

                new AlertDialog.Builder(context)
                        .setMessage(message)
                        .setPositiveButton(confirmTxt, confirmClickListener)
                        .setNegativeButton(cancelTxt, null)
                        .show();
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public static void showTwoChoiceDialog(Context context, String message, String confirmTxt, String cancelTxt, DialogInterface.OnClickListener confirmClickListener, DialogInterface.OnClickListener cancelClickListener) {

        try {

            if (context != null) {

                new AlertDialog.Builder(context)
                        .setMessage(message)
                        .setPositiveButton(confirmTxt, confirmClickListener)
                        .setNegativeButton(cancelTxt, cancelClickListener)
                        .show();
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }
}
