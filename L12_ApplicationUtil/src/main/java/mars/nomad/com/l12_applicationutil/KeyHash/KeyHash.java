package mars.nomad.com.l12_applicationutil.KeyHash;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by SJ Han, NomadSoft.INC on 2016-11-05. <br/>
 */

public class KeyHash {

    private static final String TAG = KeyHash.class.getSimpleName();

    /**
     * 패키지 이름을 기반으로 키해쉬를 프린트한다.
     * @param context
     * @param packageName
     */
    public static void printKeyHash(Context context, String packageName){
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
               Log.e(TAG, Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("test", "ex",e);
        } catch (NoSuchAlgorithmException e) {
            Log.e("test", "ex",e);
        }
    }
}
