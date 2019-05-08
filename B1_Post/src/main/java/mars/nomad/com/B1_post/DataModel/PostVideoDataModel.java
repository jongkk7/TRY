package mars.nomad.com.B1_post.DataModel;

import java.io.Serializable;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-03-25.
 */
public class PostVideoDataModel implements Serializable {

    private String thumbPath;

    private int thumb_width;

    private int thumb_height;

    private String filePath;

    private int width;

    private int height;



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
        return "PostVideoDataModel{" +
                "thumbPath='" + thumbPath + '\'' +
                ", thumbWidth='" + thumb_width + '\'' +
                ", thumbHeight='" + thumb_height + '\'' +
                ", filePath='" + filePath + '\'' +
                ", width='" + width + '\'' +
                ", height='" + height + '\'' +
                '}';
    }
}
