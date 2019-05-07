package mars.nomad.com.c2_customview.RecyclerView;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;

import mars.nomad.com.c2_customview.Value.ViewConstants;
import mars.nomad.com.l0_base.Logger.ErrorController;

public class NsMaxSizeRecyclerView extends RecyclerView {

    public NsMaxSizeRecyclerView(Context context) {
        super(context);
    }

    public NsMaxSizeRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NsMaxSizeRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {

        int resultMax = 200 * 3;

        try{

            double height = ViewConstants.SCREEN_HEIGHT;

            double resultDouble = (height / 3) * 2;

            resultMax = (int) resultDouble;

        }catch(Exception e){
            ErrorController.showError(e);
        }


        heightSpec = MeasureSpec.makeMeasureSpec(resultMax, MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, heightSpec);
    }
}
