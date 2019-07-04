package mars.nomad.com.b0_generaltemplate;

import android.app.Activity;
import android.content.Intent;

import androidx.core.app.ActivityOptionsCompat;

import java.io.Serializable;

import mars.nomad.com.a0_common.DataBase.Room.NsProject.NsProject;
import mars.nomad.com.c1_activitymanager.ActivityManager;
import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by SJH, NomadSoft.Inc, 2019-07-04
 */
public class ActivityManagerTemplate extends ActivityManager {


    public static void goActivityNsModule(Activity activity, ActivityOptionsCompat options, String action, NsProject project) {

        try {

            Intent intent = new Intent(activity, getClassByName("ActivityNsModule"));
            intent.setAction(action);
            intent.putExtra("project", (Serializable) project);

            startActivity(activity, intent, options);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }
}
