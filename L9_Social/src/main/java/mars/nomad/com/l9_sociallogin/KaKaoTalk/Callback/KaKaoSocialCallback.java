package mars.nomad.com.l9_sociallogin.KaKaoTalk.Callback;

import mars.nomad.com.l9_sociallogin.Common.CommonSocialCallback;
import mars.nomad.com.l9_sociallogin.KaKaoTalk.DataModel.KakaoSnsLoginDataModel;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-03-29.
 */
public interface KaKaoSocialCallback extends CommonSocialCallback {

    void onSuccess(KakaoSnsLoginDataModel item);

    void onError(String fault);

}
