package mars.nomad.com.m0_imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import mars.nomad.com.l0_base.Logger.ErrorController;


/**
 * Created by SJH on 2017-06-29.
 */

public class ImageLoader {

    /**
     * Glide의 캐시 전략을 위한 변수. 프로필 로딩 시 쓰인다. Signature 값이 같은 동안은 네트워킹을 하기보다는 캐시에 있는 파일을 우선적으로 사용한다.
     * 만약 그림을 업데이트하고자 한다면 updateSigString() 메소드를 통해 mSigString값을 업데이트 하면 된다.
     * 기본적으로 앱이 처음 구동할 때 mSigString이 초기화되며(최초의 loadProfileImage()에서 초기화됨), 사용자가 프로필 사진을 바꾸는 등의 특별한 일이 없으면 앱이 다시 시작될 때까지 유지된다.
     */
    private static String mSigString = null;


    /**
     * 프로필 이미지를 캐시가 아닌 네트워크에서 받아오기 위한 메소드.
     * mSigString이 바뀌면 loadProfileImage의 signature값이 변경됨에 따라 더이상 캐시를 참조하지 않고, 네트워크에서 적절한 파일을 다운받아온다.
     */
    public static void updateSigString() {

        mSigString = System.currentTimeMillis() + "";

    }


    public static void loadImageWithDefault(Context context, ImageView view, String imgUrl, String filePath) {

        try {


            if (filePath == null) {
                return;
            }

            String url = imgUrl + "download/" + filePath;

            ErrorController.showMessage("[loadImageWithDefault] : " + url + " - - - ");

            // GlideUrl glideUrl = new GlideUrl(url, new LazyHeaders.Builder().addHeader("Authorization", "bearer " + MyInfo.getAccess_token(context)).build());

            Glide.with(context).clear(view);

            RequestOptions options = new RequestOptions().centerCrop().dontAnimate().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).priority(Priority.HIGH);

            Glide.with(context).load(url).apply(options).into(view);

        } catch (Exception e) {
            //ErrorController.showError(e);
        }
    }

    public static void loadImageWithDefault(Context context, ImageView view, String imgUrl, String filePath, String access_token) {

        try {


            if (filePath == null) {
                return;
            }

            String url = imgUrl + "download/" + filePath;

            ErrorController.showMessage("[loadImageWithDefault] : " + url + " - - - ");

            // GlideUrl glideUrl = new GlideUrl(url, new LazyHeaders.Builder().addHeader("Authorization", "bearer " + MyInfo.getAccess_token(context)).build());

            Glide.with(context).clear(view);

            RequestOptions options = new RequestOptions().centerCrop().dontAnimate().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).priority(Priority.HIGH);

            Glide.with(context).load(url).apply(options).into(view);

        } catch (Exception e) {
            //ErrorController.showError(e);
        }
    }


    public static void loadImageWithDefault(Context context, ImageView view, String imgUrl, String filePath, int placeholderResId) {

        try {


            if (filePath == null) {
                return;
            }

            String url = imgUrl + "download/" + filePath;

            ErrorController.showMessage("[loadImageWithDefault] : " + url + " - - - ");

            // GlideUrl glideUrl = new GlideUrl(url, new LazyHeaders.Builder().addHeader("Authorization", "bearer " + MyInfo.getAccess_token(context)).build());

            Glide.with(context).clear(view);

            RequestOptions options = new RequestOptions().centerCrop().dontAnimate().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).priority(Priority.HIGH);

            Glide.with(context).load(url).apply(options).into(view);

        } catch (Exception e) {
            //ErrorController.showError(e);
        }
    }

    public static void loadProfileWith(Context context, ImageView view, String imgUrl, String userId) {

        try {


            if (userId == null) {
                return;
            }

            String url = imgUrl + "file/profile/" + userId + "?isThumb=1";

            ErrorController.showMessage("[profile] : " + url + " - - - ");

            // GlideUrl glideUrl = new GlideUrl(url, new LazyHeaders.Builder().addHeader("Authorization", "bearer " + MyInfo.getAccess_token(context)).build());


            Glide.with(context).clear(view);

            RequestOptions options = new RequestOptions().centerCrop().dontAnimate().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).priority(Priority.HIGH);

            Glide.with(context).load(url).apply(options).into(view);

        } catch (Exception e) {
            //ErrorController.showError(e);
        }
    }

    public static void loadImageWithMaxSize(Context context, ImageView view, String imgUrl, String filePath, int maxDp, int originalWidth, int originalHeight) {

        try {


            if (filePath == null) {
                return;
            }

            String url = imgUrl + "download/" + filePath;

            ErrorController.showMessage("[loadImageWithDefault] : " + url + " - - - ");

            double maxPixel = getDpToPixel(context, maxDp);


            if (originalWidth == 0) {

                originalWidth = (int) maxPixel;
            }

            if (originalHeight == 0) {

                originalHeight = (int) maxPixel;
            }

            double resizedWidth = originalWidth;
            double resizedHeight = originalHeight;


            if (resizedWidth < maxPixel) {//맥스 픽셀보다 작을 경우 -> 너무 작을 경우를 고려한다.

                while (true) {

                    if (resizedWidth < maxPixel) {

                        resizedWidth = resizedWidth * 1.2;
                        resizedHeight = resizedHeight * 1.2;

                    } else {

                        //maxPixel 이하가 되도록 조정
                        //width : height = max : y
                        //y = (max * height)/width
                        resizedHeight = (maxPixel * resizedHeight) / resizedWidth;
                        resizedWidth = maxPixel;

                        break;
                    }
                }

            } else {//멕스 픽셀보다 클 경우 -> 멕스 픽셀에 맞게 사이즈를 조절한다.

                while (true) {//맥스 픽셀보다 클 경우 리사이즈

                    if (resizedWidth > maxPixel) {

                        resizedWidth = resizedWidth / 1.2;
                        resizedHeight = resizedHeight / 1.2;

                    } else {
                        break;
                    }
                }
            }
            ErrorController.showMessage("[SizeLoader] oWidth : " + originalWidth + ", oHeight : " + originalHeight + ", rWidth : " + resizedWidth + ", rHeight : " + resizedHeight);

            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.width = (int) resizedWidth;
            params.height = (int) resizedHeight;
            view.setLayoutParams(params);


            Glide.with(context).clear(view);

            RequestOptions options = new RequestOptions().centerCrop().dontAnimate().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).priority(Priority.HIGH);

            Glide.with(context).load(url).apply(options).into(view);

        } catch (Exception e) {
            //ErrorController.showError(e);
        }
    }


    public static void loadImageWithDefaultNonCrop(Context context, ImageView view, String imgUrl, String filePath) {

        try {


            if (filePath == null) {
                return;
            }

            String url = imgUrl + "download/" + filePath;

            ErrorController.showMessage("[loadImageWithDefault] : " + url + " - - - ");

            // GlideUrl glideUrl = new GlideUrl(url, new LazyHeaders.Builder().addHeader("Authorization", "bearer " + MyInfo.getAccess_token(context)).build());


            Glide.with(context).clear(view);

            RequestOptions options = new RequestOptions().dontAnimate().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).priority(Priority.HIGH);

            Glide.with(context).load(url).apply(options).into(view);

        } catch (Exception e) {
            //ErrorController.showError(e);
        }
    }


    public static void loadLocalThumbImage(ImageView view, Context context, String local) {
        try {


            view.setScaleType(ImageView.ScaleType.CENTER_CROP);

            String uri = "";

            if (!local.startsWith("file://")) {
                uri = "file://";
            }
            uri += local;
            ErrorController.showMessage("load thumbnail path : " + uri);

            Glide.with(context).clear(view);

            RequestOptions options = new RequestOptions().dontAnimate().skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).priority(Priority.HIGH);

            Glide.with(context).load(uri).apply(options).into(view);
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    public interface ImageLoaderCallback {

        void onSuccess(Bitmap bitmap);

        void onFailed(String fault);
    }

    public static int getDpToPixel(Context context, int DP) {
        float px = 0;
        try {
            px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DP, context.getResources().getDisplayMetrics());
        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return (int) px;
    }

}
