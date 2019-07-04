package mars.nomad.com.a0_common.DataBase.Room.NsTemplate;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.l8_room.NsDao;


@Dao
public abstract class NsTemplateDao extends NsDao<NsTemplate> {


    @Query("SELECT * FROM NsTemplate")
    public abstract LiveData<List<NsTemplate>> getTemplateList();


    @Query("SELECT * from NsTemplate WHERE templateName= :templateName")
    public abstract List<NsTemplate> getItemById(String templateName);


    void insertOrUpdate(NsTemplate item) {

        try {


            List<NsTemplate> itemFromDB = getItemById(item.getTemplateName());

            if (itemFromDB == null || itemFromDB.size() == 0) {

                insert(item);

            } else {

                update(item);
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }
}
