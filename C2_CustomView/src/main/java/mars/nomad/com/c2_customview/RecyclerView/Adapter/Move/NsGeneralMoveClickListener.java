package mars.nomad.com.c2_customview.RecyclerView.Adapter.Move;

import mars.nomad.com.c2_customview.RecyclerView.Adapter.NsGeneralClickListener;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-01-24.
 */
public interface NsGeneralMoveClickListener<T> extends NsGeneralClickListener {

    void onItemDismiss(T item);

    void onItemMove(T fromItem, T toItem);

}
