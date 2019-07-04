package mars.nomad.com.b0_generaltemplate.NsModule.mvvm;

import android.content.Context;
import android.content.Intent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import mars.nomad.com.a0_common.DataBase.Room.NsModule.NsModule;
import mars.nomad.com.a0_common.DataBase.Room.NsModule.NsModuleRepository;
import mars.nomad.com.a0_common.DataBase.Room.NsProject.NsProject;
import mars.nomad.com.b0_generaltemplate.NsAddPackage.DataModel.InputDataModel;
import mars.nomad.com.b0_generaltemplate.R;
import mars.nomad.com.b0_generaltemplate.Util.TemplateUtil;
import mars.nomad.com.b0_generaltemplate.Value.GeneralTemplateConstants;
import mars.nomad.com.l0_base.Callback.CommonCallback;
import mars.nomad.com.l0_base.Callback.NsPredicateObject;
import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.l12_applicationutil.Filter.FilterUtil;
import mars.nomad.com.l12_applicationutil.String.StringChecker;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-07-01.
 */
public class NsModuleViewModel extends ViewModel {

    private NsProject mProject;

    private LiveData<List<NsModule>> rawLiveData;

    private LiveData<List<NsModule>> moduleLiveData;

    public NsModuleViewModel() {

        rawLiveData = NsModuleRepository.getInstance().getModuleList();

    }


    public boolean getData(Intent intent) {

        try {

            mProject = (NsProject) intent.getSerializableExtra("project");

            if (mProject != null && StringChecker.isStringNotNull(mProject.getProjectName())) {

                moduleLiveData = Transformations.switchMap(rawLiveData, new Function<List<NsModule>, LiveData<List<NsModule>>>() {
                    @Override
                    public LiveData<List<NsModule>> apply(List<NsModule> input) {
                        return NsModuleRepository.getInstance().filterBy(mProject.getProjectName(), input);
                    }
                });

                return true;
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return false;
    }

    public LiveData<List<NsModule>> getModuleLiveData() {
        return moduleLiveData;
    }

    public NsProject getProject() {
        return mProject;
    }

    public List<InputDataModel> getModuleInput() {

        List<InputDataModel> result = new ArrayList<>();

        try {

            result.add(new InputDataModel("모듈 이름"));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return result;
    }

    public void createModule(final Context context, Map<String, String> data, final CommonCallback.SingleObjectCallback callback) {

        try {

            String moduleName = "";

            for (String s : data.keySet()) {

                moduleName = data.get(s);
                break;
            }

            if (!StringChecker.isStringNotNull(moduleName)) {

                callback.onFailed("모듈 이름이 없습니다.");
                return;
            }

            //중복 검사
            final String finalModuleName = moduleName;

            List<NsModule> currentModuleList = moduleLiveData.getValue();

            if (currentModuleList != null && currentModuleList.size() > 0) {

                boolean isExist = FilterUtil.isItemExist(currentModuleList, new NsPredicateObject<NsModule>() {
                    @Override
                    public boolean apply(NsModule listItem) {

                        if (listItem.getProjectName().equalsIgnoreCase(mProject.getProjectName()) && listItem.getModuleName().equalsIgnoreCase(finalModuleName)) {

                            return true;
                        }
                        return false;
                    }
                });

                if (!isExist) {

                    saveModule(context, finalModuleName, currentModuleList.size());

                } else {

                    callback.onFailed("같은 이름의 모듈이 이미 존재합니다.");
                }

            } else {

                saveModule(context, finalModuleName, 0);
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    private void saveModule(Context context, String moduleName, int moduleSize) {

        try {

            NsModule module = new NsModule();
            module.setProjectName(mProject.getProjectName());
            module.setModuleName("a" + moduleSize + "_" + moduleName);
            module.setBasePackageName("mars.nomad.com." + module.getModuleName());
            module.setRegDate(System.currentTimeMillis());

            NsModuleRepository.getInstance().insert(module);

            //실제 파일 생성
            String rootPath = GeneralTemplateConstants.templatePath + "/" + mProject.getProjectName();

            //모듈 패스 생성
            String modulePath = rootPath + "/" + module.getModuleName();

            File moduleFile = new File(modulePath);

            if (!moduleFile.exists()) {

                moduleFile.mkdirs();
            }

            //gitignore생성
            String gitIgnoreString = TemplateUtil.readContentsFromFile(context, R.raw.gitignore);
            TemplateUtil.saveAsFile(gitIgnoreString, ".gitignore", modulePath);

            //build.gradle생성
            String buildGradleString = TemplateUtil.readContentsFromFile(context, R.raw.build_gradle);
            TemplateUtil.saveAsFile(buildGradleString, "build.gradle", modulePath);

            //프로가드 생성
            String proguardString = TemplateUtil.readContentsFromFile(context, R.raw.proguard);
            TemplateUtil.saveAsFile(proguardString, ".proguard-rules.pro", modulePath);

            //src/main/java생성
            File javaPackage = new File(modulePath + "/src/main/java" + TemplateUtil.getDirectoryNameFromPackageName(module.getBasePackageName()));

            if (!javaPackage.exists()) {

                javaPackage.mkdirs();
            }

            //리소스 디렉토리 생성
            File layout = new File(modulePath + "/src/main/res/layout");

            if (!layout.exists()) {

                layout.mkdirs();
            }

            File drawable = new File(modulePath + "/src/main/res/drawable-xxhdpi");

            if (!drawable.exists()) {

                drawable.mkdirs();
            }

            //매니페스트 생성
            String manifestString = TemplateUtil.readContentsFromFile(context, R.raw.xml_manifest);
            manifestString = manifestString.replace("{$base_package_name}", module.getBasePackageName());
            TemplateUtil.saveAsFile(manifestString, "AndroidManifest.xml", modulePath + "/src/main");

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void deleteItem(NsModule item) {

        try {

            TemplateUtil.deleteAllFiles(GeneralTemplateConstants.templatePath + "/" + item.getProjectName() + "/" + item.getModuleName());

            NsModuleRepository.getInstance().delete(item);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }
}
