package mars.nomad.com.l14_camera;

import android.content.Context;
import android.graphics.Matrix;
import android.hardware.display.DisplayManager;
import android.util.Size;
import android.view.Display;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-05-23.
 */
public class AutoFitPreviewBuilder {

    private PreviewConfig mConfig;
    private TextureView mTextureView;
    Preview useCase;

    int bufferRotation;
    int viewFinderRotation;
    Size bufferDimens;
    Size viewFinderDimens;
    int viewFinderDisplay;
    DisplayManager displayManager;

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

    public AutoFitPreviewBuilder(PreviewConfig config, TextureView viewFinderRef) {
        try {

            this.mTextureView = viewFinderRef;
            this.mConfig = config;

            init();
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    private void init() {
        try {
            viewFinderDisplay = mTextureView.getDisplay().getDisplayId();
            viewFinderRotation = getDisplaySurfaceRotation(mTextureView.getDisplay());
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
                    updateTransform(mTextureView, rotation, output.getTextureSize(), viewFinderDimens);
                }
            });

            mTextureView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View view, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    mTextureView = (TextureView) view;
                    Size newViewFinderDimens = new Size(right - left, bottom - top);
                    int rotation = getDisplaySurfaceRotation(mTextureView.getDisplay());
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
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    private void updateTransform(TextureView mTextureView, int rotation, Size newBufferDimens, Size newViewFinderDimens) {
        try {
            if (mTextureView == null) {
                return;
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

            ErrorController.showMessage("[centerX] : " + centerX);
            ErrorController.showMessage("[centerY] : " + centerY);
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
