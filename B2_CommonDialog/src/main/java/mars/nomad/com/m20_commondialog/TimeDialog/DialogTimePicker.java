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
import java.util.Locale;

import mars.nomad.com.m20_commondialog.R;


/**
 * Created by SJH, NomadSoft.Inc on 2017-09-27.
 */

public class DialogTimePicker extends DialogFragment {

    private static final int MAX_YEAR = 2099;
    private DatePickerDialog.OnDateSetListener listener;

    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();


        View dialog = inflater.inflate(R.layout.dialog_date_picker, null);
        final NumberPicker hourPicker = (NumberPicker) dialog.findViewById(R.id.picker_second);
        final NumberPicker ampmPicker = (NumberPicker) dialog.findViewById(R.id.picker_first);
        final NumberPicker minutePicker = (NumberPicker) dialog.findViewById(R.id.picker_third);

        hourPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        ampmPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        minutePicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);


        final String[] arrayString= new String[]{"오전", "오후"};
        ampmPicker.setMinValue(0);
        ampmPicker.setMaxValue(arrayString.length-1);

        ampmPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return arrayString[value];
            }
        });
//        ampmPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        Calendar calendar = Calendar.getInstance(Locale.KOREA);

        ampmPicker.setValue(calendar.get(Calendar.AM_PM));


        hourPicker.setMinValue(1);
        hourPicker.setMaxValue(12);
        hourPicker.setValue(calendar.get(Calendar.HOUR) + 1);


        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(59);
        minutePicker.setValue(calendar.get(Calendar.MINUTE));


        builder.setView(dialog)
                // Add action buttons
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDateSet(null, ampmPicker.getValue(), hourPicker.getValue(), minutePicker.getValue());
                    }
                })
                .setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getDialog().cancel();
                    }
                });
        return builder.create();
    }
}