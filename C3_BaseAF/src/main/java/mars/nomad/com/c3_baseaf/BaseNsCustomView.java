package mars.nomad.com.c3_baseaf;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by SJH, NomadSoft.Inc on 2017-09-27.
 */

public abstract class BaseNsCustomView extends LinearLayout {

    public BaseNsCustomView(Context context) {
        super(context);
    }

    public BaseNsCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseNsCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    protected abstract void initView();
    protected abstract void setEvent();

    protected void getAttrs(int[] styleable, AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, styleable);

        setTypeArray(typedArray);
    }


    protected void getAttrs(int[] styleable, AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, styleable, defStyle, 0);
        setTypeArray(typedArray);

    }

    protected abstract void setTypeArray(TypedArray typedArray);

}
