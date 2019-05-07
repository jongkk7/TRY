package mars.nomad.com.l9_sociallogin.FaceBook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.util.Arrays;

import mars.nomad.com.l9_sociallogin.FaceBook.Callback.FaceBookSocialCallback;
import mars.nomad.com.l9_sociallogin.FaceBook.DataModel.FacebookSNSLoginDataModel;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-01-14.
 */

/**
 * 페이스북은 중간에 Cancel되었을때 이벤트도 받을 수 있음.
 * 일단 callback으로 뺴놨음.
 */
public class FaceBookLoginHelper {

    private CallbackManager callbackManager; // 페이스북 콜백메니져

    public void requestFacebookLogin(Activity activity, final FaceBookSocialCallback callback) {
        try {
            callbackManager = CallbackManager.Factory.create();

            LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("public_profile", "email"));
            LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(final LoginResult loginResult) {
                    GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            try {

                                FacebookSNSLoginDataModel item = new FacebookSNSLoginDataModel();

                                item.setUserId(loginResult.getAccessToken().getUserId());
                                if (object.has("email")) {
                                    item.setEmail(object.getString("email"));
                                }

                                if (object.has("name")) {
                                    item.setUserName(object.getString("name"));
                                }
                                item.setAccessToken(loginResult.getAccessToken().getToken());
                                item.setJoinType(1);

                                String profilePath = "https://graph.facebook.com/" + loginResult.getAccessToken().getUserId() + "/picture";

                                item.setProfilePath(profilePath);

                                callback.onSuccess(item);

                            } catch (Exception e) {
                                Log.e("Mars", "Error", e);

                            }
                        }
                    });
                    Bundle params = new Bundle();
                    params.putString("fields", "id, name, email");
                    request.setParameters(params);
                    request.executeAsync();
                }

                @Override
                public void onCancel() {
                    callback.onCancel("Facebook Login Canceled");
                }

                @Override
                public void onError(FacebookException error) {
                    callback.onError(error.getMessage());
                }
            });
        } catch (Exception e) {
           callback.onException(e);
        }

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //페북
        if (callbackManager != null) {
            if (callbackManager.onActivityResult(requestCode, resultCode, data)) {
                return;
            }
        }

    }
}
