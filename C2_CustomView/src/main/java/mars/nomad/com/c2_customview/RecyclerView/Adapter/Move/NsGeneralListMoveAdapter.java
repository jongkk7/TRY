package mars.nomad.com.c2_customview.RecyclerView.Adapter.Move;

import android.content.Context;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import mars.nomad.com.c2_customview.RecyclerView.Adapter.NsBaseListAdapter;
import mars.nomad.com.c2_customview.RecyclerView.Adapter.NsGeneralView;
import mars.nomad.com.c2_customview.RecyclerView.Adapter.NsGeneralViewHolder;
import mars.nomad.com.c2_customview.RecyclerView.ClickListener.ItemTouchHelperAdapter;
import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-01-22.
 */
public class NsGeneralListMoveAdapter<T> extends NsBaseListAdapter<T, NsGeneralMoveViewHolder<T>> implements ItemTouchHelperAdapter {

    private final Context mContext;
    private NsGeneralMoveClickListener<T> mGeneralClickListener;
    private Class[] mClasses;
    NsGeneralMoveView<T> NsListView;

    public NsGeneralListMoveAdapter(Context mContext, NsGeneralMoveView<T> NsListView, ArrayList<T> list, @NonNull DiffUtil.ItemCallback<T> diffCallback) {
        super(diffCallback);
        this.mContext = mContext;
        this.NsListView = NsListView;



        submitList(list);
    }

    public NsGeneralListMoveAdapter(Context mContext, NsGeneralMoveView<T> NsListView, ArrayList<T> list, NsGeneralMoveClickListener<T> mGeneralClickListener, @NonNull DiffUtil.ItemCallback<T> diffCallback) {
        super(diffCallback);
        this.mContext = mContext;
        this.NsListView = NsListView;
        this.mGeneralClickListener = mGeneralClickListener;

        submitList(list);
    }

    @NonNull
    @Override
    public NsGeneralMoveViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        try {
            Constructor<?> con = NsListView.getClass().getDeclaredConstructor(Context.class);

            return new NsGeneralMoveViewHolder<>((NsGeneralMoveView<T>) con.newInstance(parent.getContext()));
        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull NsGeneralMoveViewHolder<T> holder, int position) {

        holder.onBindData(getData(), holder, getRealItem(position), mGeneralClickListener);

    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        ErrorController.showMessage("notifyItemMoved : " + fromPosition + ", toPosition : " + toPosition);

        mGeneralClickListener.onItemMove(getRealItem(fromPosition), getRealItem(toPosition));
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        mGeneralClickListener.onItemDismiss(getRealItem(position));
    }
}
