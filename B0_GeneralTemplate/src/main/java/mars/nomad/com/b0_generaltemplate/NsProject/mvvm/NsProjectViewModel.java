package mars.nomad.com.b0_generaltemplate.NsProject.mvvm;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import mars.nomad.com.a0_common.DataBase.Room.NsProject.NsProject;
import mars.nomad.com.a0_common.DataBase.Room.NsProject.NsProjectRepository;
import mars.nomad.com.b0_generaltemplate.DataModel.InputDataModel;
import mars.nomad.com.b0_generaltemplate.Value.GeneralTemplateConstants;
import mars.nomad.com.l0_base.Callback.CommonCallback;
import mars.nomad.com.l0_base.Callback.NsPredicateObject;
import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.l12_applicationutil.Filter.FilterUtil;
import mars.nomad.com.l8_room.RDBCallback;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-07-01.
 */
public class NsProjectViewModel extends ViewModel {

    private LiveData<List<NsProject>> projectLIveData;

    public NsProjectViewModel() {

        projectLIveData = NsProjectRepository.getInstance().getProjectList();
    }

    public LiveData<List<NsProject>> getProjectLIveData() {
        return projectLIveData;
    }

    public List<InputDataModel> getProjectCreateParam() {

        List<InputDataModel> result = new ArrayList<>();

        try {

            result.add(new InputDataModel("프로젝트 이름"));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return result;
    }

    public void saveProject(final Map<String, String> data, final CommonCallback.SingleObjectCallback callback) {

        try {

            for (final String s : data.keySet()) {

                NsProjectRepository.getInstance().findAll(new RDBCallback.QueryCallback<List<NsProject>>() {
                    @Override
                    public void onSuccess(List<NsProject> result) {

                        if (result != null && result.size() > 0) {

                            boolean exist = FilterUtil.isItemExist(result, new NsPredicateObject<NsProject>() {
                                @Override
                                public boolean apply(NsProject listItem) {

                                    if (listItem.getProjectName().equalsIgnoreCase(data.get(s))) {

                                        return true;
                                    }
                                    return false;
                                }
                            });

                            if (!exist) {

                                insertProject(data.get(s));

                            } else {

                                callback.onFailed("이미 같은 프로젝트가 존재합니다.");
                            }

                        } else {

                            insertProject(data.get(s));
                        }
                    }
                });
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    /**
     * 실제 경로를 만들고 db상에 인서트한다.
     *
     * @param projectName
     */
    private void insertProject(String projectName) {

        try {

            File dir = new File(GeneralTemplateConstants.templatePath + "/" + projectName);

            NsProject project = new NsProject();
            project.setProjectName(projectName);
            project.setRegDate(System.currentTimeMillis());

            NsProjectRepository.getInstance().insert(project);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }
}
