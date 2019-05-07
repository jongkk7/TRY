package mars.nomad.com.l12_applicationutil.Crypt;

import android.util.Base64;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by SJH on 2017-04-21.
 */

public class NsCrypto {

    public static String AESDecrypt(String text, String key) {

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            byte[] keyBytes = new byte[16];

            byte[] b = key.getBytes("UTF-8");

            int len = b.length;

            if (len > keyBytes.length) len = keyBytes.length;

            System.arraycopy(b, 0, keyBytes, 0, len);

            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

            IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);

            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

            byte[] results = cipher.doFinal(Base64.decode(text, 0));

            return new String(results, "UTF-8");
        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return null;
    }


    public static String AESEncrypt(String text, String key) {

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            byte[] keyBytes = new byte[16];

            byte[] b = key.getBytes("UTF-8");

            int len = b.length;

            if (len > keyBytes.length) len = keyBytes.length;

            System.arraycopy(b, 0, keyBytes, 0, len);

            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

            IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);

            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

            byte[] results = cipher.doFinal(text.getBytes("UTF-8"));

            return Base64.encodeToString(results, 0);
        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return null;
    }
}
