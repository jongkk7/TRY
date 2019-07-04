package mars.nomad.com.b0_generaltemplate.NsAddPackage.mvvm;

import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import mars.nomad.com.a0_common.DataBase.Room.NsModule.NsModule;
import mars.nomad.com.a0_common.DataBase.Room.NsPackage.NsPackage;
import mars.nomad.com.a0_common.DataBase.Room.NsPackage.NsPackageRepository;
import mars.nomad.com.a0_common.DataBase.Room.NsTemplate.NsFile;
import mars.nomad.com.a0_common.DataBase.Room.NsTemplate.NsTemplate;
import mars.nomad.com.b0_generaltemplate.GeneralTemplateEngine;
import mars.nomad.com.b0_generaltemplate.NsAddPackage.DataModel.InputDataModel;
import mars.nomad.com.b0_generaltemplate.Util.TemplateUtil;
import mars.nomad.com.b0_generaltemplate.Value.GeneralTemplateConstants;
import mars.nomad.com.l0_base.Callback.CommonCallback;
import mars.nomad.com.l0_base.Callback.NsPredicateObject;
import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.l12_applicationutil.Filter.FilterUtil;
import mars.nomad.com.l12_applicationutil.String.StringChecker;

/**
 * Created by SJH, NomadSoft.Inc, 2019-07-01
 */
public class GeneralTemplateViewModel extends ViewModel {


    private MutableLiveData<List<NsTemplate>> templateListLive = new MutableLiveData<>();

    private List<NsTemplate> templateList = new ArrayList<>();

    private NsModule mModule;

