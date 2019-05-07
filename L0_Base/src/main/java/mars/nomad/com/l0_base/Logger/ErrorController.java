package mars.nomad.com.l0_base.Logger;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by SJH on 2017-02-28.
 * 각종 로깅이나 토스트를 간략하게 표현하기 위한 스태틱 메소드들을 모아둔 클래스이다.
 * 상황에 따라 적절한 메소드를 선택하여 사용하도록 한다.
 */
public class ErrorController {

    public static final String TAG = "Mars";

    private static boolean DEBUG = true;

    /**
     * 단순히 Log.e 메세지를 로그에 출력해주는 래퍼.
     *
     * @param msg 내용
     */
    public static void showMessage(String msg) {

        if (DEBUG) {

            Log.e(TAG, msg);
        }
    }


    /**
     * Log.e(TAG, "MSG", e); 를 간략화한 래퍼.
     *
     * @param e catch문에서 던져지는 Exception.
     */
    public static void showError(Exception e) {
        Log.e(TAG, "Exception.", e);
    }

    /**
     * Log.e(TAG, "MSG", e); 를 간략화한 래퍼.
     *
     * @param t catch문에서 던져지는 Exception.
     */
    public static void showError(Throwable t) {
        Log.e(TAG, "Exception.", t);
    }

    /**
     * "[Debug] To be implemented" 라는 토스트를 보여준다. 아직 미구현인 이벤트 콜백에 쓰도록 한다.
     *
     * @param context 해당 화면의 context.
     */
    private static void showToBeImplementedToast(Context context) {
        try {
            Toast.makeText(context, "[Debug] To be implemented.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    /**
     * Toast.makeText(context, msg, LENGTH).show()를 간략화한 래퍼
     *
     * @param context 해당 화면의 context.
     * @param msg     내용
     */
    public static void showToast(Context context, String msg) {
        try {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    /**
     * Toast.makeText(context, "[DEBUG] " + msg, LENGTH).show()를 간략화한 래퍼.
     * 디버그 토스트를 표현할 떄 사용한다.
     *
     * @param context 해당 화면의 context.
     * @param msg     내용
     */
    public static void showDebugToast(Context context, String msg) {
        try {
            Toast.makeText(context, "[Debug] " + msg, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    /**
     * InfoToastView를 래핑해서 보여준다.
     * [2017-03-06] SJH  : mColorFlag가 변경됨에 따라 int를 파라미터로 받도록 수정.
     *
     * @param context   현재 화면의 context
     * @param message   출력할 메시지
     * @param colorFlag 0(green), 1(blue), 2(red)
     */
    public static void showInfoToast(Context context, String message, int colorFlag) {
        try {
            InfoToastView.showToast(context, message, colorFlag).show();
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


}
