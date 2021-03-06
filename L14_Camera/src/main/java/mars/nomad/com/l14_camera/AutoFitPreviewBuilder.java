package mars.nomad.com.l14_camera;

import android.app.Activity;
import android.content.Context;
import android.graphics.Matrix;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.util.Size;
import android.view.Display;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import mars.nomad.com.l0_base.Callback.CommonCallback;
import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-05-23.
 */
public class AutoFitPreviewBuilder {

    private Activity activity;
    private boolean isMatchSize;
    private PreviewConfig mConfig;
    private TextureView mTextureView;
    Preview useCase;

    int bufferRotation;
    int viewFinderRotation;
    Size bufferDimens;
    Size viewFinderDimens;
    int viewFinderDisplay;
    DisplayManager displayManager;
    private int mRotation;

    boolean isLock = false;

    DisplayManager.DisplayListener displayListener = new DisplayManager.DisplayListener() {
        @Override
        public void onDisplayAdded(int displayId) {

        }

        @Override
        public void onDisplayRemoved(int displayId) {

        }

        @Override
        public void onDisplayChanged(int displayId) {
            if (mTextureView == null) {
                return;
            }
            if (displayId != viewFinderDisplay) {
                Display display = displayManager.getDisplay(displayId);
                int rotation = getDisplaySurfaceRotation(display);
                updateTransform(mTextureView, rotation, bufferDimens, viewFinderDimens);
            }
        }
    };


