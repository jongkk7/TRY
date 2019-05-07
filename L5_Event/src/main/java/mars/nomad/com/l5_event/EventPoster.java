package mars.nomad.com.l5_event;

import android.app.Activity;
import androidx.fragment.app.Fragment;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by SJH on 2017-02-28.
 * 이벤트 버스를 간략화한 래퍼 클래스.
 */
public class EventPoster {

    /**
     * EventBus.getDefault().register(activity) 를 try catch와 함께 같이 해줌. onResume()에서 호출하도록 한다.<br/>
     * 에러 컨트롤러를 통한 에러 스택트레이스는 무의미하므로 사용하지 않음.
     * @param activity 현재 실행 중인 activity 객체.
     */
    public static void register(Activity activity) {
        try {
            EventBus.getDefault().register(activity);
        } catch (Exception e) {
            //ErrorController.showError(e);
        }
    }

    /**
     * EventBus.getDefault().unregister(activity) 를 try catch와 함께 같이 해줌. onStop()에서 super.onStop() 전에 호출하도록 한다.<br/>
     * 에러 컨트롤러를 통한 에러 스택트레이스는 무의미하므로 사용하지 않음.
     * @param activity 현재 실행 중인 activity 객체.
     */
    public static void unregister(Activity activity) {
        try {
            EventBus.getDefault().unregister(activity);
        } catch (Exception e) {
            //ErrorController.showError(e);
        }
    }

    public static void register(Fragment fragment){
        try {
            EventBus.getDefault().register(fragment);
        } catch (Exception e) {
            //ErrorController.showError(e);
        }
    }

    public static void unregister(Fragment fragment) {
        try {
            EventBus.getDefault().unregister(fragment);
        } catch (Exception e) {
            //ErrorController.showError(e);
        }
    }

    public static void post(Object event) {
        EventBus.getDefault().post(event);
    }
}
