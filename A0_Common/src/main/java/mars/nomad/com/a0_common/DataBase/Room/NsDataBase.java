package mars.nomad.com.a0_common.DataBase.Room;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import mars.nomad.com.a0_common.DataBase.Room.LoginUser.LoginUser;
import mars.nomad.com.a0_common.DataBase.Room.LoginUser.LoginUserDao;


/**
 * Created by SJH, NomadSoft.Inc, 2019-01-07
 */
@Database(entities = {LoginUser.class}, version = 1, exportSchema = false)
public abstract class NsDataBase extends RoomDatabase {

    private static NsDataBase instance;

    /**
     * DB 이름
     */
    public static final String DATABASE_NAME = "LINNO_DB_2019";

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


}
