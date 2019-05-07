package mars.nomad.com.a0_common.DataBase;

import mars.nomad.com.a0_common.DataBase.Room.LoginUser.LoginUser;
import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by SJH, NomadSoft.Inc, 2019-01-14
 */
public class MyInfoDb {

    private static MyInfoDb instance;

    public static MyInfoDb getInstance() {

        try {

            if (instance == null) {

                instance = new MyInfoDb();
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return instance;
    }

    private Object mSync = new Object();

    private LoginUser mUser;

    public void updateUser(LoginUser user) {

        synchronized (mSync) {

            this.mUser = user;

        }
    }

    public LoginUser getMe() {

        LoginUser result = null;

        try {

            synchronized (mSync) {

                result = mUser;
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return result;
    }

    public String getUserId(){

        String result = "";

        try{

            LoginUser user = getMe();

            if(user != null){

                result = user.getUserId();
            }

        }catch(Exception e){
            ErrorController.showError(e);
        }
        return result;
    }
}
