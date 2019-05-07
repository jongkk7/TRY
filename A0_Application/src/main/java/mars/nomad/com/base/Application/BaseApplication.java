package mars.nomad.com.base.Application;


import android.content.Context;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;


import com.jakewharton.processphoenix.ProcessPhoenix;


import mars.nomad.com.a0_common.DataBase.DataBaseFlag;
import mars.nomad.com.a0_common.DataBase.LitePal.NsLitePal;
import mars.nomad.com.a0_common.DataBase.Room.LoginUser.LoginUserRepository;
import mars.nomad.com.base.ActivityRegister;
import mars.nomad.com.l0_base.Logger.ErrorController;


/**
 * Created by SJH on 2017-06-26.
 */

public class BaseApplication extends MultiDexApplication {

    private static BaseApplication mInstance;

    private boolean isFirst = true;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;


        // LitePal 사용시
        if (DataBaseFlag.isUseLitePal()) {
            NsLitePal.initialize(this);
        }

        // Room 사용시
        if (DataBaseFlag.isUseRoom()) {
            RoomInit();
        }


        setExceptionControl();

        registerActivity();

    }

    private void RoomInit() {

        try {

            LoginUserRepository.getInstance().initiateDao(this);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    public static BaseApplication getGlobalApplicationContext() {
        if (mInstance == null)
            throw new IllegalStateException("this application does not inherit GlobalApplication");
        return mInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    private Thread.UncaughtExceptionHandler uncaughtExcption;


    private void setExceptionControl() {

        try {

//            this.uncaughtExcption = new Thread.UncaughtExceptionHandler() {
//                @Override
//                public void uncaughtException(Thread t, Throwable e) {
//                    restart();
//                }
//            };
//
//            Thread.setDefaultUncaughtExceptionHandler(uncaughtExcption);


        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    private void restart() {


        try {

            ProcessPhoenix.triggerRebirth(getGlobalApplicationContext());
        } catch (Exception e) {

            ErrorController.showError(e);

        } finally {
            Runtime.getRuntime().exit(0);

        }
    }

    private void registerActivity() {
        try {
            ActivityRegister.register();

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }
}
