package mars.nomad.com.a0_common.DataBase.Room.NsModule;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.l8_room.NsRepository;
import mars.nomad.com.a0_common.DataBase.Room.NsDataBase;

public class NsModuleRepository extends NsRepository<NsModule> {

    private static NsModuleRepository instance;

    private LiveData<List<NsModule>> moduleList;

    public static NsModuleRepository getInstance() {

        try {

            if (instance == null) {

                instance = new NsModuleRepository();
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return instance;
    }

    private NsModuleRepository() {
    }

    @Override
    public void initiateDao(Application application) {

        try {

            NsDataBase dataBase = NsDataBase.getInstance(application);
            nsDao = dataBase.getNsModuleDao();
            moduleList = ((NsModuleDao) nsDao).getModuleList();

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public LiveData<List<NsModule>> getModuleList() {
        return moduleList;
    }

    public LiveData<List<NsModule>> filterBy(String projectName, List<NsModule> input) {

        LiveData<List<NsModule>> result = new MutableLiveData<>();

        List<NsModule> filter = new ArrayList<>();

        for (NsModule nsModule : input) {

            if(nsModule.getProjectName() != null && nsModule.getProjectName().equalsIgnoreCase(projectName)){

                filter.add(nsModule);
            }
        }

        ((MutableLiveData<List<NsModule>>) result).setValue(filter);

        return result;
    }
}
