package mars.nomad.com.l9_sociallogin.Google;

import android.app.Activity;
import android.content.Intent;
import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import mars.nomad.com.l9_sociallogin.Common.SocialFlag;
import mars.nomad.com.l9_sociallogin.Google.Callback.GoogleSocialCallback;
import mars.nomad.com.l9_sociallogin.Google.DataModel.GoogleSnsLoginDataModel;
import mars.nomad.com.l9_sociallogin.R;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-03-29.
 */
public class GoogleLoginHelper {

    private GoogleSocialCallback mGoogleCallback;

    private int REQUEST_CODE_GOOGLE = 9595;
    private FirebaseAuth mAuth;
    private GoogleSignInOptions gso;

    public void requestGoogleLogin(Activity activity, GoogleSocialCallback callback) {
        try {

            if (!SocialFlag.googleInit) {
                SocialFlag.googleInit = true;
                mAuth = FirebaseAuth.getInstance();

                gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(activity.getString(R.string.google_client_id))
                        .requestEmail()
                        .build();
            }

            try {

                this.mGoogleCallback = callback;


                GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);

                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                activity.startActivityForResult(signInIntent, REQUEST_CODE_GOOGLE);

            } catch (Exception e) {
                callback.onException(e);
            }


        } catch (Exception e) {
            callback.onException(e);
        }
    }

    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {

        try {

            //구글 로그인 결과를 처리한다.
            if (requestCode == REQUEST_CODE_GOOGLE) {

                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleGoogleLoginResult(activity, task);
                return;
            }
        } catch (Exception e) {
            if (mGoogleCallback != null) {
                mGoogleCallback.onException(e);
            }
        }

    }

    /**
     * 구글 로그인 결과 처리
     *
     * @param activity
     * @param task
     */
    public void handleGoogleLoginResult(Activity activity, Task<GoogleSignInAccount> task) {

        if (mGoogleCallback != null) {

            try {

                final GoogleSignInAccount account = task.getResult(ApiException.class);

                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

                mAuth.signInWithCredential(credential).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();

                            GoogleSnsLoginDataModel dataModel = new GoogleSnsLoginDataModel();

                            dataModel.setAccessToken(account.getIdToken());
                            if (user != null) {
                                dataModel.setUserId(user.getUid());
                                dataModel.setUserName(user.getDisplayName());
                            }
                            dataModel.setJoinType(6);





                        } else {
                            mGoogleCallback.onError("Google Login Failed");
                        }
                    }
                });

                GoogleSnsLoginDataModel item = new GoogleSnsLoginDataModel();

                String token = account.getIdToken();

                if (token == null || token.equalsIgnoreCase("")) {

                    token = System.currentTimeMillis() + "";
                }

                item.setAccessToken(token);
                item.setUserId(account.getId());
                item.setUserName(account.getDisplayName());
                item.setJoinType(6);
                mGoogleCallback.onSuccess(item);

            } catch (Exception e) {
                mGoogleCallback.onException(e);
            }
        }
    }


}
