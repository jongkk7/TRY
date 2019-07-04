package mars.nomad.com.b0_generaltemplate.NsPackage.Adapter;

import android.content.Context;
import android.view.View;

import java.util.List;

import mars.nomad.com.a0_common.DataBase.Room.NsPackage.NsPackage;
import mars.nomad.com.b0_generaltemplate.NsPackage.Adapter.ClickListener.NsPackageClickListener;
import mars.nomad.com.b0_generaltemplate.R;
import mars.nomad.com.c2_customview.Adapter.NsGeneralClickListener;
import mars.nomad.com.c2_customview.Adapter.NsGeneralView;
import mars.nomad.com.l0_base.Logger.ErrorController;


public class AdapterNsPackage extends NsGeneralView<NsPackage> {

    public AdapterNsPackage(Context context) {
        super(context);
    }

    @Override
    public int initViewId() {
        return R.layout.adapter_ns_package;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void setEvent(List<NsPackage> NsPackage, NsPackage item, NsGeneralClickListener<NsPackage> mClickListener) {

        try {

            NsPackageClickListener clickListener = (NsPackageClickListener) mClickListener;

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    @Override
    public void onBindData(List<NsPackage> data, NsPackage item) {

        try {

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

}
