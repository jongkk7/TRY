package nomad.com.a1_init.p2_login.mvvm;

import android.app.Activity;

import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import mars.nomad.com.l0_base.Callback.CommonCallback;
import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.l9_sociallogin.Google.Callback.GoogleSocialCallback;
import mars.nomad.com.l9_sociallogin.Google.GoogleLoginHelper;
import mars.nomad.com.l9_sociallogin.NsSocialConstants;

/**
 * Created by YJK on 2019-10-03
 **/
public class LoginViewModel extends ViewModel {

    private GoogleLoginHelper mGoogleHelper;

    /**
     * 구글 로그인
     */
    public void loginGoogle(Activity activity, GoogleSocialCallback callback) {

        try {

            mGoogleHelper = new GoogleLoginHelper();

            mGoogleHelper.requestGoogleLogin(activity, callback);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    /**
     * 네이버 로그인
     */
    public void loginNaver() {
        try {


        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    /**
     * 카카오톡 로그인
     */
    public void loginKakao() {
        try {


        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    /**
     * 로그인
     */
    public void login(Activity activity, String id, String pwd, final CommonCallback.SingleObjectCallback<Boolean> callback){

        try{

            regPushKey(activity, new CommonCallback.SingleObjectCallback<Boolean>() {
                @Override
                public void onSuccess(Boolean result) {

                    // todo login

                }

                @Override
                public void onFailed(String fault) {
                    callback.onFailed("[PushKey] Error : " + fault);
                }
            });

        }catch (Exception e){
            ErrorController.showError(e);
        }
    }

    /**
     * 푸시키 가져오기
     */
    public void regPushKey(final Activity activity, final CommonCallback.SingleObjectCallback<Boolean> callback) {

        try {



            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(activity, new OnSuccessListener<InstanceIdResult>() {
                @Override
                public void onSuccess(InstanceIdResult instanceIdResult) {
                    String newToken = instanceIdResult.getToken();

                    ErrorController.showMessage("[DEBUG] PUSHKEY : " + newToken);
                    callback.onSuccess(true);

                }
            });

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }
}
