package mars.nomad.com.l11_hardwareutil.FingerPrint.Util;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;

import mars.nomad.com.l0_base.Callback.CommonCallback;

/**
 * Created by YJK on 2019-08-13
 **/
@TargetApi(Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback{

    private CancellationSignal cancellationSignal;
    private Context context;

    private CommonCallback.SingleObjectCallback<FingerprintManager.AuthenticationResult> callback;

    public FingerprintHandler(Context context, CommonCallback.SingleObjectCallback<FingerprintManager.AuthenticationResult> callback){
        this.context = context;
        this.callback = callback;
    }

    //메소드들 정의
    public void startAutho(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject){
        cancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        callback.onFailed(errString.toString());
    }

    @Override
    public void onAuthenticationFailed() {
        callback.onFailed("인증 실패");
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        callback.onFailed("[Error] " + helpString);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        callback.onSuccess(result);
    }

    public void stopFingerAuth(){
        if(cancellationSignal != null && !cancellationSignal.isCanceled()){
            cancellationSignal.cancel();
        }
    }

}