package mars.nomad.com.c2_customview.Adapter.Move;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-01-22.
 */
public class NsGeneralMoveViewHolder<T> extends RecyclerView.ViewHolder {

    NsGeneralMoveView<T> itemView;

    public NsGeneralMoveViewHolder(@NonNull NsGeneralMoveView<T> itemView) {
        super(itemView);
        this.itemView = itemView;
    }

    public void onBindData(List<T> data, NsGeneralMoveViewHolder<T> holder, T item, NsGeneralMoveClickListener<T> mClickListener) {
        itemView.onBindViewHolder(data, holder, item, mClickListener);
    }

}
