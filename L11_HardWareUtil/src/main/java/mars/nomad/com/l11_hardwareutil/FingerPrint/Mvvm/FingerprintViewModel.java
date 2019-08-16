package mars.nomad.com.l11_hardwareutil.FingerPrint.Mvvm;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;

import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import mars.nomad.com.l0_base.Callback.CommonCallback;
import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.l11_hardwareutil.FingerPrint.Util.FingerprintHandler;

/**
 * Created by YJK on 2019-08-16
 **/
public class FingerprintViewModel {

    private static final String KEY_NAME = "secure_manger_key"; // 암호화 키

    private final String ERROR_VERSION = "지문 인식을 사용할 수 없는 버전입니다.";
    private final String ERROR_DEVICE = "지문을 사용할 수 없는 장치입니다.";
    private final String ERROR_PERMISSION = "지문 권한이 허가되지 않았습니다.";
    private final String ERROR_NO_FINGERPRINT = "등록된 지문이 없습니다.";

    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private Cipher cipher;
    private FingerprintManager.CryptoObject cryptoObject;

    private FingerprintHandler fingerprintHandler;

    /**
     * 지문 인식 준비
     * 1. 6.0 이상만 사용가능
     * 2. 지문 권한 허가시에만 사용가능 ( USE_FINGERPRINT )
     * 3. 등록된 지문이 있을경우에만 사용가능
     * 4. 지문 인식 준비 시작
     */
    public void initFingerPrint(Context context, final IFingerprintCallback callback) {

        try{

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // 6.0 이상만 사용가능하다.

                fingerprintManager = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);
                keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);

                if (!fingerprintManager.isHardwareDetected()) { // 지문인식 장치가 없음

                    callback.onFailed(ERROR_DEVICE);

                } else if (context.checkSelfPermission(Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) { // 권한허가 안됨

                    callback.onFailed(ERROR_PERMISSION);

                } else if (!fingerprintManager.hasEnrolledFingerprints()) { // 등록된 지문이 없음

                    callback.onFailed(ERROR_NO_FINGERPRINT);

                } else { // 지문 인식 초기화

                    // 지문 준비 완료
                    generateKey();
                    if (cipherInit()) {
                        cryptoObject = new FingerprintManager.CryptoObject(cipher);

                        callback.onSuccessInit(); // 초기화 성공

                        //핸들러실행
                        fingerprintHandler = new FingerprintHandler(context, new CommonCallback.SingleObjectCallback<FingerprintManager.AuthenticationResult>() {
                            @Override
                            public void onSuccess(final FingerprintManager.AuthenticationResult result) {
                                callback.onEqual(result);
                            }

                            @Override
                            public void onFailed(String fault) {
                                callback.onNotEqual(fault);
                            }
                        });

                        fingerprintHandler.startAutho(fingerprintManager, cryptoObject);
                    }
                }

            } else {
                callback.onFailed(ERROR_VERSION);
            }

        }catch (Exception e){
            ErrorController.showError(e);
        }
    }

    public void stopHandler(){

        try{

            fingerprintHandler.stopFingerAuth();

        }catch (Exception e){
            ErrorController.showError(e);
        }
    }

    //Cipher Init()
    public boolean cipherInit() {
        try {
            cipher = Cipher.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES + "/"
                            + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }
        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }

    //Key Generator
    protected void generateKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException("Failed to get KeyGenerator instance", e);
        }

        try {
            keyStore.load(null);
            keyGenerator.init(new KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public interface IFingerprintCallback{

        void onFailed(String fault);

        void onSuccessInit(); // 초기화 성공

        void onEqual(FingerprintManager.AuthenticationResult result);// 지문 인식 성공

        void onNotEqual(String fault); // 지문 인식 실패
    }
}
