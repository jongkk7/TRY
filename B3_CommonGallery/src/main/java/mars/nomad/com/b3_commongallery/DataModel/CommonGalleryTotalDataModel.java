package mars.nomad.com.b3_commongallery.DataModel;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-05-21.
 */
public class CommonGalleryTotalDataModel extends CommonGalleryDataModel  implements Serializable {

    private int childCnt;
    private String directoryName;
    private String directoryPath;

    public CommonGalleryTotalDataModel(String fullPath, String directoryName, String directoryPath, int childCnt) {
        super(fullPath);
        this.fullPath = fullPath;
        this.directoryName = directoryName;
        this.directoryPath = directoryPath;
        this.childCnt = childCnt;
    }

    public int getChildCnt() {
        return childCnt;
    }

    public void setChildCnt(int childCnt) {
        this.childCnt = childCnt;
    }


    public String getDirectoryName() {
        return directoryName;
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }

    public String getDirectoryPath() {
        return directoryPath;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommonGalleryTotalDataModel)) return false;
        if (!super.equals(o)) return false;
        CommonGalleryTotalDataModel that = (CommonGalleryTotalDataModel) o;
        return childCnt == that.childCnt &&
                Objects.equals(directoryName, that.directoryName) &&
                Objects.equals(directoryPath, that.directoryPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), childCnt, directoryName, directoryPath);
    }

    @Override
    public String toString() {
        return "CommonGalleryTotalDataModel{" +
                "childCnt=" + childCnt +
                ", directoryName='" + directoryName + '\'' +
                ", directoryPath='" + directoryPath + '\'' +
                '}';
    }
}
