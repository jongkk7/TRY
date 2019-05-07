package mars.nomad.com.l12_applicationutil.Hash;


import android.util.Base64;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by SJH on 2017-04-21.
 */

public class NsHash {

    public static String md5(String s)
    {
        MessageDigest digest;
        try
        {
            digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes(Charset.forName("US-ASCII")),0,s.length());
            byte[] magnitude = digest.digest();
            BigInteger bi = new BigInteger(1, magnitude);
            String hash = String.format("%0" + (magnitude.length << 1) + "x", bi);
            return hash;
        }
        catch (NoSuchAlgorithmException e)
        {
            ErrorController.showError(e);
        }
        return null;
    }

    public static String sha256(String original) {

        String result = "";

        try {

            StringBuffer hexString = new StringBuffer();

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(original.getBytes("UTF-8"));
//            digest.update("base 64".getBytes("UTF-8"));

            byte[] hash = digest.digest();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            result = hexString.toString();
            result = Base64.encodeToString(result.getBytes(), 0).replaceAll("\n", "");


        } catch (Exception e) {
            ErrorController.showError(e);
        }

        return result;
    }

    public static String getHashKey(String input) {
        String result = "";

        try {

            MessageDigest md = MessageDigest.getInstance("SHA-256");

            md.update(input.getBytes());
            byte byteData[] = md.digest();
            result = Base64.encodeToString(byteData, Base64.NO_WRAP);

        } catch (NoSuchAlgorithmException e) {
            ErrorController.showError(e);
        }

        return result;
    }

}
