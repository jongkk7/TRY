package mars.nomad.com.a0_common.DataBase.Room.LoginUser;

import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.room.Dao;

import java.util.List;

import mars.nomad.com.l8_room.NsDao;


@Dao
public abstract class LoginUserDao extends NsDao<LoginUser> {


    public LoginUser getLoginIdItem(String userId) {
        SimpleSQLiteQuery query = new SimpleSQLiteQuery(
                "select * from " + getTableName() + " where loginId = '" + userId + "'"
        );
        return doFind(query);
    }


    public List<LoginUser> findAllOrderUserId() {
        SimpleSQLiteQuery query = new SimpleSQLiteQuery(
                "select * from " + getTableName() + " order by loginId"
        );
        return doFindQuery(query);
    }
}
