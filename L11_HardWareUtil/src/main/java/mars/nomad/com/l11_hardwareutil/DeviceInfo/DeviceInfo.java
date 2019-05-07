package mars.nomad.com.l11_hardwareutil.DeviceInfo;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.util.UUID;

import mars.nomad.com.l0_base.Logger.ErrorController;

public class DeviceInfo {
    @SuppressLint("MissingPermission")
    public static String getDeviceID(Context context) {
        String result = "";
        try {
            final TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);

            final String tmDevice, tmSerial, androidId;
            tmDevice = "" + tm.getDeviceId();
            tmSerial = "" + tm.getSimSerialNumber();
            androidId = ""
                    + android.provider.Settings.Secure.getString(
                    context.getContentResolver(),
                    android.provider.Settings.Secure.ANDROID_ID);

            UUID deviceUuid = new UUID(androidId.hashCode(),
                    ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
            result = deviceUuid.toString();
        } catch (Exception e) {

            ErrorController.showError(e);
        }
        return result;
    }

    private static String uniqueID = null;
    private static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";

    public synchronized static String id(Context context) {
        if (uniqueID == null) {
            SharedPreferences sharedPrefs = context.getSharedPreferences(
                    PREF_UNIQUE_ID, Context.MODE_PRIVATE);
            uniqueID = sharedPrefs.getString(PREF_UNIQUE_ID, null);
            if (uniqueID == null) {
                uniqueID = UUID.randomUUID().toString();
                Editor editor = sharedPrefs.edit();
                editor.putString(PREF_UNIQUE_ID, uniqueID);
                editor.commit();
            }
        }
        return uniqueID;
    }

    public static String getOsType() {
        return "android";
    }

    public static String getOsVersion() {
        return String.valueOf(Build.VERSION.SDK_INT);
    }

    public static String getDeviceName() {
        return Build.MODEL;
    }

    public static String getAppVersion(Context context) {
        try {
            PackageInfo pinfo = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);

            return pinfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static int getSDKVersion() {
        int nSDKVersion = Integer.parseInt(Build.VERSION.SDK);
        return nSDKVersion;
    }



    /**
     * 모델명
     *
     * @return
     */
    public static String getDeviceModel() {
        String result = "";

        try {

            result = Build.MODEL;

        } catch (Exception e) {
            ErrorController.showError(e);
        }

        return result;
    }

    public static String getDeviceManufacturer() {

        String result = "";

        try {

            result = Build.MANUFACTURER;

        } catch (Exception e) {
            ErrorController.showError(e);
        }

        return result;
    }

    public static String getUDID(Context contxet) {

        String result = "";

        try {

            result = Settings.Secure.getString(contxet.getContentResolver(),
                    Settings.Secure.ANDROID_ID);

        } catch (Exception e) {
            ErrorController.showError(e);
        }

        return result;
    }


    public static String getAccountEmail(Context context) {
        String result = "";
        try {
            AccountManager am = (AccountManager) context
                    .getSystemService(Context.ACCOUNT_SERVICE);
            @SuppressLint("MissingPermission") Account[] accounts = am.getAccounts();
            if (accounts.length > 0) {
                result = accounts[0].name;
            }
        } catch (Exception e) {
            ErrorController.showError(e);

        }

        return result;
    }
}
