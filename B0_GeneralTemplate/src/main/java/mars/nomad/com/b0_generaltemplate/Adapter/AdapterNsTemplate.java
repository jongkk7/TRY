package mars.nomad.com.b0_generaltemplate.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


import mars.nomad.com.b0_generaltemplate.Adapter.ClickListener.NsTemplateClickListener;
import mars.nomad.com.b0_generaltemplate.DataModel.NsTemplate;
import mars.nomad.com.b0_generaltemplate.R;
import mars.nomad.com.c2_customview.Adapter.NsGeneralClickListener;
import mars.nomad.com.c2_customview.Adapter.NsGeneralView;
import mars.nomad.com.l0_base.Callback.CommonCallback;
import mars.nomad.com.l0_base.Callback.SingleClickListener;
import mars.nomad.com.l0_base.Logger.ErrorController;


public class AdapterNsTemplate extends NsGeneralView<NsTemplate> {


    private LinearLayout frameLayoutCell;
    private TextView textViewName;
    private TextView textViewDescription;

    public AdapterNsTemplate(Context context) {
        super(context);
    }

    @Override
    public int initViewId() {
        return R.layout.adapter_ns_template;
    }

    @Override
    public void initView(View view) {

        frameLayoutCell = (LinearLayout) view.findViewById(R.id.frameLayoutCell);
        textViewName = (TextView) view.findViewById(R.id.textViewName);
        textViewDescription = (TextView) view.findViewById(R.id.textViewDescription);
    }

    @Override
    public void setEvent(List<NsTemplate> data, final NsTemplate item, NsGeneralClickListener<NsTemplate> mClickListener) {

        try {

            final NsTemplateClickListener clickListener = (NsTemplateClickListener) mClickListener;

            frameLayoutCell.setOnClickListener(new SingleClickListener(new CommonCallback.SingleActionCallback() {
                @Override
                public void onAction() {

                    clickListener.onClickTemplate(item);
                }
            }));


        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    @Override
    public void onBindData(List<NsTemplate> data, NsTemplate item) {

        try {

            textViewName.setText(item.getTemplateName());

            textViewDescription.setText(item.getDescription());

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

}
