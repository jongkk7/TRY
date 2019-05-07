package mars.nomad.com.m20_commondialog.CommonListDialog;

import android.app.Dialog;
import android.content.Context;
import androidx.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import mars.nomad.com.l0_base.Callback.CommonCallback;

import mars.nomad.com.l12_applicationutil.String.StringChecker;
import mars.nomad.com.l4_language.NsLanguagePack;
import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.m20_commondialog.R;


/**
 * Created by SJH on 2017-08-09.
 */

public class CommonInputDialog extends Dialog {

    private String mTitle;
    private String mHint;
    private String mDescription;
    private String mPredefinedValue;//미리 설정된 값
    private CommonCallback.SingleSelectionCallback<String> mCallback;


    private TextView textViewTitle;
    private RelativeLayout relativeLayoutClose;
    private TextView textViewInputGuide;
    private EditText editTextInput;
    private TextView textViewConfirm;


    public CommonInputDialog(@NonNull Context context, String title, String description, String hint, String predefinedValue, CommonCallback.SingleSelectionCallback<String> callback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        setContentView(R.layout.p5_common_input_dialog);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND, WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setDimAmount(0.3f);
        this.mTitle = title;
        this.mHint = hint;
        this.mDescription = description;
        this.mPredefinedValue = predefinedValue;
        this.mCallback = callback;

        initView();
        setEvent();
        setView();
    }


    private void initView() {

        this.textViewConfirm = (TextView) findViewById(R.id.textViewConfirm);
        this.editTextInput = (EditText) findViewById(R.id.editTextInput);
        this.textViewInputGuide = (TextView) findViewById(R.id.textViewInputGuide);
        this.relativeLayoutClose = (RelativeLayout) findViewById(R.id.relativeLayoutClose);
        this.textViewTitle = (TextView) findViewById(R.id.textViewTitle);
    }

    private void setEvent() {

        this.relativeLayoutClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//닫기
                dismiss();
            }
        });

        this.editTextInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {//키보드에서 완료 버튼 클릭
                if (i == EditorInfo.IME_ACTION_SEARCH) {

                    textViewConfirm.performClick();
                    return true;
                }
                return false;
            }
        });

        this.textViewConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//확인 버튼 클릭

                String input = editTextInput.getText().toString();

                if (StringChecker.isStringNotNull(input)) {
                    if (mCallback != null) {
                        mCallback.onSelection(input);
                        dismiss();
                    } else {
                        ErrorController.showInfoToast(getContext(), "콜백이 설정되어 있지 않습니다.", 2);
                    }
                } else {
                    ErrorController.showInfoToast(getContext(), NsLanguagePack.getText("p55_bottom_something_wrong_no_data"), 1);
                }
            }
        });
    }//end of setEvent

    /**
     * 할당된 값들로 뷰를 꾸민다.
     */
    private void setView() {
        try {

            if (StringChecker.isStringNotNull(mTitle)) {
                textViewTitle.setText(mTitle);
            } else {
                textViewTitle.setText(getContext().getText(R.string.app_name));
            }

            if (StringChecker.isStringNotNull(mDescription)) {
                textViewInputGuide.setText(mDescription);
            } else {
                textViewInputGuide.setText("");
            }

            if (StringChecker.isStringNotNull(mHint)) {
                editTextInput.setHint(mHint);
            } else {
                editTextInput.setHint("");
            }

            if (StringChecker.isStringNotNull(mPredefinedValue)) {
                editTextInput.setText(mPredefinedValue);
            } else {
                editTextInput.setText("");
            }

            textViewConfirm.setText(NsLanguagePack.getText("p0_common_confirm_btn"));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    /**
     * 빌더 클래스
     */
    public static class Builder {

        private String mTitle;

        private Context mContext;

        private String mDescription = "";

        private String mHint;

        private String mPredefinedValue = "";

        private CommonCallback.SingleSelectionCallback<String> mCallback;

        public Builder(Context context) {
            this.mContext = context;
        }

        /**
         * 제목을 설정한다.
         *
         * @param title
         * @return
         */
        public Builder setTitle(String title) {
            this.mTitle = title;
            return this;
        }

        /**
         * 입력창에 표기될 hint를 설정한다.
         *
         * @param hint
         * @return
         */
        public Builder setHint(String hint) {
            this.mHint = hint;
            return this;
        }

        /**
         * 입력창 위에 표시될 설명이 있으면 설정한다.
         *
         * @param description
         * @return
         */
        public Builder setDescription(String description) {
            this.mDescription = description;
            return this;
        }

        /**
         * 입력값이 이미 있고, 이를 수정하는 경우, 이미 있는 입력값을 설정한다.
         *
         * @param predefinedValue
         * @return
         */
        public Builder setPredefinedValue(String predefinedValue) {
            this.mPredefinedValue = predefinedValue;
            return this;
        }

        /**
         * 변경된 값이 null이거나 공백이 아니면 돌려줄 수 있는 콜백을 설정한다.
         *
         * @param callback
         * @return
         */
        public Builder setCallback(CommonCallback.SingleSelectionCallback<String> callback) {
            this.mCallback = callback;
            return this;
        }

        /**
         * 설정한 값에 따라 인풋 다이얼로그를 표현한다.
         *
         * @return
         */
        public void show() {
            new CommonInputDialog(mContext, mTitle, mDescription, mHint, mPredefinedValue, mCallback).show();
        }
    }


}
