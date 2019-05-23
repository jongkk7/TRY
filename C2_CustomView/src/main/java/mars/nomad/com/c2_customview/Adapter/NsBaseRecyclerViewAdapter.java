package mars.nomad.com.c2_customview.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;

import mars.nomad.com.l0_base.Callback.CommonCallback;
import mars.nomad.com.l0_base.Callback.NsPredicateObject;
import mars.nomad.com.l0_base.Callback.RecyclerViewClickListener;

import java.util.ArrayList;
import java.util.List;

import mars.nomad.com.l0_base.Logger.ErrorController;


/**
 * Created by SJH, NomadSoft.Inc on 2017-10-16.
 */

public abstract class NsBaseRecyclerViewAdapter<VH extends RecyclerView.ViewHolder, T> extends RecyclerView.Adapter<VH> {

    protected Context mContext;

    protected List<T> data;

    protected RecyclerViewClickListener<T> mClickListener;

    protected CommonCallback.SingleObjectActionCallback<Integer> mNotifyCallback;

    public NsBaseRecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public NsBaseRecyclerViewAdapter(Context mContext, List<T> data) {
        this.mContext = mContext;
        this.data = data;
    }



    public NsBaseRecyclerViewAdapter(Context mContext, List<T> data, RecyclerViewClickListener<T> mClickListener) {
        this.mContext = mContext;
        this.data = data;
        this.mClickListener = mClickListener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public void addAll(List<T> list) {

        try {

            int position = data.size();

            data.addAll(list);

            nsNotifyItemRangeInserted(position, list.size());

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void addAll2(List<T> list) {

        try {


            for (T t : list) {

                data.add(t);

            }
            nsNotifyDataSetChanged();


        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void removeItem(T item, NsPredicateObject<T> predicate) {

        try {

            removeItemPredicate(this, getData(), predicate);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    @NonNull
    @Override
    public abstract VH onCreateViewHolder(@NonNull ViewGroup parents, int viewType);

    @Override
    public abstract void onBindViewHolder(@NonNull VH holder, int position);

    /**
     * 어댑터에서 해당 아이템을 제거 처리한다.
     *
     * @param adapter
     * @param list
     * @param predicate
     * @param <T>
     */
    public static <T> void removeItemPredicate(RecyclerView.Adapter adapter, List<T> list, NsPredicateObject<T> predicate) {

        try {

            int position = -1;

            if (list == null) {
                return;
            }

            for (T t : list) {
                if (predicate.apply(t)) {
                    position = list.indexOf(t);
                    break;
                }
            }

            if (position != -1) {
                list.remove(position);

                if (adapter instanceof NsBaseRecyclerViewAdapter) {

                    ((NsBaseRecyclerViewAdapter) adapter).nsNotifyItemRemoved(position);

                } else {

                    adapter.notifyItemRemoved(position);
                }

            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public void replaceAll(List<T> data) {

        this.data = data;
        nsNotifyDataSetChanged();
    }

    public RecyclerViewClickListener<T> getClickListener() {
        return mClickListener;
    }

    public void setClickListener(RecyclerViewClickListener<T> mClickListener) {
        this.mClickListener = mClickListener;
    }

    /**
     * 중복을 허용하지 않고 아이템을 추가한다. 즉, 중복된 값이 있다면 추가하지 않는다.
     *
     * @param item
     */
    public void addWithoutDuplicate(T item, NsPredicateObject<T> predicate) {

        try {

            boolean isExist = isItemExist(getData(), predicate);

            if (!isExist) {

                data.add(item);
                nsNotifyItemInserted(data.indexOf(item));
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public static <T> boolean isItemExist(List<T> list, NsPredicateObject<T> predicate) {

        try {

            if (list == null) {
                list = new ArrayList<>();
                return false;
            }

            for (T t : list) {
                if (predicate.apply(t)) {
                    return true;
                }
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return false;
    }

    /**
     * 해당 position이 리스트의 마지막 항목인지 돌려준다.
     *
     * @param position
     * @return
     */
    protected boolean isLastOfList(int position) {

        try {

            if (data != null && data.size() > 0) {

                if (position == data.size() - 1) {

                    return true;

                }
            } else {//리스트 크기가 0이면 무조건 마지막이다.

                return true;
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return false;
    }

    public void addItem(T item) {

        try {

            if (data == null) {
                data = new ArrayList<>();
            }

            data.add(item);
            nsNotifyItemInserted(data.indexOf(item));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void setNotifyCallback(CommonCallback.SingleObjectActionCallback<Integer> mNotifyCallback) {
        this.mNotifyCallback = mNotifyCallback;
    }


    public void nsNotifyDataSetChanged() {

        try {

            notifyDataSetChanged();

            if (mNotifyCallback != null) {

                mNotifyCallback.onAction(data.size());
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void nsNotifyItemInserted(int position) {

        try {

            notifyItemInserted(position);

            if (mNotifyCallback != null) {

                mNotifyCallback.onAction(data.size());
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void nsNotifyItemRemoved(int position) {

        try {

            notifyItemRemoved(position);

            if (mNotifyCallback != null) {

                mNotifyCallback.onAction(data.size());
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void nsNotifyItemChanged(int position) {

        try {

            notifyItemChanged(position);

            if (mNotifyCallback != null) {

                mNotifyCallback.onAction(data.size());
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void nsNotifyItemRangeInserted(int start, int size) {

        try {

            notifyItemRangeInserted(start, size);

            if (mNotifyCallback != null) {

                mNotifyCallback.onAction(data.size());
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void addInPosition(int position, T item) {

        try {

            if (data == null) {

                data = new ArrayList<>();
            }

            data.add(position, item);
            notifyItemInserted(position);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void addAll(int position, List<T> list) {

        try {

            if (data == null) {

                data = new ArrayList<>();
            }

            data.addAll(position, list);
            notifyItemRangeInserted(position, list.size());

            if (data.size() > list.size()) {

                notifyItemChanged(list.size());
            }

            //nsNotifyItemRangeInserted(position, list.size());

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }
}
