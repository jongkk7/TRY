package mars.nomad.com.c3_baseaf;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import mars.nomad.com.c2_customview.R;
import mars.nomad.com.l12_applicationutil.String.StringProcesser;
import mars.nomad.com.l4_language.NsLanguagePack;
import mars.nomad.com.l5_event.EventPoster;
import mars.nomad.com.c2_customview.Dialog.LoadingDialog;
import mars.nomad.com.c2_customview.Value.ViewConstants;
import mars.nomad.com.l5_event.BaseSubscriber;
import mars.nomad.com.l0_base.Logger.ErrorController;

import static org.greenrobot.eventbus.ThreadMode.MAIN;


/**
 * Created by SJH on 2017-02-28.
 * 모든 액티비티가 AppCompatActivity 대신 상속받을 대상이다.
 * 기본적으로 약속된 골조인 initView, setEvent를 강제적으로 구현토록 되어 있고 로딩제어, 기본적인 이벤트 버스의 라이프 사이클 제어를 담당한다.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarWrapper();
        mContext = this;

    }

    @Override
    protected void onStart() {
        super.onStart();
        // getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);


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

    public void startLoading() {
        try {

            // 로딩 생성
            if (loadingDialog == null) {
                loadingDialog = new LoadingDialog(getContext());
            }

            loadingDialog.show();

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    /**
     * 2019-01-21 SJH - 화면 닫기 전에 꼭 호출하자. LEAK가 생김.
     */
    public void stopLoading() {
        try {

            if (loadingDialog != null) {
                loadingDialog.dismiss();
                loadingDialog = null;
            }


        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        try {

            ErrorController.showMessage("[" + getLocalClassName() + "] onResume");
            NsLanguagePack.setTextLanguage((ViewGroup) getWindow().getDecorView().getRootView());

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        EventPoster.register(this); //이벤트 버스 등록
    }

    @Override
    protected void onStop() {
        EventPoster.unregister(this); //이벤트 버스 해제.
        super.onStop();
    }

    protected BaseActivity getOwnerActivity() {
        return this;
    }

    protected Activity getActivity() {
        return this;
    }

    protected Context getContext() {
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
        view.setVisibility(View.GONE);
    }

    protected Point getDisplaySize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return size;
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

    // A method to find height of the status bar
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        ErrorController.showMessage("status bar height ; " + result);
        return result;
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
                //window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
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
                //  window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.parseColor(colorCode));
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    /**
     * statusbar의 색상을 일시적으로 지정한 색으로 세팅한다.
     */
    protected void setStatusBarColorTransparent(String colorCode) {

        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(Color.parseColor(colorCode));
            } else {

                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    protected void disableRecyclerViewFlickering(RecyclerView recyclerView) {

        try {

            RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();

            if (animator instanceof SimpleItemAnimator) {
                ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    /**
     * FlagManager에 영향을 받는 화면의 경우, 일관성 유지를 위해 이 메소드를 오버라이드해서 사용하도록 한다.
     */
    protected void setUiByFlag() {

    }

    /**
     * 현재 화면에서 특정 뷰의 위치정보를 rect로 돌려준다.
     * 입력창 외의 부분을 누르면 소프트키를 제거하는 dispatchTouchEvent를 상속받는 화면에서 쓰인다.
     *
     * @param mEditText
     * @return
     */
    protected Rect getLocationOnScreen(EditText mEditText) {
        Rect mRect = new Rect();
        int[] location = new int[2];

        mEditText.getLocationOnScreen(location);

        mRect.left = location[0];
        mRect.top = location[1];
        mRect.right = location[0] + mEditText.getWidth();
        mRect.bottom = location[1] + mEditText.getHeight();

        return mRect;
    }

    public static void setTransparentBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = activity.getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    public static void setNoStatusBar(Activity activity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = activity.getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            w.addFlags(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE;

            activity.getWindow().getDecorView().setSystemUiVisibility(uiOptions);
        }
    }

    public static void setNoStatusBar2(Activity activity) {

        try {

            int currentApiVersion = Build.VERSION.SDK_INT;
            final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            if (currentApiVersion >= Build.VERSION_CODES.KITKAT) {
                activity.getWindow().getDecorView().setSystemUiVisibility(flags);
                final View decorView = activity.getWindow().getDecorView();
                decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int visibility) {
                        if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                            decorView.setSystemUiVisibility(flags);
                        }
                    }
                });
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    protected void hideStatusBar() {

        try {

            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            //ActionBar actionBar = getActionBar();
            //actionBar.hide();

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    public static void setStatusBarGradiant(final Activity activity, final int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(resId);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(R.color.colorTransparent));
            window.setNavigationBarColor(activity.getResources().getColor(R.color.colorWhite));
            window.setBackgroundDrawable(background);
        }
    }

/*    protected Transition enterTransition() {
        ChangeBounds bounds = new ChangeBounds();
        bounds.setDuration(150);

        return bounds;
    }

    protected Transition returnTransition() {
        ChangeBounds bounds = new ChangeBounds();
        bounds.setInterpolator(new DecelerateInterpolator());
        bounds.setDuration(150);

        return bounds;
    }*/

    /**
     * 간단한 message + 확인 조합의 얼러트 다이얼로그를 출력한다.
     *
     * @param message
     */
    protected void showSimpleAlertDialog(String message) {

        try {

            if (mContext == null) {

                ErrorController.showMessage("[BaseActivity] showSimpleAlertDialog- context is null.");
                return;
            }

            AlertDialog dialog = new AlertDialog.Builder(mContext)
                    .setMessage(message)
                    .setPositiveButton("확인", null)
                    .create();

            if (mContext != null && getActivity() != null) {

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

            if (mContext == null) {

                ErrorController.showMessage("[BaseActivity] showSimpleAlertDialog- context is null.");
                return;
            }

            AlertDialog dialog = new AlertDialog.Builder(mContext)
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton("확인", clickListener)
                    .create();

            if (mContext != null && getActivity() != null) {

                dialog.show();
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    /**
     * 간단한 message + 확인 + 취소 조합의 얼러트 다이얼로그를 출력한다.
     *
     * @param message
     */
    protected void showSimpleAlertDialog(String message, DialogInterface.OnClickListener posiveClickListener, DialogInterface.OnClickListener nagativeClickListener) {

        try {

            if (mContext == null) {

                ErrorController.showMessage("[BaseActivity] showSimpleAlertDialog- context is null.");
                return;
            }

            AlertDialog dialog = new AlertDialog.Builder(mContext)
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton("확인", posiveClickListener)
                    .setNegativeButton("취소", nagativeClickListener)
                    .create();

            if (mContext != null && getActivity() != null) {

                dialog.show();
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    /**
     * 보이지 않는 뷰를 durationToDisappear만큼의 시간만큼 보였다가 다시 안보이게 바꾼다.
     *
     * @param view                타켓
     * @param fadeInDuration      보이는 애니메이션의 시간
     * @param fadeOutDuration     사라지는 애니메이션의 시간
     * @param durationToDisappear 보인 후 사라지기 전까지의 시간
     */
    protected void fadeInAndOut(final View view, long fadeInDuration, final long fadeOutDuration, final long durationToDisappear) {

        try {

            view.setAlpha(0f);
            view.setVisibility(View.VISIBLE);

            view.animate().alpha(1).setDuration(fadeInDuration).setInterpolator(new DecelerateInterpolator()).withEndAction(new Runnable() {
                @Override
                public void run() {

                    //5초 후 꺼지도록 한다.
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            view.animate().alpha(0).setDuration(fadeOutDuration).setInterpolator(new AccelerateInterpolator()).withEndAction(new Runnable() {
                                @Override
                                public void run() {

                                    view.setVisibility(View.GONE);
                                }
                            }).start();
                        }
                    }, durationToDisappear);

                }
            }).start();


        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    protected void printScreenSize() {

        try {

            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);

            ViewConstants.SCREEN_WIDTH = metrics.widthPixels;
            ViewConstants.SCREEN_HEIGHT = metrics.heightPixels;

            ErrorController.showMessage("Metrics : " + metrics.widthPixels + ", " + metrics.heightPixels);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void hideSystemUI() {

        getWindow().getDecorView().setSystemUiVisibility(

                View.SYSTEM_UI_FLAG_LAYOUT_STABLE

                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar

                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar

                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

    }

    protected void setStatusBarWrapper() {

        try {

            setDarkStatusBar(getWindow().getDecorView(), this);
            setStatusBarColor(R.color.colorTopBar1Background);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

    public void toastInMainThread(final String msg) {

        try {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    ErrorController.showToast(getActivity(), msg);
                }
            });

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }
}
