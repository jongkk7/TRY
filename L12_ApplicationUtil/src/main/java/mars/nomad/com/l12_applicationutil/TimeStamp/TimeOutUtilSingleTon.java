package mars.nomad.com.l12_applicationutil.TimeStamp;

import android.os.Handler;


import java.util.concurrent.atomic.AtomicInteger;

import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by SJH on 2017-08-07.
 * 싱글톤 방식의 타임아웃 클래스이다. TimeOutUtilSingleTon.getInstance()로 호출한다.
 */
public class TimeOutUtilSingleTon {

    private static TimeOutUtilSingleTon instance;

    public static TimeOutUtilSingleTon getInstance() {
        if (instance == null) {

            instance = new TimeOutUtilSingleTon();
        }
        return instance;
    }

    private TimeOutUtilSingleTon() {
        //empty Constructor.
    }

    /**
     * 핸들러
     */
    private Handler mHandler = new Handler();

    /**
     * 타임아웃 카운트
     */
    private AtomicInteger mTimeOutCnt = null;


    /**
     * 타임아웃 액션 인스턴스
     */
    private ClassTimeOut mTimeOutRunnable = new ClassTimeOut();

    /**
     * 콜백
     */
    private ITimeOutEventCallback mCallback = null;


    private long mInterval = 0;

    private String mTag = "";

    /**
     * 타임아웃과 함께 이벤트를 시작한다.
     *
     * @param tag      로그에 찍힐 태그.
     * @param interval 타임아웃까지의 시간.
     * @param callback 콜백.
     */
    public void startEventWithTimeout(String tag, long interval, final ITimeOutEventCallback callback) {

        try {


            //이미 타임아웃이 있다면 멈춤.
            stopTimeOut();

            //값 초기화 및 필드 세팅.
            this.mTimeOutCnt = new AtomicInteger(0);

            this.mTag = tag;
            this.mInterval = interval;
            this.mCallback = callback;

            ErrorController.showMessage("[TimeOutUtilSingleTon] " + mTag + " - startEventWithTimeout.");

            //타임아웃 시작
            mHandler.postDelayed(mTimeOutRunnable, mInterval);
            //이벤트 시작
            mCallback.doEvent();

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    /**
     * 타임아웃을 해제한다.
     */
    public void stopTimeOut() {

        try {

            mHandler.removeCallbacks(mTimeOutRunnable);

            ErrorController.showMessage("[TimeOutUtilSingleTon] " + mTag + " - stopTimeOut.");

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    /**
     * 타임아웃 이벤트
     */
    private class ClassTimeOut implements Runnable {

        @Override
        public void run() {

            try {

                //현재의 재시도 횟수
                int currentCnt = mTimeOutCnt.incrementAndGet();

                ErrorController.showMessage("[TimeOutUtilSingleTon] " + mTag + " - TimeOut Occurred. Retry Cnt : " + currentCnt);

                //재시도 횟수가 3이하면 다시 시도한다.
                if (currentCnt < 3) {

                    //타임아웃 시작
                    mHandler.postDelayed(mTimeOutRunnable, mInterval);
                    //이벤트 시작
                    mCallback.doEvent();
                } else {//실패 처리

                    mCallback.onEventFailure("");

                }

            } catch (Exception e) {
                ErrorController.showError(e);
            }
        }
    }//end of ClassTimeOut


    public interface ITimeOutEventCallback<T> {

        void onEventSuccess(T result);

        void onEventFailure(String fault);

        void doEvent();

    }
}
