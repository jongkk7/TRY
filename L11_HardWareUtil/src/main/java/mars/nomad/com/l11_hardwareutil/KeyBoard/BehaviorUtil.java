package mars.nomad.com.l11_hardwareutil.KeyBoard;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.List;

import mars.nomad.com.l0_base.Logger.ErrorController;


/**
 * Created by SJ Han, NomadSoft.INC on 2017-03-12. <br/>
 * 정형화된 보일러 플레이트 액션을 모아둔 스태틱 클래스.
 */
public class BehaviorUtil {

    /**
     * 키보드를 숨긴다. <br/>
     * Usage : BehaviorUtil.hideKeyboard(this.getCurrentFocus(), mContext);
     *
     * @param view
     * @param context
     */
    public static void hideKeyboard(View view, Context context) {
        try {
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    @Deprecated
    public static void showKeyboard(View view, Context context) {

        try {

            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInputFromWindow(view.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    /**
     * showKeyboard가 왠지 잘 안먹혀서 이걸로 대채한다.
     *
     * @param view
     * @param context
     */
    public static void showKeyboard2(View view, Context context) {
        try {

            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 1);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    /**
     * 리스트의 마지막인지 검사한다.
     *
     * @param data
     * @param position
     * @return true : 리스트의 마지막, false : 리스트의 마지막 아님
     */
    public static boolean isLastOfList(List<?> data, int position) {

        try {

            if (data.size() == position + 1) {
                return true;
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return false;
    }

    /**
     * 뷰 리스트 중에서 특정 뷰(seq)만 보이게 한다.
     *
     * @param viewList 뷰의 리스트. seq를 제외한 나머지 인덱스들은 GONE상태로 변경됨.
     * @param seq      VISIBLE로 바뀔 리스트의 인덱스 번호.
     */
    public static void setViewVisible(List<View> viewList, int seq) {
        try {
            for (View v : viewList) {
                v.setVisibility(View.GONE);
            }
            viewList.get(seq).setVisibility(View.VISIBLE);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    /**
     * 뷰 리스트 중에서 특정 뷰(seq)만 선택되게 한다.
     *
     * @param viewList 뷰의 리스트. seq를 제외한 나머지 인덱스들은 selected = false로 변경됨.
     * @param seq      selected = true로 바뀔 리스트의 인덱스 번호.
     */
    public static void setViewSelected(List<View> viewList, int seq) {
        try {
            for (View v : viewList) {
                v.setSelected(false);
            }
            viewList.get(seq).setSelected(true);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    /**
     * @param b
     * @return
     */
    public static int booleanToVisibility(boolean b) {
        if (b) {
            return View.VISIBLE;
        } else {
            return View.GONE;
        }
    }

    public static int booleanToInt(boolean b){

        if (b) {
            return 1;
        } else {
            return 0;
        }
    }


    /**
     * Visibility를 반전시킨다. (VISIBLE <--> GONE)
     */
    public static void reverseVisibility(View view) {

        try {

            if (view.getVisibility() == View.VISIBLE) {//보이고 있음 -> 안보이게 바꿈.

                view.setVisibility(View.GONE);

            } else {//안보이고 있음 -> 보이게 바꿈.

                view.setVisibility(View.VISIBLE);

            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    /**
     * 해당 editText를 편집 가능하게 하거나 편집 불가능하게 한다.
     * @param isEditable true : 일반적인 editText의 상태 - 편집이 가능하다. false : 편집 불가능한 editText를 만든다.
     */
    public static void setEditTextEditable(final EditText view, final boolean isEditable){

        try {

            if(!isEditable) {

                view.setFocusable(false);
                view.setClickable(true);
            }else{
                view.setFocusable(true);
                view.setClickable(true);
                view.setFocusableInTouchMode(true);
                view.requestFocus();
                view.setSelection(view.getText().toString().length());
            }
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    /**
     * 0, 1의 플래그를 서로 바꾼다.
     * @param flag
     * @return
     */
    public static int reverseDigitFlag(int flag) {

        int result = 0;

        if(flag == 0){
            result = 1;
        }
        return result;
    }

    /**
     * flag = 0 false, flag = 1 true
     * @param flag
     * @return
     */
    public static int intToVisibility(int flag) {


        try {

            if(flag == 1){

                return View.VISIBLE;
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }

        return View.GONE;
    }

    /**
     * flag == 1 : true, flag == 0: false
     * @param flag
     * @return
     */
    public static boolean intToSelection(int flag) {


        try {

            if(flag == 1){

                return true;
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }

        return false;
    }

    /**
     * 평점에 따라 해당 index의 하트가 선택되어야 하는지(활성화) 아니면 선택되지 않아야 하는지를 돌려준다.
     * @param index
     * @param star_point
     * @return
     */
    public static boolean getStarPointSelection(int index, int star_point) {

        try {

            if(index <= star_point){

                return true;
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }


        return false;
    }

    public static void reverseSelection(View view){

        try{

            view.setSelected(!view.isSelected());

        }catch(Exception e){
            ErrorController.showError(e);
        }
    }

}
