package mars.nomad.com.b3_commongallery.Callback;

import mars.nomad.com.b3_commongallery.DataModel.CommonGalleryDataModel;
import mars.nomad.com.c2_customview.Adapter.NsGeneralClickListener;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-05-21.
 */
public interface CommonGalleryClickListener extends NsGeneralClickListener<CommonGalleryDataModel> {

    void onClick(CommonGalleryDataModel item);
}
