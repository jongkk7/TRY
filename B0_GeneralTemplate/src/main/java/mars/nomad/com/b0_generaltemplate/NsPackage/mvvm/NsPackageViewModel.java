package mars.nomad.com.b0_generaltemplate.NsPackage.mvvm;

import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import mars.nomad.com.a0_common.DataBase.Room.NsModule.NsModule;
import mars.nomad.com.a0_common.DataBase.Room.NsPackage.NsPackage;
import mars.nomad.com.a0_common.DataBase.Room.NsPackage.NsPackageRepository;
import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.l12_applicationutil.String.StringChecker;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-07-01.
 */
public class NsPackageViewModel extends ViewModel {

    private LiveData<List<NsPackage>> packageList;
    private LiveData<List<NsPackage>> rawLiveData;

    private NsModule mModule;

    public NsPackageViewModel() {

        rawLiveData = NsPackageRepository.getInstance().getPackageList();
    }

    public boolean getData(Intent intent) {

        try {

            mModule = (NsModule) intent.getSerializableExtra("module");

            if (mModule != null && StringChecker.isStringNotNull(mModule.getModuleName())) {

                packageList = Transformations.switchMap(rawLiveData, new Function<List<NsPackage>, LiveData<List<NsPackage>>>() {
                    @Override
                    public LiveData<List<NsPackage>> apply(List<NsPackage> input) {
                        return NsPackageRepository.getInstance().filterBy(mModule.getProjectName(), mModule.getModuleName(), input);
                    }
                });

                return true;
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return false;
    }
    public LiveData<List<NsPackage>> getPackageList() {
        return packageList;
    }

    public NsModule getModule() {
        return mModule;
    }
}
