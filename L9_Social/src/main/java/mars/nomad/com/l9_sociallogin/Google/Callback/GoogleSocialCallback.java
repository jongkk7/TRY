package mars.nomad.com.l9_sociallogin.Google.Callback;

import mars.nomad.com.l9_sociallogin.Common.CommonSocialCallback;
import mars.nomad.com.l9_sociallogin.Google.DataModel.GoogleSnsLoginDataModel;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-03-29.
 */
public interface GoogleSocialCallback extends CommonSocialCallback {

    void onSuccess(GoogleSnsLoginDataModel item);

    void onError(String fault);


}
