package mars.nomad.com.b0_generaltemplate.VpTest;

;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import java.util.List;


import mars.nomad.com.a0_common.DataBase.Room.NsModule.NsModule;
import mars.nomad.com.b0_generaltemplate.R;
import mars.nomad.com.b0_generaltemplate.VpTest.Adapter.AdapterNsVpTestPager;
import mars.nomad.com.b0_generaltemplate.VpTest.Mvvm.NsVpTestViewModel;
import mars.nomad.com.c3_baseaf.BaseActivity;
import mars.nomad.com.l0_base.Callback.CommonCallback;
import mars.nomad.com.l0_base.Callback.SingleClickListener;
import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by SJH, NomadSoft.Inc, 2019-07-01
 */
public class ActivityNsVpTest extends BaseActivity {

    private ImageButton imageButtonBack;
    private ViewPager viewPager;

    private NsVpTestViewModel mViewModel;
    private AdapterNsVpTestPager mAdapterPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setEvent();
        getData();
        loadViewPager();
    }


    @Override
    protected void initView() {

        try {

            setContentView(R.layout.activity_viewpager_ns_vp_test);

            imageButtonBack = (ImageButton) findViewById(R.id.imageButtonBack);
            viewPager = (ViewPager) findViewById(R.id.viewPager);

            this.mContext = this;
            this.mViewModel = ViewModelProviders.of(this).get(NsVpTestViewModel.class);

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
                    finish();
                }
            }));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    private void getData() {

        try {

            if (!mViewModel.getData(getIntent())) {

                showSimpleAlertDialog("TODO : Fault");
                finish();
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    private void loadViewPager() {

        try {

            startLoading();

            mViewModel.loadViewPager(new CommonCallback.SingleObjectCallback<List<NsModule>>() {
                @Override
                public void onSuccess(List<NsModule> result) {

                    try {

                        stopLoading();

                        mAdapterPager = new AdapterNsVpTestPager(getSupportFragmentManager(), result, new CommonCallback.BasicAdapterPagerCallback<NsModule>() {
                            @Override
                            public void onStartLoading() {

                                startLoading();
                            }

                            @Override
                            public void onStopLoading() {

                                stopLoading();
                            }
                        });

                    } catch (Exception e) {
                        ErrorController.showError(e);
                    }
                }

                @Override
                public void onFailed(String fault) {

                    stopLoading();
                    showSimpleAlertDialog(fault);
                }
            });

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }
}
