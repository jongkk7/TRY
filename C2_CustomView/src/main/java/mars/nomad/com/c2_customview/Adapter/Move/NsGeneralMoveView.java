package mars.nomad.com.c2_customview.Adapter.Move;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import java.util.List;

import mars.nomad.com.l0_base.Callback.NsPredicateAction;
import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.l12_applicationutil.ViewSetting.NsViewUtil;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-01-22.
 */
public abstract class NsGeneralMoveView<T> extends FrameLayout {


    public NsGeneralMoveView(Context context) {
        super(context);
        initSetting();
    }

    private void initSetting() {
        View childView = LayoutInflater.from(this.getContext()).inflate(initViewId(), this, false);
        //recyclerview에 등록시 generalview 가 wrap / wrap으로 적용되기 때문에 원하는 childview의 layoutparam으로 맞춰준다
        setLayoutParams(childView.getLayoutParams());
        initView(childView);
        addView(childView);
    }

    public abstract int initViewId();

    public abstract void initView(View view);

    public abstract void setEvent(final List<T> data, NsGeneralMoveViewHolder<T> holder, final T item, final NsGeneralMoveClickListener<T> mClickListener);

    public abstract void onBindData(final List<T> data, final T item);


    // 일반적으로 사용하진 않는데 리사이클러뷰 안에 리사이클러뷰를 셋팅해야할때 필요함.
    public void setSubAdapter(final List<T> data, final T item, NsGeneralMoveClickListener<T> mClickListener) {

    }


    public void onBindViewHolder(List<T> data, NsGeneralMoveViewHolder<T> holder, final T item, NsGeneralMoveClickListener<T> mClickListener) {
        onBindData(data, item);
        setEvent(data, holder, item, mClickListener);
        setSubAdapter(data, item, mClickListener);
    }


    // 아래쪽은 유틸쪽 액션을 정의 (어댑터에서 주로 쓰는 유틸)

    /**
     * 특정 조건에 따라 view의 visibility를 조정
     *
     * @param view
     * @param predicate
     */
    public static void setVisibility(View view, NsPredicateAction predicate) {
        try {
            NsViewUtil.setVisibility(view, predicate);
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


}
