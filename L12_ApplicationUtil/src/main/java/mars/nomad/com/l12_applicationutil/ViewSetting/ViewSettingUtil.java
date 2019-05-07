package mars.nomad.com.l12_applicationutil.ViewSetting;



import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.l12_applicationutil.String.StringChecker;


/**
 * Created by 김창혁, NomadSoft.Inc on 2018-01-05. <br/>
 * 뷰의 데이터를 통해 뷰의 상태를 셋팅하는 유틸
 */
public class ViewSettingUtil {


    /**
     * text가 ''이나 null이 아닐 때 view에 세팅해준다.
     * @param view
     * @param text
     */
    public static boolean setTextFilterNull(EditText view, String text){

        try {

            if(StringChecker.isStringNotNull(text)){

                view.setText(text);
                return true;
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return false;
    }

    /**
     * text의 값이 있을 경우 그 text를 셋팅하고, editText를 disable 시키는 유틸 (주로 회원가입에서 사용)
     * 만약 text가 '"이나 null일 경우 별도로 아무것도 안한다.
     * @param editText
     * @param text
     */
    public static void setTextAndDisableView(EditText editText, String text) {

        try {

            if (setTextFilterNull(editText, text)) {

                editText.setEnabled(false);
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    /**
     * text가 null이 아니면 textView를 보이게 하고 세팅하며, null이거나 비었으면 visibility를 GONE으로 변경한다.
     * @param textView
     * @param text
     */
    public static void setTextIfNotNullOrGone(TextView textView, String text){

        try {

            if(StringChecker.isStringNotNull(text)){

                textView.setVisibility(View.VISIBLE);
                textView.setText(text);
            }else {

                textView.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    public static int getDpToPixel(Context context, int DP) {
        float px = 0;
        try {
            px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DP, context.getResources().getDisplayMetrics());
        } catch (Exception e) {
        }
        return (int) px;
    }


}
