package mars.nomad.com.l9_sociallogin.Google.DataModel;

import mars.nomad.com.l9_sociallogin.Common.CommonSNSLoginDataModel;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-03-29.
 */
public class GoogleSnsLoginDataModel extends CommonSNSLoginDataModel {

    private String userName;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "GoogleSnsLoginDataModel{" +
                "userName='" + userName + '\'' +
                '}';
    }
}
