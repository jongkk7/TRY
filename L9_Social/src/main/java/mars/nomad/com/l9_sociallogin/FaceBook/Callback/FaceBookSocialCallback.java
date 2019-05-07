package mars.nomad.com.l9_sociallogin.FaceBook.Callback;


import mars.nomad.com.l9_sociallogin.Common.CommonSocialCallback;
import mars.nomad.com.l9_sociallogin.FaceBook.DataModel.FacebookSNSLoginDataModel;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-01-14.
 */
public interface FaceBookSocialCallback extends CommonSocialCallback {

    void onSuccess(FacebookSNSLoginDataModel item);

    void onCancel(String onCancel);

    void onError(String fault);
}
