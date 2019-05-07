package mars.nomad.com.l4_language.LanguagePack;

/**
 * Created by 김창혁, NomadSoft.Inc on 2018-04-02.
 */


import mars.nomad.com.l4_language.BaseNsLanguagePack;
import mars.nomad.com.l4_language.DataModel.LanguageDataModel;
import mars.nomad.com.l4_language.ErrorController.LgErrorController;

/**
 * 다른 패키지에서도 불변인 언어들만 모아놓는다.
 */
public class CommonLanguagePack extends BaseNsLanguagePack {

    public CommonLanguagePack() {
        super();

    }




    @Override
    protected void setLanguageData() {
        try{

            // 공통 사용
            setCommonLanguage();

            // Toast 문자열
            setToastString();

        }catch (Exception e){
            LgErrorController.showError(e);
        }
    }
    /**
     * 언어팩중 공통으로 사용되는 언어를 정의한다. (어플을 따지지않고 사용한다)
     */
    private void setCommonLanguage() {

        // 공통적으로 사용하는 언어 (OK, Cancel)
        map.put("OK", new LanguageDataModel("확인", "OK"));
        map.put("Cancel", new LanguageDataModel("취소", "Cancel"));
        map.put("Press_Again_To_Exit", new LanguageDataModel("한번 더 누르시면 종료됩니다.", "Press again to exit."));

    }

    private void setToastString() {

        map.put("Data_was_not_received_normally", new LanguageDataModel("데이터를 정상적으로 받지 못했습니다.", "Data was not received normally."));

    }

    

}
