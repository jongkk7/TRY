package mars.nomad.com.b0_generaltemplate.NsModule.Adapter.ClickListener;

import mars.nomad.com.a0_common.DataBase.Room.NsModule.NsModule;
import mars.nomad.com.c2_customview.Adapter.NsGeneralClickListener;

public interface NsModuleClickListener extends NsGeneralClickListener<NsModule> {

    void onClickItem(NsModule item);

    void onLongClick(NsModule item);

}
