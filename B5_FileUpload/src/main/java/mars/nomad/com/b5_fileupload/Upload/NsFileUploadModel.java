package mars.nomad.com.b5_fileupload.Upload;


import java.io.File;

import mars.nomad.com.l0_base.Callback.CommonCallback;
import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.m0_http.NSCallback;
import mars.nomad.com.m0_http.RetrofitSender2;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class NsFileUploadModel {

    public void uploadImgFileApi(String strUserId, String strTargetType, String strTargetKey, File thumbnailFile, File originalFile, final CommonCallback.DoubleObjectCallback<String, String> callback) {
        try {

            MultipartBody.Part fileData = MultipartBody.Part.createFormData("fileData", originalFile.getName(), RequestBody.create(MediaType.parse("image/*"), originalFile));

            RequestBody filePath =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), strTargetType);

            MultipartBody.Part thumbData = MultipartBody.Part.createFormData("thumbData", thumbnailFile.getName(), RequestBody.create(MediaType.parse("image/*"), thumbnailFile));


            RequestBody useUniqueFileName =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), "1");
            RequestBody useDateFolder =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), "1");
            RequestBody makeThumb =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), "0");
            RequestBody thumbWidth =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), "400");
            RequestBody thumbHeight =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), "400");

            RequestBody userId =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), strUserId);

            RequestBody targetType =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), strTargetType);

            RequestBody targetKey =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), strTargetKey);


            RequestBody makeMicro =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), "0");

            RetrofitSender2.initAndGetFileEndPoint(WebEndPointFile.class).sendImageUploadNoAuth(fileData,
                    filePath, thumbData, useUniqueFileName, useDateFolder, makeThumb, thumbWidth, thumbHeight, userId, targetType, targetKey, makeMicro).enqueue(new NSCallback<UploadPictureResponseModel>(new NSCallback.SingleObjectCallback<UploadPictureResponseModel>() {
                @Override
                public void onSuccess(UploadPictureResponseModel result) {

                    callback.onSuccess(result.getFile_path() + result.getFile_name(), result.getFile_path() + result.getThumb_name());
                }

                @Override
                public void onFailed(String fault) {

                    callback.onFailed(fault);
                }
            }));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void uploadImgFileApi(String strUserId, String strTargetType, String strTargetKey, File thumbnailFile, File originalFile, final CommonCallback.SingleObjectCallback<String> callback) {
        try {

            MultipartBody.Part fileData = MultipartBody.Part.createFormData("fileData", originalFile.getName(), RequestBody.create(MediaType.parse("image/*"), originalFile));

            RequestBody filePath =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), "ZzimCong");

            MultipartBody.Part thumbData = MultipartBody.Part.createFormData("thumbData", thumbnailFile.getName(), RequestBody.create(MediaType.parse("image/*"), thumbnailFile));


            RequestBody useUniqueFileName =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), "1");
            RequestBody useDateFolder =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), "1");
            RequestBody makeThumb =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), "0");
            RequestBody thumbWidth =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), "400");
            RequestBody thumbHeight =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), "400");

            RequestBody userId =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), strUserId);

            RequestBody targetType =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), strTargetType);

            RequestBody targetKey =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), strTargetKey);


            RequestBody makeMicro =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), "1");


            RetrofitSender2.initAndGetFileEndPoint(WebEndPointFile.class).sendImageUploadNoAuth(fileData,
                    filePath, thumbData, useUniqueFileName, useDateFolder, makeThumb, thumbWidth, thumbHeight, userId, targetType, targetKey, makeMicro).enqueue(new NSCallback<UploadPictureResponseModel>(new NSCallback.SingleObjectCallback<UploadPictureResponseModel>() {
                @Override
                public void onSuccess(UploadPictureResponseModel result) {

                    callback.onSuccess(result.getFile_seq() + "");

                }

                @Override
                public void onFailed(String fault) {

                    callback.onFailed(fault);
                }
            }));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void uploadImgFileApi2(String strUserId, String strTargetType, String strTargetKey, File thumbnailFile, File originalFile, final CommonCallback.SingleObjectCallback<UploadPictureResponseModel> callback) {
        try {

            MultipartBody.Part fileData = MultipartBody.Part.createFormData("fileData", originalFile.getName(), RequestBody.create(MediaType.parse("image/*"), originalFile));

            RequestBody filePath =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), "ZzimCong");

            MultipartBody.Part thumbData = MultipartBody.Part.createFormData("thumbData", thumbnailFile.getName(), RequestBody.create(MediaType.parse("image/*"), thumbnailFile));


            RequestBody useUniqueFileName =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), "1");
            RequestBody useDateFolder =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), "1");
            RequestBody makeThumb =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), "0");
            RequestBody thumbWidth =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), "400");
            RequestBody thumbHeight =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), "400");

            RequestBody userId =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), strUserId);

            RequestBody targetType =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), strTargetType);

            RequestBody targetKey =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), strTargetKey);


            RequestBody makeMicro =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), "0");

            RetrofitSender2.initAndGetFileEndPoint(WebEndPointFile.class).sendImageUploadNoAuth(fileData,
                    filePath, thumbData, useUniqueFileName, useDateFolder, makeThumb, thumbWidth, thumbHeight, userId, targetType, targetKey, makeMicro).enqueue(new NSCallback<UploadPictureResponseModel>(new NSCallback.SingleObjectCallback<UploadPictureResponseModel>() {
                @Override
                public void onSuccess(UploadPictureResponseModel result) {

                    callback.onSuccess(result);

                }

                @Override
                public void onFailed(String fault) {

                    callback.onFailed(fault);
                }
            }));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }
}
