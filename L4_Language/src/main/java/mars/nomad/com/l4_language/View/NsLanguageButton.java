package mars.nomad.com.l4_language.View;

import android.content.Context;
import android.util.AttributeSet;

import mars.nomad.com.l4_language.ErrorController.LgErrorController;
import mars.nomad.com.l4_language.NsLanguagePack;


/**
 * Created by 김창혁, NomadSoft.Inc on 2018-02-06.
 */

public class NsLanguageButton extends androidx.appcompat.widget.AppCompatButton {
    public NsLanguageButton(Context context) {
        super(context);
    }

    public NsLanguageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setLanguageText() {

        try {
            String tag = (String) this.getTag();

            this.setText(NsLanguagePack.getText(tag));


        } catch (Exception e) {
            LgErrorController.showError(e);
        }

    }
}
