package mars.nomad.com.l9_sociallogin.FaceBook.DataModel;

import mars.nomad.com.l9_sociallogin.Common.CommonSNSLoginDataModel;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-01-14.
 */
public class FacebookSNSLoginDataModel extends CommonSNSLoginDataModel {

    private String email;

    private String userName;

    private String profilePath;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    @Override
    public String toString() {
        return "FacebookSNSLoginDataModel{" +
                "email='" + email + '\'' +
                ", userName='" + userName + '\'' +
                ", profileImg='" + profilePath + '\'' +
                '}';
    }
}
