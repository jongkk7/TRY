package mars.nomad.com.l9_sociallogin.KaKaoTalk;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.kakao.auth.ApprovalType;
import com.kakao.auth.AuthType;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.ISessionConfig;
import com.kakao.auth.KakaoAdapter;
import com.kakao.auth.KakaoSDK;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;

import java.util.ArrayList;
import java.util.List;

import mars.nomad.com.l9_sociallogin.Common.SocialFlag;
import mars.nomad.com.l9_sociallogin.KaKaoTalk.Callback.KaKaoSocialCallback;
import mars.nomad.com.l9_sociallogin.KaKaoTalk.DataModel.KakaoSnsLoginDataModel;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-03-29.
 */
public class KaKaoTalkLoginHelper {

    ISessionCallback iSessionCallback;

    /**
     * 아래부턴 플래그값임. 해당 플래그값이 있을 경우 해당하는 데이터를 받아옴.
     * 단 개발자 계정에서 설정되어있어야만 에러없이 받아올수 있음.
     * (이메일은 기본으로 가져올수 있음. - 단 유저가 이메일을 정보제공 동의를 하지않으면 못받아옴.)
     *
     * 아래내용외 다른것도 가져올 순 있으나, 심사를 받아야함.
     *
     * - 카카오 스토리 관련 내용은 임의로 제외함. (사용한 적이 없음)
     */

    /**
     * 프로필 이미지 (640 * 640 크기)
     */
    private final boolean isProfileImage = false;


    /**
     * 프로필 썸네일 이미지 (110 * 110 크기)
     */
    private final boolean isThumbProfileImage = false;

    /**
     * 나이
     */
    private final boolean isAge = false;

    /**
     * 생일
     */
    private final boolean isBirthDay = false;


    /**
     * 성별
     */
    private final boolean isGender = false;

    /**
     * 여러개 들어오는걸 방지하기 위함
     */
    private String accessToken = "";


    public void requestKakaoLogin(Application application, Activity activity, final KaKaoSocialCallback callback) {
        try {

            if (!SocialFlag.kakaoInit) {
                SocialFlag.kakaoInit = true;
                KakaoSDK.init(new KakaoSDKAdapter(application));

            }

            if (iSessionCallback == null) {

                iSessionCallback = new ISessionCallback() {
                    @Override
                    public void onSessionOpened() {
                        UserManagement userManagement = UserManagement.getInstance();

                        List<String> keys = new ArrayList<>();
                        keys.add("kakao_account.email");

                        if (isProfileImage) {
                            keys.add("properties.profile_image");
                        }

                        if (isThumbProfileImage) {
                            keys.add("properties.thumbnail_image");
                        }

                        if (isAge) {
                            keys.add("kakao_account.age_range");
                        }

                        if (isBirthDay) {
                            keys.add("kakao_account.birthday");
                        }

                        if (isGender) {
                            keys.add("kakao_account.gender");
                        }

                        userManagement.me(keys, new MeV2ResponseCallback() {
                            @Override
                            public void onSessionClosed(ErrorResult errorResult) {
                                callback.onError(errorResult.getErrorMessage());
                            }

                            @Override
                            public void onSuccess(MeV2Response response) {

                                if (accessToken == null || accessToken.equalsIgnoreCase("") || !accessToken.equalsIgnoreCase(Session.getCurrentSession().getTokenInfo().getAccessToken())) {
                                    accessToken = Session.getCurrentSession().getTokenInfo().getAccessToken();

                                    KakaoSnsLoginDataModel dataModel = new KakaoSnsLoginDataModel();

                                    dataModel.setAccessToken(Session.getCurrentSession().getTokenInfo().getAccessToken());
                                    dataModel.setUserId(response.getId() + "");
                                    dataModel.setJoinType(2);
                                    dataModel.setEmail(response.getKakaoAccount().getEmail());

                                    if (isProfileImage) {
                                        dataModel.setProfileImg(response.getProfileImagePath());
                                    }

                                    if (isThumbProfileImage) {
                                        dataModel.setProfileThumbImg(response.getThumbnailImagePath());
                                    }

                                    if (isAge) {
                                        if (response.getKakaoAccount().getAgeRange() != null) {
                                            dataModel.setAge(response.getKakaoAccount().getAgeRange().getValue());
                                        }
                                    }

                                    if (isBirthDay) {
                                        dataModel.setBirthDay(response.getKakaoAccount().getBirthday());
                                    }

                                    if (isGender) {
                                        if (response.getKakaoAccount().getGender() != null) {
                                            dataModel.setGender(response.getKakaoAccount().getGender().getValue());
                                        }

                                    }

                                    callback.onSuccess(dataModel);
                                }

                            }
                        });
                    }

                    @Override
                    public void onSessionOpenFailed(KakaoException exception) {
                        callback.onError(exception.getMessage());
                    }
                };

                Session.getCurrentSession().addCallback(iSessionCallback);
            }

            if (!Session.getCurrentSession().checkAndImplicitOpen()) {
                Session.getCurrentSession().open(AuthType.KAKAO_TALK, activity);
            }

        } catch (Exception e) {
            callback.onException(e);
        }

    }

    private static class KakaoSDKAdapter extends KakaoAdapter {

        Application application;

        public KakaoSDKAdapter(Application application) {
            this.application = application;
        }

        /**
         * Session Config에 대해서는 default값들이 존재한다.
         * 필요한 상황에서만 override해서 사용하면 됨.
         *
         * @return Session의 설정값.
         */
        @Override
        public ISessionConfig getSessionConfig() {
            return new ISessionConfig() {
                @Override
                public AuthType[] getAuthTypes() {
                    return new AuthType[]{AuthType.KAKAO_LOGIN_ALL};
                }

                @Override
                public boolean isUsingWebviewTimer() {
                    return false;
                }

                @Override
                public boolean isSecureMode() {
                    return false;
                }

                @Override
                public ApprovalType getApprovalType() {
                    return ApprovalType.INDIVIDUAL;
                }

                @Override
                public boolean isSaveFormData() {
                    return true;
                }
            };
        }

        @Override
        public IApplicationConfig getApplicationConfig() {
            return new IApplicationConfig() {
                @Override
                public Context getApplicationContext() {
                    return application;
                }
            };
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //카카오톡
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
    }

}
