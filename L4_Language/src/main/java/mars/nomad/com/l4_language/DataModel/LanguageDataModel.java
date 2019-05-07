package mars.nomad.com.l4_language.DataModel;

/**
 * Created by 김창혁, NomadSoft.Inc on 2018-02-05.
 */

public class LanguageDataModel {

    private String kr;
    private String us;

    public LanguageDataModel(String kr, String us) {
        this.kr = kr;
        this.us = us;
    }


    public String getKr() {
        return kr;
    }

    public void setKr(String kr) {
        this.kr = kr;
    }

    public String getUs() {
        return us;
    }

    public void setUs(String us) {
        this.us = us;
    }

    @Override
    public String toString() {
        return "LanguageDataModel{" +
                "kr='" + kr + '\'' +
                ", us='" + us + '\'' +
                '}';
    }
}
