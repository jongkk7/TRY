package mars.nomad.com.c2_customview.Dialog;

import android.app.DatePickerDialog;
import android.content.Context;

import java.util.Calendar;
import java.util.GregorianCalendar;

import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by SJH on 2017-03-02.
 */

public class DateView {

    private Context mContext;
    private DatePickerDialog.OnDateSetListener mDateSetListener = null;

    public DateView(Context mContext) {
        this.mContext = mContext;
    }

    public void setListener(DatePickerDialog.OnDateSetListener mDateSetListener) {
        this.mDateSetListener = mDateSetListener;
    }

    public DatePickerDialog getDatePickerDialog() {
        GregorianCalendar calendar = new GregorianCalendar();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        ErrorController.showMessage("Check Calender - " + year + ", " + month + ", " + day);
        DatePickerDialog dialog = new DatePickerDialog(mContext, mDateSetListener, 1980, month, day);
        return dialog;
    }

    public DatePickerDialog getDatePickerDialog(int year, int month, int day) {
        DatePickerDialog dialog = new DatePickerDialog(mContext, DatePickerDialog.THEME_DEVICE_DEFAULT_LIGHT, mDateSetListener, year, month, day);
        return dialog;
    }

    public static class DatePickerBuilder {

        private Context mContext;
        private DateView mDateView;
        private DatePickerDialog.OnDateSetListener mDateSetListener = null;

        public DatePickerBuilder(Context mContext) {
            this.mContext = mContext;
        }

        public DatePickerBuilder setEvent(DatePickerDialog.OnDateSetListener listener) {
            this.mDateSetListener = listener;
            return this;
        }

        public DatePickerDialog create() {
            this.mDateView = new DateView(this.mContext);
            this.mDateView.setListener(this.mDateSetListener);
            return this.mDateView.getDatePickerDialog();
        }

        public DatePickerDialog create(int year, int month, int day) {
            this.mDateView = new DateView(this.mContext);
            this.mDateView.setListener(this.mDateSetListener);
            return this.mDateView.getDatePickerDialog(year, month, day);
        }

    }


}
