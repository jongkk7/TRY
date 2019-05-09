package mars.nomad.com.c2_customview.RecyclerView.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
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
        View childView = LayoutInflater.from(this.getContext()).inflate(initViewId(), this, false);
        //recyclerview에 등록시 generalview 가 wrap / wrap으로 적용되기 때문에 원하는 childview의 layoutparam으로 맞춰준다
        setLayoutParams(childView.getLayoutParams());
        addView(childView);
    }

    public abstract int initViewId();

    public abstract void initView();

    public abstract void setEvent(final List<T> data, final T item, NsGeneralClickListener<T> mClickListener);

    public abstract void onBindData(final List<T> data, final T item);


    // 일반적으로 사용하진 않는데 리사이클러뷰 안에 리사이클러뷰를 셋팅해야할때 필요함.
    public void setSubAdapter(final List<T> data, final T item, NsGeneralClickListener<T> mClickListener) {

    }


    public void onBindViewHolder(List<T> data, T item, NsGeneralClickListener<T> mClickListener) {
        onBindData(data, item);
        setEvent(data, item, mClickListener);
        setSubAdapter(data, item, mClickListener);
    }


}
