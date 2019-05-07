package mars.nomad.com.c2_customview.LinearLayout;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by 김창혁, NomadSoft.Inc, 2018-07-26
 */
public class TwoReverseWidthPriorityLinearLayout extends LinearLayout {

    private View leftView, rightView;

    public TwoReverseWidthPriorityLinearLayout(Context context) {
        super(context);
    }

    public TwoReverseWidthPriorityLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TwoReverseWidthPriorityLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (rightView == null) {
            leftView = (View)getChildAt(0);
            rightView = (View) getChildAt(1);
        }

        int spec = MeasureSpec.makeMeasureSpec(getMeasuredWidth(), MeasureSpec.AT_MOST);
        rightView.measure(spec, spec);

        int remaining = getMeasuredWidth() - rightView.getMeasuredWidth();
        if (leftView.getMeasuredWidth() > remaining) {
            int specW = MeasureSpec.makeMeasureSpec(remaining, MeasureSpec.AT_MOST);
            int specH = MeasureSpec.makeMeasureSpec(leftView.getMeasuredHeight(), MeasureSpec.EXACTLY);
            leftView.measure(specW, specH);
        }
    }

}
