package mars.nomad.com.c3_baseaf.Main;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import mars.nomad.com.c2_customview.R;
import mars.nomad.com.c3_baseaf.BaseNsCustomView;
import mars.nomad.com.l0_base.Logger.ErrorController;

public class RelativeLayoutMainTopBar extends BaseNsCustomView {


    private RelativeLayout relativeLayoutHamburger;
    private TextView textViewTitle;

    public RelativeLayoutMainTopBar(Context context) {
        super(context);
        initView();
        setEvent();
    }

    public RelativeLayoutMainTopBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
        setEvent();
        getAttrs(attrs);
    }

    @Override
    protected void initView() {

        try {

            LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = li.inflate(R.layout.layout_main_top_bar, this, false);
            this.textViewTitle = (TextView) v.findViewById(R.id.textViewTitle);
            this.relativeLayoutHamburger = (RelativeLayout) v.findViewById(R.id.relativeLayoutHamburger);
            addView(v);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    @Override
    protected void setEvent() {

        try {


        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener clickListener) {

        try {

            relativeLayoutHamburger.setOnClickListener(clickListener);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    @Override
    protected void setTypeArray(TypedArray typedArray) {

        try {

            String text = typedArray.getString(R.styleable.mainTopBar_topBarText);
            textViewTitle.setText(text);

            typedArray.recycle();

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.mainTopBar);
        setTypeArray(typedArray);
    }

}
