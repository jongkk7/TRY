package mars.nomad.com.b3_commongallery.Callback;

import mars.nomad.com.b3_commongallery.DataModel.CommonGalleryTotalDataModel;
import mars.nomad.com.c2_customview.Adapter.NsGeneralClickListener;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-05-21.
 */
public interface CommonGalleryTotalClickListener extends NsGeneralClickListener<CommonGalleryTotalDataModel> {

    void onClick(CommonGalleryTotalDataModel item);
}
