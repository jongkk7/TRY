package mars.nomad.com.b0_generaltemplate.VpTest.Mvvm;

;

import android.content.Intent;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import mars.nomad.com.a0_common.DataBase.Room.NsModule.NsModule;
import mars.nomad.com.l0_base.Callback.CommonCallback;
import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by SJH, NomadSoft.Inc, 2019-07-01
 */
public class NsVpTestViewModel extends ViewModel {

    public boolean getData(Intent intent) {

        try {

            //todo insert logic to be true
            if (true) {

                return true;
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return false;
    }

    public void loadViewPager(final CommonCallback.SingleObjectCallback<List<NsModule>> callback) {

        try {

            List<NsModule> result = new ArrayList<>();
            //result.add(new InputDataModel("A"));
            //result.add(new InputDataModel("B"));
            //result.add(new InputDataModel("C"));

            callback.onSuccess(result);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }
}
