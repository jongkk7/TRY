package mars.nomad.com.B1_post.CustomView;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import mars.nomad.com.B1_post.DataModel.PostImageDataModel;
import mars.nomad.com.B1_post.R;
import mars.nomad.com.B1_post.SubActivity.ActivityOneImageViewer;
import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.m0_imageloader.ImageLoader;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-03-26.
 */
public class CustomPostImageView extends CustomPostBaseView {
    private ImageView imageViewContents;
    private LinearLayout linearLayoutCell;

    public CustomPostImageView(Context context) {
        super(context);
    }

    public CustomPostImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void initVIew() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.custom_post_image_view, this, false);
        this.linearLayoutCell = (LinearLayout) view.findViewById(R.id.linearLayoutCell);
        this.imageViewContents = (ImageView) view.findViewById(R.id.imageViewContents);

        addView(view);
    }

    /**
     * 이미지는 반드시 이미지 형태여야함
     *
     * @param contents
     */
    public void setContents(final String BaseUrl, String contents, final String accessToken) {
        try {

            final PostImageDataModel image = new Gson().fromJson(contents, PostImageDataModel.class);

            // 썸네일 셋팅


            imageViewContents.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ViewGroup.LayoutParams params = imageViewContents.getLayoutParams();

                    double extendedHeight = (double) image.getThumb_height() * imageViewContents.getWidth() / image.getThumb_width();

                    params.height = (int) extendedHeight;

                    imageViewContents.setLayoutParams(params);

                    ImageLoader.loadImageWithDefault(getContext(), imageViewContents, BaseUrl, image.getThumbPath(), accessToken);
                }
            }, 100);

            // 클릭 이벤트
            imageViewContents.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getContext(), ActivityOneImageViewer.class);

                    intent.putExtra(ActivityOneImageViewer.PHOTO_DATA, image);
                    intent.putExtra(ActivityOneImageViewer.PHOTO_URL, BaseUrl);

                    getContext().startActivity(intent);

                }
            });

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }
}
