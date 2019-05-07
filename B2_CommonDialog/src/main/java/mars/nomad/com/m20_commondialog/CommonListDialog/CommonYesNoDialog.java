package mars.nomad.com.m20_commondialog.CommonListDialog;

import android.content.Context;
import androidx.annotation.NonNull;
import android.widget.Button;
import android.widget.TextView;

import mars.nomad.com.c2_customview.Dialog.BaseNsDialog;
import mars.nomad.com.l0_base.Callback.CommonCallback;
import mars.nomad.com.l0_base.Callback.SingleClickListener;

import mars.nomad.com.l4_language.NsLanguagePack;
import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.m20_commondialog.R;


/**
 * Created by SJH, NomadSoft.Inc on 2018-02-21.
 */

public class CommonYesNoDialog extends BaseNsDialog {

    private String mTitle;
    private String mMessage;

    private String mPositiveText;
    private String mNegativeText;

    private CommonCallback.UserSelectionCallback mCallback;
    private TextView textViewTitle;
    private TextView textViewMessage;
    private Button buttonNegative;
    private Button buttonPositive;


    public CommonYesNoDialog(@NonNull Context context, String mTitle, String mMessage, String mPositiveText, String mNegativeText, CommonCallback.UserSelectionCallback mCallback) {
        super(context);
        this.mTitle = mTitle;
        this.mMessage = mMessage;
        this.mPositiveText = mPositiveText;
        this.mNegativeText = mNegativeText;
        this.mCallback = mCallback;
        setContentView(R.layout.p0_common_yes_no_dialog);
        setWindow();
        initView();
        setEvent();
        setUI();
    }


    @Override
    protected void initView() {

        try {

            this.buttonPositive = (Button) findViewById(R.id.buttonPositive);
            this.buttonNegative = (Button) findViewById(R.id.buttonNegative);
            this.textViewMessage = (TextView) findViewById(R.id.textViewMessage);
            this.textViewTitle = (TextView) findViewById(R.id.textViewTitle);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    @Override
    protected void setEvent() {

        try {

            this.buttonNegative.setOnClickListener(new SingleClickListener(new CommonCallback.SingleActionCallback() {
                @Override
                public void onAction() {

                    mCallback.onNegativeSelection();
                    dismiss();
                }
            }));

            this.buttonPositive.setOnClickListener(new SingleClickListener(new CommonCallback.SingleActionCallback() {
                @Override
                public void onAction() {

                    mCallback.onPositiveSelection("");
                    dismiss();
                }
            }));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    private void setUI() {

        try {

            //제목
            if(mTitle != null){

                textViewTitle.setText(mTitle);
            }else{

                textViewTitle.setText(getContext().getString(R.string.app_name));
            }

            //안내
            if(mMessage != null){

                textViewMessage.setText(mMessage);
            }else{

                textViewMessage.setText("");
            }

            //취소
            if(mNegativeText != null){

                buttonNegative.setText(mNegativeText);

            }else{
                buttonNegative.setText(NsLanguagePack.getText("p0_common_cancel_btn"));
            }

            //확인
            if(mPositiveText != null){

                buttonPositive.setText(mPositiveText);

            }else{
                buttonPositive.setText(NsLanguagePack.getText("p0_common_confirm_btn"));
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

}
