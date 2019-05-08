package mars.nomad.com.l12_applicationutil.Video;

import android.media.MediaPlayer;
import android.view.Surface;

import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-03-29.
 */
public class VideoControlUtil {


    private static VideoControlUtil instance;
    private String videoPath;
    private VideoControlCallback mCallback;

    public static VideoControlUtil getInstance() {
        if (instance == null) {
            instance = new VideoControlUtil();
        }

        return instance;
    }

    private MediaPlayer mediaPlayer;

    public void startMediaPlayerNoAuto(String url, String filePath, boolean selected, Surface surface, final VideoControlUtil.VideoControlCallback callback) {
        try {

            if (selected) { // 재생일경우

                if (mediaPlayer != null && videoPath.equalsIgnoreCase(filePath)) {
                    mCallback.onStart();
                    mediaPlayer.start();
                } else {
                    stopMediaPlay();

                    this.mCallback = callback;

                    mediaPlayer = new MediaPlayer();

                    videoPath = filePath;

                    String playUrl = url + "files/" + filePath;

                    ErrorController.showMessage("[PlayUrl] : " + playUrl);

                    mediaPlayer.setDataSource(playUrl);
                    mediaPlayer.setSurface(surface);
                    mCallback.onStart();
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            mCallback.onPrePared();
                            mediaPlayer.start();
                        }
                    });

                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            mCallback.onComplete();
                            stopMediaPlay();
                        }
                    });

                    mediaPlayer.prepareAsync();
                }
            } else {
                if (mediaPlayer != null) {
                    callback.onPause();
                    mediaPlayer.pause();
                }
            }
        } catch (Exception e) {
            ErrorController.showError(e);
        }

    }

    /**
     * 화면이 넘어가거나 하는 액션에서 얘는 반드시 실행해줘야함.
     */
    public void stopMediaPlay() {
        try {
            if (mediaPlayer != null) {
                ErrorController.showMessage("[CustomPostVideoView] stopMediaPlayer");
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
                videoPath = "";
            }
            if (mCallback != null) {
                mCallback.onStop();
            }
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public interface VideoControlCallback {
        void onStart();

        void onPrePared();

        void onComplete();

        void onPause();

        void onStop();

    }
}
