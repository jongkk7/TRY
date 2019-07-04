package mars.nomad.com.b0_generaltemplate.NsPackage;

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

import mars.nomad.com.a0_common.DataBase.Room.NsPackage.NsPackage;
import mars.nomad.com.b0_generaltemplate.ActivityManagerTemplate;
import mars.nomad.com.b0_generaltemplate.NsPackage.Adapter.AdapterNsPackage;
import mars.nomad.com.b0_generaltemplate.NsPackage.Adapter.ClickListener.NsPackageClickListener;
import mars.nomad.com.b0_generaltemplate.NsPackage.mvvm.NsPackageViewModel;
import mars.nomad.com.b0_generaltemplate.R;
import mars.nomad.com.c2_customview.Adapter.NsGeneralListAdapter;
import mars.nomad.com.c3_baseaf.BaseActivity;
import mars.nomad.com.l0_base.Callback.CommonCallback;
import mars.nomad.com.l0_base.Callback.SingleClickListener;
import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.l11_hardwareutil.KeyBoard.BehaviorUtil;

public class ActivityNsPackage extends BaseActivity {

    private RecyclerView recyclerViewNsPackage;
    private ImageButton imageButtonBack;
    private NsPackageViewModel mVmodel;
    private NsGeneralListAdapter<NsPackage> mAdapter;
    private TextView textViewTitle;
    private FrameLayout frameLayoutAddPackage;
    private TextView textViewNoContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setEvent();

        if (getData()) {

            setTitle();
            setLiveData();

        } else {

            showSimpleAlertDialog("모듈 정보를 가져오지 못했습니다.", new DialogInterface.OnClickListener() {
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
            this.mVmodel = ViewModelProviders.of(this).get(NsPackageViewModel.class);

            setContentView(R.layout.activity_ns_package);

            recyclerViewNsPackage = (RecyclerView) findViewById(R.id.recyclerViewNsPackage);
            imageButtonBack = (ImageButton) findViewById(R.id.imageButtonBack);
            textViewTitle = (TextView) findViewById(R.id.textViewTitle);
            frameLayoutAddPackage = (FrameLayout) findViewById(R.id.frameLayoutAddPackage);
            textViewNoContents = (TextView) findViewById(R.id.textViewNoContents);

            recyclerViewNsPackage.setLayoutManager(new LinearLayoutManager(getActivity()));

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

            this.frameLayoutAddPackage.setOnClickListener(new SingleClickListener(new CommonCallback.SingleActionCallback() {
                @Override
                public void onAction() {

                    ActivityManagerTemplate.goActivityAddPackage(getActivity(), null, null, mVmodel.getModule());
                }
            }));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    private void setLiveData() {

        try {

            mVmodel.getPackageList().observe(this, new Observer<List<NsPackage>>() {
                @Override
                public void onChanged(List<NsPackage> nsPackages) {

                    try {

                        if (mAdapter == null) {

                            mAdapter = new NsGeneralListAdapter<NsPackage>(getActivity(), new AdapterNsPackage(getActivity()), new ArrayList<NsPackage>(), new NsPackageClickListener() {

                            }, new DiffUtil.ItemCallback<NsPackage>() {
                                @Override
                                public boolean areItemsTheSame(@NonNull NsPackage left, @NonNull NsPackage right) {
                                    return left.getModuleName().equalsIgnoreCase(right.getModuleName());
                                }

                                @Override
                                public boolean areContentsTheSame(@NonNull NsPackage left, @NonNull NsPackage right) {
                                    return left.equals(right);
                                }
                            });

                            recyclerViewNsPackage.setAdapter(mAdapter);
                        }

                        mAdapter.submitList(nsPackages);

                        textViewNoContents.setVisibility(BehaviorUtil.booleanToVisibility(!(nsPackages != null && nsPackages.size() > 0)));

                    } catch (Exception e) {
                        ErrorController.showError(e);
                    }
                }
            });

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    private boolean getData() {
        return mVmodel.getData(getIntent());
    }

    private void setTitle() {

        try {

            textViewTitle.setText(mVmodel.getModule().getModuleName());

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }
}