package mars.nomad.com.c1_activitymanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;

import java.util.HashMap;
import java.util.Map;

import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.l12_applicationutil.String.StringChecker;

/**
 * Created by SJH, NomadSoft.Inc on 2018-03-08.<br/>
 * <B>클래스 네이밍 규칙</B><br/>
 * 접미사가 중복되는 클래스는 만들지 않도록 한다.<br/>
 * 대표적인 멸망 케이스 : ActivitySample.class가 이미 있는데, ActivityActivitySample.class를 등록하는 경우,<br/>
 * 저 중 먼저 addclass()에 들어간 클래스가 무조건 우선 호출된다.<br/>
 */
public class ActivityManager {

    /**
     * 이 액티비티 매니저에 등록된 액티비티들의 목록
     */
    protected static Map<String, Class<?>> mClassList = new HashMap<>();

    /**
     * 액티비티 클래스 목록에 activity를 더한다.
     * 여기에 더해둬야 나중에 goActivityXXX()가 유효하게 동작한다.
     *
     * @param activity
     */
    public static void addClass(String key, Class<?> activity) {

        try {

            mClassList.put(key, activity);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    /**
     * goActivityXXX()류의 메소드들에서 사용됨.
     * 각 메소드에 하드코딩된 name을 사용하여 실제 필요한 클래스를 구한다.
     *
     * @param key
     * @return
     */
    public static Class<?> getClassByName(String key) {

        Class<?> result = null;

        try {

            if (mClassList.containsKey(key)) {

                result = mClassList.get(key);
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }

        return result;
    }

    public static void goActivityWithoutExtra(Activity activity, ActivityOptionsCompat options, String action, boolean animationEnabled, String activityName) {

        try {

            try {

                Intent intent = new Intent(activity, getClassByName(activityName));

                if (StringChecker.isStringNotNull(action)) {
                    intent.setAction(action);
                }

                startActivity(activity, intent, animationEnabled, options);


            } catch (Exception e) {
                displayErrorDialog(activity);
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    public static void goActivityForResultWithoutExtra(Activity activity, Fragment fragment, int requestCode, ActivityOptionsCompat options, String action, String activityName) {

        try {

            try {

                Intent intent = new Intent(activity, getClassByName(activityName));

                if (StringChecker.isStringNotNull(action)) {
                    intent.setAction(action);
                }

                startActivityForResult(activity, fragment, options, intent, requestCode);

            } catch (Exception e) {
                displayErrorDialog(activity);
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    /**
     * 해당 intent의 액티비티를 시작한다.
     * 단순 activity 이동이므로 fragment를 고려하지 않는다.(getActivity() 합시다.)
     * 만약 target이 비어있다면 "등록된 패키지가 없습니다" 라고 메시지를 출력해준다.
     *
     * @param activity 현재의 액티비티
     * @param options  transition을 위한 옵션 번들 객체
     * @param intent   extra가 포장된 intent객체.
     */
    public static void startActivity(Activity activity, Intent intent, ActivityOptionsCompat options) {

        try {

            if (options != null) {//transition 애니메이션이 있다.

                ActivityCompat.startActivity(activity, intent, options.toBundle());

            } else {//transition 애니메이션이 없다.

                activity.startActivity(intent);
                activity.overridePendingTransition(0, 0);
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public static void startActivity(Activity activity, Intent intent, boolean animationEnabled, ActivityOptionsCompat options) {

        try {

            if (options != null) {//transition 애니메이션이 있다.

                ActivityCompat.startActivity(activity, intent, options.toBundle());

            } else {//transition 애니메이션이 없다.

                activity.startActivity(intent);

                if (animationEnabled) {
                    activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    /**
     * 해당 intent의 액티비티를 requestCode와 함께 시작한다.
     * 단순 activity 이동이이 아니므로, fragment가 null이 아닐때는 fragment객체가 실행하도록 한다.
     * 만약 target이 비어있다면 "등록된 패키지가 없습니다" 라고 메시지를 출력해준다.
     *
     * @param activity    현재의 액티비티
     * @param fragment    현재의 프래그먼트(null일 수 있다.)
     * @param options     transition을 위한 옵션 번들 객체
     * @param intent      extra가 포장된 intent객체.
     * @param requestCode request Code.
     */
    public static void startActivityForResult(Activity activity, Fragment fragment, ActivityOptionsCompat options, Intent intent, int requestCode) {

        try {

            if (options != null) {//transition 애니메이션이 있다.

                if (fragment == null) {//액티비티에서 호출한 경우

                    ActivityCompat.startActivityForResult(activity, intent, requestCode, options.toBundle());

                } else {//fragment에서 호출한 경우

                    fragment.startActivityForResult(intent, requestCode, options.toBundle());

                }
            } else {//transition 애니메이션이 없다.

                if (fragment == null) {

                    activity.startActivityForResult(intent, requestCode);

                } else {

                    fragment.startActivityForResult(intent, requestCode);
                }
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public static void displayErrorDialog(Context context) {

        try {

            ErrorController.showInfoToast(context, "등록된 패키지가 없습니다.", 2);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


}