    public AutoFitPreviewBuilder(Activity activity, boolean isMatchSize, PreviewConfig config, TextureView viewFinderRef) {
        try {
            this.activity = activity;
            this.isMatchSize = isMatchSize;
            this.mTextureView = viewFinderRef;
            this.mConfig = config;

            init();
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    int rotation = 0;


    private void init() {
        try {
            if (isMatchSize) {
                viewFinderDisplay = activity.getWindowManager().getDefaultDisplay().getDisplayId();
            }
            viewFinderRotation = getDisplaySurfaceRotation(mTextureView.getDisplay());

            if (isMatchSize) {
                viewFinderRotation = getDisplaySurfaceRotation(activity.getWindowManager().getDefaultDisplay());
            }
            useCase = new Preview(mConfig);
            useCase.setOnPreviewOutputUpdateListener(new Preview.OnPreviewOutputUpdateListener() {
                @Override
                public void onUpdated(Preview.PreviewOutput output) {
                    // To update the SurfaceTexture, we have to remove it and re-add it
                    ViewGroup parent = (ViewGroup) mTextureView.getParent();

                    parent.removeView(mTextureView);
                    parent.addView(mTextureView, 0);

                    mTextureView.setSurfaceTexture(output.getSurfaceTexture());
                    bufferRotation = output.getRotationDegrees();
                    int rotation = getDisplaySurfaceRotation(mTextureView.getDisplay());

                    if (isMatchSize) {
                        rotation = getDisplaySurfaceRotation(activity.getWindowManager().getDefaultDisplay());
                    }
                    updateTransform(mTextureView, rotation, output.getTextureSize(), viewFinderDimens);
                }
            });

            mTextureView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View view, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    mTextureView = (TextureView) view;
                    Size newViewFinderDimens = new Size(right - left, bottom - top);
                    int rotation = getDisplaySurfaceRotation(mTextureView.getDisplay());

                    if (isMatchSize) {
                        rotation = getDisplaySurfaceRotation(activity.getWindowManager().getDefaultDisplay());
                    }
                    ErrorController.showMessage("[onLayoutChange] **");
                    updateTransform(mTextureView, rotation, bufferDimens, newViewFinderDimens);
                }
            });

            // Every time the orientation of device changes, recompute layout
            displayManager = (DisplayManager) mTextureView.getContext()
                    .getSystemService(Context.DISPLAY_SERVICE);
            displayManager.registerDisplayListener(displayListener, null);

            mTextureView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(View v) {

                }

                @Override
                public void onViewDetachedFromWindow(View v) {
                    displayManager.unregisterDisplayListener(displayListener);
                }
            });

            final OrientationEventListener orientationEventListener = new OrientationEventListener(mTextureView.getContext(), SensorManager.SENSOR_DELAY_UI) {
                @Override
                public void onOrientationChanged(int orientation) {
                    // 0˚ (portrait)
                    // 90˚
                    if (orientation >= 82 && orientation < 98) {
                        rotation = 270;
                    }

                    // 270˚ (landscape)
                    else if (orientation >= 262 && orientation < 278) {
                        rotation = 90;
                    }

                    if (mRotation != rotation) {

                        mRotation = rotation;


                        if (rotation == 270 || rotation == 90) {

                            final int finalRotation = rotation;
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    ErrorController.showMessage("[onOrientationChanged] **");


                                    updateTransform(mTextureView, finalRotation, bufferDimens, viewFinderDimens);
                                }
                            }, 100);
                        }
                    }

                }
            };


            ((AppCompatActivity) activity).getLifecycle().addObserver(new LifecycleEventObserver() {
                @Override
                public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {

                    try {
                        if (event == Lifecycle.Event.ON_RESUME) {
                            if (orientationEventListener.canDetectOrientation()) {
                                orientationEventListener.enable();
                            }
                        } else if (event == Lifecycle.Event.ON_PAUSE) {
                            orientationEventListener.disable();
                        }
                    } catch (Exception e) {
                        ErrorController.showError(e);
                    }

                }
            });


        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    private void updateTransform(TextureView mTextureView, int rotation, Size newBufferDimens, Size newViewFinderDimens) {
        try {
            if (mTextureView == null) {
                return;
            }
            if (isLock) {
                isLock = false;
                return;
            } else {
                isLock = true;
            }

            if (rotation == viewFinderRotation &&
                    Objects.equals(newBufferDimens, bufferDimens) &&
                    Objects.equals(newViewFinderDimens, viewFinderDimens)) {
                // Nothing has changed, no need to transform output again
                return;
            }

            viewFinderRotation = rotation;

            if (newBufferDimens.getWidth() == 0 || newBufferDimens.getHeight() == 0) {
                // Invalid buffer dimens - wait for valid inputs before setting matrix
                return;
            } else {
                // Update internal field with new inputs
                bufferDimens = newBufferDimens;
            }

            if (newViewFinderDimens == null || newViewFinderDimens.getWidth() == 0 || newViewFinderDimens.getHeight() == 0) {
                // Invalid view finder dimens - wait for valid inputs before setting matrix
                return;
            } else {
                // Update internal field with new inputs
                viewFinderDimens = newViewFinderDimens;
            }

            Matrix matrix = new Matrix();

            // Compute the center of the view finder
            float centerX = viewFinderDimens.getWidth() / 2f;
            float centerY = viewFinderDimens.getHeight() / 2f;

            ErrorController.showMessage("[viewFinderRotation] : " + viewFinderRotation);

            // Correct preview output to account for display rotation
            matrix.postRotate(-viewFinderRotation, centerX, centerY);

            // Buffers are rotated relative to the device's 'natural' orientation: swap width and height
            float bufferRatio = bufferDimens.getHeight() / (float) bufferDimens.getWidth();

            ErrorController.showMessage("[bufferRatio] : " + bufferRatio);


            int scaledWidth;
            int scaledHeight;
            // Match longest sides together -- i.e. apply center-crop transformation
            if (viewFinderDimens.getWidth() > viewFinderDimens.getHeight()) {
                scaledHeight = viewFinderDimens.getWidth();
                scaledWidth = Math.round(viewFinderDimens.getWidth() * bufferRatio);
            } else {
                scaledHeight = viewFinderDimens.getHeight();
                scaledWidth = Math.round(viewFinderDimens.getHeight() * bufferRatio);
            }

            // Compute the relative scale value
            float xScale = scaledWidth / (float) viewFinderDimens.getWidth();
            float yScale = scaledHeight / (float) viewFinderDimens.getHeight();

            // Scale input buffers to fill the view finder
            matrix.preScale(xScale, yScale, centerX, centerY);

            // Finally, apply transformations to our TextureView
            mTextureView.setTransform(matrix);
            isLock = false;

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    private int getDisplaySurfaceRotation(Display display) {

        int result = 0;

        switch (display.getRotation()) {
            case Surface.ROTATION_0:
                result = 0;
                break;
            case Surface.ROTATION_90:
                result = 90;
                break;
            case Surface.ROTATION_180:
                result = 180;
                break;
            case Surface.ROTATION_270:
                result = 270;
                break;
        }

        return result;

    }


    public Preview Builder() {
        return useCase;
    }
}
