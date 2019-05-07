package mars.nomad.com.c2_customview.ViewPager;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by SJH, NomadSoft.Inc, 2018-07-12
 * (가로 세로가 겹쳐있을때 액션을 정확히 잡기위한 뷰페이져 (Nested inside ViewPager)
 */
public class NsNestedScrollViewForViewPager extends NestedScrollView {

    private float xDistance, yDistance, lastX, lastY;


    public NsNestedScrollViewForViewPager(@NonNull Context context) {
        super(context);
    }

    public NsNestedScrollViewForViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                lastX = ev.getX();
                lastY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();
                xDistance += Math.abs(curX - lastX);
                yDistance += Math.abs(curY - lastY);
                lastX = curX;
                lastY = curY;
                if (xDistance > yDistance)
                    return false;
        }

        return super.onInterceptTouchEvent(ev);
    }


}
