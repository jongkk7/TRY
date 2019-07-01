package mars.nomad.com.B1_post.SubActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import mars.nomad.com.B1_post.DataModel.PostImageDataModel;
import mars.nomad.com.B1_post.R;
import mars.nomad.com.c3_baseaf.BaseActivity;
import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.m0_http.RetrofitSender2;
import mars.nomad.com.m0_imageloader.ImageLoader;
import uk.co.senab.photoview.PhotoView;

public class ActivityOneImageViewer extends BaseActivity {

    private Context mContext;
    private uk.co.senab.photoview.PhotoView PhotoViewImage;

    public static String PHOTO_DATA = "post.photodata";
    public static String ACCESS_TOKEN = "post.accessToken";

    private PostImageDataModel image;
    private String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNoStatusBar2(getActivity());

        initView();
        getData();
        setEvent();
        setImage();
    }


    protected void initView() {

        try {

            this.mContext = this;

            setContentView(R.layout.activity_one_image_viewer);

            this.PhotoViewImage = (PhotoView) findViewById(R.id.PhotoViewImage);
            PhotoViewImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    protected void setEvent() {

        try {

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    protected Activity getActivity() {
        return this;
    }

    private void getData() {
        try {


            image = (PostImageDataModel) getIntent().getSerializableExtra(PHOTO_DATA);
            accessToken = getIntent().getStringExtra(ACCESS_TOKEN);

            if (image == null) {
                ErrorController.showToast(getActivity(), "올바른 데이터를 받지 못했습니다.");
                finish();
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }

    }


    private void setImage() {
        try {
            PhotoViewImage.post(new Runnable() {
                @Override
                public void run() {

                    ViewGroup.LayoutParams params = PhotoViewImage.getLayoutParams();

                    double extendedHeight = (double) image.getThumb_height() * PhotoViewImage.getWidth() / image.getThumb_width();

                    params.height = (int) extendedHeight;

                    PhotoViewImage.setLayoutParams(params);

                    ImageLoader.loadImageWithDefault(getActivity(), PhotoViewImage, RetrofitSender2.URL_IMG_BASE, image.getThumbPath(), accessToken);
                }
            });

//
//            ImageLoader.loadImageWithDefault(getActivity(), PhotoViewImage, url, image.getThumbPath(), MyInfoDb.getMe().getAccess_token());


        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public static void setNoStatusBar2(Activity activity) {

        try {

            int currentApiVersion = Build.VERSION.SDK_INT;
            final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            if (currentApiVersion >= Build.VERSION_CODES.KITKAT) {
                activity.getWindow().getDecorView().setSystemUiVisibility(flags);
                final View decorView = activity.getWindow().getDecorView();
                decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int visibility) {
                        if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                            decorView.setSystemUiVisibility(flags);
                        }
                    }
                });
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }
}
