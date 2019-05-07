package mars.nomad.com.l4_language;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.l4_language.DataModel.LanguageDataModel;
import mars.nomad.com.l4_language.ErrorController.LgErrorController;
import mars.nomad.com.l4_language.LanguagePack.CommonLanguagePack;
import mars.nomad.com.l4_language.View.NsLanguageButton;
import mars.nomad.com.l4_language.View.NsLanguageEditText;
import mars.nomad.com.l4_language.View.NsLanguageTextView;


/**
 * Created by 김창혁, NomadSoft.Inc on 2018-02-05.
 */

public class NsLanguagePack {


    // 언어팩 모음. 여기 넣으면 알아서 create 시켜줌
    public static List<Class> languagePackList = new ArrayList<>();


    // 랭귀지 코드
    // 이값을 구분값으로 생각하면됨.
    private static String langCode = "kr";

    // 언어 종류
    public static String lgKor = "kr";

    public static String lgEng = "en";
    private CommonLanguagePack NsCommonData;

    public static String getKorCode() {
        return lgKor;
    }

    public static String getEngCode() {
        return lgEng;
    }

    /**
     * 언어가 변경되었는지 판단한다.
     */
    public static boolean isLanguageChanged = false;


    // 입력한 랭귀지로 셋팅한다.
    public static void setLangCode(String langCode) {
        NsLanguagePack.langCode = langCode;
        isLanguageChanged = true;
    }


    private static Map<String, LanguageDataModel> map = new HashMap<>();


    public NsLanguagePack() {

        try {

            new CommonLanguagePack();


            LgErrorController.showMessage("[NsLanguagePack Setting Complete]");


        } catch (Exception e) {
            LgErrorController.showError(e);
        }
    }


    /**
     * 각각의 패키지에서 셋팅하는 LanguagePack을 받아서 셋팅한다.
     *
     * @param languageMap
     */
    public static void setLanguageMap(Map<String, LanguageDataModel> languageMap) {
        try {

            map.putAll(languageMap);

        } catch (Exception e) {
            LgErrorController.showError(e);
        }
    }

    /**
     * 태그를 받아서 데이터를 리턴한다.
     *
     * @param tag
     * @return
     */
    public static String getText(String tag) {

        try {

            if (!map.containsKey(tag)) {

                LgErrorController.showMessage("[NsLanguagePack] key : " + tag + " is empty");
                return "";
            }

            switch (langCode) {
                case "kr":  // 한글
                    return map.get(tag).getKr();
                case "en":  // 영어
                    return map.get(tag).getUs();
            }


        } catch (Exception e) {
            LgErrorController.showMessage("[NsLanguagePack] key : " + tag + " is empty");
            LgErrorController.showError(e);
        }

        return "";
    }

    /**
     * 태그와 언어코드를 받아서 데이터를 리턴한다.
     * 이 경우 두가지를 혹은 강제로 한쪽을 사용해야 할 경우 사용한다.
     *
     * @param tag
     * @return
     */
    public static String getTextForLgCode(String tag, String langCode) {

        try {

            if (!map.containsKey(tag)) {

                LgErrorController.showMessage("[NsLanguagePack] key : " + tag + " is empty");
                return "";
            }

            switch (langCode) {
                case "kr":  // 한글
                    return map.get(tag).getKr();
                case "en":  // 영어
                    return map.get(tag).getUs();
            }


        } catch (Exception e) {
            LgErrorController.showMessage("[NsLanguagePack] key : " + tag + " is empty");
            LgErrorController.showError(e);
        }

        return "";
    }


    public static String getLangCode() {
        return langCode;
    }


    public static void setTextLanguage(ViewGroup rootView) {
        try {
            for (int i = 0; i < rootView.getChildCount(); ++i) {
                View tempView = rootView.getChildAt(i);
                if (tempView instanceof ViewGroup) {
                    setTextLanguage((ViewGroup) tempView);
                }

                if (tempView instanceof NsLanguageTextView) {
                    ((NsLanguageTextView) tempView).setLanguageText();
                }

                if (tempView instanceof NsLanguageEditText) {
                    ((NsLanguageEditText) tempView).setLanguageText();
                }

                if (tempView instanceof NsLanguageButton) {
                    ((NsLanguageButton) tempView).setLanguageText();
                }

            }
        } catch (Exception e) {
            ErrorController.showError(e);
        }

    }

}
