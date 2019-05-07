package mars.nomad.com.l9_sociallogin.Naver.Callback;

import mars.nomad.com.l9_sociallogin.Common.CommonSocialCallback;
import mars.nomad.com.l9_sociallogin.Naver.DataModel.NaverSnsLoginDataModel;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-03-29.
 */
public interface NaverSocialCallback  extends CommonSocialCallback {
    void onError(String fault);

    void onSuccess(NaverSnsLoginDataModel item);
}
