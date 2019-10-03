package mars.nomad.com.a0_application;


import mars.nomad.com.b0_generaltemplate.NsAddPackage.ActivityAddPackage;
import mars.nomad.com.b0_generaltemplate.NsModule.ActivityNsModule;
import mars.nomad.com.b0_generaltemplate.NsPackage.ActivityNsPackage;
import mars.nomad.com.b0_generaltemplate.NsProject.ActivityNsProject;
import mars.nomad.com.b0_generaltemplate.SingleModule.ActivityGeneralTemplateSingleModule;
import mars.nomad.com.b3_commongallery.ActivityCommonGallery;
import mars.nomad.com.c1_activitymanager.ActivityManager;
import mars.nomad.com.l0_base.Logger.ErrorController;
import nomad.com.a1_init.p1_intro.ActivityIntro;
import nomad.com.a1_init.p2_login.ActivityLogin;
import nomad.com.a1_init.p3_join.ActivityJoin;

/**
 * Created by SJH, NomadSoft.Inc, 2019-02-12
 */
public class ActivityRegister {

    public static void register(){

        try{

            ActivityManager.addClass("ActivityCommonGallery", ActivityCommonGallery.class);

            ActivityManager.addClass("ActivityAddPackage", ActivityAddPackage.class);
            ActivityManager.addClass("ActivityNsProject", ActivityNsProject.class);
            ActivityManager.addClass("ActivityNsModule", ActivityNsModule.class);
            ActivityManager.addClass("ActivityNsPackage", ActivityNsPackage.class);
            ActivityManager.addClass("ActivityGeneralTemplateSingleModule", ActivityGeneralTemplateSingleModule.class);

            // init
            ActivityManager.addClass("ActivityIntro", ActivityIntro.class);
            ActivityManager.addClass("ActivityLogin", ActivityLogin.class);
            ActivityManager.addClass("ActivityJoin", ActivityJoin.class);

            // main



        }catch(Exception e){
            ErrorController.showError(e);
        }
    }

    public static boolean isRegiDataExist() {
        return ActivityManager.isRegiDataExist();
    }
}
