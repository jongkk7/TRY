package mars.nomad.com.l12_applicationutil.Size;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import mars.nomad.com.l0_base.Logger.ErrorController;


/**
 * Created by SJH, NomadSoft.Inc on 2018-03-30.
 */

public class SizeUtil {


    public static int getDpToPixel(Context context, int DP) {
        float px = 0;
        try {
            px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DP, context.getResources().getDisplayMetrics());
        } catch (Exception e) {
        }
        return (int) px;
    }




    // 레이아웃을 받아서 dp to px 단위로 셋팅해준다
    public static void dpToPxViewLayout(Context context, int widthDp, int heightDp, View view) {

        try {

            if (view.getLayoutParams() instanceof LinearLayout.LayoutParams) {
                view.setLayoutParams(new LinearLayout.LayoutParams(getDpToPixel(context, widthDp), getDpToPixel(context, heightDp)));
            } else if (view.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
                view.setLayoutParams(new RelativeLayout.LayoutParams(getDpToPixel(context, widthDp), getDpToPixel(context, heightDp)));
            } else if (view.getLayoutParams() instanceof FrameLayout.LayoutParams) {
                view.setLayoutParams(new FrameLayout.LayoutParams(getDpToPixel(context, widthDp), getDpToPixel(context, heightDp)));
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }

    }

    // 각기 다른 방법으로 뷰를 가운데 셋팅한후 돌려준다.
    public static void dpToPxViewLayoutCenter(Context context, int widthDp, int heightDp, View view) {

        try {

            if (view.getLayoutParams() instanceof LinearLayout.LayoutParams) {
                // 불가능
                view.setLayoutParams(new LinearLayout.LayoutParams(getDpToPixel(context, widthDp), getDpToPixel(context, heightDp)));
            } else if (view.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
                RelativeLayout.LayoutParams relativeLayoutParams = new RelativeLayout.LayoutParams(getDpToPixel(context, widthDp), getDpToPixel(context, heightDp));
                relativeLayoutParams.addRule(Gravity.CENTER);
                view.setLayoutParams(relativeLayoutParams);
            } else if (view.getLayoutParams() instanceof FrameLayout.LayoutParams) {
                FrameLayout.LayoutParams frameLayoutParams = new FrameLayout.LayoutParams(getDpToPixel(context, widthDp), getDpToPixel(context, heightDp));
                frameLayoutParams.gravity = Gravity.CENTER;
                view.setLayoutParams(frameLayoutParams);

            }


        } catch (Exception e) {
            ErrorController.showError(e);
        }

    }

}
