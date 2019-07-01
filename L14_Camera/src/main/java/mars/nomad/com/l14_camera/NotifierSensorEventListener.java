package mars.nomad.com.l14_camera;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-05-31.
 */
public class NotifierSensorEventListener implements SensorEventListener {
    private final OrientationChangeCallback callback;
    private int mOrientation = 0;

    public NotifierSensorEventListener(OrientationChangeCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        int newOrientation = mOrientation;
        if (x < 5 && x > -5 && y > 5)
            newOrientation = 0;
        else if (x < -5 && y < 5 && y > -5)
            newOrientation = 90;
        else if (x < 5 && x > -5 && y < -5)
            newOrientation = 180;
        else if (x > 5 && y < 5 && y > -5)
            newOrientation = 270;

        if (mOrientation != newOrientation) {
            mOrientation = newOrientation;

            callback.onChange(mOrientation);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    public interface OrientationChangeCallback {

        void onChange(int ori);

    }
}
