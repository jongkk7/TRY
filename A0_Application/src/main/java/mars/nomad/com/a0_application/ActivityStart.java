package mars.nomad.com.a0_application;

import android.Manifest;
import android.os.Bundle;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

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

            TedPermission.with(mContext)
                    .setPermissionListener(new PermissionListener() {

                        @Override
                        public void onPermissionGranted() {

                            ActivityManager.goActivityWithoutExtra(getActivity(), null, null, false, "ActivityGeneralTemplate");
                            //ActivityManager.goActivityWithoutExtra(getActivity(), null, null, false, "ActivityActivityTest");
                            finish();
                        }

                        @Override
                        public void onPermissionDenied(ArrayList<String> deniedPermissions) {

                            finish();
                        }
                    })
                    .setRationaleTitle("사용자 기기 접근 권한 요청")
                    .setRationaleMessage("필수접근 권한\n필수접근 권한 미동의 시, 어플리케이션을 사용하실 수 없습니다.")
                    .setDeniedMessage("해당 권한을 허용하지 않으면 서비스 이용이 불가합니다. 하단의 설정 버튼을 누르신 후 권한을 활성화주세요.")
//                    .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA,
//                            Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                    .setPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                    .check();

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }
}
