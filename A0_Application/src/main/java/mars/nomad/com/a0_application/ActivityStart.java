package mars.nomad.com.a0_application;

import android.os.Bundle;

import mars.nomad.com.c1_activitymanager.ActivityManager;
import mars.nomad.com.c3_baseaf.BaseApplicationActivity;
import mars.nomad.com.l0_base.Logger.ErrorController;


public class ActivityStart extends BaseApplicationActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        goToNextActivity();
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void setEvent() {

    }

    @Override
    protected void setActivityManager() {

    }

    @Override
    protected void goToNextActivity() {
        try {
            finish();
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }
}
