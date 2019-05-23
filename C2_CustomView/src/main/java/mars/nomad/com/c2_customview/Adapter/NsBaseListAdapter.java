package mars.nomad.com.c2_customview.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;

import com.google.gson.Gson;
import mars.nomad.com.l0_base.Callback.RecyclerViewClickListener;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SJH, NomadSoft.Inc, 2019-01-07
 */
public abstract class NsBaseListAdapter<T, VH extends RecyclerView.ViewHolder> extends ListAdapter<T, VH> {


    protected RecyclerViewClickListener<T> mClickListener;

    protected Context mContext;

    private List<T> data;

    public NsBaseListAdapter(@NonNull DiffUtil.ItemCallback<T> diffCallback) {
        super(diffCallback);
    }

    protected NsBaseListAdapter(final @NonNull DiffUtil.ItemCallback<T> diffCallback, final Context context) {
        super(diffCallback);
        this.mContext = context;
    }

    public NsBaseListAdapter(final @NonNull DiffUtil.ItemCallback<T> diffCallback, final Context mContext, final RecyclerViewClickListener<T> mClickListener) {
        super(diffCallback);
        this.mContext = mContext;
        this.mClickListener = mClickListener;

    }


    @NonNull
    @Override
    public abstract VH onCreateViewHolder(@NonNull ViewGroup parents, int viewType);

    @Override
    public abstract void onBindViewHolder(@NonNull VH holder, int position);

    public List<T> getData() {
        return data;
    }

    protected T getRealItem(int position) {
        return data.get(position);
    }

    @Override
    public void submitList(@Nullable List<T> list) {

        Gson gson = new Gson();

        data = list;

        List<T> temp = new ArrayList<>();

        if (list.size() > 0) {
            T item = list.get(0);

            temp = fromJsonList(gson.toJson(list), (Class<T>) item.getClass());

        }

        super.submitList(temp);
    }

    public List<T> fromJsonList(String json, Class<T> klass) {
        Gson gson = new Gson();
        return gson.fromJson(json, new ListOfSomething<T>(klass));
    }


    class ListOfSomething<X> implements ParameterizedType {

        private Class<?> wrapped;

        public ListOfSomething(Class<X> wrapped) {
            this.wrapped = wrapped;
        }

        public Type[] getActualTypeArguments() {
            return new Type[]{wrapped};
        }

        public Type getRawType() {
            return List.class;
        }

        public Type getOwnerType() {
            return null;
        }

    }
}
