package mars.nomad.com.l12_applicationutil.Record;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import androidx.fragment.app.Fragment;


import java.io.File;

import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.l12_applicationutil.File.FileUtil;

/**
 * Created by SJH, NomadSoft.Inc on 2018-03-12. <br/>
 * 비디오를 녹화한 후, 녹화 완료된 경로를 돌려준다. <br/>
 * startRecording()을 통해 녹화를 시작하고, onActivityResult()를 통해 그 결과를 받는다. <br/>
 * 즉, 이 클래스를 사용하는 화면은 모두 onActivityResult에 이 클래스의 onActivityResult()를 호출해야 한다.<br/>
 * <B>이 클래스를 사용하는 액티비티, 및 프래그먼트를 포함하는 액티비티는 반드시 아래의 코드를 android manifest에 포함시켜야 한다.</B><br/>
 * <B>android:configChanges="orientation|screenSize"
 * android:screenOrientation="portrait"</B>
 */
public class NsVideoRecorder {

    public static final int NS_VIDEO_RECORDER_REQUEST_CODE = 16215;

    private File imageFileForNote2;

    private Uri imageCaptureUri;

    /**
     * 영상의 녹화를 시작한다.<br/>
     * 액티비티에서 이 메소드를 호출할 경우 startRecording(activity, null),<br/>
     * fragment에서 호출하는 경우 startRecording(activity, fragment)로 사용토록 한다.
     */
    public void startRecording(final Activity activity, final Fragment fragment, String dir) {

        try {

            try {

                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());

            } catch (Exception e) {
                ErrorController.showError(e);
            }

            Intent takePictureIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

            File file = new File(dir, System.currentTimeMillis() + ".mp4");
            file.createNewFile();

            setImageFileForNote2(file);

//            Uri photoURI = FileProvider.getUriForFile(activity, activity.getApplicationContext().getPackageName() + ".my.package.name.provider", file);

            setImageCaptureUri(Uri.fromFile(file));


            takePictureIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageCaptureUri);

            if (fragment == null) {

                activity.startActivityForResult(takePictureIntent, NS_VIDEO_RECORDER_REQUEST_CODE);
                activity.overridePendingTransition(0, 0);
            } else {

                fragment.startActivityForResult(takePictureIntent, NS_VIDEO_RECORDER_REQUEST_CODE);
                activity.overridePendingTransition(0, 0);
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    /**
     * 영상 녹화 결과를 처리 후 돌려준다.
     */
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data, INsVideoRecorderCallback callback) {

        try {

            if (requestCode == NS_VIDEO_RECORDER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {//영상 촬영 완료 -> 결과값을 파싱한다.

                Uri imageUri = getImageCaptureUri();

                String path = "";

                if (imageUri != null) {

                    path = FileUtil.getPath(activity, imageUri);
                    ErrorController.showMessage(path);
                    callback.onFileSaveSuccess(path);

                } else {

                    ErrorController.showMessage("Note 2 : picture uri is null.");
                    path = imageFileForNote2.getPath();
                    ErrorController.showMessage(path);

                    if (path != null) {//크기 체크

                        if (!FileUtil.sizeCheck(path)) {
                            callback.onFailed("50MB이상의 파일은 전송할 수 없습니다.");
                            return;
                        }

                    } else {
                        callback.onFailed("파일 경로를 갖고 오는것에 실패했습니다.");
                        return;
                    }

                    callback.onFileSaveSuccess(path);
                }
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    /**
     * 콜백 인터페이스
     */
    public interface INsVideoRecorderCallback {

        /**
         * 파일 저장 성공-> 저장된 경로를 내려준다.
         */
        void onFileSaveSuccess(String savedFilePath);

        /**
         * 파일 저장 실패 -> 실패 사유를 돌려준다.
         */
        void onFailed(String fault);
    }


    private void setImageFileForNote2(File imageFileForNote2) {
        this.imageFileForNote2 = imageFileForNote2;
    }

    private void setImageCaptureUri(Uri imageCaptureUri) {
        this.imageCaptureUri = imageCaptureUri;
    }

    private File getImageFileForNote2() {
        return imageFileForNote2;
    }

    private Uri getImageCaptureUri() {
        return imageCaptureUri;
    }

}
