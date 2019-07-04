package mars.nomad.com.a0_common.DataBase.Room.NsTemplate;


import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import mars.nomad.com.a0_common.DataBase.Room.NsDataBase;
import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.l8_room.NsRepository;
import mars.nomad.com.l8_room.RDBCallback;

public class NsTemplateRepository extends NsRepository<NsTemplate> {

    private static NsTemplateRepository instance;

    private LiveData<List<NsTemplate>> templateList;

    public static NsTemplateRepository getInstance() {

        try {

            if (instance == null) {

                instance = new NsTemplateRepository();
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return instance;
    }

    private NsTemplateRepository() {
    }

    @Override
    public void initiateDao(Application application) {

        try {

            NsDataBase dataBase = NsDataBase.getInstance(application);
            nsDao = dataBase.getNsTemplateDao();
            templateList = ((NsTemplateDao) nsDao).getTemplateList();

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public LiveData<List<NsTemplate>> getTemplateList() {
        return templateList;
    }

    public void insertOrUpdate(List<NsTemplate> newItem, final RDBCallback.QueryCallback callback) {

        try {

            InsertOrUpdate async = new InsertOrUpdate((NsTemplateDao) nsDao, newItem, callback);
            async.execute();

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    private static class InsertOrUpdate extends AsyncTask<Void, Void, Integer> {

        private NsTemplateDao nsDao;
        private RDBCallback.QueryCallback callback;
        private List<NsTemplate> newItem;

        public InsertOrUpdate(NsTemplateDao nsDao, List<NsTemplate>  newItem, RDBCallback.QueryCallback callback) {
            this.nsDao = nsDao;
            this.newItem = newItem;
            this.callback = callback;
        }
        @Override
        protected Integer doInBackground(Void... voids) {

            int result = 0;

            try{

                if(newItem == null){

                    newItem = new ArrayList<>();
                }

                for (NsTemplate gallery : newItem) {

                    nsDao.insertOrUpdate(gallery);
                }

            }catch(Exception e){
                ErrorController.showError(e);
            }
            return result;
        }

        @Override
        protected void onPostExecute(Integer integer) {

            try{

                callback.onSuccess(integer);

            }catch(Exception e){
                ErrorController.showError(e);
            }
        }
    }
}
