package mars.nomad.com.c2_customview.Dialog;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.WindowManager;

import mars.nomad.com.c2_customview.R;


/**
 * Created by 김창혁, NomadSoft.Inc on 2018-05-18.
 */
// 로딩을 담당하는 다이어로그
public class LoadingDialog extends BaseNsDialog {

    public LoadingDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_loading);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND, WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        getWindow().setDimAmount(0.8f);
        setCancelable(false);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setEvent() {

    }

    @Override
    public void onBackPressed() {
        dismiss();
    }
}
