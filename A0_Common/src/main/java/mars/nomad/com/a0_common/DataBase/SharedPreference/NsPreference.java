package mars.nomad.com.a0_common.DataBase.SharedPreference;

import android.content.Context;

public class NsPreference {



    private static Boolean isLoginIdSaved = null;

    public static Boolean isLoginIdSaved(Context context) {
        if (isLoginIdSaved == null) {
            isLoginIdSaved = SharedObject.getProperty_boolean(context, "isLoginIdSaved", false);
        }
        return isLoginIdSaved;
    }

    public static void setLoginIdSaved(Context context, Boolean value) {
        SharedObject.setProperty_boolean(context, "isLoginIdSaved", value);
        isLoginIdSaved = null;
    }





}
