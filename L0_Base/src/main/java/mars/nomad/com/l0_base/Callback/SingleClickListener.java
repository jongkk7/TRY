package mars.nomad.com.l0_base.Callback;

import android.os.SystemClock;
import android.view.View;

/**
 * Created by SJH, NomadSoft.Inc on 2017-09-22.
 */

public class SingleClickListener implements View.OnClickListener {

    private long mLastClickTime = 0;

    private CommonCallback.SingleActionCallback callback;

    public SingleClickListener(CommonCallback.SingleActionCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onClick(View view) {

        if (SystemClock.elapsedRealtime() - mLastClickTime < 100){
            return;
        }else {
            mLastClickTime = SystemClock.elapsedRealtime();
            callback.onAction();
        }
    }
}