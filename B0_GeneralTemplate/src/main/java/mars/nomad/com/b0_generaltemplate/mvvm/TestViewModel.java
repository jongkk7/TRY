package mars.nomad.com.b0_generaltemplate.mvvm;

import android.content.Intent;

import androidx.lifecycle.ViewModel;

import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by SJH, NomadSoft.Inc, 2019-07-01
 */
public class TestViewModel extends ViewModel {

    public boolean getData(Intent intent){

        try{

            //todo insert logic to be true
            if(true) {

                return true;
            }

        }catch(Exception e){
            ErrorController.showError(e);
        }
        return false;
    }
}
