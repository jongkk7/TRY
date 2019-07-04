package mars.nomad.com.a0_common.DataBase.Room.NsPackage;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

@Entity(tableName = "NsPackage")
public class NsPackage implements Serializable {


    @PrimaryKey(autoGenerate = true)
    private int seq;

    private String packageName;

    private String moduleName;

    private String projectName;

    private String type;

    private long regDate;

    @NonNull
    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(@NonNull String packageName) {
        this.packageName = packageName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getRegDate() {
        return regDate;
    }

    public void setRegDate(long regDate) {
        this.regDate = regDate;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NsPackage nsPackage = (NsPackage) o;
        return seq == nsPackage.seq &&
                regDate == nsPackage.regDate &&
                Objects.equals(packageName, nsPackage.packageName) &&
                Objects.equals(moduleName, nsPackage.moduleName) &&
                Objects.equals(projectName, nsPackage.projectName) &&
                Objects.equals(type, nsPackage.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq, packageName, moduleName, projectName, type, regDate);
    }

    @Override
    public String toString() {
        return "NsPackage{" +
                "seq=" + seq +
                ", packageName='" + packageName + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", projectName='" + projectName + '\'' +
                ", type='" + type + '\'' +
                ", regDate=" + regDate +
                '}';
    }
}
