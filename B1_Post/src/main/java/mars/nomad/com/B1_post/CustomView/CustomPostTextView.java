package mars.nomad.com.B1_post.CustomView;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import mars.nomad.com.B1_post.DataModel.PostTextDataModel;
import mars.nomad.com.B1_post.R;
import mars.nomad.com.l0_base.Logger.ErrorController;


/**
 * Created by 김창혁, NomadSoft.Inc on 2019-03-26.
 */
public class CustomPostTextView extends CustomPostBaseView {

    private TextView textViewContents1;
    private TextView textViewContents2;
    private TextView textViewContents3;
    private TextView textViewContents4;
    private TextView textViewContents5;
    private TextView textViewContents6;

    public CustomPostTextView(Context context) {
        super(context);
    }

    public CustomPostTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initVIew() {
        try {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.custom_post_text_view, this, false);

            this.textViewContents6 = (TextView) view.findViewById(R.id.textViewContents6);
            this.textViewContents5 = (TextView) view.findViewById(R.id.textViewContents5);
            this.textViewContents4 = (TextView) view.findViewById(R.id.textViewContents4);
            this.textViewContents3 = (TextView) view.findViewById(R.id.textViewContents3);
            this.textViewContents2 = (TextView) view.findViewById(R.id.textViewContents2);
            this.textViewContents1 = (TextView) view.findViewById(R.id.textViewContents1);


            addView(view);
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    /**
     * 텍스트는 두가지 타입이 있음. 구버전, 신버전
     * 구버전 : 평범한 평문으로 들어옴
     * 신버전 : 사이즈 등을 합친 json으로 들어옴
     *
     * @param contents
     */
    @Override
    public void setContents(String baseUrl, String contents, final String accessToken) {
        try {

            try {

                PostTextDataModel text = new Gson().fromJson(contents, PostTextDataModel.class);

                textViewContents1.setVisibility(GONE);
                textViewContents2.setVisibility(GONE);
                textViewContents3.setVisibility(GONE);
                textViewContents4.setVisibility(GONE);
                textViewContents5.setVisibility(GONE);
                textViewContents6.setVisibility(GONE);


                // 텍스트 사이즈
                switch (text.getFontSize()) {
                    case 1: // 가장 작음
                        setTextData(text, textViewContents1);
                        break;
                    case 2:
                        setTextData(text, textViewContents2);
                        break;
                    case 3:
                        setTextData(text, textViewContents3);
                        break;
                    case 4:
                        setTextData(text, textViewContents4);
                        break;
                    case 5:
                        setTextData(text, textViewContents5);
                        break;
                    case 6: // 가장 큼
                        setTextData(text, textViewContents6);
                        break;
                    default: // 그외의 값일 경우 기본으로 셋팅
                        setTextData(text, textViewContents1);
                        break;
                }


            } catch (Exception e) {   // 에러가 잡힐경우 구버전

                // 모든 글이 평문이므로 그냥 셋팅
                textViewContents1.setVisibility(VISIBLE);
                textViewContents1.setText(contents);
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    private void setTextData(PostTextDataModel text, TextView textView) {
        try {

            textView.setVisibility(VISIBLE);

            // Bold 유무 && Italic 유무

            // Bold + Italic
            if (text.getIsBold() && text.getIsItalic()) {
                textView.setTypeface(null, Typeface.BOLD_ITALIC);
            } else if (text.getIsBold()) { // Bold Only
                textView.setTypeface(null, Typeface.BOLD);
            } else if (text.getIsItalic()) { // Italic Only
                textView.setTypeface(null, Typeface.ITALIC);
            }

            textView.setText(text.getContents());

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }
}
