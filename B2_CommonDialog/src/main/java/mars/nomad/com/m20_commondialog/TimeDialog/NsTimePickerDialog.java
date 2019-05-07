package mars.nomad.com.m20_commondialog.TimeDialog;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class NsTimePickerDialog extends DialogFragment {

    private Context mContext = null;

    private TimePickerDialog.OnTimeSetListener mListener;

    private int hour = 0;

    private int min = 0;

    public void setContext(Context context) {

        this.mContext = context;
    }

    public void setListener(TimePickerDialog.OnTimeSetListener mListener) {
        this.mListener = mListener;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMin(int min) {
        this.min = min;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if(hour == 0 && min == 0){

            Calendar calendar = Calendar.getInstance();
            hour = calendar.get(Calendar.HOUR_OF_DAY);
            min = calendar.get(Calendar.MINUTE);
        }

        TimePickerDialog mTimePickerDialog = new TimePickerDialog(mContext, mListener, hour, min, false);
        return mTimePickerDialog;
    }


}