    public GeneralTemplateViewModel() {

        try {

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public boolean getData(Intent intent) {

        try {

            mModule = (NsModule) intent.getSerializableExtra("module");

            if (mModule != null && StringChecker.isStringNotNull(mModule.getModuleName())) {

                return true;
            }
        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return false;
    }

    public void loadTemplateList() {

        try {

            templateList = GeneralTemplateEngine.InitializeTemplateEngine();

            templateListLive.postValue(templateList);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    /**
     * map에 있는 내용들을 바꿔치기해서 반환한다.
     *
     * @param original
     * @param replacer
     * @return
     */
    private String replaceString(String original, Map<String, String> replacer) {

        String result = original;

        try {

            for (String key : replacer.keySet()) {

                String value = replacer.get(key);

                if (StringChecker.isStringNotNull(value)) {

                    result = result.replace(key, value);
                }
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return result;
    }


    public MutableLiveData<List<NsTemplate>> getTemplateListLive() {
        return templateListLive;
    }

    /**
     * 파일을 읽어서 여기에 해당하는 모든 {$}를 가져온다.
     *
     * @param item
     * @return
     */
    public List<InputDataModel> getInputList(Context context, NsTemplate item) {

        List<InputDataModel> result = new ArrayList<>();

        List<String> fieldNameList = new ArrayList<>();

        try {

            for (NsFile templateFile : item.getTemplateFiles()) {

                String rawString = TemplateUtil.readContentsFromFile(context, templateFile.getResId());

                String[] words = rawString.split("\\s+");

                for (String word : words) {

                    if (StringChecker.isStringNotNull(word) && word.contains("{$")) {

                        final String fieldName = word.substring(word.indexOf("{"), word.indexOf("}")) + "}";
                        ErrorController.showMessage("fieldName : " + fieldName);

                        boolean isExist = FilterUtil.isItemExist(fieldNameList, new NsPredicateObject<String>() {
                            @Override
                            public boolean apply(String listItem) {

                                if (fieldName.equalsIgnoreCase(listItem)) {

                                    return true;
                                }
                                return false;
                            }
                        });

                        if (word.contains("{$package_name}")) {//예약어는 건너뜀

                            continue;
                        }

                        if (word.contains("{$Data_lower}")) { // 건너뜀
                            continue;
                        }

                        if (!isExist) {

                            fieldNameList.add(fieldName);
                        }
                    }
                }//end of for
            }//end of for

            for (String s : fieldNameList) {

                result.add(new InputDataModel(s));
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return result;
    }

    /**
     * 실제로 파일을 교체하고, 파일을 생성한다.
     *
     * @param item
     * @param replacer
     */
    public void createTemplateFiles(Context context, NsTemplate item, Map<String, String> replacer, final CommonCallback.SingleObjectCallback callback) {

        try {

            String packageName = "";

            for (String s : replacer.keySet()) {

                if (s.equalsIgnoreCase("{$Data}")) {

                    packageName = replacer.get(s);
                    break;
                }
            }

            // 예외 1 : 레이아웃은 받은 Data를 res (lowercase + _로 변환)
            replacer.put("{$Data_lower}", TemplateUtil.getResText(packageName));

            if (!StringChecker.isStringNotNull(packageName)) {

                packageName = item.getTemplateName();
            }

            NsPackage nsPackage = new NsPackage();
            nsPackage.setModuleName(mModule.getModuleName());
            nsPackage.setProjectName(mModule.getProjectName());
            nsPackage.setRegDate(System.currentTimeMillis());
            nsPackage.setType(item.getTemplateName());
            nsPackage.setPackageName(packageName);

            for (NsFile templateFile : item.getTemplateFiles()) {

                //기본적으로 템플릿에 설정된 값들을 교체한다.
                String rawString = TemplateUtil.readContentsFromFile(context, templateFile.getResId());
                rawString = replaceString(rawString, replacer);

                //예약어들을 적용한다.
                String p = "";

                if (StringChecker.isStringNotNull(templateFile.getDirectory())) {

                    p = templateFile.getDirectory().replace("/", ".");
                }

                rawString = rawString.replace("{$package_name}", mModule.getBasePackageName() + "." + packageName + p);

                String fileName = replaceString(templateFile.getNamingRule(), replacer);

                String directory = "/" + nsPackage.getPackageName();

                String savePath = "";

                if (StringChecker.isStringNotNull(templateFile.getDirectory())) {//일반 파일

                    TypedValue value = new TypedValue();
                    context.getResources().getValue(templateFile.getResId(), value, true);

                    String templateFileName = value.string.toString().replace("res/raw/", "");

                    if (!templateFileName.startsWith("xml")) {

                        directory += replaceString(templateFile.getDirectory(), replacer);

                        savePath = GeneralTemplateConstants.templatePath + "/" +
                                mModule.getProjectName() + "/" +
                                mModule.getModuleName() + "/" +
                                "src/main/java" + TemplateUtil.getDirectoryNameFromPackageName(mModule.getBasePackageName()) + directory;

                    } else {

                        //모듈 패스 생성
                        String modulePath = GeneralTemplateConstants.templatePath + "/" + mModule.getProjectName() + "/" + mModule.getModuleName();

                        savePath = modulePath + "/src/main/res/" + templateFile.getDirectory();
                    }
                } else {

                    savePath = GeneralTemplateConstants.templatePath + "/" +
                            mModule.getProjectName() + "/" +
                            mModule.getModuleName() + "/" +
                            "src/main/java" + TemplateUtil.getDirectoryNameFromPackageName(mModule.getBasePackageName()) + directory;
                }

                TemplateUtil.saveAsFile(rawString, fileName, savePath);
            }

            NsPackageRepository.getInstance().insert(nsPackage);

            callback.onSuccess("");

        } catch (Exception e) {
            ErrorController.showError(e);
            callback.onFailed("익셉션 발생");
        }
    }



    /**
     * 리플레이서 중 첫번째 키의 value값을 가져온다.
     *
     * @param replacer
     * @return
     */
    private String getFirstReplacer(Map<String, String> replacer) {

        String result = "";

        try {

            for (String s : replacer.keySet()) {

                String val = replacer.get(s);

                if (StringChecker.isStringNotNull(val)) {

                    result = val;
                    break;
                }
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return result;
    }

    public NsModule getModule() {
        return mModule;
    }
}
