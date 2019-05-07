package mars.nomad.com.l8_room;

/**
 * Created by SJH, NomadSoft.Inc, 2019-01-14
 */
public class RDBCallback {

    public interface SingleObjectCallback<T> {

        void onSuccess(T result);

        void onFailed(String fault);
    }

    public interface QueryCallback<T> {

        void onSuccess(T result);
    }
}
