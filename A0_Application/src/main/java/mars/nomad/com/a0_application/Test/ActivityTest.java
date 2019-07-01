package mars.nomad.com.a0_application.Test;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import mars.nomad.com.a0_application.R;
import mars.nomad.com.c3_baseaf.BaseActivity;
import mars.nomad.com.l0_base.Logger.ErrorController;

public class ActivityTest extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setEvent();
        setAdapter();


        this.mContext = this;

        setContentView(R.layout.activity_test);


    }

    private void setAdapter() {
        try {

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    @Override
    protected void initView() {

        try {


        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    @Override
    protected void setEvent() {

        try {

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}