package mars.nomad.com.a0_common.DataBase.Room.NsModule;

import androidx.lifecycle.LiveData;
import androidx.room.Query;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.room.Dao;

import java.util.List;

import mars.nomad.com.a0_common.DataBase.Room.NsProject.NsProject;
import mars.nomad.com.l8_room.NsDao;


@Dao
public abstract class NsModuleDao extends NsDao<NsModule> {

    @Query("SELECT * FROM NsModule")
    public abstract LiveData<List<NsModule>> getModuleList();
}
