package mars.nomad.com.a0_common.DataBase.Room.NsModule;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

@Entity(tableName = "NsModule")
public class NsModule implements Serializable {


    @PrimaryKey
    @NonNull
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

    @Override
    public String toString() {
        return "NsModule{" +
                "moduleName='" + moduleName + '\'' +
                ", projectName='" + projectName + '\'' +
                ", basePackageName='" + basePackageName + '\'' +
                ", regDate=" + regDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NsModule nsModule = (NsModule) o;
        return regDate == nsModule.regDate &&
                moduleName.equals(nsModule.moduleName) &&
                Objects.equals(projectName, nsModule.projectName) &&
                Objects.equals(basePackageName, nsModule.basePackageName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(moduleName, projectName, basePackageName, regDate);
    }
}
