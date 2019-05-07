package mars.nomad.com.l12_applicationutil.Animation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import mars.nomad.com.l0_base.Callback.CommonCallback;
import mars.nomad.com.l0_base.Abstract.AbstractAnimationEndListener;

import java.util.ArrayList;
import java.util.List;

import mars.nomad.com.l12_applicationutil.Animation.DataModel.AnimationDataModel;
import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by SJH on 2017-03-08.
 */

public class CommonAnimation {


    /**
     * 이 뷰의 원래 크기를 의미하는 상수
     */
    public final static int ORIGINAL = -1;

    public enum FADE_ANIM_TYPE {fade_in, fade_out, none}

    private List<AnimationDataModel> mView;


    private Context mContext;

    private long mDuration = 200;

    /**
     * Constructor
     *
     * @param context
     */
    public CommonAnimation(Context context) {

        this.mContext = context;
    }


    /**
     * 애니메이션이 적용될 수 있는 뷰들을 세팅한다.
     *
     * @param params
     */
    public void init(View... params) {

        try {

            mView = new ArrayList<>();

            int index = 0;

            while (true) {

                if (params[index] == null) {
                    break;
                }

                AnimationDataModel animView = new AnimationDataModel();
                animView.setView(params[index]);

                mView.add(animView);

                index++;

            }

        } catch (Exception e) {
            //어짜피 최소 1회는 익셉션이 뜨게 되있음. 괜히 신경쓰이므로 블록 처리.
            //ErrorController.showError(e);
        }
    }

