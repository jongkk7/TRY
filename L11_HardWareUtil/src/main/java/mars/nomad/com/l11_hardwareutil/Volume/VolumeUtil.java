package mars.nomad.com.l11_hardwareutil.Volume;

import android.content.Context;
import android.media.AudioManager;

import mars.nomad.com.l0_base.Logger.ErrorController;


/**
 * Created by 김창혁, NomadSoft.Inc on 2018-07-24.
 */
public class VolumeUtil {

    /**
     * 볼륨이 0이면 해당 볼륨으로 셋팅한다.
     *
     * @param context
     * @param percent 0~1 사이 float값 (1이 최대이며 0.7이 넘어가면 높은 볼륨 주의 경고창이 뜬다)
     */
    public static void onZeroVolumeSeeting(Context context, float percent) {

        try {

            AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            int currentVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);

            ErrorController.showMessage("[currentVolume] : " + currentVolume + "");
            if (currentVolume == 0) {

                int maxVolume = audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                int seventyVolume = (int) (maxVolume * percent);
                audio.setStreamVolume(AudioManager.STREAM_MUSIC, seventyVolume, 0);
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }
}
