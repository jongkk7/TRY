package mars.nomad.com.b3_commongallery.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.List;

import mars.nomad.com.b3_commongallery.Callback.CommonGalleryClickListener;
import mars.nomad.com.b3_commongallery.DataModel.CommonGalleryDataModel;
import mars.nomad.com.b3_commongallery.R;
import mars.nomad.com.c2_customview.Adapter.NsGeneralClickListener;
import mars.nomad.com.c2_customview.Adapter.NsGeneralView;
import mars.nomad.com.l0_base.Callback.CommonCallback;
import mars.nomad.com.l0_base.Callback.SingleClickListener;
import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.m0_imageloader.ImageLoader;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-05-21.
 */
public class AdapterCommonGallery extends NsGeneralView<CommonGalleryDataModel> {
    private ImageView imageViewPicture;
    private ImageView imageViewCheckBox;
    private RelativeLayout relativeLayoutCell;

    public AdapterCommonGallery(Context context) {
        super(context);
    }

    @Override
    public int initViewId() {
        return R.layout.adapter_common_gallery;
    }

    @Override
    public void initView(View view) {

        try {
            imageViewPicture = (ImageView) findViewById(R.id.imageViewPicture);
            imageViewCheckBox = (ImageView) findViewById(R.id.imageViewCheckBox);
            relativeLayoutCell = (RelativeLayout) findViewById(R.id.relativeLayoutCell);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    @Override
    public void setEvent(List<CommonGalleryDataModel> data, final CommonGalleryDataModel item, NsGeneralClickListener<CommonGalleryDataModel> mClickListener) {
        try {

            final CommonGalleryClickListener clickListener = (CommonGalleryClickListener) mClickListener;

            relativeLayoutCell.setOnClickListener(new SingleClickListener(new CommonCallback.SingleActionCallback() {
                @Override
                public void onAction() {
                    clickListener.onClick(item);
                }
            }));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    @Override
    public void onBindData(List<CommonGalleryDataModel> data, CommonGalleryDataModel item) {
        try {

            ImageLoader.loadLocalThumbImage(imageViewPicture, getContext(), item.getFullPath());

            // 체크
            imageViewCheckBox.setSelected(item.isSelected());

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }
}
