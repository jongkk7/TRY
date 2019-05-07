package mars.nomad.com.l4_language;

import java.util.HashMap;
import java.util.Map;

import mars.nomad.com.l4_language.DataModel.LanguageDataModel;


/**
 * Created by 김창혁, NomadSoft.Inc on 2018-02-05.
 */

// Base가 되는 languagePack
// 이 클래스를 상속받아서 언어팩을 설정한다.
public abstract class BaseNsLanguagePack {

    public Map<String, LanguageDataModel> map = new HashMap<>();

    public BaseNsLanguagePack() {
        setLanguageData();

        NsLanguagePack.setLanguageMap(map);

    }

    protected abstract void setLanguageData();

}
