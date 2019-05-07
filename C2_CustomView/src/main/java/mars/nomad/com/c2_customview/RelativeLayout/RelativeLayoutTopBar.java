package mars.nomad.com.c2_customview.RelativeLayout;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import mars.nomad.com.c2_customview.R;
import mars.nomad.com.l0_base.Logger.ErrorController;

public class RelativeLayoutTopBar extends RelativeLayout {

    private RelativeLayout relativeLayoutBack;
    private TextView textViewTitle;

    public RelativeLayoutTopBar(Context context) {
        super(context);
        initView();
        setEvent();
    }

    public RelativeLayoutTopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        setEvent();
        getAttrs(attrs);
    }

    private void initView() {

        try {

            LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = li.inflate(R.layout.relative_layout_top_bar, this, false);
            this.textViewTitle = (TextView) v.findViewById(R.id.textViewTitle);
            this.relativeLayoutBack = (RelativeLayout) v.findViewById(R.id.relativeLayoutBack);
            addView(v);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    private void setEvent() {

        try {


        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void setTextViewTitle(String title) {

        try {

            textViewTitle.setText(title);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void setTitleClickListener(OnClickListener listener){

        try{

            this.textViewTitle.setOnClickListener(listener);

        }catch(Exception e){
            ErrorController.showError(e);
        }
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener clickListener) {

        try {

            relativeLayoutBack.setOnClickListener(clickListener);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.topBar);
        setTypeArray(typedArray);
    }

    private void setTypeArray(TypedArray typedArray) {

        try {

            String text = typedArray.getString(R.styleable.topBar_topBarText2);
            textViewTitle.setText(text);

            typedArray.recycle();

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }
}
