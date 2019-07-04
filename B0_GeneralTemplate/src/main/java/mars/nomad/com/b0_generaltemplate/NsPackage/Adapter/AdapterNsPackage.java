package mars.nomad.com.b0_generaltemplate.NsPackage.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import mars.nomad.com.a0_common.DataBase.Room.NsPackage.NsPackage;
import mars.nomad.com.b0_generaltemplate.NsPackage.Adapter.ClickListener.NsPackageClickListener;
import mars.nomad.com.b0_generaltemplate.R;
import mars.nomad.com.c2_customview.Adapter.NsGeneralClickListener;
import mars.nomad.com.c2_customview.Adapter.NsGeneralView;
import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.l12_applicationutil.Date.NSDate;


public class AdapterNsPackage extends NsGeneralView<NsPackage> {

    private LinearLayout frameLayoutCell;
    private TextView textViewName;
    private TextView textViewPackageName;
    private TextView textViewModuleName;
    private TextView textViewRegDate;

    public AdapterNsPackage(Context context) {
        super(context);
    }

    @Override
    public int initViewId() {
        return R.layout.adapter_ns_package;
    }

    @Override
    public void initView(View view) {

        frameLayoutCell = (LinearLayout) view.findViewById(R.id.frameLayoutCell);
        textViewName = (TextView) view.findViewById(R.id.textViewName);
        textViewPackageName = (TextView) view.findViewById(R.id.textViewPackageName);
        textViewModuleName = (TextView) view.findViewById(R.id.textViewModuleName);
        textViewRegDate = (TextView) view.findViewById(R.id.textViewRegDate);
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

            textViewName.setText(item.getPackageName());
            textViewPackageName.setText(item.getType());
            textViewModuleName.setText(item.getModuleName());
            textViewRegDate.setText(NSDate.dateFormatDefault.format(new Date(item.getRegDate())));


        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

}
