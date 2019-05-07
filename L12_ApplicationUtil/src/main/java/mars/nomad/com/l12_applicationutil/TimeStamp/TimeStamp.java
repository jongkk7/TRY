package mars.nomad.com.l12_applicationutil.TimeStamp;

import java.util.HashMap;

import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by SJH, NomadSoft.Inc on 2018-02-19.
 */

public class TimeStamp {

    private static HashMap<String, Timer> map = new HashMap<>();

    /**
     * tag인 타이머를 시작한다.
     * @param tag
     */
    public static void startTimeStamp(final String tag){

        try {

            if(!map.containsKey(tag)){

                Timer timer = new Timer();
                timer.startTime = System.currentTimeMillis();

                map.put(tag, timer);
                ErrorController.showMessage("[TimeStamp] Starting Timer : " + tag);
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }

    }

    public static void endTimerStamp(final String tag){

        try {

            if(map.containsKey(tag)){

                Timer timer = map.get(tag);

                timer.endTime = System.currentTimeMillis();

                long diff = timer.endTime - timer.startTime;

                ErrorController.showMessage("[TimeStamp] Ending Timer : " + tag + ", DIFF : " + diff + "ms");

            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public static class Timer{

        public long startTime = 0;

        public long endTime = 0;

    }

}
