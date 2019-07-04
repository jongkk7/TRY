package mars.nomad.com.b0_generaltemplate.NsProject;

import android.Manifest;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mars.nomad.com.a0_common.DataBase.Room.NsProject.NsProject;
import mars.nomad.com.b0_generaltemplate.ActivityManagerTemplate;
import mars.nomad.com.b0_generaltemplate.Dialog.DialogDialogInput;
import mars.nomad.com.b0_generaltemplate.NsProject.Adapter.AdapterNsProject;
import mars.nomad.com.b0_generaltemplate.NsProject.Adapter.ClickListener.NsProjectClickListener;
import mars.nomad.com.b0_generaltemplate.NsProject.mvvm.NsProjectViewModel;
import mars.nomad.com.b0_generaltemplate.R;
import mars.nomad.com.b0_generaltemplate.Value.GeneralTemplateConstants;
import mars.nomad.com.c2_customview.Adapter.NsGeneralListAdapter;
import mars.nomad.com.c3_baseaf.BaseActivity;
import mars.nomad.com.l0_base.Callback.CommonCallback;
import mars.nomad.com.l0_base.Callback.SingleClickListener;
import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.l11_hardwareutil.KeyBoard.BehaviorUtil;

public class ActivityNsProject extends BaseActivity {

    private RecyclerView recyclerViewNsProject;
    private ImageButton imageButtonBack;
    private NsProjectViewModel mVmodel;
    private NsGeneralListAdapter<NsProject> mAdapter;
    private FrameLayout frameLayoutAddProject;
    private TextView textViewNoContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setEvent();
        setLiveData();
        checkAuth();
    }


    @Override
    protected void initView() {

        try {

            this.mContext = this;
            this.mVmodel = ViewModelProviders.of(this).get(NsProjectViewModel.class);

            setContentView(R.layout.activity_ns_project);

            recyclerViewNsProject = (RecyclerView) findViewById(R.id.recyclerViewNsProject);
            imageButtonBack = (ImageButton) findViewById(R.id.imageButtonBack);
            frameLayoutAddProject = (FrameLayout) findViewById(R.id.frameLayoutAddProject);
            textViewNoContents = (TextView) findViewById(R.id.textViewNoContents);

            recyclerViewNsProject.setLayoutManager(new LinearLayoutManager(getActivity()));

        } catch (Exception e) {
            ErrorController.showError(e);
        }

    }

    @Override
    protected void setEvent() {

        try {

            this.imageButtonBack.setOnClickListener(new SingleClickListener(new CommonCallback.SingleActionCallback() {
                @Override
                public void onAction() {
                    onBackPressed();
                }
            }));

            this.frameLayoutAddProject.setOnClickListener(new SingleClickListener(new CommonCallback.SingleActionCallback() {
                @Override
                public void onAction() {

                    try{

                        new DialogDialogInput(getContext(), mVmodel.getProjectCreateParam(), new CommonCallback.SingleObjectActionCallback<Map<String, String>>() {
                            @Override
                            public void onAction(Map<String, String> data) {

                                mVmodel.saveProject(data, new CommonCallback.SingleObjectCallback() {
                                    @Override
                                    public void onSuccess(Object result) {
                                        //do nothing
                                    }

                                    @Override
                                    public void onFailed(String fault) {
                                        showSimpleAlertDialog(fault);
                                    }
                                });
                            }
                        }).show();

                    }catch(Exception e){
                        ErrorController.showError(e);
                    }
                }
            }));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    private void setLiveData() {

        try {

            mVmodel.getProjectLIveData().observe(this, new Observer<List<NsProject>>() {
                @Override
                public void onChanged(List<NsProject> nsProjects) {

                    try {

                        if (mAdapter == null) {

                            mAdapter = new NsGeneralListAdapter<NsProject>(getActivity(), new AdapterNsProject(getActivity()), new ArrayList<NsProject>(), new NsProjectClickListener() {

                                @Override
                                public void onItemClick(NsProject item) {

                                    ActivityManagerTemplate.goActivityNsModule(getActivity(), null, null, item);
                                }
                            }, new DiffUtil.ItemCallback<NsProject>() {
                                @Override
                                public boolean areItemsTheSame(@NonNull NsProject left, @NonNull NsProject right) {
                                    return left.getProjectName().equalsIgnoreCase(right.getProjectName());
                                }

                                @Override
                                public boolean areContentsTheSame(@NonNull NsProject left, @NonNull NsProject right) {
                                    return left.equals(right);
                                }
                            });

                            recyclerViewNsProject.setAdapter(mAdapter);
                        }

                        mAdapter.submitList(nsProjects);
                        textViewNoContents.setVisibility(BehaviorUtil.booleanToVisibility(!(nsProjects != null && nsProjects.size() > 0)));

                    } catch (Exception e) {
                        ErrorController.showError(e);
                    }
                }
            });


        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    private void checkAuth() {

        try {

            TedPermission.with(mContext)
                    .setPermissionListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted() {

                            createDirectory();

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

}