package mars.nomad.com.c2_customview.Dialog;

import android.app.Dialog;
import android.content.Context;
import androidx.annotation.NonNull;
import android.view.WindowManager;

import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by SJH, NomadSoft.Inc on 2017-11-02.
 */

public abstract class BaseNsDialog extends Dialog {

    public BaseNsDialog(@NonNull Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);

    }

    protected abstract void initView();

    protected abstract void setEvent();

    protected void setWindow(){

        try {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND, WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            getWindow().setDimAmount(0.8f);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

}
