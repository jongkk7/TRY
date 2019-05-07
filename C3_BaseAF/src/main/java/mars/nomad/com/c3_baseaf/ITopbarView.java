package mars.nomad.com.c3_baseaf;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import mars.nomad.com.l0_base.Callback.IMainActionCallback;


/**
 * Created by SJH on 2017-06-29.
 */

public abstract class ITopbarView extends RelativeLayout {

    protected Context mContext;

    /**
     * topbar에서 액션을 취했을때, fragment나 혹은 activityMain까지 영향을 끼칠 경우, callback을 이용하여 액션을 취급한다.
     */
    protected IMainActionCallback mCallback;


    public ITopbarView(Context context) {
        super(context);
    }

    public ITopbarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected abstract void initView();

    protected abstract void setEvent();


    public IMainActionCallback getCallback() {
        return mCallback;
    }

    public void setCallback(IMainActionCallback mCallback) {
        this.mCallback = mCallback;
    }

    /**
     * 상단바에 변경을 요청할 때 사용된다.
     *
     * @param params
     */
    public abstract void doTopbarAction(Object... params);

    /**
     * 빌더 플래그에 따라 네비게이션 버튼의 위치를 변경한다.
     */
    public void changeTopBarStyle(View leftNav, View rightNav) {

    }


}
