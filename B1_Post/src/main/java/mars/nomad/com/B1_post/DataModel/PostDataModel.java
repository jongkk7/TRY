package mars.nomad.com.B1_post.DataModel;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-03-25.
 */
public class PostDataModel implements Serializable {

    @Expose(serialize = false, deserialize = false)
    private int order_num = 0;

    private String type;

    @Expose(serialize = false, deserialize = false)
    private String url;

    @Expose(serialize = false, deserialize = false)
    private String accessToken;

    @Expose(serialize = false, deserialize = false)
    private int editOption = 1;

    private String contents;

    public PostDataModel(PostDataModel other) {
        this.type = other.type;
        this.url = other.url;
        this.accessToken = other.accessToken;
        this.editOption = other.editOption;
        this.contents = other.contents;
    }

    public PostDataModel(String type, String contents, int order_num) {
        this.type = type;
        this.contents = contents;
        this.order_num = order_num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }


    public int getEditOption() {
        return editOption;
    }

    public void setEditOption(int editOption) {
        this.editOption = editOption;
    }

    public int getOrder_num() {
        return order_num;
    }

    public void setOrder_num(int order_num) {
        this.order_num = order_num;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PostDataModel)) return false;
        PostDataModel that = (PostDataModel) o;
        return order_num == that.order_num &&
                editOption == that.editOption &&
                Objects.equals(type, that.type) &&
                Objects.equals(url, that.url) &&
                Objects.equals(accessToken, that.accessToken) &&
                Objects.equals(contents, that.contents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order_num, type, url, accessToken, editOption, contents);
    }

    @Override
    public String toString() {
        return "PostDataModel{" +
                "type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", editOption=" + editOption +
                ", contents='" + contents + '\'' +
                '}';
    }
}
