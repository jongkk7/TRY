package mars.nomad.com.a0_common.DataBase.Room.LoginUser;


import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.l8_room.NsRepository;
import mars.nomad.com.l8_room.RDBCallback;
import mars.nomad.com.a0_common.DataBase.Room.NsDataBase;

public class LoginUserRepository extends NsRepository<LoginUser> {

    private static LoginUserRepository instance;

    public static LoginUserRepository getInstance() {

        try {

            if (instance == null) {

                instance = new LoginUserRepository();
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return instance;
    }

    private LoginUserRepository() {
    }

    @Override
    public void initiateDao(Application application) {

        try {

            NsDataBase dataBase = NsDataBase.getInstance(application);
            nsDao = dataBase.getLoginUserDao();

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    public void getUserInfo(String loginId, RDBCallback.QueryCallback<LoginUser> callback) {
        try {

            new userInfoAsyncTask(loginId, (LoginUserDao) nsDao, callback).execute();
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void findAllOrderUserId(RDBCallback.QueryCallback<List<LoginUser>> callback) {
        new findAllOrderUserIdAsyncTask((LoginUserDao) nsDao, callback).execute();
    }


    private static class findAllOrderUserIdAsyncTask extends AsyncTask<Void, Void, Void> {

        private final RDBCallback.QueryCallback<List<LoginUser>> callback;
        private LoginUserDao nsDao;
        private List<LoginUser> temp;

        public findAllOrderUserIdAsyncTask(LoginUserDao nsDao, RDBCallback.QueryCallback<List<LoginUser>> callback) {
            this.nsDao = nsDao;
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(Void... item) {

            temp = nsDao.findAllOrderUserId();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            callback.onSuccess(temp);
        }
    }

    private static class userInfoAsyncTask extends AsyncTask<Void, Void, Void> {

        private final RDBCallback.QueryCallback<LoginUser> callback;
        private LoginUserDao nsDao;
        String loginId;
        private LoginUser temp;

        public userInfoAsyncTask(String loginId, LoginUserDao nsDao, RDBCallback.QueryCallback<LoginUser> callback) {
            this.loginId = loginId;
            this.nsDao = nsDao;
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(Void... item) {

            temp = nsDao.getLoginIdItem(loginId);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            callback.onSuccess(temp);
        }
    }


    public void exist(LoginUser item, RDBCallback.QueryCallback<Boolean> callback) {
        new isExistAsyncTask(item, (LoginUserDao) nsDao, callback).execute();
    }

    private static class isExistAsyncTask extends AsyncTask<Void, Void, Void> {

        private final RDBCallback.QueryCallback<Boolean> callback;
        private LoginUserDao nsDao;
        LoginUser data;

        public isExistAsyncTask(LoginUser data, LoginUserDao nsDao, RDBCallback.QueryCallback<Boolean> callback) {
            this.data = data;
            this.nsDao = nsDao;
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(Void... item) {

            LoginUser temp = nsDao.getLoginIdItem(data.getLoginId());

            if (temp != null) {
                callback.onSuccess(true);
            } else {
                callback.onSuccess(false);
            }
            return null;
        }
    }


}
