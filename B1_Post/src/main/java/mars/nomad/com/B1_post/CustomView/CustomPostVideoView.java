package mars.nomad.com.B1_post.CustomView;

import android.content.Context;
import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;

import mars.nomad.com.B1_post.SubActivity.ActivityOneVideoViewer;
import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.B1_post.DataModel.PostVideoDataModel;
import mars.nomad.com.B1_post.R;
import mars.nomad.com.l12_applicationutil.Video.VideoControlUtil;
import mars.nomad.com.m0_http.RetrofitSender2;
import mars.nomad.com.m0_imageloader.ImageLoader;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-03-26.
 */
public class CustomPostVideoView extends CustomPostBaseView {
    private TextureView surfaceView;
    private ImageView imageViewPlayButton;
    private ImageView imageViewFullScreen;
    private RelativeLayout relativeLayoutVideo;

    private final boolean isAutoPlay = false;
    private ImageView imageViewThumb;
    private RelativeLayout relativeLayoutCell;

    public CustomPostVideoView(Context context) {
        super(context);
    }

    public CustomPostVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initVIew() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.custom_post_video_view, this, false);
        this.relativeLayoutCell = (RelativeLayout) view.findViewById(R.id.relativeLayoutCell);

        this.imageViewThumb = (ImageView) view.findViewById(R.id.imageViewThumb);
        this.relativeLayoutVideo = (RelativeLayout) view.findViewById(R.id.relativeLayoutVideo);
        this.imageViewFullScreen = (ImageView) view.findViewById(R.id.imageViewFullScreen);
        this.imageViewPlayButton = (ImageView) view.findViewById(R.id.imageViewPlayButton);
        this.surfaceView = (TextureView) view.findViewById(R.id.surfaceView);

        addView(view);
    }

    /**
     * 비디오는 반드시 반드시 비디오어야함.
     *
     * @param contents
     */
    @Override
    public void setContents(final String contents, final String accessToken) {
        try {

            final PostVideoDataModel video = new Gson().fromJson(contents, PostVideoDataModel.class);

            // 썸네일 셋팅

            ImageLoader.loadImageWithDefault(getContext(), imageViewThumb,  RetrofitSender2.URL_IMG_BASE, video.getThumbPath(), accessToken);


            relativeLayoutVideo.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    relativeLayoutVideo.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                    ViewGroup.LayoutParams params = relativeLayoutVideo.getLayoutParams();

                    double extendedHeight = (double) video.getThumb_height() * relativeLayoutCell.getWidth() / video.getThumb_width();

                    params.height = (int) extendedHeight;

                    relativeLayoutVideo.setLayoutParams(params);


                    ViewGroup.LayoutParams imageParams = imageViewThumb.getLayoutParams();

                    imageParams.height = (int) extendedHeight;

                    imageViewThumb.setLayoutParams(imageParams);

                }
            });

            // 썸네일 클릭시
            imageViewThumb.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    startMedia(video.getFilePath());

                }
            });

            surfaceView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 클릭의 반대로 봐야함. 처음은 false 이므로 액션은 그다음 true 액션이 되어야함.
                    startMedia(video.getFilePath());

                }
            });

            // surfaceview 리스너
            surfaceView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
                @Override
                public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                    if (isAutoPlay) {   // Auto플레이가 가능할경우 실행
                        startMedia(video.getFilePath());
                    }
                }

                @Override
                public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

                }

                @Override
                public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                    try {
                        VideoControlUtil.getInstance().stopMediaPlay();
                    } catch (Exception e) {
                        ErrorController.showError(e);
                    }
                    return true;
                }

                @Override
                public void onSurfaceTextureUpdated(SurfaceTexture surface) {

                }
            });

            imageViewFullScreen.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), ActivityOneVideoViewer.class);

                    intent.putExtra(ActivityOneVideoViewer.VIDEO_DATA, video);

                    getContext().startActivity(intent);
                }
            });

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    private void startMedia( String filePath) {
        try {
            VideoControlUtil.getInstance().startMediaPlayerNoAuto( RetrofitSender2.URL_IMG_BASE, filePath, (imageViewPlayButton.getVisibility() == VISIBLE), new Surface(surfaceView.getSurfaceTexture()), new VideoControlUtil.VideoControlCallback() {
                @Override
                public void onStart() {
                    imageViewPlayButton.setVisibility(GONE);

                }

                @Override
                public void onPrePared() {
                    imageViewThumb.setVisibility(GONE);
                }

                @Override
                public void onComplete() {
                    imageViewPlayButton.setVisibility(VISIBLE);
                    imageViewThumb.setVisibility(VISIBLE);
                }

                @Override
                public void onPause() {
                    imageViewPlayButton.setVisibility(VISIBLE);

                }

                @Override
                public void onStop() {
                    imageViewPlayButton.setVisibility(View.VISIBLE);
                    imageViewThumb.setVisibility(View.VISIBLE);
                }
            });
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


}
