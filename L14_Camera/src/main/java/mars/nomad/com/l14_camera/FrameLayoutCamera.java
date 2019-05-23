package mars.nomad.com.l14_camera;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-05-23.
 */
public class FrameLayoutCamera extends FrameLayout {
    private TextureView textureViewCamera;
    private CameraXModule mVmodel;
    private RelativeLayout relativeLayoutCaptureAnim;

    public FrameLayoutCamera(@NonNull Context context) {
        super(context);
        initView();
        setEvent();
    }


    public FrameLayoutCamera(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
        setEvent();
    }

    public FrameLayoutCamera(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        setEvent();
    }

    public void initView() {
        try {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.framelayout_camera, this, false);

            mVmodel = new CameraXModule();

            textureViewCamera = (TextureView) view.findViewById(R.id.textureViewCamera);
            relativeLayoutCaptureAnim = (RelativeLayout) view.findViewById(R.id.relativeLayoutCaptureAnim);

            addView(view);


        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    private void setEvent() {
        try {


        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void initCamera(final Activity activity, final LifecycleOwner owner, final CameraCallback callback) {
        try {

            if (mVmodel != null && textureViewCamera != null) {
                textureViewCamera.post(new Runnable() {
                    @Override
                    public void run() {
                        mVmodel.initCamera(activity, owner, textureViewCamera);
                        callback.onCameraReady();
                    }
                });
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initCamera(activity, owner, callback);
                    }
                }, 500);
            }
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void takePicture(File baseFolder, String fileName, String extension, final boolean isAnim, boolean isGallery, final CameraXModule.CameraCaptureCallback captureCallback) {
        try {

            mVmodel.takePicture(getContext(), baseFolder, fileName, extension, isGallery, new CameraXModule.CameraCaptureCallback() {
                @Override
                public void onCapture(File captureFile) {

                    if (isAnim) {

                        relativeLayoutCaptureAnim.setVisibility(VISIBLE);
                        postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                relativeLayoutCaptureAnim.setVisibility(GONE);

                            }
                        }, 100);
                    }

                }

                @Override
                public void onFailed(String fault) {
                    captureCallback.onFailed(fault);
                }
            });

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    public interface CameraCallback {
        void onCameraReady();
    }

}
