package mars.nomad.com.b0_generaltemplate.Adapter.ClickListener;

import mars.nomad.com.b0_generaltemplate.DataModel.NsTemplate;
import mars.nomad.com.c2_customview.Adapter.NsGeneralClickListener;

public interface NsTemplateClickListener extends NsGeneralClickListener<NsTemplate> {

    void onClickTemplate(NsTemplate item);
}
