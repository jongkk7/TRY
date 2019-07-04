package mars.nomad.com.a0_common.DataBase.Room.NsProject;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

@Entity(tableName = "NsProject")
public class NsProject implements Serializable {


    @PrimaryKey(autoGenerate = true)
    private int seq;


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
        NsProject project = (NsProject) o;
        return seq == project.seq &&
                regDate == project.regDate &&
                Objects.equals(projectName, project.projectName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq, projectName, regDate);
    }

    @Override
    public String toString() {
        return "NsProject{" +
                "seq=" + seq +
                ", projectName='" + projectName + '\'' +
                ", regDate=" + regDate +
                '}';
    }
}
