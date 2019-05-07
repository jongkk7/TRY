package mars.nomad.com.l0_base.Callback;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by SJH, NomadSoft.Inc on 2018-01-05.
 */

public class NsFunction {

    public interface VoidFunction{

        void onAction();
    }

    public interface GenericFunction<T>{

        T onAction();
    }

    public interface AdapterViewDecorator<T>{

        void onBindViewHolder(RecyclerView.ViewHolder holder, T item);
    }

}
