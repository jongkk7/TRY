package mars.nomad.com.b3_commongallery.DataModel;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-05-21.
 */
public class CommonGalleryDataModel implements Serializable {
    protected String fullPath;

    protected boolean isSelected = false;

    public CommonGalleryDataModel(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommonGalleryDataModel)) return false;
        CommonGalleryDataModel that = (CommonGalleryDataModel) o;
        return isSelected == that.isSelected &&
                Objects.equals(fullPath, that.fullPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullPath, isSelected);
    }

    @Override
    public String toString() {
        return "CommonGalleryDataModel{" +
                "fullPath='" + fullPath + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}
