package nomad.com.a1_init.p2_login;

import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.lifecycle.ViewModelProviders;

import mars.nomad.com.c1_activitymanager.ActivityManager;
import mars.nomad.com.c3_baseaf.BaseActivity;
import mars.nomad.com.l0_base.Callback.CommonCallback;
import mars.nomad.com.l0_base.Callback.SingleClickListener;
import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.l9_sociallogin.Google.Callback.GoogleSocialCallback;
import mars.nomad.com.l9_sociallogin.Google.DataModel.GoogleSnsLoginDataModel;
import nomad.com.a1_init.R;
import nomad.com.a1_init.p2_login.mvvm.LoginViewModel;


public class ActivityLogin extends BaseActivity {

    private LoginViewModel mViewModel;

    private RelativeLayout relativeLayoutGoogle;
    private RelativeLayout relativeLayoutNaver;
    private RelativeLayout relativeLayoutKakao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        setEvent();
    }

    @Override
    protected void initView() {

        try{

            mViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

            relativeLayoutGoogle = (RelativeLayout) findViewById(R.id.relativeLayoutGoogle);
            relativeLayoutNaver = (RelativeLayout) findViewById(R.id.relativeLayoutNaver);
            relativeLayoutKakao = (RelativeLayout) findViewById(R.id.relativeLayoutKakao);

        }catch (Exception e){
            ErrorController.showError(e);
        }
    }

    @Override
    protected void setEvent() {

        try{

            relativeLayoutGoogle.setOnClickListener(new SingleClickListener(new CommonCallback.SingleActionCallback() {
                @Override
                public void onAction() {

                    mViewModel.loginGoogle(getActivity(), new GoogleSocialCallback() {
                        @Override
                        public void onException(Exception e) {
                            ErrorController.showMessage("[GOOGLE] onException : " + e.getMessage());
                        }

                        @Override
                        public void onSuccess(final GoogleSnsLoginDataModel item) {
                            ErrorController.showMessage("[requestGoogleLogin] : " + item.toString());




                        }

                        @Override
                        public void onError(String fault) {
                            ErrorController.showMessage("[GOOGLE] onError : " + fault);
                        }
                    });

                }
            }));

            relativeLayoutNaver.setOnClickListener(new SingleClickListener(new CommonCallback.SingleActionCallback() {
                @Override
                public void onAction() {

                }
            }));

            relativeLayoutKakao.setOnClickListener(new SingleClickListener(new CommonCallback.SingleActionCallback() {
                @Override
                public void onAction() {

                }
            }));
        }catch (Exception e){
            ErrorController.showError(e);
        }
    }


}
