package mars.nomad.com.l0_base.Abstract;

import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.widget.LinearLayout;


/**
 * Created by SJH on 2017-04-04.
 */

public abstract class AbstractSlidingPageChanger2 implements ViewPager.OnPageChangeListener {


    private View viewIndexerPre;
    private View viewIndexerPost;

    public AbstractSlidingPageChanger2(View viewIndexerPre, View viewIndexerPost) {
        this.viewIndexerPre = viewIndexerPre;
        this.viewIndexerPost = viewIndexerPost;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //ErrorController.showMessage("roomIdx : " + roomIdx + ", offset : " + positionOffset);

        LinearLayout.LayoutParams preParam = (LinearLayout.LayoutParams) viewIndexerPre.getLayoutParams();
        LinearLayout.LayoutParams postParam = (LinearLayout.LayoutParams) viewIndexerPost.getLayoutParams();

        if (position == 0) {

            if (positionOffset <= 0.99) {
                preParam.weight = 0 + positionOffset;
                postParam.weight = 1 - positionOffset;
            }

        } else if (position == 1) {

            if (positionOffset <= 0.99) {
                preParam.weight = 1 - positionOffset;
                postParam.weight = 0 + positionOffset;
            }

        }

       // ErrorController.showMessage("Weight : " + preParam.weight + ", " + postParam.weight);

        viewIndexerPre.setLayoutParams(preParam);
        viewIndexerPost.setLayoutParams(postParam);
    }
    @Override
    public void onPageScrollStateChanged(int state) {

    }
}