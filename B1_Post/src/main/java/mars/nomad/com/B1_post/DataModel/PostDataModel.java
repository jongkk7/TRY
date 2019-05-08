package mars.nomad.com.B1_post.DataModel;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-03-25.
 */
public class PostDataModel implements Serializable {

    private String type;

    @Expose(serialize = false, deserialize = false)
    private String url;

    @Expose(serialize = false, deserialize = false)
    private String accessToken;

    private String contents;

    public PostDataModel(String type, String contents) {
        this.type = type;
        this.contents = contents;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PostDataModel)) return false;
        PostDataModel that = (PostDataModel) o;
        return Objects.equals(type, that.type) &&
                Objects.equals(url, that.url) &&
                Objects.equals(accessToken, that.accessToken) &&
                Objects.equals(contents, that.contents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, url, accessToken, contents);
    }

    @Override
    public String toString() {
        return "PostDataModel{" +
                "type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
