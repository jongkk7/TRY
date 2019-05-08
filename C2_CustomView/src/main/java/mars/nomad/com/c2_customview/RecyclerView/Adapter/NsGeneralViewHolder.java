package mars.nomad.com.c2_customview.RecyclerView.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-01-22.
 */
public class NsGeneralViewHolder<T> extends RecyclerView.ViewHolder {

    NsGeneralView<T> itemView;

    public NsGeneralViewHolder(@NonNull NsGeneralView<T> itemView) {
        super(itemView);
        this.itemView = itemView;
    }

    public void onBindData(List<T> data, T item, NsGeneralClickListener<T> mClickListener) {
        itemView.onBindViewHolder(data, item, mClickListener);
    }

}
