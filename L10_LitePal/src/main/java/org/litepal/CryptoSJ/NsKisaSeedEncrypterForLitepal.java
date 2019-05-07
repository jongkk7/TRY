package org.litepal.CryptoSJ;

import android.util.Base64;


/**
 * Created by SJH, NomadSoft.Inc, 2018-10-18
 * SEED 암호화 및 복호화를 위한 래퍼
 */
public class NsKisaSeedEncrypterForLitepal {

    private static String charset = "utf-8";


    public static byte pbUserKey[] = {
            (byte) 0xb8, (byte) 0xa6, (byte) 0x2e, (byte) 0x0b, (byte) 0x75
            , (byte) 0xce, (byte) 0x7b, (byte) 0x4c, (byte) 0xab, (byte) 0x95
            , (byte) 0x7d, (byte) 0xda, (byte) 0xaf, (byte) 0x10, (byte) 0x23
            , (byte) 0x08
    };

    public static byte bszIV[] = {
            (byte) 0xea, (byte) 0x47, (byte) 0x43, (byte) 0x6d, (byte) 0x5d
            , (byte) 0xdf, (byte) 0xc4, (byte) 0x65, (byte) 0xba, (byte) 0x78
            , (byte) 0x13, (byte) 0xb0, (byte) 0xb2, (byte) 0xe1, (byte) 0x60
            , (byte) 0xe8
    };


    public static String encrypt(String str) {

        String result = "";

        byte[] encArray = null;

        try {

            byte[] enc = null;

            //암호화 함수 호출
            enc = KISA_SEED_CBC_FOR_LITE_PAL.SEED_CBC_Encrypt(pbUserKey, bszIV, str.getBytes(charset), 0,
                    str.getBytes(charset).length);

            encArray = Base64.encode(enc, Base64.DEFAULT);

            result = new String(encArray, "utf-8");

            LPErrorController.showMessage("Encrypted String : " + result);

        } catch (Exception e) {
            LPErrorController.showError(e);
        }

        return result;
    }

    public static String decrypt(byte[] str) {

        String result = "";

        try {

            byte[] enc = Base64.decode(str, Base64.DEFAULT);

            byte[] dec = null;

            //복호화 함수 호출
            dec = KISA_SEED_CBC_FOR_LITE_PAL.SEED_CBC_Decrypt(pbUserKey, bszIV, enc, 0, enc.length);
            result = new String(dec, charset);

           // LPErrorController.showMessage("Decrypted String = " + result);

        } catch (Exception e) {
            LPErrorController.showError(e);
        }
        return result;
    }


    public static String notNullEncrypt(String original) {

        String result = "";

        try {

            if (original != null && !original.equalsIgnoreCase("")) {

                result = encrypt(original);
            }

        } catch (Exception e) {
            LPErrorController.showError(e);
        }
        return result;
    }
}
