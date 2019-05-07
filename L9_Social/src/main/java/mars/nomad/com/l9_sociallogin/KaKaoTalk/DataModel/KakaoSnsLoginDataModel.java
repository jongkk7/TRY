package mars.nomad.com.l9_sociallogin.KaKaoTalk.DataModel;

import mars.nomad.com.l9_sociallogin.Common.CommonSNSLoginDataModel;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-03-29.
 */
public class KakaoSnsLoginDataModel extends CommonSNSLoginDataModel {

    private String email;

    private String nickName;

    private String profileImg;

    private String profileThumbImg;

    private String age;

    private String birthDay;

    private String gender;



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getProfileThumbImg() {
        return profileThumbImg;
    }

    public void setProfileThumbImg(String profileThumbImg) {
        this.profileThumbImg = profileThumbImg;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "KakaoSnsLoginDataModel{" +
                "email='" + email + '\'' +
                ", nickName='" + nickName + '\'' +
                ", profileImg='" + profileImg + '\'' +
                ", profileThumbImg='" + profileThumbImg + '\'' +
                ", age='" + age + '\'' +
                ", birthDay='" + birthDay + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
