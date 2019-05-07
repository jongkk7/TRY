package mars.nomad.com.a0_common.DataBase.Room.LoginUser;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;


@Entity(tableName = "LoginUser")
public class LoginUser {


    @PrimaryKey
    @NonNull
    private String userId;

    private String loginId;

    private String password;

    private String loginType;

    private String isAdvanced;

    private boolean isAutoLogin = false;

    private boolean isShared = false;

    private boolean isFirstRun = true;

    private boolean sound = true;

    private int shareSeq = -1;

    private String shareName = "";

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }


    public boolean isFirstRun() {
        return isFirstRun;
    }

    public void setFirstRun(boolean firstRun) {
        isFirstRun = firstRun;
    }

    public void setSound(boolean sound) {
        this.sound = sound;
    }

    public String getIsAdvanced() {
        return isAdvanced;
    }

    public void setIsAdvanced(String isAdvanced) {
        this.isAdvanced = isAdvanced;
    }

    public boolean isSound() {
        return sound;
    }

    public int getShareSeq() {
        return shareSeq;
    }

    public void setShareSeq(int shareSeq) {
        this.shareSeq = shareSeq;
    }

    public String getShareName() {
        return shareName;
    }

    public void setShareName(String shareName) {
        this.shareName = shareName;
    }


    public boolean isShared() {
        return isShared;
    }

    public void setShared(boolean shared) {
        isShared = shared;
    }

    public boolean isAutoLogin() {
        return isAutoLogin;
    }

    public void setAutoLogin(boolean autoLogin) {
        isAutoLogin = autoLogin;
    }

    @Override
    public String toString() {
        return "LoginUser{" +
                "userId='" + userId + '\'' +
                ", loginId='" + loginId + '\'' +
                ", password='" + password + '\'' +
                ", loginType='" + loginType + '\'' +
                ", isAdvanced='" + isAdvanced + '\'' +
                ", isAutoLogin=" + isAutoLogin +
                ", isShared=" + isShared +
                ", isFirstRun=" + isFirstRun +
                ", sound=" + sound +
                ", shareSeq=" + shareSeq +
                ", shareName='" + shareName + '\'' +
                '}';
    }
}
