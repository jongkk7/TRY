package mars.nomad.com.b0_generaltemplate.DataModel;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by SJH, NomadSoft.Inc, 2019-07-02
 */
public class NsFile implements Serializable {

    private int resId;

    /**
     * 비어있으면 걍 루트다.
     */
    private String directory;

    /**
     * 이름 짓는 룰
     */
    private String namingRule;

    public NsFile() {
    }

    public NsFile(int resId, String directory, String namingRule) {
        this.resId = resId;
        this.directory = directory;
        this.namingRule = namingRule;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getNamingRule() {
        return namingRule;
    }

    public void setNamingRule(String namingRule) {
        this.namingRule = namingRule;
    }

    @Override
    public String toString() {
        return "NsFile{" +
                "resId=" + resId +
                ", directory='" + directory + '\'' +
                ", namingRule='" + namingRule + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NsFile nsFile = (NsFile) o;
        return resId == nsFile.resId &&
                Objects.equals(directory, nsFile.directory) &&
                Objects.equals(namingRule, nsFile.namingRule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resId, directory, namingRule);
    }
}
