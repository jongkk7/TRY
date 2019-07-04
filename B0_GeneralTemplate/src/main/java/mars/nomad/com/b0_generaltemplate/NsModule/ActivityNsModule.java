package mars.nomad.com.b0_generaltemplate.NsModule;

import android.content.DialogInterface;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mars.nomad.com.a0_common.DataBase.Room.NsModule.NsModule;
import mars.nomad.com.b0_generaltemplate.Dialog.DialogDialogInput;
import mars.nomad.com.b0_generaltemplate.NsModule.Adapter.AdapterNsModule;
import mars.nomad.com.b0_generaltemplate.NsModule.Adapter.ClickListener.NsModuleClickListener;
import mars.nomad.com.b0_generaltemplate.NsModule.mvvm.NsModuleViewModel;
import mars.nomad.com.b0_generaltemplate.R;
import mars.nomad.com.c2_customview.Adapter.NsGeneralListAdapter;
import mars.nomad.com.c3_baseaf.BaseActivity;
import mars.nomad.com.l0_base.Callback.CommonCallback;
import mars.nomad.com.l0_base.Callback.SingleClickListener;
import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.l11_hardwareutil.KeyBoard.BehaviorUtil;
import mars.nomad.com.m20_commondialog.AlertDialog.NsAlertDialog;

public class ActivityNsModule extends BaseActivity {

    private RecyclerView recyclerViewNsModule;
    private ImageButton imageButtonBack;
    private NsModuleViewModel mVmodel;
    private NsGeneralListAdapter<NsModule> mAdapter;
    private TextView textViewNoContents;
    private FrameLayout frameLayoutAddModule;
    private TextView textViewTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setEvent();

        if (getData()) {

            setLiveData();
            setTitle();

        } else {

            showSimpleAlertDialog("프로젝트 정보를 가져오지 못했습니다.", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });
        }
    }


    @Override
    protected void initView() {

        try {

            this.mContext = this;
            this.mVmodel = ViewModelProviders.of(this).get(NsModuleViewModel.class);

            setContentView(R.layout.activity_ns_module);

            recyclerViewNsModule = (RecyclerView) findViewById(R.id.recyclerViewNsModule);
            imageButtonBack = (ImageButton) findViewById(R.id.imageButtonBack);
            textViewNoContents = (TextView) findViewById(R.id.textViewNoContents);
            frameLayoutAddModule = (FrameLayout) findViewById(R.id.frameLayoutAddModule);
            textViewTitle = (TextView) findViewById(R.id.textViewTitle);

            recyclerViewNsModule.setLayoutManager(new LinearLayoutManager(getActivity()));

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

            this.frameLayoutAddModule.setOnClickListener(new SingleClickListener(new CommonCallback.SingleActionCallback() {
                @Override
                public void onAction() {

                    new DialogDialogInput(getContext(), mVmodel.getModuleInput(), new CommonCallback.SingleObjectActionCallback<Map<String, String>>() {
                        @Override
                        public void onAction(Map<String, String> data) {

                            mVmodel.createModule(getContext(), data, new CommonCallback.SingleObjectCallback() {
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
                }
            }));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    private boolean getData() {
        return mVmodel.getData(getIntent());
    }


    private void setTitle() {

        try {

            textViewTitle.setText(mVmodel.getProject().getProjectName());

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    private void setLiveData() {

        try {

            mVmodel.getModuleLiveData().observe(this, new Observer<List<NsModule>>() {
                @Override
                public void onChanged(List<NsModule> nsModules) {

                    try {

                        if (mAdapter == null) {

                            mAdapter = new NsGeneralListAdapter<NsModule>(getActivity(), new AdapterNsModule(getActivity()), new ArrayList<NsModule>(), new NsModuleClickListener() {

                                @Override
                                public void onClickItem(NsModule item) {

                                }

                                @Override
                                public void onLongClick(final NsModule item) {

                                    try {

                                        NsAlertDialog.showTwoChoiceDialog(getContext(), "이 모듈 진짜 지울까요? 실제 파일들도 모두 날라갑니다.", "지움", "취소", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                dialog.dismiss();
                                                mVmodel.deleteItem(item);
                                            }
                                        });

                                    } catch (Exception e) {
                                        ErrorController.showError(e);
                                    }
                                }
                            }, new DiffUtil.ItemCallback<NsModule>() {
                                @Override
                                public boolean areItemsTheSame(@NonNull NsModule left, @NonNull NsModule right) {
                                    return left.getModuleName().equalsIgnoreCase(right.getModuleName());
                                }

                                @Override
                                public boolean areContentsTheSame(@NonNull NsModule left, @NonNull NsModule right) {
                                    return left.equals(right);
                                }
                            });

                            recyclerViewNsModule.setAdapter(mAdapter);
                        }

                        mAdapter.submitList(nsModules);

                        textViewNoContents.setVisibility(BehaviorUtil.booleanToVisibility(!(nsModules != null && nsModules.size() > 0)));

                    } catch (Exception e) {
                        ErrorController.showError(e);
                    }
                }
            });


        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }
}