package mars.nomad.com.a0_common.DataBase.Room;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.Context;

import mars.nomad.com.a0_common.DataBase.Room.LoginUser.LoginUser;
import mars.nomad.com.a0_common.DataBase.Room.LoginUser.LoginUserDao;
import mars.nomad.com.a0_common.DataBase.Room.NsModule.NsModule;
import mars.nomad.com.a0_common.DataBase.Room.NsModule.NsModuleDao;
import mars.nomad.com.a0_common.DataBase.Room.NsPackage.NsPackage;
import mars.nomad.com.a0_common.DataBase.Room.NsPackage.NsPackageDao;
import mars.nomad.com.a0_common.DataBase.Room.NsProject.NsProject;
import mars.nomad.com.a0_common.DataBase.Room.NsProject.NsProjectDao;


/**
 * Created by SJH, NomadSoft.Inc, 2019-01-07
 */
@Database(entities = {LoginUser.class, NsProject.class, NsModule.class, NsPackage.class}, version = 12, exportSchema = false)
public abstract class NsDataBase extends RoomDatabase {

    private static NsDataBase instance;

    /**
     * DB 이름
     */
    public static final String DATABASE_NAME = "NS_ANDROID_DB";

    /**
     * DB의 싱글톤 인스턴스를 반환한다.
     *
     * @param context
     * @return
     */
    public static synchronized NsDataBase getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), NsDataBase.class, NsDataBase.DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();

        }
        return instance;
    }

    /**
     * Entities에 속하는 DB 오브젝트를 반환. 파라미터가 있으면 안된다.
     *
     * @return
     */

    public abstract LoginUserDao getLoginUserDao();

    public abstract NsProjectDao getNsProjectDao();

    public abstract NsModuleDao getNsModuleDao();

    public abstract NsPackageDao getNsPackageDao();
}
