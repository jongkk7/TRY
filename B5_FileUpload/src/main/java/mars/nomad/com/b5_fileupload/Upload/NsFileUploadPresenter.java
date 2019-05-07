package mars.nomad.com.b5_fileupload.Upload;

import android.content.Context;

import java.io.File;

import mars.nomad.com.l12_applicationutil.File.FileUtil;
import mars.nomad.com.l12_applicationutil.File.ThumbContainer;
import mars.nomad.com.l0_base.Callback.CommonCallback;
import mars.nomad.com.l0_base.Logger.ErrorController;


public class NsFileUploadPresenter {

    private NsFileUploadModel mModel;

    public NsFileUploadPresenter() {
        this.mModel = new NsFileUploadModel();
    }

    public void createThumbAndUploadImage(final Context context, final String userId, final String originalPath, final String targetType, final String targetKey, final CommonCallback.DoubleObjectCallback<String, String> callback) {

        try {

            FileUtil.createThumbnail(500, originalPath, context, new FileUtil.ThumbCallback() {
                @Override
                public void onThumbnailCreated(ThumbContainer thumb) {

                    mModel.uploadImgFileApi(userId, targetType, targetKey, thumb.getFile(), new File(originalPath), callback);
                }

                @Override
                public void onFailure(String fault) {
                    callback.onFailed(fault);
                }
            });

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void createThumbAndUploadImage(final Context context, final String userId, final String originalPath, final String targetType, final String targetKey, final CommonCallback.SingleObjectCallback<String> callback) {

        try {

            FileUtil.createThumbnail(500, originalPath, context, new FileUtil.ThumbCallback() {
                @Override
                public void onThumbnailCreated(ThumbContainer thumb) {

                    mModel.uploadImgFileApi(userId, targetType, targetKey, thumb.getFile(), new File(originalPath), callback);
                }

                @Override
                public void onFailure(String fault) {
                    callback.onFailed(fault);
                }
            });

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void createThumbAndUploadImage2(final Context context, final String userId, final String originalPath, final String targetType, final String targetKey, final CommonCallback.SingleObjectCallback<UploadPictureResponseModel> callback) {

        try {

            FileUtil.createThumbnail(500, originalPath, context, new FileUtil.ThumbCallback() {
                @Override
                public void onThumbnailCreated(ThumbContainer thumb) {

                    mModel.uploadImgFileApi2(userId, targetType, targetKey, thumb.getFile(), new File(originalPath), callback);
                }

                @Override
                public void onFailure(String fault) {
                    callback.onFailed(fault);
                }
            });

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }
}
