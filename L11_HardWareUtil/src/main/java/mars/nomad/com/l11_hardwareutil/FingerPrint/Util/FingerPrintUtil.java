package mars.nomad.com.l11_hardwareutil.FingerPrint.Util;

import android.app.Activity;

import androidx.biometric.BiometricPrompt;
import androidx.fragment.app.FragmentActivity;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by YJK on 2019-08-13
 *
 * 구글에서 제공해주는 지문인식 그대로 사용할 경우에 사용
 *
 **/
public class FingerPrintUtil {

    private Activity activity;
    private BiometricPrompt.AuthenticationCallback callback;
    private BiometricPrompt.PromptInfo info;
    private BiometricPrompt prompt;

    public FingerPrintUtil(Activity activity, BiometricPrompt.AuthenticationCallback callback){
        try{
            this.activity = activity;
            this.callback = callback;
            setting();
        }catch (Exception e){
            ErrorController.showError(e);
        }
    }

    /**
     * 지문인식 작동
     */
    public void authenticate(){
        try{

            prompt.authenticate(info);

        }catch (Exception e){
            ErrorController.showError(e);
        }
    }

    /**
     * Alert창에 나올 String 셋팅
     */
    private void setting(){

        try{

            Executor newExecutor = Executors.newSingleThreadExecutor();

            info = new androidx.biometric.BiometricPrompt.PromptInfo.Builder()
                    .setTitle("지문인식")
                    .setSubtitle("")
                    .setDescription("사용자의 인증 정보를 확인합니다.")
                    .setNegativeButtonText("취소")
                    .build();

            prompt = new BiometricPrompt((FragmentActivity)activity, newExecutor, callback);


        }catch (Exception e){
            ErrorController.showError(e);
        }
    }

}
