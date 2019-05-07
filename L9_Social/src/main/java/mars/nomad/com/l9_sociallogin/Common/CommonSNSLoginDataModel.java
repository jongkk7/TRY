package mars.nomad.com.l9_sociallogin.Common;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-01-14.
 * <p>
 * SNS 전부 공통으로 들고오는 데이터의 경우 여기서 사용하고, 그외에는 각각의 데이터모델로 사용한다.
 */
public class CommonSNSLoginDataModel {

    String userId;


    String accessToken;

    int joinType;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getJoinType() {
        return joinType;
    }

    public void setJoinType(int joinType) {
        this.joinType = joinType;
    }


    @Override
    public String toString() {
        return "CommonSNSLoginDataModel{" +
                "userId='" + userId + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", joinType=" + joinType +
                '}';
    }
}
