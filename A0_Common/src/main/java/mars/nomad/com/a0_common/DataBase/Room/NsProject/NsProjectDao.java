package mars.nomad.com.a0_common.DataBase.Room.NsProject;

import androidx.lifecycle.LiveData;
import androidx.room.Query;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.room.Dao;

import java.util.List;

import mars.nomad.com.a0_common.DataBase.Room.NsTemplate.NsTemplate;
import mars.nomad.com.l8_room.NsDao;


@Dao
public abstract class NsProjectDao extends NsDao<NsProject> {


    @Query("SELECT * FROM NsProject")
    public abstract LiveData<List<NsProject>> getProjectList();
}
