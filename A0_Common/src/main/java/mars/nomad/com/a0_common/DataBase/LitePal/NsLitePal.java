package mars.nomad.com.a0_common.DataBase.LitePal;

import android.app.Application;

import org.litepal.LitePal;

import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-05-07.
 */
public class NsLitePal {

    public static void initialize(Application application){
        try{
            LitePal.initialize(application);

        }catch (Exception e){
            ErrorController.showError(e);
        }
    }
}
