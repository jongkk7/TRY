package mars.nomad.com.c3_baseaf;

import android.content.Context;
import android.content.DialogInterface;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import mars.nomad.com.l12_applicationutil.String.StringProcesser;
import mars.nomad.com.l4_language.NsLanguagePack;
import mars.nomad.com.l5_event.EventPoster;
import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by SJH on 2017-03-07.
 */

public abstract class BaseFragment extends Fragment {


    protected abstract void initView(View view);

    protected abstract void setEvent();

    public abstract boolean onBackPressed();

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

    public void startLoading(View view) {
        try {

            view.setVisibility(View.VISIBLE);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void stopLoading(View view) {
        try {

            view.setVisibility(View.GONE);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    @Override
    public void onResume() {

        super.onResume();

        try {

            EventPoster.register(this);

            NsLanguagePack.setTextLanguage((ViewGroup) getActivity().getWindow().getDecorView().getRootView());

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

    public BaseFragment getFragment() {

        return this;
    }

    /**
     * 간단한 message + 확인 조합의 얼러트 다이얼로그를 출력한다.
     *
     * @param message
     */
    protected void showSimpleAlertDialog(String message) {

        try {

            if (getContext() == null) {

                ErrorController.showMessage("[BaseFragment] showSimpleAlertDialog- context is null.");
                return;
            }

            AlertDialog dialog = new AlertDialog.Builder(getContext())
                    .setMessage(message)
                    .setPositiveButton("확인", null)
                    .create();

            if (getContext() != null && getActivity() != null) {

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

            if (getContext() == null) {

                ErrorController.showMessage("[BaseFragment] showSimpleAlertDialog- context is null.");
                return;
            }

            AlertDialog dialog = new AlertDialog.Builder(getContext())
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton("확인", clickListener)
                    .create();

            if (getContext() != null && getActivity() != null) {

                dialog.show();
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }
}
