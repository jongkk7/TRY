package mars.nomad.com.a0_application;


import mars.nomad.com.b0_generaltemplate.ActivityGeneralTemplate;
import mars.nomad.com.b0_generaltemplate.NsProject.ActivityNsProject;
import mars.nomad.com.b3_commongallery.ActivityCommonGallery;
import mars.nomad.com.c1_activitymanager.ActivityManager;
import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by SJH, NomadSoft.Inc, 2019-02-12
 */
public class ActivityRegister {

    public static void register(){

        try{

            ActivityManager.addClass("ActivityCommonGallery", ActivityCommonGallery.class);

            ActivityManager.addClass("ActivityGeneralTemplate", ActivityGeneralTemplate.class);
            ActivityManager.addClass("ActivityNsProject", ActivityNsProject.class);



        }catch(Exception e){
            ErrorController.showError(e);
        }
    }

    public static boolean isRegiDataExist() {
        return ActivityManager.isRegiDataExist();
    }
}
