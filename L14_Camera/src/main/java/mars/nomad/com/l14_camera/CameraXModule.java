package mars.nomad.com.l14_camera;

import android.app.Activity;
import android.content.Context;
import android.media.MediaScannerConnection;
import android.util.DisplayMetrics;
import android.util.Rational;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.webkit.MimeTypeMap;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureConfig;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.lifecycle.LifecycleOwner;
import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-05-23.
 */
public class CameraXModule {

    private ImageCapture imageCapture;
    private Preview preview;
    private PreviewConfig config;
    private CameraX.LensFacing lensFacing = CameraX.LensFacing.BACK;
    private int imageRotation;

    public void initCamera(final Activity activity, final LifecycleOwner owner, final TextureView textureView) {
        try {

            CameraX.unbindAll();

            DisplayMetrics realMetrics = new DisplayMetrics();
            // Initialize a DisplayMetrics object that receives the TextureView's real display size
            textureView.getDisplay().getRealMetrics(realMetrics);

            Size screenSize = new Size(realMetrics.widthPixels, realMetrics.heightPixels);
            Rational screenAspectRatio = new Rational(realMetrics.widthPixels, realMetrics.heightPixels);

            ErrorController.showMessage("Metrics:" + realMetrics.widthPixels + " x " + realMetrics.heightPixels);


            config = new PreviewConfig.Builder()
                    .setLensFacing(lensFacing)
                    .setTargetResolution(screenSize)
                    .setTargetAspectRatio(screenAspectRatio)
                    .setTargetRotation(textureView.getDisplay().getRotation())
                    .build();


            preview = new AutoFitPreviewBuilder(config, textureView).Builder();

            imageRotation = textureView.getDisplay().getRotation();

            ImageCaptureConfig imageCaptureConfig =
                    new ImageCaptureConfig.Builder()
                            .setLensFacing(lensFacing)
                            .setCaptureMode(ImageCapture.CaptureMode.MAX_QUALITY)
                            .setTargetAspectRatio(screenAspectRatio)
                            .setTargetRotation(imageRotation)
                            .build();


            imageCapture = new ImageCapture(imageCaptureConfig);

            // The use case is bound to an Android Lifecycle with the following code.
            CameraX.bindToLifecycle(owner, preview, imageCapture);
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    public void takePicture(final Context context, File baseFolder, String fileName,  final String extension, final boolean isGallery, final CameraCaptureCallback captureCallback) {
        try {


            if (imageCapture != null) {
                final File photoFile = createFile(baseFolder, fileName, extension);

                if (photoFile.exists()) {
                    photoFile.delete();
                }
                ImageCapture.Metadata metadata = new ImageCapture.Metadata();
                metadata.isReversedHorizontal = lensFacing == CameraX.LensFacing.FRONT ||  imageRotation == Surface.ROTATION_90;
                // Setup image capture listener which is triggered after photo has been taken
                imageCapture.takePicture(photoFile, new ImageCapture.OnImageSavedListener() {
                    @Override
                    public void onImageSaved(@NonNull File file) {
                        ErrorController.showMessage("Photo capture succeeded:" + photoFile.getAbsolutePath());

                        String mimeType = MimeTypeMap.getSingleton()
                                .getMimeTypeFromExtension(extension);
                        if (isGallery) {
                            MediaScannerConnection.scanFile(
                                    context, new String[]{photoFile.getAbsolutePath()}, new String[]{mimeType}, null);
                        }


                        captureCallback.onCapture(photoFile);
                    }

                    @Override
                    public void onError(@NonNull ImageCapture.UseCaseError useCaseError, @NonNull String message, @Nullable Throwable cause) {
                        ErrorController.showError(cause);
                        ErrorController.showMessage("Photo capture failed:" + message);
                        captureCallback.onFailed(message);
                    }
                }, metadata);
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    private File createFile(File baseFolder, String fileName, String extension) {
        return new File(baseFolder,
                fileName + "." + extension);
    }

    public interface CameraCaptureCallback {

        void onCapture(File captureFile);

        void onFailed(String fault);

    }
}
