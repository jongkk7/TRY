package mars.nomad.com.b3_commongallery.DataModel;

import java.io.Serializable;

/**
 * Created by SJH on 2017-07-03.
 */

public class AlbumDataModel implements Serializable {

    private static final long serialVersionUID = 1L;
    private String fullPath;
    private String directoryName;
    private String directoryPath;
    private int width;
    private int height;

    /**
     * 앨범 목록 전용
     */
    private int childCnt = 0;

    private boolean isSelected = false;

    public AlbumDataModel(String fullPath, String directoryName,
                          String directoryPath) {
        this.fullPath = fullPath;
        this.directoryName = directoryName;
        this.directoryPath = directoryPath;
    }

    public AlbumDataModel(String fullPath, String directoryName,
                          String directoryPath, int childCnt) {
        this.fullPath = fullPath;
        this.directoryName = directoryName;
        this.directoryPath = directoryPath;
        this.childCnt = childCnt;
    }

    public AlbumDataModel(String directoryName, String directoryPath, int width, int height, boolean isSelected) {
        this.directoryName = directoryName;
        this.directoryPath = directoryPath;
        this.width = width;
        this.height = height;
        this.isSelected = isSelected;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getChildCnt() {
        return childCnt;
    }

    public void setChildCnt(int childCnt) {
        this.childCnt = childCnt;
    }

    @Override
    public String toString() {
        return "AlbumDataModel{" +
                "fullPath='" + fullPath + '\'' +
                ", directoryName='" + directoryName + '\'' +
                ", directoryPath='" + directoryPath + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", childCnt=" + childCnt +
                ", isSelected=" + isSelected +
                '}';
    }
}