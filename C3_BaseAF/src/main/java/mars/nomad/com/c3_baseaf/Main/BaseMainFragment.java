package mars.nomad.com.c3_baseaf.Main;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import mars.nomad.com.l5_event.EventPoster;
import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by SJH on 2017-06-29.
 * 메인 메뉴용 프래그먼트
 */
public abstract class BaseMainFragment extends Fragment {

    protected AMainViewItem mMainViewItem;

    protected abstract void initView(View view);

    protected abstract void setEvent();

    public abstract boolean onBackPressed();

    public void setMainViewItem(AMainViewItem mainViewItem){
        this.mMainViewItem = mainViewItem;
    }

    public AMainViewItem getMainViewItem() {
        return mMainViewItem;
    }

    protected void fadeInAndShow(Context context, View view){
        Animation animation2 = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        animation2.reset();
        view.clearAnimation();
        view.startAnimation(animation2);
        view.setVisibility(View.VISIBLE);
    }

    protected void fadeoutAndHide(Context context, View view){
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
        animation.reset();
        view.clearAnimation();
        view.startAnimation(animation);
        view.setVisibility(View.GONE);
    }

    protected void fadeoutAndInvisible(Context context, View view){
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
        animation.reset();
        view.clearAnimation();
        view.startAnimation(animation);
        view.setVisibility(View.INVISIBLE);
    }

    public void startLoading(View view){
        try {

            view.setVisibility(View.VISIBLE);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void stopLoading(View view){
        try {

            view.setVisibility(View.GONE);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    protected void disableRecyclerViewFlickering(RecyclerView recyclerView){

        try {

            RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();

            if (animator instanceof SimpleItemAnimator) {
                ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    protected BaseMainFragment getFragment(){
        return this;
    }

    /**
     * FlagManager에 영향을 받는 화면의 경우, 일관성 유지를 위해 이 메소드를 오버라이드해서 사용하도록 한다.
     */
    protected void setUiByFlag(){

    }

    /**
     *
     */
    public void doEventAction(){

    }

    public void doEventAction(String distance){


    }

    public abstract void refreshUI();

    @Override
    public void onResume() {
        super.onResume();
        try {

            EventPoster.register(this);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    @Override
    public void onStop() {

        try {

            EventPoster.unregister(this);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        super.onStop();
    }
}
