package mars.nomad.com.b3_commongallery.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import mars.nomad.com.b3_commongallery.Callback.CommonGalleryTotalClickListener;
import mars.nomad.com.b3_commongallery.DataModel.AlbumDataModel;
import mars.nomad.com.b3_commongallery.DataModel.CommonGalleryTotalDataModel;
import mars.nomad.com.b3_commongallery.R;
import mars.nomad.com.c2_customview.Adapter.NsGeneralClickListener;
import mars.nomad.com.c2_customview.Adapter.NsGeneralView;
import mars.nomad.com.l0_base.Callback.CommonCallback;
import mars.nomad.com.l0_base.Callback.SingleClickListener;
import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.l12_applicationutil.String.StringProcesser;
import mars.nomad.com.m0_imageloader.ImageLoader;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-05-21.
 */
public class AdapterCommonGalleryTotal extends NsGeneralView<CommonGalleryTotalDataModel> {

    private ImageView imageViewPicture;
    private TextView textViewTitle;
    private TextView textViewCount;
    private LinearLayout linearLayoutCell;

    public AdapterCommonGalleryTotal(Context context) {
        super(context);
    }

    @Override
    public int initViewId() {
        return R.layout.adapter_common_gallery_total;
    }

    @Override
    public void initView(View view) {

        imageViewPicture = (ImageView) findViewById(R.id.imageViewPicture);
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        textViewCount = (TextView) findViewById(R.id.textViewCount);
        linearLayoutCell = (LinearLayout) findViewById(R.id.linearLayoutCell);
    }

    @Override
    public void setEvent(List<CommonGalleryTotalDataModel> data, final CommonGalleryTotalDataModel item, NsGeneralClickListener<CommonGalleryTotalDataModel> mClickListener) {
        try {
            final CommonGalleryTotalClickListener clickListener = (CommonGalleryTotalClickListener) mClickListener;

            this.linearLayoutCell.setOnClickListener(new SingleClickListener(new CommonCallback.SingleActionCallback() {
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
    public void onBindData(List<CommonGalleryTotalDataModel> data, CommonGalleryTotalDataModel item) {
        try {

            // 디렉토리 이름
            textViewTitle.setText(item.getDirectoryName());

            // 이미지
            ImageLoader.loadLocalThumbImage(imageViewPicture, getContext(), item.getFullPath());

            // 전체수
            textViewCount.setText(item.getChildCnt() + "");

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }
}
