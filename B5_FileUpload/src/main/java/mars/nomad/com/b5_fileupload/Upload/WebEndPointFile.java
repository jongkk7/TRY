package mars.nomad.com.b5_fileupload.Upload;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface WebEndPointFile {

    @Multipart
    @POST("/file/img")
    Call<UploadPictureResponseModel> sendImageUploadNoAuth(
            @Part MultipartBody.Part fileData, @Part("filePath") RequestBody filePath,
            @Part MultipartBody.Part thumbData, @Part("useUniqueFileName") RequestBody useUniqueFileName,
            @Part("useDateFolder") RequestBody useDateFolder, @Part("makeThumb") RequestBody makeThumb,
            @Part("thumbWidth") RequestBody thumbWidth, @Part("thumbHeight") RequestBody thumbHeight,
            @Part("userId") RequestBody userId, @Part("targetType") RequestBody targetType,
            @Part("targetKey") RequestBody targetKey, @Part("makeMicro") RequestBody makeMicro);
}