    /**
     * initView에서 등록한 뷰들의 Width와 height을 설정한다.
     * 단 특정 조건을 만족할 때 mCallback.onLoadNavigation()이 갈 수 있도록 한다.
     * (ex) invisible한 뷰의 width, height를 구한 후 이 뷰의 상태를 GONE으로 변경할 수 있다.
     *
     * @param callback
     * @param <T>
     */
    public <T> void setEvent(final CommonCallback.SingleSelectionCallback<T> callback) {

        try {

            if (mView != null) {

                for (final AnimationDataModel animView : mView) {

                    if (animView.getView() != null) {

                        animView.getView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {//view의 높이와 너비를 구해서 mView에 세팅한다. 이는 이 레이아웃이 이후 작아지거나 커질 수 있는 애니메이션 동작을 필요로 하기 때문이다.

                                //리스너 해제
                                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                                    animView.getView().getViewTreeObserver().removeGlobalOnLayoutListener(this);
                                } else {
                                    animView.getView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
                                }
                                animView.setWidth(animView.getView().getMeasuredWidth());
                                animView.setHeight(animView.getView().getMeasuredHeight());
                                animView.setPositionX(animView.getView().getX());
                                animView.setPositionY(animView.getView().getY());

                                try {

                                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) animView.getView().getLayoutParams();
                                    animView.setMarginBottom(params.bottomMargin);
                                    animView.setMarginTop(params.topMargin);
                                    animView.setMarginLeft(params.leftMargin);
                                    animView.setMarginRight(params.rightMargin);

                                } catch (Exception e) {
                                    // ErrorController.showError(e);
                                }

                                try {

                                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) animView.getView().getLayoutParams();
                                    animView.setMarginBottom(params.bottomMargin);
                                    animView.setMarginTop(params.topMargin);
                                    animView.setMarginLeft(params.leftMargin);
                                    animView.setMarginRight(params.rightMargin);

                                } catch (Exception e) {
                                    // ErrorController.showError(e);
                                }

                                try {

                                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) animView.getView().getLayoutParams();
                                    animView.setMarginBottom(params.bottomMargin);
                                    animView.setMarginTop(params.topMargin);
                                    animView.setMarginLeft(params.leftMargin);
                                    animView.setMarginRight(params.rightMargin);
                                } catch (Exception e) {
                                    // ErrorController.showError(e);
                                }

                                ErrorController.showMessage("[CommonAnimation] w/h : " + animView.toString());

                                if (callback != null) {
                                    callback.onSelection((T) animView.getView());
                                }
                            }
                        });
                    }
                }
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    /**
     * 뷰의 높이를 from 에서 to까지 애니메이트하며 변경한다..
     *
     * @param v
     * @param from
     * @param to
     */
    public void animateHeight(final View v, int from, int to, AbstractAnimationEndListener listener) {

        try {

            if (from == ORIGINAL) {

                for (AnimationDataModel animView : mView) {

                    if (animView.getView() == v) {
                        from = animView.getHeight();
                        break;
                    }
                }
            }

            if (to == ORIGINAL) {

                for (AnimationDataModel animView : mView) {

                    if (animView.getView() == v) {
                        to = animView.getHeight();
                        break;
                    }
                }
            }

            ValueAnimator va = ValueAnimator.ofInt(from, to);
            va.setDuration(mDuration);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                public void onAnimationUpdate(ValueAnimator animation) {

                    int growingHeight = (int) animation.getAnimatedValue();
                    ViewGroup.LayoutParams params = v.getLayoutParams();
                    params.height = growingHeight;
                    v.setLayoutParams(params);
                }
            });
            if (listener != null) {
                va.addListener(listener);
            }
            va.start();
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    /**
     * 뷰의 너비를 from 에서 to까지 애니메이트하며 변경한다..
     *
     * @param v
     * @param from
     * @param to
     */
    public void animateWidth(final View v, int from, int to, AbstractAnimationEndListener listener) {

        try {
            if (from == ORIGINAL) {

                for (AnimationDataModel animView : mView) {

                    if (animView.getView() == v) {
                        from = animView.getWidth();
                        break;
                    }
                }
            }

            if (to == ORIGINAL) {

                for (AnimationDataModel animView : mView) {

                    if (animView.getView() == v) {
                        to = animView.getWidth();
                        break;
                    }
                }
            }

            if (to != 0) {
                v.setVisibility(View.VISIBLE);
            }

            ValueAnimator va = ValueAnimator.ofInt(from, to);
            va.setDuration(mDuration);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                public void onAnimationUpdate(ValueAnimator animation) {

                    int growingWidth = (int) animation.getAnimatedValue();
                    ViewGroup.LayoutParams params = v.getLayoutParams();
                    params.width = growingWidth;
                    v.setLayoutParams(params);
                }
            });
            if (listener != null) {
                va.addListener(listener);
            }
            va.start();
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void animateMarginBottom(final View v, int from, int to, CommonAnimation.FADE_ANIM_TYPE fadeType, AbstractAnimationEndListener listener) {

        if (from == ORIGINAL) {

            for (AnimationDataModel animView : mView) {

                if (animView.getView() == v) {
                    from = animView.getMarginBottom();
                    break;
                }
            }
        }

        if (to == ORIGINAL) {

            for (AnimationDataModel animView : mView) {

                if (animView.getView() == v) {
                    to = animView.getMarginBottom();
                    break;
                }
            }
        }

        ValueAnimator va = ValueAnimator.ofInt(from, to);
        va.setDuration(mDuration);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            public void onAnimationUpdate(ValueAnimator animation) {

                int growingMaring = (int) animation.getAnimatedValue();


                try {

                    if (v.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) v.getLayoutParams();
                        params.bottomMargin = growingMaring;
                        v.setLayoutParams(params);
                        return;
                    }
                } catch (Exception e) {
                    ErrorController.showError(e);
                }

                try {
                    if (v.getLayoutParams() instanceof LinearLayout.LayoutParams) {
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) v.getLayoutParams();
                        params.bottomMargin = growingMaring;
                        v.setLayoutParams(params);
                        return;
                    }
                } catch (Exception e) {
                    ErrorController.showError(e);
                }

                try {
                    if (v.getLayoutParams() instanceof FrameLayout.LayoutParams) {
                        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) v.getLayoutParams();
                        params.bottomMargin = growingMaring;
                        v.setLayoutParams(params);
                        return;
                    }
                } catch (Exception e) {
                    ErrorController.showError(e);
                }

