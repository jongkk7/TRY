package mars.nomad.com.B1_post.DataModel;

import java.io.Serializable;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-03-25.
 */
public class PostTextDataModel implements Serializable {

    private String contents;

    private boolean isBold = false;

    private boolean isItalic = false;

    private int fontSize = 0;

    public PostTextDataModel() {
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public boolean getIsBold() {
        return isBold;
    }

    public void setIsBold(boolean isBold) {
        this.isBold = isBold;
    }

    public boolean getIsItalic() {
        return isItalic;
    }

    public void setIsItalic(boolean isItalic) {
        this.isItalic = isItalic;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    @Override
    public String toString() {
        return "PostTextDataModel{" +
                "contents='" + contents + '\'' +
                ", isBold=" + isBold +
                ", isItalic=" + isItalic +
                ", fontSize=" + fontSize +
                '}';
    }


}
