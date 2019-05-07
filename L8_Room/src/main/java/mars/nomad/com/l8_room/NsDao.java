package mars.nomad.com.l8_room;

import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.RawQuery;
import androidx.room.Update;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-01-04.
 */
@Dao
public abstract class NsDao<T> {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long save(T item);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long[] saveAll(List<T> item);


    @Insert(onConflict = OnConflictStrategy.FAIL)
    public abstract long insert(T item);


    @Insert(onConflict = OnConflictStrategy.FAIL)
    public abstract long[] insertAll(List<T> item);

    @Update
    public abstract void update(T item);

    @Delete
    public abstract void delete(T item);

    public int deleteAll() {
        SimpleSQLiteQuery query = new SimpleSQLiteQuery(
                "delete from " + getTableName()
        );
        return doDeleteAll(query);
    }


    public List<T> findAll() {
        SimpleSQLiteQuery query = new SimpleSQLiteQuery(
                "select * from " + getTableName()
        );
        return doFindQuery(query);
    }

    public T find(long id) {
        SimpleSQLiteQuery query = new SimpleSQLiteQuery(
                "select * from " + getTableName() + " where id = ?",
                new Object[]{id}
        );
        return doFind(query);
    }

    public String getTableName() {

        Class clazz = (Class)
                ((ParameterizedType) getClass().getSuperclass().getGenericSuperclass())
                        .getActualTypeArguments()[0];
        String tableName = clazz.getSimpleName();
        return tableName;
    }

    @RawQuery
    protected abstract List<T> doFindQuery(SupportSQLiteQuery query);

    @RawQuery
    protected abstract T doFind(SupportSQLiteQuery query);

    @RawQuery
    protected abstract int doDeleteAll(SupportSQLiteQuery query);


    @RawQuery
    protected abstract int setQuery(SupportSQLiteQuery query);


}
