package mars.nomad.com.c3_baseaf.Main;

import android.content.Context;

import mars.nomad.com.c3_baseaf.ITopbarView;
import mars.nomad.com.l0_base.Callback.IMainActionCallback;


/**
 * Created by SJH on 2017-06-28.
 * Main에 속하는 모든 아이템이 공통으로 갖는 요소를 모아둔 클래스.
 * p11친구 목록으로 부터 시작되는 메인의 fragment에 속하는 모든 페이지는 모두 이 클래스로 래핑될 것이다.
 */
public abstract class AMainViewItem {

    /**
     * fragment의 Action이 ActivityMain에 영향을 끼칠 경우, callback을 main에서 셋팅하여 호출하는 형태로 진행한다.
     */
    protected IMainActionCallback mCallback;

    /**
     * 현재 탭의 식별자
     */
    protected String mTag;

    /**
     * 상단바로 기능할 커스텀뷰.
     */
    @Deprecated
    protected ITopbarView mTopBar;

    /**
     * 탭 버튼에 사용될 background drawable 객체
     */
    protected int mTabButtonDrawable;


    /**
     * 페이지의 본문이 될 fragment 객체
     */
    protected BaseMainFragment mFragment;


    public IMainActionCallback getCallback() {
        return mCallback;
    }

    public void setCallback(IMainActionCallback mCallback) {
        this.mCallback = mCallback;
    }

    /**
     * 아래의 값들을 세팅한다.<br>
     * 이 값들은 반드시 세팅되어야 앱의 main에서 정상적으로 동작한다.<br>
     * setFragment(Fragment)
     * setTopBar(View)
     * setTabButtonDrawable(INT)
     */
    public abstract void setItem(Context context);


    public ITopbarView getTopBar() {
        return mTopBar;
    }

    public void setTopBar(ITopbarView mTopBar) {
        this.mTopBar = mTopBar;
    }

    public int getTabButtonDrawable() {
        return mTabButtonDrawable;
    }

    public void setTabButtonDrawable(int mTabButtonDrawable) {
        this.mTabButtonDrawable = mTabButtonDrawable;
    }

    public BaseMainFragment getFragment() {
        return mFragment;
    }

    public void setFragment(BaseMainFragment mFragment) {
        this.mFragment = mFragment;
    }

    public String getTag() {
        return mTag;
    }

    public void setTag(String mTag) {
        this.mTag = mTag;
    }
}
