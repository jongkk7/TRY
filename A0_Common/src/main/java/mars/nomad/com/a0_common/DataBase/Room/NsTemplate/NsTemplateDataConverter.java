package mars.nomad.com.a0_common.DataBase.Room.NsTemplate;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by SJH, NomadSoft.Inc, 2019-07-02
 */
public class NsTemplateDataConverter {

    @TypeConverter
    public String fromTemplateFiles(List<NsFile> templateFiles) {

        if (templateFiles == null) {

            return (null);
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<NsFile>>() {
        }.getType();
        String json = gson.toJson(templateFiles, type);
        return json;
    }

    @TypeConverter
    public List<NsFile> toTemplateFiles(String templateFilesString) {

        if (templateFilesString == null) {

            return (null);
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<NsFile>>() {
        }.getType();

        List<NsFile> json = gson.fromJson(templateFilesString, type);
        return json;
    }
}
