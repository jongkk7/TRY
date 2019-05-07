package mars.nomad.com.l8_room;

import java.util.List;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-01-04.
 */
public interface NsRoomCallback<T> {

    void onFindAll(List<T>item);
}
