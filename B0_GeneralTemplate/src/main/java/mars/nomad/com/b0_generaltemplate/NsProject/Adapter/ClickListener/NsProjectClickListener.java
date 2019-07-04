package mars.nomad.com.b0_generaltemplate.NsProject.Adapter.ClickListener;

import mars.nomad.com.a0_common.DataBase.Room.NsProject.NsProject;
import mars.nomad.com.c2_customview.Adapter.NsGeneralClickListener;

public interface NsProjectClickListener extends NsGeneralClickListener<NsProject> {

    void onItemClick(NsProject item);
}
