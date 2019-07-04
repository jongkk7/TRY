package mars.nomad.com.a0_common.DataBase.Room.NsProject;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

@Entity(tableName = "NsProject")
public class NsProject implements Serializable {

    @PrimaryKey
    @NonNull
    private String projectName;

    private long regDate;

    @NonNull
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(@NonNull String projectName) {
        this.projectName = projectName;
    }

    public long getRegDate() {
        return regDate;
    }

    public void setRegDate(long regDate) {
        this.regDate = regDate;
    }

    @Override
    public String toString() {
        return "NsProject{" +
                "projectName='" + projectName + '\'' +
                ", regDate=" + regDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NsProject nsProject = (NsProject) o;
        return regDate == nsProject.regDate &&
                projectName.equals(nsProject.projectName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectName, regDate);
    }
}
