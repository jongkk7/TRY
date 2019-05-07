package mars.nomad.com.c2_customview.EditText;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import mars.nomad.com.l0_base.Abstract.AbstractTextWatcher;

import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.l12_applicationutil.String.StringProcesser;

@SuppressLint("AppCompatCustomView")
public class NsNumberEditText extends EditText {

    private String cached;

    public NsNumberEditText(Context context) {
        super(context);
        setEvent();
    }

    public NsNumberEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEvent();
    }

    private void setEvent() {

        try {

            addTextChangedListener(new AbstractTextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if (s != null && s.length() > 0 && !s.toString().equalsIgnoreCase(cached)) {

                        String pureNumber = s.toString().replace(",", "");

                        String formattedText = StringProcesser.getFormattedNumber(Long.parseLong(pureNumber));
                        cached = formattedText;
                        setText(formattedText);
                        setSelection(formattedText.length());
                    }
                }
            });

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    /*
    @Override
    public Editable getText() {

        Editable editable = super.getText();

        try{

            String temp = editable.toString();

            temp.replace(",", "");

            editable.clear();
            editable.append(temp);

        }catch(Exception e){
            ErrorController.showError(e);
        }

        return editable;
    }*/

}
