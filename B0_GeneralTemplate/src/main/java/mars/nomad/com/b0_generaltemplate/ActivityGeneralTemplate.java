package mars.nomad.com.b0_generaltemplate;

import android.Manifest;
import android.os.Bundle;
import android.util.TypedValue;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import mars.nomad.com.b0_generaltemplate.DataModel.NsFile;
import mars.nomad.com.b0_generaltemplate.DataModel.NsTemplate;
import mars.nomad.com.b0_generaltemplate.Dialog.DialogDialogInput;
import mars.nomad.com.b0_generaltemplate.Adapter.AdapterNsTemplate;
import mars.nomad.com.b0_generaltemplate.Adapter.ClickListener.NsTemplateClickListener;
import mars.nomad.com.b0_generaltemplate.Value.GeneralTemplateConstants;
import mars.nomad.com.b0_generaltemplate.mvvm.GeneralTemplateViewModel;
import mars.nomad.com.c2_customview.Adapter.NsGeneralListAdapter;
import mars.nomad.com.c3_baseaf.BaseActivity;
import mars.nomad.com.l0_base.Callback.CommonCallback;
import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by SJH, NomadSoft.Inc, 2019-07-01
 */
public class ActivityGeneralTemplate extends BaseActivity {


    private GeneralTemplateViewModel mViewModel;

    private NsGeneralListAdapter<NsTemplate> mAdapterTemplate;
    private RecyclerView recyclerViewTemplateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setEvent();
        setStatusBarWrapper();
        checkAuth();
    }

    private void checkAuth() {
        try {
            TedPermission.with(mContext)
                    .setPermissionListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted() {

                            createDirectory();
                            loadLiveData();
                            loadGeneralTemplate();

                        }

                        @Override
                        public void onPermissionDenied(ArrayList<String> deniedPermissions) {

                            finish();
                        }
                    })
                    .setRationaleTitle("사용자 기기 접근 권한 요청")
                    .setRationaleMessage("필수접근 권한\n필수접근 권한 미동의 시, 어플리케이션을 사용하실 수 없습니다.")
                    .setDeniedMessage("해당 권한을 허용하지 않으면 서비스 이용이 불가합니다. 하단의 설정 버튼을 누르신 후 권한을 활성화주세요.")
                    .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                    .check();
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    private void loadGeneralTemplate() {

        try {
            mViewModel.loadTemplateList();
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    private void createDirectory() {
        try {
            File file = new File(GeneralTemplateConstants.templatePath);

            if (!file.exists()) {

                file.mkdir();
            }
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    @Override
    protected void initView() {

        try {

            setContentView(R.layout.activity_general_templete);
            recyclerViewTemplateList = (RecyclerView) findViewById(R.id.recyclerViewTemplateList);

            this.mContext = this;
            this.mViewModel = ViewModelProviders.of(this).get(GeneralTemplateViewModel.class);
            recyclerViewTemplateList.setLayoutManager(new LinearLayoutManager(getContext()));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    @Override
    protected void setEvent() {

        try {


        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    private void loadLiveData() {

        try {

            mViewModel.getTemplateListLive().observe(this, new Observer<List<NsTemplate>>() {
                @Override
                public void onChanged(List<NsTemplate> nsTemplates) {

                    try {

                        if (mAdapterTemplate == null) {

                            mAdapterTemplate = new NsGeneralListAdapter<NsTemplate>(getActivity(), new AdapterNsTemplate(getActivity()), new ArrayList<NsTemplate>(), new NsTemplateClickListener() {
                                @Override
                                public void onClickTemplate(NsTemplate item) {

                                    try {

                                        for (NsFile templateFile : item.getTemplateFiles()) {

                                            TypedValue value = new TypedValue();
                                            getResources().getValue(templateFile.getResId(), value, true);

                                            ErrorController.showMessage("Resource Name : " + value.string.toString().replace("res/raw/", ""));
                                        }
                                        showTemplateInputDialog(item);

                                    } catch (Exception e) {
                                        ErrorController.showError(e);
                                    }
                                }
                            }, new DiffUtil.ItemCallback<NsTemplate>() {
                                @Override
                                public boolean areItemsTheSame(@NonNull NsTemplate left, @NonNull NsTemplate right) {
                                    return false;
                                }

                                @Override
                                public boolean areContentsTheSame(@NonNull NsTemplate left, @NonNull NsTemplate right) {
                                    return left.equals(right);
                                }
                            });

                            recyclerViewTemplateList.setAdapter(mAdapterTemplate);
                        }

                        mAdapterTemplate.submitList(nsTemplates);

                    } catch (Exception e) {
                        ErrorController.showError(e);
                    }
                }
            });

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    private void showTemplateInputDialog(final NsTemplate item) {

        try {

            new DialogDialogInput(getContext(), mViewModel.getInputList(getContext(), item), new CommonCallback.SingleObjectActionCallback<Map<String, String>>() {
                @Override
                public void onAction(Map<String, String> data) {

                    startLoading();
                    mViewModel.createTemplateFiles(getContext(), item, data, new CommonCallback.SingleObjectCallback() {
                        @Override
                        public void onSuccess(Object result) {

                            stopLoading();
                            ErrorController.showToast(getContext(), "생성 완료");
                        }

                        @Override
                        public void onFailed(String fault) {

                            stopLoading();
                            showSimpleAlertDialog(fault);
                        }
                    });

                }
            }).show();

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


}