package mars.nomad.com.B1_post.DataModel;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-03-25.
 */
public class PostImageDataModel implements Serializable {

    private String thumbPath;

    private int thumb_width;

    private int thumb_height;

    private String filePath;

    private int width;

    private int height;

    @Expose(serialize = false, deserialize = false)
    private boolean isLocal = false;

    public PostImageDataModel(String thumbPath, int thumb_width, int thumb_height, String filePath, int width, int height, boolean isLocal) {
        this.thumbPath = thumbPath;
        this.thumb_width = thumb_width;
        this.thumb_height = thumb_height;
        this.filePath = filePath;
        this.width = width;
        this.height = height;
        this.isLocal = isLocal;
    }


    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public int getThumb_width() {
        return thumb_width;
    }

    public void setThumb_width(int thumb_width) {
        this.thumb_width = thumb_width;
    }

    public int getThumb_height() {
        return thumb_height;
    }

    public void setThumb_height(int thumb_height) {
        this.thumb_height = thumb_height;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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

    @Override
    public String toString() {
        return "PostImageDataModel{" +
                "thumbPath='" + thumbPath + '\'' +
                ", thumbWidth='" + thumb_width + '\'' +
                ", thumbHeight='" + thumb_height + '\'' +
                ", filePath='" + filePath + '\'' +
                ", width='" + width + '\'' +
                ", height='" + height + '\'' +
                '}';
    }
}
