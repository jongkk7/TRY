package mars.nomad.com.c2_customview.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-01-22.
 */
public class NsGeneralListAdapter<T> extends NsBaseListAdapter<T, NsGeneralViewHolder<T>> {

    private final Context mContext;
    private NsGeneralClickListener<T> mGeneralClickListener;
    private Class[] mClasses;
    NsGeneralView<T> NsListView;

    public NsGeneralListAdapter(Context mContext, NsGeneralView<T> NsListView, ArrayList<T> list, @NonNull DiffUtil.ItemCallback<T> diffCallback) {
        super(diffCallback);
        this.mContext = mContext;
        this.NsListView = NsListView;

        submitList(list);
    }

    public NsGeneralListAdapter(Context mContext, NsGeneralView<T> NsListView, ArrayList<T> list, NsGeneralClickListener<T> mGeneralClickListener, @NonNull DiffUtil.ItemCallback<T> diffCallback) {
        super(diffCallback);
        this.mContext = mContext;
        this.NsListView = NsListView;
        this.mGeneralClickListener = mGeneralClickListener;

        submitList(list);
    }

    @NonNull
    @Override
    public NsGeneralViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        try {
            Constructor<?> con = NsListView.getClass().getDeclaredConstructor(Context.class);

            return new NsGeneralViewHolder<>((NsGeneralView<T>) con.newInstance(parent.getContext()));
        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull NsGeneralViewHolder<T> holder, int position) {

        holder.onBindData(getData(), getRealItem(position), mGeneralClickListener);

    }

}
