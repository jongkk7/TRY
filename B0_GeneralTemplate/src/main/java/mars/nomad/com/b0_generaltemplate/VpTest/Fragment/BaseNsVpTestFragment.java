package mars.nomad.com.b0_generaltemplate.VpTest.Fragment;

;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import mars.nomad.com.a0_common.DataBase.Room.NsModule.NsModule;
import mars.nomad.com.b0_generaltemplate.R;
import mars.nomad.com.b0_generaltemplate.VpTest.Mvvm.NsVpTestViewModel;
import mars.nomad.com.c3_baseaf.BaseFragment;
import mars.nomad.com.l0_base.Callback.CommonCallback;
import mars.nomad.com.l0_base.Logger.ErrorController;

public class BaseNsVpTestFragment extends BaseFragment {

    protected NsVpTestViewModel mViewModel;

    protected CommonCallback.BasicAdapterPagerCallback<NsModule> mCallback;

    public static BaseNsVpTestFragment newInstance(CommonCallback.BasicAdapterPagerCallback<NsModule> callback) {

        BaseNsVpTestFragment fragment = new BaseNsVpTestFragment();

        try {

            Bundle bundle = new Bundle();
            bundle.putParcelable("callback", callback);

            fragment.setArguments(bundle);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {

            mCallback = getArguments().getParcelable("callback");

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewpager_ns_vp_test, container, false);
        initView(view);
        setEvent();
        return view;
    }

    @Override
    protected void initView(View view) {

        try {

            this.mViewModel = ViewModelProviders.of(getActivity()).get(NsVpTestViewModel.class);

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

    @Override
    public boolean onBackPressed() {
        return false;
    }
}
