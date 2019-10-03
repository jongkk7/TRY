package nomad.com.a1_init.p1_intro;

import android.os.Bundle;
import android.os.Handler;

import mars.nomad.com.c1_activitymanager.ActivityManager;
import mars.nomad.com.c3_baseaf.BaseActivity;
import mars.nomad.com.c3_baseaf.BaseApplicationActivity;
import mars.nomad.com.l0_base.Logger.ErrorController;
import nomad.com.a1_init.R;


public class ActivityIntro extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        // TODO 버전체크 -> 자동로그인
        nextActivity(false);

    }

    @Override
    protected void initView() {
    }

    @Override
    protected void setEvent() {

    }

    /**
     * 최신버전 확인
     */
    private void versionCheck(){

        try{



        }catch (Exception e){
            ErrorController.showError(e);
        }
    }

    /**
     * 자동 로그인
     */
    private void tryAutoLogin(){

        try{


        }catch (Exception e){
            ErrorController.showError(e);
        }
    }

    /**
     * 다음 화면으로
     */
    private void nextActivity(final boolean isLoginSuccese){

        try{

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    if(isLoginSuccese){

                        ActivityManager.goActivityWithoutExtra(getActivity(), null, null, false, "ActivityMain");

                    }else{

                        ActivityManager.goActivityWithoutExtra(getActivity(), null, null, false, "ActivityLogin");

                    }
                }
            }, 1000);


        }catch (Exception e){
            ErrorController.showError(e);
        }
    }

}
