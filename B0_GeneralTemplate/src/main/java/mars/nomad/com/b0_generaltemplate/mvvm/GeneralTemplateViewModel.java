package mars.nomad.com.b0_generaltemplate.mvvm;

import android.content.Context;

import androidx.annotation.RawRes;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mars.nomad.com.b0_generaltemplate.DataModel.InputDataModel;
import mars.nomad.com.b0_generaltemplate.DataModel.NsFile;
import mars.nomad.com.b0_generaltemplate.DataModel.NsTemplate;
import mars.nomad.com.b0_generaltemplate.GeneralTemplateEngine;
import mars.nomad.com.b0_generaltemplate.R;
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

    public GeneralTemplateViewModel() {

        try {

            initTemplateData();
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    private void initTemplateData() {

        try {


        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void createActivity(final Context context, final String name, final CommonCallback.SingleObjectCallback callback) {

        try {

            String resName = "activity_" + name.toLowerCase();

            String activityString = readContentsFromFile(context, R.raw.java_empty_activity);

            String xmlString = readContentsFromFile(context, R.raw.xml_empty_layout);

            String mvvmString = readContentsFromFile(context, R.raw.java_empty_viewmodel);

            Map<String, String> replacer = new HashMap<>();
            replacer.put("{$name}", name);
            replacer.put("{$res_name}", name);

            activityString = replaceString(activityString, replacer);
            mvvmString = replaceString(mvvmString, replacer);

            saveAsFile(activityString, "Activity" + name + ".java", GeneralTemplateConstants.templatePath + "/" + name);
            saveAsFile(xmlString, resName + ".xml", GeneralTemplateConstants.templatePath + "/" + name + "/res/layout");
            saveAsFile(mvvmString, name + "ViewModel.java", GeneralTemplateConstants.templatePath + "/" + name + "/mvvm");

            callback.onSuccess("");

        } catch (Exception e) {
            ErrorController.showError(e);
        }
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
     * raw에 있는 id의 파일 내용을 싹 긁어온다.
     *
     * @param context
     * @param id
     * @return
     */
    private String readContentsFromFile(final Context context, @RawRes int id) {

        String result = "";

        try {

            InputStream inputStream = context.getResources().openRawResource(id);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int size;

            try {

                while ((size = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, size);
                }

            } catch (IOException e) {
                ErrorController.showError(e);
            } finally {
                outputStream.close();
                inputStream.close();
            }

            result = outputStream.toString();

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return result;
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

    /**
     * 파일로 저장
     *
     * @param originalString
     * @param fileName
     * @param dirName
     */
    private void saveAsFile(String originalString, String fileName, String dirName) {

        try {

            File dir = new File(dirName);

            if (!dir.exists()) {

                dir.mkdirs();
            }

            File file = new File(dirName, fileName);

            if (!file.exists()) {

                file.createNewFile();
            }

            FileWriter out = new FileWriter(file);
            out.write(originalString);
            out.close();

        } catch (Exception e) {
            ErrorController.showError(e);
        }
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

                String rawString = readContentsFromFile(context, templateFile.getResId());

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

            for (NsFile templateFile : item.getTemplateFiles()) {

                String rawString = readContentsFromFile(context, templateFile.getResId());
                rawString = replaceString(rawString, replacer);

                String fileName = replaceString(templateFile.getNamingRule(), replacer);

                String directory = item.getTemplateName() + getFirstReplacer(replacer);

                if (StringChecker.isStringNotNull(templateFile.getDirectory())) {

                    directory += templateFile.getDirectory();
                }

                saveAsFile(rawString, fileName, GeneralTemplateConstants.templatePath + "/" + directory);

            }

            callback.onSuccess("");

        } catch (Exception e) {
            ErrorController.showError(e);
            callback.onFailed("익셉션 발생");
        }
    }

    /**
     * 리플레이서중 첫번째 키의 value값을 가져온다.
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
}
