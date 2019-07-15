package mars.nomad.com.a0_common.DataBase.Room.NsModule;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;
import mars.nomad.com.l0_base.Logger.ErrorController;

import java.io.Serializable;
import java.util.Objects;

@Entity(tableName = "NsModule")
public class NsModule implements Serializable {


    @PrimaryKey(autoGenerate = true)
    private int seq;

    private String moduleName;

    private String projectName;

    /**
     * 사용자에게 입력받는게 아니다. 자동으로 생성된다.
     */
    private String basePackageName;

    private long regDate;

    @NonNull
    public String getModuleName() {
        return moduleName;
    }

    @NonNull
    public String getModuleNameFolder() {

        String result = moduleName;

        try {

            result = moduleName.toCharArray()[0] + moduleName.substring(1);


        } catch (Exception e) {
            ErrorController.showError(e);
        }

        return result;
    }

    public void setModuleName(@NonNull String moduleName) {
        this.moduleName = moduleName;
    }

    public String getBasePackageName() {
        return basePackageName;
    }

    public void setBasePackageName(String basePackageName) {
        this.basePackageName = basePackageName;
    }

    public long getRegDate() {
        return regDate;
    }

    public void setRegDate(long regDate) {
        this.regDate = regDate;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    @Override
    public String toString() {
        return "NsModule{" +
                "seq=" + seq +
                ", moduleName='" + moduleName + '\'' +
                ", projectName='" + projectName + '\'' +
                ", basePackageName='" + basePackageName + '\'' +
                ", regDate=" + regDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NsModule module = (NsModule) o;
        return seq == module.seq &&
                regDate == module.regDate &&
                Objects.equals(moduleName, module.moduleName) &&
                Objects.equals(projectName, module.projectName) &&
                Objects.equals(basePackageName, module.basePackageName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq, moduleName, projectName, basePackageName, regDate);
    }
}
