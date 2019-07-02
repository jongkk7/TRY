package mars.nomad.com.a0_application.Test;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

import java.io.IOException;

import androidx.annotation.Nullable;
import mars.nomad.com.a0_application.R;
import mars.nomad.com.c3_baseaf.BaseActivity;
import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.l14_camera.FaceDetector.FaceGraphic;
import mars.nomad.com.l14_camera.FaceDetector.GraphicOverlay;
import mars.nomad.com.l14_camera.FaceDetector.NsCameraSourcePreview;

public class ActivityTest extends BaseActivity {

    private NsCameraSourcePreview cameraSourcePreview;
    private mars.nomad.com.l14_camera.FaceDetector.GraphicOverlay GraphicOverlay;
    private CameraSource mCameraSource;
    private RelativeLayout relativeLayoutRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setEvent();
        createCameraSource();
    }


    @Override
    protected void initView() {

        try {
            this.mContext = this;

            setContentView(R.layout.activity_test_2);
            relativeLayoutRoot = (RelativeLayout) findViewById(R.id.relativeLayoutRoot);
            cameraSourcePreview = (NsCameraSourcePreview) findViewById(R.id.cameraSourcePreview);
            GraphicOverlay = (GraphicOverlay) findViewById(R.id.GraphicOverlay);
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


    private void createCameraSource() {

        Context context = getApplicationContext();
        FaceDetector detector = new FaceDetector.Builder(context)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .build();

        detector.setProcessor(
                new MultiProcessor.Builder<>(new GraphicFaceTrackerFactory())
                        .build());

        if (!detector.isOperational()) {
            // Note: The first time that an app using face API is installed on a device, GMS will
            // download a native library to the device in order to do detection.  Usually this
            // completes before the app is run for the first time.  But if that download has not yet
            // completed, then the above call will not detect any faces.
            //
            // isOperational() can be used to check if the required native library is currently
            // available.  The detector will automatically become operational once the library
            // download completes on device.
            ErrorController.showMessage("Face detector dependencies are not yet available.");
        }

        mCameraSource = new CameraSource.Builder(context, detector)
                .setRequestedPreviewSize(1920, 1080)
                .setFacing(CameraSource.CAMERA_FACING_FRONT)
                .setRequestedFps(30.0f)
                .build();


    }


    /**
     * Restarts the camera.
     */
    @Override
    protected void onResume() {
        super.onResume();

        startCameraSource();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCameraSource != null) {
            mCameraSource.release();
        }
    }

    private void startCameraSource() {

        try {
            // check that the device has play services available.
            int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
                    getApplicationContext());
            if (code != ConnectionResult.SUCCESS) {
                Dialog dlg =
                        GoogleApiAvailability.getInstance().getErrorDialog(this, code, 9001);
                dlg.show();
            }

            if (mCameraSource != null) {
                try {
                    cameraSourcePreview.start(mCameraSource, GraphicOverlay);
                } catch (IOException e) {
                    ErrorController.showError(e);
                    mCameraSource.release();
                    mCameraSource = null;
                }
            }
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    /**
     * Stops the camera.
     */
    @Override
    protected void onPause() {
        super.onPause();
        cameraSourcePreview.stop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Factory for creating a face tracker to be associated with a new face.  The multiprocessor
     * uses this factory to create face trackers as needed -- one for each individual.
     */
    private class GraphicFaceTrackerFactory implements MultiProcessor.Factory<Face> {
        @Override
        public Tracker<Face> create(Face face) {
            return new GraphicFaceTracker(GraphicOverlay);
        }
    }

    /**
     * Face tracker for each detected individual. This maintains a face graphic within the app's
     * associated face overlay.
     */
    private class GraphicFaceTracker extends Tracker<Face> {
        private GraphicOverlay mOverlay;
        private FaceGraphic mFaceGraphic;

        GraphicFaceTracker(GraphicOverlay overlay) {
            mOverlay = overlay;
            mFaceGraphic = new FaceGraphic(overlay);
        }

        /**
         * Start tracking the detected face instance within the face overlay.
         */
        @Override
        public void onNewItem(int faceId, Face item) {
            mFaceGraphic.setId(faceId);
        }

        /**
         * Update the position/characteristics of the face within the overlay.
         */
        @Override
        public void onUpdate(FaceDetector.Detections<Face> detectionResults, Face face) {
            mOverlay.add(mFaceGraphic);
            mFaceGraphic.updateFace(face);
        }

        /**
         * Hide the graphic when the corresponding face was not detected.  This can happen for
         * intermediate frames temporarily (e.g., if the face was momentarily blocked from
         * view).
         */
        @Override
        public void onMissing(FaceDetector.Detections<Face> detectionResults) {
            mOverlay.remove(mFaceGraphic);
        }

        /**
         * Called when the face is assumed to be gone for good. Remove the graphic annotation from
         * the overlay.
         */
        @Override
        public void onDone() {
            mOverlay.remove(mFaceGraphic);
        }
    }

}