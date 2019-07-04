package mars.nomad.com.a0_common.DataBase.Room.NsPackage;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.l8_room.NsRepository;
import mars.nomad.com.a0_common.DataBase.Room.NsDataBase;

public class NsPackageRepository extends NsRepository<NsPackage> {

    private static NsPackageRepository instance;
    private LiveData<List<NsPackage>> packageList;

    public static NsPackageRepository getInstance() {

        try {

            if (instance == null) {

                instance = new NsPackageRepository();
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return instance;
    }

    private NsPackageRepository() {
    }

    @Override
    public void initiateDao(Application application) {

        try {

            NsDataBase dataBase = NsDataBase.getInstance(application);
            nsDao = dataBase.getNsPackageDao();
            packageList = ((NsPackageDao) nsDao).getPackageList();

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public LiveData<List<NsPackage>> getPackageList() {
        return packageList;
    }

    public LiveData<List<NsPackage>> filterBy(String projectName, String moduleName, List<NsPackage> input) {

        LiveData<List<NsPackage>> result = new MutableLiveData<>();

        List<NsPackage> filter = new ArrayList<>();

        for (NsPackage pkg : input) {

            if (pkg.getProjectName() != null && pkg.getProjectName().equalsIgnoreCase(projectName) &&
                    pkg.getModuleName() != null && pkg.getModuleName().equalsIgnoreCase(moduleName)) {

                filter.add(pkg);
            }
        }

        ((MutableLiveData<List<NsPackage>>) result).setValue(filter);

        return result;
    }
}
