package mars.nomad.com.c2_customview.RelativeLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import mars.nomad.com.c2_customview.R;


/**
 * Created by SJH on 2017-06-26.
 */

public class RelativeLayoutLoading extends RelativeLayout {

    public RelativeLayoutLoading(Context context) {
        super(context);
        initView();
    }

    public RelativeLayoutLoading(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {


        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.loading_view, this, false);
        addView(view);
    }

}
