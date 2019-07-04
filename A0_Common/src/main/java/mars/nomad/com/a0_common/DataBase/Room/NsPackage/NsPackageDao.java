package mars.nomad.com.a0_common.DataBase.Room.NsPackage;

import androidx.lifecycle.LiveData;
import androidx.room.Query;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.room.Dao;

import java.util.List;

import mars.nomad.com.a0_common.DataBase.Room.NsModule.NsModule;
import mars.nomad.com.l8_room.NsDao;


@Dao
public abstract class NsPackageDao extends NsDao<NsPackage> {

    @Query("SELECT * FROM NsPackage")
    public abstract LiveData<List<NsPackage>> getPackageList();
}