/*                if(v.getVisibility() != View.VISIBLE){
                    v.setVisibility(View.VISIBLE);
                }*/

            }

        });
        if (listener != null) {
            va.addListener(listener);
        }

        switch (fadeType) {
            case fade_in:
                v.animate().alpha(1).setDuration(mDuration);
                break;

            case fade_out:
                v.animate().alpha(0).setDuration(mDuration);
                break;

            case none:
                //do nothing
                break;
        }
        va.start();
    }


    public void animateMarginLeft(final View v, int from, int to, CommonAnimation.FADE_ANIM_TYPE fadeType, AbstractAnimationEndListener listener) {

        if (from == ORIGINAL) {

            for (AnimationDataModel animView : mView) {

                if (animView.getView() == v) {
                    from = animView.getMarginBottom();
                    break;
                }
            }
        }

        if (to == ORIGINAL) {

            for (AnimationDataModel animView : mView) {

                if (animView.getView() == v) {
                    to = animView.getMarginBottom();
                    break;
                }
            }
        }

        ValueAnimator va = ValueAnimator.ofInt(from, to);
        va.setDuration(mDuration);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            public void onAnimationUpdate(ValueAnimator animation) {

                int growingMaring = (int) animation.getAnimatedValue();

                try {

                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) v.getLayoutParams();
                    params.leftMargin = growingMaring;
                    v.setLayoutParams(params);
                    return;
                } catch (Exception e) {
                    //ErrorController.showError(e);
                }

                try {

                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) v.getLayoutParams();
                    params.leftMargin = growingMaring;
                    v.setLayoutParams(params);
                    return;
                } catch (Exception e) {
                    // ErrorController.showError(e);
                }

                try {

                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) v.getLayoutParams();
                    params.leftMargin = growingMaring;
                    v.setLayoutParams(params);
                    return;
                } catch (Exception e) {
                    //   ErrorController.showError(e);
                }
            }

        });
        if (listener != null) {
            va.addListener(listener);
        }

        switch (fadeType) {
            case fade_in:
                v.animate().alpha(1).setDuration(mDuration);
                break;

            case fade_out:
                v.animate().alpha(0).setDuration(mDuration);
                break;

            case none:
                //do nothing
                break;
        }
        va.start();
    }


    public void animateMarginTop(final View v, int from, int to, CommonAnimation.FADE_ANIM_TYPE fadeType, AbstractAnimationEndListener listener) {

        if (from == ORIGINAL) {

            for (AnimationDataModel animView : mView) {

                if (animView.getView() == v) {
                    from = animView.getMarginTop();
                    break;
                }
            }
        }

        if (to == ORIGINAL) {

            for (AnimationDataModel animView : mView) {

                if (animView.getView() == v) {
                    to = animView.getMarginTop();
                    break;
                }
            }
        }

        ValueAnimator va = ValueAnimator.ofInt(from, to);
        va.setDuration(mDuration);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            public void onAnimationUpdate(ValueAnimator animation) {

                int growingMaring = (int) animation.getAnimatedValue();


                try {

                    if (v.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) v.getLayoutParams();
                        params.topMargin = growingMaring;
                        v.setLayoutParams(params);
                        return;
                    }
                } catch (Exception e) {
                    ErrorController.showError(e);
                }

                try {

                    if (v.getLayoutParams() instanceof LinearLayout.LayoutParams) {
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) v.getLayoutParams();
                        params.topMargin = growingMaring;
                        v.setLayoutParams(params);
                        return;
                    }
                } catch (Exception e) {
                    ErrorController.showError(e);
                }

                try {
                    if (v.getLayoutParams() instanceof FrameLayout.LayoutParams) {
                        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) v.getLayoutParams();
                        params.topMargin = growingMaring;
                        v.setLayoutParams(params);
                        return;
                    }
                } catch (Exception e) {
                    ErrorController.showError(e);
                }


/*                if(v.getVisibility() != View.VISIBLE){
                    v.setVisibility(View.VISIBLE);
                }*/
            }

        });

        switch (fadeType) {
            case fade_in:
                v.animate().alpha(1).setDuration(mDuration);
                break;

            case fade_out:
                v.animate().alpha(0).setDuration(mDuration);
                break;

            case none:
                //do nothing
                break;
        }

        if (listener != null) {
            va.addListener(listener);
        }
        va.start();
    }


    public static void expand(final View v) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }


    public static void expandAlbum(final View v) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.MATCH_PARENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }


    public static void rotate(final View v, final boolean rotate) {

        if (rotate) {

        }

        Animation a = new Animation() {

            float rotateDegree = 0;
            float rotateDegree2 = 180;

            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    if (rotate) {
                        v.setRotation(180);
                    } else {
                        v.setRotation(0);
                    }

                } else {
                    if (rotate) {
                        if (rotateDegree < 180) {
                            rotateDegree += 10;
                            v.setRotation(rotateDegree);
                        }
                    } else {
                        if (rotateDegree2 > 0) {
                            rotateDegree2 -= 10;
                            v.setRotation(rotateDegree2);
                        }
                    }

                }

            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1/ms

        a.setDuration((int) (1800));


        v.startAnimation(a);
    }


}
