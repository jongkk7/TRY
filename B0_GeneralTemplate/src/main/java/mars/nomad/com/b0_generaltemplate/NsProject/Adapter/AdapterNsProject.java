package mars.nomad.com.b0_generaltemplate.NsProject.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import mars.nomad.com.a0_common.DataBase.Room.NsProject.NsProject;
import mars.nomad.com.b0_generaltemplate.NsProject.Adapter.ClickListener.NsProjectClickListener;
import mars.nomad.com.b0_generaltemplate.R;
import mars.nomad.com.c2_customview.Adapter.NsGeneralClickListener;
import mars.nomad.com.c2_customview.Adapter.NsGeneralView;
import mars.nomad.com.l0_base.Callback.CommonCallback;
import mars.nomad.com.l0_base.Callback.SingleClickListener;
import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.l12_applicationutil.Date.NSDate;


public class AdapterNsProject extends NsGeneralView<NsProject> {

    private LinearLayout frameLayoutCell;
    private TextView textViewName;
    private TextView textViewRegDate;

    public AdapterNsProject(Context context) {
        super(context);
    }

    @Override
    public int initViewId() {
        return R.layout.adapter_ns_project;
    }

    @Override
    public void initView(View view) {

        frameLayoutCell = (LinearLayout) view.findViewById(R.id.frameLayoutCell);
        textViewName = (TextView) view.findViewById(R.id.textViewName);
        textViewRegDate = (TextView) view.findViewById(R.id.textViewRegDate);
    }

    @Override
    public void setEvent(List<NsProject> NsProject, final NsProject item, final NsGeneralClickListener<NsProject> mClickListener) {

        try {

            final NsProjectClickListener clickListener = (NsProjectClickListener) mClickListener;

            frameLayoutCell.setOnClickListener(new SingleClickListener(new CommonCallback.SingleActionCallback() {
                @Override
                public void onAction() {

                    clickListener.onItemClick(item);
                }
            }));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    @Override
    public void onBindData(List<NsProject> data, NsProject item) {

        try {

            textViewName.setText(item.getProjectName());

            textViewRegDate.setText(NSDate.dateFormatDefault.format(new Date(item.getRegDate())));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

}
