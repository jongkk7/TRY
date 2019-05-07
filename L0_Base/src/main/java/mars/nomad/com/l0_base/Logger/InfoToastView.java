package mars.nomad.com.l0_base.Logger;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import mars.nomad.com.l0_base.R;


/**
 * Created by SJH on 2017-02-28.
 * 어디서든 부를 수 있도록 빌더 패턴화한 InfoView. 커스텀뷰를 사용한다.<br/>
 * [2017-03-06] SJH : mColorFlag가 boolean에서 int로 변경되었습니다.(info 타입의 Info가 추가됨).<br/>
 *  변경 전 <br/>
 *  true -> green(success) <br/>
 *  false -> red(fail) <br/>
 *  사용 : ErrorController.showInfoToast(mContext, "", true/false); <br/>
 *  변경 후 <br/>
 *  0 -> green(success) <br/>
 *  1 -> blue(info) <br/>
 *  2 -> red(fail) <br/>
 * 사용 : ErrorController.showInfoToast(mContext, "", 0/1/2); <br/>
 */
public class InfoToastView extends LinearLayout{

    private TextView textViewMessage;
    private ImageView imageViewQuit;
    private LinearLayout linearLayoutCell;
    private RelativeLayout relativeLayoutCell;

    private Context mContext;
    private String mMessage;

    /**
     * 0 : green <br/>
     * 1 : blue <br/>
     * 2 : red
     */
    private int mColorFlag;

    public InfoToastView(Context context, String message, int colorFlag) {
        super(context);
        this.mMessage = message;
        this.mContext = context;
        this.mColorFlag = colorFlag;
        initView(context);
    }

    public InfoToastView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void initView(Context context){
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.info_view, this, false);
        this.relativeLayoutCell = (RelativeLayout) view.findViewById(R.id.relativeLayoutCell);
        this.linearLayoutCell = (LinearLayout) view.findViewById(R.id.linearLayoutCell);
        this.imageViewQuit = (ImageView) view.findViewById(R.id.imageViewQuit);
        this.textViewMessage = (TextView) view.findViewById(R.id.textViewMessage);
        addView(view);
    }


    /**
     * InfoToastBuilder를 깔끔하게 래핑한 형태.
     * @param context 현재 화면의 context
     * @param message 표시할 메시지
     * @return InfoToastView 객체.
     */
    public static InfoToastView showToast(Context context, String message, int colorFlag){
        return new InfoToastView.InfoToastBuilder(context).setText(message).setColorFlag(colorFlag).create();
    }


    public static Toast toast;

    public void show(){
        textViewMessage.setText(mMessage);

        if(mColorFlag == 0){
            this.relativeLayoutCell.setBackgroundColor(Color.parseColor("#8bc34a"));
        }else if(mColorFlag == 1){
            this.relativeLayoutCell.setBackgroundColor(Color.parseColor("#2196f3"));
        }else{
            this.relativeLayoutCell.setBackgroundColor(Color.parseColor("#f44336"));
        }

        if(toast == null) {

            toast = new Toast(mContext);

        }else{

            try {

                toast.cancel();

            } catch (Exception e) {
                ErrorController.showError(e);
            }
        }
        toast = new Toast(mContext);

        toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(this);
        toast.show();
    }

    public static class InfoToastBuilder{
        private String mText;
        private Context mContext;
        private int mColorFlag;

        public InfoToastBuilder(Context context){
            this.mContext = context;
        }

        public InfoToastBuilder setText(String text){
            this.mText = text;
            return this;
        }

        public InfoToastBuilder setColorFlag(int colorFlag){
            this.mColorFlag = colorFlag;
            return this;
        }

        public InfoToastView create(){
            return new InfoToastView(mContext, mText, mColorFlag);
        }

    }


}
