package org.litepal.CryptoSJ;

import android.util.Log;

/**
 * Created by SJH, NomadSoft.Inc, 2018-11-29
 */
public class LPErrorController {

    public static final String TAG = "LP";

    /**
     * 단순히 Log.e 메세지를 로그에 출력해주는 래퍼.
     * @param msg 내용
     */
    public static void showMessage(String msg){
       // Log.e(TAG, "#LP " + msg);
    }


    /**
     * Log.e(TAG, "MSG", e); 를 간략화한 래퍼.
     * @param e catch문에서 던져지는 Exception.
     */
    public static void showError(Exception e) {
        Log.e(TAG, "#LP Exception.", e);
    }
}
