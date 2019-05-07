package mars.nomad.com.l8_room;

import android.util.Log;

/**
 * Created by SJH, NomadSoft.Inc, 2019-01-14
 */
public class RdbErrorController {

    public static final String TAG = "RDB";

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
}
