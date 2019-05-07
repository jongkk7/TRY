package mars.nomad.com.l4_language.View;

import android.content.Context;
import android.util.AttributeSet;

import mars.nomad.com.l4_language.ErrorController.LgErrorController;
import mars.nomad.com.l4_language.NsLanguagePack;


/**
 * Created by 김창혁, NomadSoft.Inc on 2018-02-06.
 */

public class NsLanguageTextView extends androidx.appcompat.widget.AppCompatTextView {

    public NsLanguageTextView(Context context) {
        super(context);
    }

    public NsLanguageTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setLanguageText() {

        try {
            String tag = (String) this.getTag();

            if (tag == null || tag == "null") {
                LgErrorController.showMessage("[NsLanguageTextView] NULL : " + this.getText() );
            }

            this.setText(NsLanguagePack.getText(tag));


        } catch (Exception e) {
            LgErrorController.showError(e);
        }

    }
}
