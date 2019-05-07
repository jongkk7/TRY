package mars.nomad.com.l12_applicationutil.File;

import java.io.File;

/**
 * Created by SJH on 2017-03-23.
 */

public class ThumbContainer {

    private File file;

    private int width;

    private int height;

    public ThumbContainer(File file, int width, int height) {
        this.file = file;
        this.width = width;
        this.height = height;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
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
        return "ThumbContainer{" +
                "file=" + file +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
