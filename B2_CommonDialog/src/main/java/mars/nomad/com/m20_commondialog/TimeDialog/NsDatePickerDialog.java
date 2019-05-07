package mars.nomad.com.m20_commondialog.TimeDialog;

import android.app.DatePickerDialog;
import android.content.Context;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RelativeLayout;

import mars.nomad.com.c2_customview.Dialog.BaseNsDialog;
import mars.nomad.com.c2_customview.R;
import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by SJH, NomadSoft.Inc, 2018-10-10
 */
public class NsDatePickerDialog extends BaseNsDialog {

    private DatePickerDialog.OnDateSetListener listener;
    private DatePicker datePicker;
    private RelativeLayout relativeLayoutCancel;
    private RelativeLayout relativeLayoutConfirm;

    public NsDatePickerDialog(@NonNull Context context, DatePickerDialog.OnDateSetListener listener) {
        super(context);
        this.listener = listener;
        setContentView(R.layout.dialog_ns_date_picker);
        setWindow();
        initView();
        setEvent();
    }

    @Override
    protected void initView() {

        try{

            this.relativeLayoutConfirm = (RelativeLayout) findViewById(R.id.relativeLayoutConfirm);
            this.relativeLayoutCancel = (RelativeLayout) findViewById(R.id.relativeLayoutCancel);
            this.datePicker = (DatePicker) findViewById(R.id.datePicker);

        }catch(Exception e){
            ErrorController.showError(e);
        }
    }

    @Override
    protected void setEvent() {

        try{

            this.relativeLayoutCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dismiss();
                }
            });

            this.relativeLayoutConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onDateSet(null, datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                    dismiss();
                }
            });

        }catch(Exception e){
            ErrorController.showError(e);
        }
    }
}
