package mars.nomad.com.m20_commondialog.TimeDialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import java.util.Calendar;

import mars.nomad.com.m20_commondialog.R;


/**
 * Created by SJH, NomadSoft.Inc on 2017-09-26.
 */

public class DialogMonthYearPicker extends DialogFragment {

    private static final int MAX_YEAR = 2099;
    private DatePickerDialog.OnDateSetListener listener;

    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);

        View dialog = inflater.inflate(R.layout.dialog_date_picker, null);
        final NumberPicker monthPicker = (NumberPicker) dialog.findViewById(R.id.picker_second);
        final NumberPicker yearPicker = (NumberPicker) dialog.findViewById(R.id.picker_first);
        final NumberPicker picker_date = (NumberPicker) dialog.findViewById(R.id.picker_third);

        monthPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        yearPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        picker_date.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setValue(cal.get(Calendar.MONTH) + 1);

        monthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {

                Calendar newCal = Calendar.getInstance();
                int newMaxDate = newCal.getActualMaximum(Calendar.DAY_OF_MONTH);

                if (picker_date.getValue() > newMaxDate) {
                    picker_date.setValue(1);
                }

                picker_date.setMaxValue(newMaxDate);

            }
        });

        int year = cal.get(Calendar.YEAR);
        yearPicker.setMinValue(year);
        yearPicker.setMaxValue(MAX_YEAR);
        yearPicker.setValue(year);

        int month = cal.get(Calendar.MONTH);


        int maxDate = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        picker_date.setMinValue(1);
        picker_date.setMaxValue(maxDate);
        picker_date.setValue(cal.get(Calendar.DATE));


        builder.setView(dialog)
                // Add action buttons
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDateSet(null, yearPicker.getValue(), monthPicker.getValue(), picker_date.getValue());
                    }
                })
                .setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DialogMonthYearPicker.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
