package mars.nomad.com.a0_application.Test;

import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;

import mars.nomad.com.a0_application.R;
import mars.nomad.com.c3_baseaf.BaseActivity;
import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.l14_camera.CameraXModule;
import mars.nomad.com.l14_camera.FrameLayoutCamera;

public class ActivityTest extends BaseActivity {

    private FrameLayoutCamera frameLayoutCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setEvent();
        initCamera();
    }


    @Override
    protected void initView() {

        try {

            this.mContext = this;

            setContentView(R.layout.activity_test);

            frameLayoutCamera = (FrameLayoutCamera) findViewById(R.id.frameLayoutCamera);


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


    private void initCamera() {
        try {

            frameLayoutCamera.initCamera(getActivity(), getOwnerActivity(), new FrameLayoutCamera.CameraCallback() {
                @Override
                public void onCameraReady() {
                }
            });
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {

            case KeyEvent.KEYCODE_VOLUME_DOWN: // 볼륨다운버튼 클릭시 사진저장

                String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();

                File myDir = new File(root + "/churo");
                if (!myDir.exists()) {
                    myDir.mkdir();
                }

                frameLayoutCamera.takePicture(myDir, new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.US).format(System.currentTimeMillis()), "jpg",
                        true, true, new CameraXModule.CameraCaptureCallback() {
                            @Override
                            public void onCapture(File captureFile) {

                            }

                            @Override
                            public void onFailed(String fault) {
                                ErrorController.showToast(getActivity(), fault);
                            }
                        });
                return true;
        }
        return super.onKeyDown(keyCode, event);

    }
}