package mars.nomad.com.a0_application;


import mars.nomad.com.c1_activitymanager.ActivityManager;
import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by SJH, NomadSoft.Inc, 2019-02-12
 */
public class ActivityRegister {

    public static void register(){

        try{

        }catch(Exception e){
            ErrorController.showError(e);
        }
    }

    public static boolean isRegiDataExist() {
        return ActivityManager.isRegiDataExist();
    }
}
