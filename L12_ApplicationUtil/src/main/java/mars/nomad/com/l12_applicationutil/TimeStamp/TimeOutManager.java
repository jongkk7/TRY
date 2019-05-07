package mars.nomad.com.l12_applicationutil.TimeStamp;

import android.os.Handler;

import mars.nomad.com.l0_base.Callback.CommonCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by SJH on 2017-08-07.
 */

public class TimeOutManager {

    private static TimeOutManager instance = null;

    public static TimeOutManager getInstance(){

        if(instance == null){
            instance = new TimeOutManager();
        }

        return instance;
    }

    private TimeOutManager(){

    }

    private List<TimeOutUtil> mTimeOutList = new ArrayList<>();


    public void startTimeoutEvent(String key, long interval, CommonCallback.SingleActionCallback command){

        try {

            TimeOutUtil util = new TimeOutUtil();

            synchronized (mTimeOutList){

                mTimeOutList.add(util);

            }
            util.startTimeoutEvent(key, interval, command);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void stopTimeoutEvent(String key){

        try {

            if(key == null || key.equals("")){
                ErrorController.showMessage("[TimeOutManager] key cannot be null. return.");
                return;
            }

            synchronized (mTimeOutList){

                int index = -1;

                for (TimeOutUtil timeOutUtil : mTimeOutList) {

                    if(timeOutUtil.getTag().equals(key)){
                        timeOutUtil.stopTimeOut();
                        index = mTimeOutList.indexOf(timeOutUtil);
                        break;
                    }
                }

                if(index != -1){
                    mTimeOutList.remove(index);
                }

            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }



    /**
     * 타임아웃 클래스
     */
    private class TimeOutUtil {

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
        private TimeOutRuunable mTimeOutRunnable = new TimeOutRuunable();

        /**
         * 콜백
         */
        private CommonCallback.SingleActionCallback mCommand = null;

        /**
         * 콜백이 호출되기 까지의 시간
         */
        private long mInterval = 0;

        /**
         * 콜백 식별자
         */
        private String mTag = "";

        public String getTag() {
            return mTag;
        }

        public void startTimeoutEvent(String tag, long interval, CommonCallback.SingleActionCallback command){

            //이미 타임아웃이 있다면 멈춤.
            stopTimeOut();

            //값 초기화 및 필드 세팅.
            this.mTimeOutCnt = new AtomicInteger(0);

            this.mTag = tag;
            this.mInterval = interval;
            this.mCommand = command;

            ErrorController.showMessage("[TimeOut] " + mTag + " - startEventWithTimeout.");

            //타임아웃 시작
            mHandler.postDelayed(mTimeOutRunnable, mInterval);
            //이벤트 시작
            mCommand.onAction();

        }

        /**
         * 타임아웃을 해제한다.
         */
        public void stopTimeOut() {

            try {

                mHandler.removeCallbacks(mTimeOutRunnable);

                ErrorController.showMessage("[TimeOut] " + mTag + " - stopTimeOut.");

            } catch (Exception e) {
                ErrorController.showError(e);
            }
        }

        private class TimeOutRuunable implements Runnable{

            @Override
            public void run() {

                try {

                    //현재의 재시도 횟수
                    int currentCnt = mTimeOutCnt.incrementAndGet();

                    ErrorController.showMessage("[TimeOut] " + mTag + " - TimeOut Occurred. Retry Cnt : " + currentCnt);

                    //재시도 횟수가 3이하면 다시 시도한다.
                    if (currentCnt < 3) {

                        //타임아웃 시작
                        mHandler.postDelayed(mTimeOutRunnable, mInterval);
                        //이벤트 시작
                        mCommand.onAction();

                    } else {//실패 처리

                        /*List<NsChatMessage> select = DataSupport.where("msgKey = ?" , mTag).find(NsChatMessage.class);

                        if(select != null && select.size() > 0){

                            select.get(0).setStatus(-1);
                            select.get(0).save();
                        }*/

                    }

                } catch (Exception e) {
                    ErrorController.showError(e);
                }
            }
        }

    }//end of TimeOutUtil

}
