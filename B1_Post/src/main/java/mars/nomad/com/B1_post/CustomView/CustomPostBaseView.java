package mars.nomad.com.B1_post.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-03-26.
 * 구조상 필요는 없어보이는데 혹여나 필요할 경우를 대비해서 베이스를만들어놨음.
 */
public abstract class CustomPostBaseView extends RelativeLayout {


    public CustomPostBaseView(Context context) {
        super(context);
        initVIew();
    }

    public CustomPostBaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initVIew();
    }


    protected abstract void initVIew();

    public abstract void setContents(String baseUrl, String contents, String accessToken);
}
