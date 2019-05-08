package mars.nomad.com.c3_baseaf;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import mars.nomad.com.l5_event.BaseSubscriber;
import mars.nomad.com.l5_event.EventPoster;
import mars.nomad.com.l0_base.Logger.ErrorController;

import static org.greenrobot.eventbus.ThreadMode.MAIN;

/**
 * Created by SJH on 2017-06-30.
 */

public abstract class IMainActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {

        super.onCreate(savedInstanceState, persistentState);
        // 화면 캡처 못하도록 막기

    }

    @Override
    protected void onStart() {
        super.onStart();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);

    }

    /**
     * 모든 findViewById, 혹은 View 클래스로의 위임, 프리젠터의 선언이 이곳에서 이뤄진다.
     */
    protected abstract void initView();

    /**
     * 순수 View이벤트를 제외한 presenter인터렉션이 필요한 이벤트 콜백이 여기서 선언된다.
     */
    protected abstract void setEvent();

    /**
     * 로딩뷰로 지정된 뷰를 화면의 맨 앞에 보여준다.
     *
     * @param layout VIew를 상속받는 레이아웃.
     */
    protected void startLoading(View layout) {
        try {
            layout.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    /**
     * 로딩뷰를 화면에서 치운다.
     *
     * @param layout View를 상속받는 레이아웃.
     */
    protected void stopLoading(View layout) {
        try {
            layout.setVisibility(View.GONE);
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventPoster.register(this); //이벤트 버스 등록

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {

            EventPoster.unregister(this); //이벤트 버스 해제.

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    protected Activity getActivity() {
        return this;
    }

    /**
     * EventBus에 register할 때 subscribe된 이벤트가 하나도 없으면 앱이 크래쉬한다.
     * 이를 방지하기 위해(또한 무의미한 에러 스택 트레이스를 보지 않기 위해) 어디에서도 쓰일 예정이 없는 무의미한 이벤트 하나를 무조건 subscribe해둔다.
     *
     * @param event 아무 의미 없는 이벤트. 어디에서도 쓰이지 않을 계획이다.
     */
    @Subscribe(threadMode = MAIN)
    public void BasicSubscriber(BaseSubscriber event) {

    }

    protected void fadeInAndShow(Context context, View view) {
        Animation animation2 = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        animation2.reset();
        view.clearAnimation();
        view.startAnimation(animation2);
        view.setVisibility(View.VISIBLE);
    }

    protected void fadeoutAndHide(Context context, View view) {
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
        animation.reset();
        view.clearAnimation();
        view.startAnimation(animation);
        view.setVisibility(View.GONE);
    }

    protected void fadeoutAndInvisible(Context context, View view) {
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
        animation.reset();
        view.clearAnimation();
        view.startAnimation(animation);
        view.setVisibility(View.INVISIBLE);
    }


    /**
     * statusbar를 밝게하고, 그 위에 표현되는 모든 것을 어둡게 한다.
     *
     * @param view
     * @param activity
     */
    public static void setLightStatusBar(View view, Activity activity) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            activity.getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    /**
     * statusbar를 어둡게하고, 그 위에 표현되는 모든 것을 밝게 한다.
     *
     * @param view
     * @param activity
     */
    public static void setDarkStatusBar(View view, Activity activity) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int flags = view.getSystemUiVisibility();
            flags = 0;
            view.setSystemUiVisibility(flags);
            activity.getWindow().setStatusBarColor(Color.GRAY);
        }
    }

    /**
     * statusbar의 색상을 일시적으로 지정한 색으로 세팅한다.
     */
    protected void setStatusBarColor(int resId) {

        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                // window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(resId));
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    /**
     * statusbar의 색상을 일시적으로 지정한 색으로 세팅한다.
     */
    protected void setStatusBarColor(String colorCode) {

        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                // window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.parseColor(colorCode));
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    protected Transition enterTransition() {
        ChangeBounds bounds = new ChangeBounds();
        bounds.setDuration(150);

        return bounds;
    }

    protected Transition returnTransition() {
        ChangeBounds bounds = new ChangeBounds();
        bounds.setInterpolator(new DecelerateInterpolator());
        bounds.setDuration(150);

        return bounds;
    }


    /**
     * 간단한 message + 확인 조합의 얼러트 다이얼로그를 출력한다.
     *
     * @param message
     */
    protected void showSimpleAlertDialog(String message) {

        try {

            if (getActivity() == null) {

                ErrorController.showMessage("[BaseActivity] showSimpleAlertDialog- context is null.");
                return;
            }

            AlertDialog dialog = new AlertDialog.Builder(getActivity())
                    .setMessage(message)
                    .setPositiveButton("확인", null)
                    .create();

            if (getActivity() != null) {

                dialog.show();
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    /**
     * 간단한 message + 확인 조합의 얼러트 다이얼로그를 출력한다.
     *
     * @param message
     */
    protected void showSimpleAlertDialog(String message, DialogInterface.OnClickListener clickListener) {

        try {

            if (getActivity() == null) {

                ErrorController.showMessage("[BaseActivity] showSimpleAlertDialog- context is null.");
                return;
            }

            AlertDialog dialog = new AlertDialog.Builder(getActivity())
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton("확인", clickListener)
                    .create();

            if (getActivity() != null) {

                dialog.show();
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

}
