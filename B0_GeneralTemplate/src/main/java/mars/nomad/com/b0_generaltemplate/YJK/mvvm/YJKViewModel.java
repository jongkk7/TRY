package mars.nomad.com.b0_generaltemplate.YJK.mvvm;

import android.content.Intent;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import mars.nomad.com.b0_generaltemplate.YJK.DataModel.YJKDataModel;
import mars.nomad.com.l0_base.Callback.CommonCallback;
import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by SJH, NomadSoft.Inc, 2019-07-02
 */
public class YJKViewModel extends ViewModel {

    private int mPage = 1;

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

    public void loadList(final boolean isFirst, final CommonCallback.ListCallback<YJKDataModel> callback) {

        try {

            if (isFirst) {

                mPage = 1;

            } else {

                mPage++;
            }

            //todo load list from api
            callback.onSuccess(new ArrayList<YJKDataModel>());

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }
}
