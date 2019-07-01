package mars.nomad.com.B1_post.CustomView;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

import mars.nomad.com.B1_post.DataModel.PostTextDataModel;
import mars.nomad.com.B1_post.R;
import mars.nomad.com.B1_post.Util.PostConstants;
import mars.nomad.com.l0_base.Abstract.AbstractTextWatcher;
import mars.nomad.com.l0_base.Callback.CommonCallback;
import mars.nomad.com.l0_base.Logger.ErrorController;


/**
 * Created by 김창혁, NomadSoft.Inc on 2019-03-26.
 */
public class CustomPostEditView extends CustomPostBaseView {


    private EditText currentEditText;

    private EditText editTextContents1;
    private EditText editTextContents2;
    private EditText editTextContents3;
    private EditText editTextContents4;
    private EditText editTextContents5;
    private EditText editTextContents6;

    public CustomPostEditView(Context context) {
        super(context);
    }

    public CustomPostEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initVIew() {
        try {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.custom_post_edit_text_view, this, false);

            editTextContents1 = (EditText) view.findViewById(R.id.editTextContents1);
            editTextContents2 = (EditText) view.findViewById(R.id.editTextContents2);
            editTextContents3 = (EditText) view.findViewById(R.id.editTextContents3);
            editTextContents4 = (EditText) view.findViewById(R.id.editTextContents4);
            editTextContents5 = (EditText) view.findViewById(R.id.editTextContents5);
            editTextContents6 = (EditText) view.findViewById(R.id.editTextContents6);


            addView(view);
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    /**
     * 텍스트는 두가지 타입이 있음. 구버전, 신버전
     * 구버전 : 평범한 평문으로 들어옴
     * 신버전 : 사이즈 등을 합친 json으로 들어옴
     *
     * @param contents
     */
    @Override
    public void setContents(String contents, final String accessToken) {
        try {

            editTextContents1.setVisibility(GONE);
            editTextContents2.setVisibility(GONE);
            editTextContents3.setVisibility(GONE);
            editTextContents4.setVisibility(GONE);
            editTextContents5.setVisibility(GONE);
            editTextContents6.setVisibility(GONE);


            try {

                if (PostConstants.isOld) {

                    // 모든 글이 평문이므로 그냥 셋팅
                    editTextContents1.setVisibility(VISIBLE);
                    editTextContents1.setText(contents);
                    currentEditText = editTextContents1;

                } else {
                    PostTextDataModel text = new Gson().fromJson(contents, PostTextDataModel.class);


                    // 텍스트 사이즈
                    switch (text.getFontSize()) {
                        case 1: // 가장 작음
                            setTextData(text, editTextContents1);
                            break;
                        case 2:
                            setTextData(text, editTextContents2);
                            break;
                        case 3:
                            setTextData(text, editTextContents3);
                            break;
                        case 4:
                            setTextData(text, editTextContents4);
                            break;
                        case 5:
                            setTextData(text, editTextContents5);
                            break;
                        case 6: // 가장 큼
                            setTextData(text, editTextContents6);
                            break;
                        default: // 그외의 값일 경우 기본으로 셋팅
                            setTextData(text, editTextContents1);
                            break;
                    }

                }

            } catch (Exception e) {   // 에러가 잡힐경우 구버전

                ErrorController.showError(e);
            }


        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    private void setTextData(PostTextDataModel text, EditText editText) {
        try {

            editText.setVisibility(VISIBLE);

            // Bold 유무 && Italic 유무

            // Bold + Italic
            if (text.getIsBold() && text.getIsItalic()) {
                editText.setTypeface(null, Typeface.BOLD_ITALIC);
            } else if (text.getIsBold()) { // Bold Only
                editText.setTypeface(null, Typeface.BOLD);
            } else if (text.getIsItalic()) { // Italic Only
                editText.setTypeface(null, Typeface.ITALIC);
            }

            editText.setText(text.getContents());

            currentEditText = editText;

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    public void removeTextWatcher(TextWatcher textWatcherData) {
        try {
            currentEditText.removeTextChangedListener(textWatcherData);
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void setTextWatcher(TextWatcher textWatcherData) {
        try {

            currentEditText.addTextChangedListener(textWatcherData);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }
}
