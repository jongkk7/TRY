package mars.nomad.com.c2_customview.RecyclerView.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.List;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-01-22.
 */
public abstract class NsGeneralView<T> extends FrameLayout {


    public NsGeneralView(Context context) {
        super(context);
        initSetting();
    }

    private void initSetting() {
        View childView = createView(this);
        //recyclerview에 등록시 generalview 가 wrap / wrap으로 적용되기 때문에 원하는 childview의 layoutparam으로 맞춰준다
        setLayoutParams(childView.getLayoutParams());
        addView(childView);
    }

    public View createView(ViewGroup parent) {
        return initView(parent);
    }

    public abstract View initView(ViewGroup parent);

    public abstract void onBindData(int position, List<T> data, T item, NsGeneralClickListener<T> mClickListener);

}
