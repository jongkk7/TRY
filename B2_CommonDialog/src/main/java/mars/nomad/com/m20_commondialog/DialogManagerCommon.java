package mars.nomad.com.m20_commondialog;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import androidx.fragment.app.Fragment;

import mars.nomad.com.l0_base.Callback.CommonCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.m20_commondialog.CommonListDialog.CommonDialog;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-01-25.
 */
public class DialogManagerCommon {


    public interface pictureCameraCallback {
        void onCameraCallback(File file);
    }

    public static final int CAMERA_REQUEST = 9093;

    public static final int PICTURE_SELECT_REQUEST = 9094;

    public static void showDialogPictureCamera(final Activity activity, final Fragment fragment, String title, final pictureCameraCallback callback) {

        try {

            final List<String> menu = new ArrayList<>();
            menu.add("Camera");
            menu.add("File Select");

            new CommonDialog(activity, title, menu, new CommonCallback.SingleSelectionCallback<String>() {
                @Override
                public void onSelection(String selection) {

                    if (selection.equals(menu.get(0))) {//카메라로 사진 촬영.


                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        try {
                            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                            StrictMode.setVmPolicy(builder.build());

                            File file = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
                            file.createNewFile();

                            callback.onCameraCallback(file);

                            takePictureIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

                            if (fragment != null) {
                                fragment.startActivityForResult(takePictureIntent, CAMERA_REQUEST);
                            } else {
                                activity.startActivityForResult(takePictureIntent, CAMERA_REQUEST);
                            }
                        } catch (Exception e) {
                            ErrorController.showError(e);
                        }

                    } else if (selection.equals(menu.get(1))) {//엘범에서 사진 선택
                        Intent mediaChooser = new Intent(Intent.ACTION_GET_CONTENT);
                        mediaChooser.setType("image/*");

                        if (fragment != null) {
                            fragment.startActivityForResult(mediaChooser, PICTURE_SELECT_REQUEST);
                        } else {
                            activity.startActivityForResult(mediaChooser, PICTURE_SELECT_REQUEST);
                        }

                    }
                }
            }).show();

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }
}
