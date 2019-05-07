package mars.nomad.com.l8_room;

import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-01-04.
 * jetpack 및 Room 사용시 해당 datamodel을 상속받아 사용한다.
 */
public abstract class NsRoomDataModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    protected int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
