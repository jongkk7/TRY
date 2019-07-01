package mars.nomad.com.B1_post.SubActivity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import mars.nomad.com.B1_post.DataModel.PostVideoDataModel;
import mars.nomad.com.B1_post.R;
import mars.nomad.com.c3_baseaf.BaseActivity;
import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.l12_applicationutil.Video.VideoControlUtil;
import mars.nomad.com.m0_http.RetrofitSender2;
import mars.nomad.com.m0_imageloader.ImageLoader;

public class ActivityOneVideoViewer extends BaseActivity {

    public static String VIDEO_DATA = "m11_post.videoData";
    public static String ACCESS_TOKEN = "m11_post.accessToken";

    private TextureView surfaceView;
    private ImageView imageViewThumb;
    private ImageView imageViewPlayButton;
    private RelativeLayout relativeLayoutVideo;
    private PostVideoDataModel video;
    private MediaPlayer mediaPlayer;
    private String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNoStatusBar2(getActivity());
        initView();
        setEvent();
        getData();
        initData();
    }


    protected void initView() {

        try {

            setContentView(R.layout.activity_one_video_viewer);

            this.relativeLayoutVideo = (RelativeLayout) findViewById(R.id.relativeLayoutVideo);
            this.imageViewPlayButton = (ImageView) findViewById(R.id.imageViewPlayButton);
            this.imageViewThumb = (ImageView) findViewById(R.id.imageViewThumb);
            this.surfaceView = (TextureView) findViewById(R.id.surfaceView);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    protected Activity getActivity() {
        return this;
    }

    protected void setEvent() {

        try {

            // 썸네일 클릭시
            imageViewThumb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startMedia();
                }
            });

            // surface 뷰 클릭시
            surfaceView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 클릭의 반대로 봐야함. 처음은 false 이므로 액션은 그다음 true 액션이 되어야함.
                    startMedia();
                }
            });


        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    private void startMedia( ) {

        VideoControlUtil.getInstance().startMediaPlayerNoAuto(RetrofitSender2.BASE_URL, video.getFilePath(), (imageViewPlayButton.getVisibility() == View.VISIBLE), new Surface(surfaceView.getSurfaceTexture()), new VideoControlUtil.VideoControlCallback() {
            @Override
            public void onStart() {
                imageViewPlayButton.setVisibility(View.GONE);
            }

            @Override
            public void onPrePared() {
                imageViewPlayButton.setVisibility(View.GONE);
                imageViewThumb.setVisibility(View.GONE);
            }

            @Override
            public void onComplete() {
                imageViewPlayButton.setVisibility(View.VISIBLE);
                imageViewThumb.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPause() {
                imageViewPlayButton.setVisibility(View.VISIBLE);

            }

            @Override
            public void onStop() {
                imageViewPlayButton.setVisibility(View.VISIBLE);
                imageViewThumb.setVisibility(View.VISIBLE);
            }
        });

    }


    private void getData() {
        try {

            video = (PostVideoDataModel) getIntent().getSerializableExtra(VIDEO_DATA);
            accessToken = getIntent().getStringExtra(ACCESS_TOKEN);

            if ( video == null) {
                ErrorController.showToast(getActivity(), "올바른 데이터를 받지 못했습니다.");
                finish();
            }

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

    private void initData() {
        try {


            // 썸네일 셋팅
            ImageLoader.loadImageWithDefault(getActivity(), imageViewThumb, RetrofitSender2.URL_IMG_BASE, video.getThumbPath(), accessToken);


            surfaceView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // 전체화면 실행시 바로 실행
                    startMedia();

                }
            }, 200);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void startMediaPlayerNoAuto(String url, String filePath, boolean selected, Surface surface) {
        try {

            if (selected) { // 재생일경우

                if (mediaPlayer != null) {

                    mediaPlayer.start();
                } else {

                    mediaPlayer = new MediaPlayer();

                    String playUrl = url + "files/" + filePath;

                    ErrorController.showMessage("[PlayUrl] : " + playUrl);

                    mediaPlayer.setDataSource(playUrl);
                    mediaPlayer.setSurface(surface);

                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {

                            mediaPlayer.start();
                        }
                    });

                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {

                            stopMediaPlay();
                        }
                    });

                    mediaPlayer.prepareAsync();
                }
            } else {
                if (mediaPlayer != null) {

                    mediaPlayer.pause();
                }
            }
        } catch (Exception e) {
            ErrorController.showError(e);
        }

    }

    private void stopMediaPlay() {
        try {
            if (mediaPlayer != null) {
                ErrorController.showMessage("[ActivityOneVideoViewer] stopMediaPlayer");
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    @Override
    protected void onPause() {
        VideoControlUtil.getInstance().stopMediaPlay();

        super.onPause();
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

    }


}
