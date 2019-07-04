package mars.nomad.com.b0_generaltemplate.NsModule.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import mars.nomad.com.a0_common.DataBase.Room.NsModule.NsModule;
import mars.nomad.com.b0_generaltemplate.NsModule.Adapter.ClickListener.NsModuleClickListener;
import mars.nomad.com.b0_generaltemplate.R;
import mars.nomad.com.c2_customview.Adapter.NsGeneralClickListener;
import mars.nomad.com.c2_customview.Adapter.NsGeneralView;
import mars.nomad.com.l0_base.Callback.CommonCallback;
import mars.nomad.com.l0_base.Callback.SingleClickListener;
import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.l12_applicationutil.Date.NSDate;


public class AdapterNsModule extends NsGeneralView<NsModule> {

    private LinearLayout frameLayoutCell;
    private TextView textViewName;
    private TextView textViewPackageName;
    private TextView textViewRegDate;

    public AdapterNsModule(Context context) {
        super(context);
    }

    @Override
    public int initViewId() {
        return R.layout.adapter_ns_module;
    }

    @Override
    public void initView(View view) {

        frameLayoutCell = (LinearLayout) view.findViewById(R.id.frameLayoutCell);
        textViewName = (TextView) view.findViewById(R.id.textViewName);
        textViewPackageName = (TextView) view.findViewById(R.id.textViewPackageName);
        textViewRegDate = (TextView) view.findViewById(R.id.textViewRegDate);
    }

    @Override
    public void setEvent(List<NsModule> NsModule, final NsModule item, NsGeneralClickListener<NsModule> mClickListener) {

        try {

            final NsModuleClickListener clickListener = (NsModuleClickListener) mClickListener;

            frameLayoutCell.setOnClickListener(new SingleClickListener(new CommonCallback.SingleActionCallback() {
                @Override
                public void onAction() {

                    clickListener.onClickItem(item);
                }
            }));

            frameLayoutCell.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    clickListener.onLongClick(item);
                    return true;
                }
            });

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    @Override
    public void onBindData(List<NsModule> data, NsModule item) {

        try {

            textViewName.setText(item.getModuleName());

            textViewPackageName.setText(item.getBasePackageName());

            textViewRegDate.setText(NSDate.dateFormatDefault.format(new Date(item.getRegDate())));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

}
