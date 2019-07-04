package mars.nomad.com.a0_common.DataBase.Room.NsProject;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import mars.nomad.com.a0_common.DataBase.Room.NsTemplate.NsTemplate;
import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.l8_room.NsRepository;
import mars.nomad.com.l8_room.RDBCallback;
import mars.nomad.com.a0_common.DataBase.Room.NsDataBase;

public class NsProjectRepository extends NsRepository<NsProject> {

    private static NsProjectRepository instance;

    private LiveData<List<NsProject>> projectList;

    public static NsProjectRepository getInstance() {

        try {

            if (instance == null) {

                instance = new NsProjectRepository();
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return instance;
    }

    private NsProjectRepository() {
    }

    @Override
    public void initiateDao(Application application) {

        try {

            NsDataBase dataBase = NsDataBase.getInstance(application);
            nsDao = dataBase.getNsProjectDao();
            projectList = ((NsProjectDao) nsDao).getProjectList();

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public LiveData<List<NsProject>> getProjectList() {
        return projectList;
    }
}
