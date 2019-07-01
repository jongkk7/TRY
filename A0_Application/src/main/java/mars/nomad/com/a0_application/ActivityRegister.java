package mars.nomad.com.a0_application;


import mars.nomad.com.a0_application.Test.ActivityTest;
import mars.nomad.com.b3_commongallery.ActivityCommonGallery;
import mars.nomad.com.c1_activitymanager.ActivityManager;
import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by SJH, NomadSoft.Inc, 2019-02-12
 */
public class ActivityRegister {

    public static void register(){

        try{

            ActivityManager.addClass("ActivityTest", ActivityTest.class);
            ActivityManager.addClass("ActivityCommonGallery", ActivityCommonGallery.class);

        }catch(Exception e){
            ErrorController.showError(e);
        }
    }

    public static boolean isRegiDataExist() {
        return ActivityManager.isRegiDataExist();
    }
}
