package mars.nomad.com.l9_sociallogin.Naver;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import mars.nomad.com.l9_sociallogin.Naver.Callback.NaverSocialCallback;
import mars.nomad.com.l9_sociallogin.Naver.DataModel.NaverSnsLoginDataModel;
import mars.nomad.com.l9_sociallogin.R;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-03-29.
 */
public class NaverLoginHelper {


    private OAuthLogin mOAuthLoginModule;

    public void requestNaverLogin(final Activity activity, final NaverSocialCallback callback) {

        mOAuthLoginModule = OAuthLogin.getInstance();

        mOAuthLoginModule.init(
                activity, activity.getResources().getString(R.string.naver_client_id)
                , activity.getResources().getString(R.string.naver_client_secret)
                , activity.getResources().getString(R.string.app_name)
        );

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mOAuthLoginModule.startOauthLoginActivity(activity, new NsOAuthLoginHandler(mOAuthLoginModule, activity, callback));
            }
        }, 1000);
    }

    private static class NsOAuthLoginHandler extends OAuthLoginHandler {

        private final OAuthLogin mOAuthLoginModule;
        private final Activity activity;
        private final NaverSocialCallback callback;

        NsOAuthLoginHandler(OAuthLogin mOAuthLoginModule, Activity activity, NaverSocialCallback callback) {
            this.mOAuthLoginModule = mOAuthLoginModule;
            this.activity = activity;
            this.callback = callback;
        }

        @Override
        public void run(boolean success) {
            if (success) {
                new GetNaverLoginInfoAsync(mOAuthLoginModule.getAccessToken(activity), callback).execute();
            } else {
                callback.onError("Naver Login Failed");
            }
        }
    }

    /**
     * 네이버 로그인 콜백 클래스
     */
    private static class GetNaverLoginInfoAsync extends AsyncTask<Void, Void, String> {

        private String token;
        private NaverSocialCallback callback;

        public GetNaverLoginInfoAsync(String token, NaverSocialCallback callback) {
            this.token = token;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(Void... voids) {

            String result = null;
            String header = "Bearer " + token; // Bearer 다음에 공백 추가

            try {

                String apiURL = "https://openapi.naver.com/v1/nid/me";
                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Authorization", header);

                int responseCode = con.getResponseCode();

                BufferedReader br;

                if (responseCode == 200) { // 정상 호출
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                } else {  // 에러 발생
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                }

                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }

                br.close();
                result = response.toString();

            } catch (Exception e) {
                callback.onException(e);
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {

                NaverSnsLoginDataModel item = new NaverSnsLoginDataModel();

                try {

                    JSONObject object = new JSONObject(result);
                    JSONObject jsonObject = object.getJSONObject("response");
                    item.setAccessToken(token);
                    item.setJoinType(3);
                    if (jsonObject.has("id")) {
                        item.setUserId(jsonObject.getString("id"));
                    }

                    if (jsonObject.has("gender")) {
                        item.setGender(jsonObject.getString("gender"));
                    }

                    if (jsonObject.has("email")) {
                        item.setEmail(jsonObject.getString("email"));
                    }

                    if (jsonObject.has("nickname")) {
                        item.setNickName(jsonObject.getString("nickname"));
                    }

                    if (jsonObject.has("name")) {
                        item.setUserName(jsonObject.getString("name"));
                    }

                } catch (Exception e) {
                    callback.onException(e);

                }
            } else {
                callback.onError("naver login info request result is null");
            }
        }
    }


}
