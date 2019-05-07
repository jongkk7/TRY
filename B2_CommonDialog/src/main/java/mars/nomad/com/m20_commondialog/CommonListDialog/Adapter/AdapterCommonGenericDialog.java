package mars.nomad.com.m20_commondialog.CommonListDialog.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mars.nomad.com.l0_base.Callback.RecyclerViewClickListener;

import java.util.List;

import mars.nomad.com.c2_customview.RecyclerView.Adapter.NsBaseRecyclerViewAdapter;
import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.m20_commondialog.R;

public class AdapterCommonGenericDialog<T> extends NsBaseRecyclerViewAdapter<AdapterCommonGenericDialog.AdapterCommonGenericDialogViewHolder, T> {


    private IGenericDialogCellDecorator iGenericDialogCellDecorator;

    public AdapterCommonGenericDialog(Context mContext, List<T> data, IGenericDialogCellDecorator iGenericDialogCellDecorator) {
        super(mContext, data);
        this.iGenericDialogCellDecorator = iGenericDialogCellDecorator;
    }

    public AdapterCommonGenericDialog(Context mContext, List<T> data, IGenericDialogCellDecorator iGenericDialogCellDecorator, RecyclerViewClickListener<T> mClickListener) {
        super(mContext, data, mClickListener);
        this.iGenericDialogCellDecorator = iGenericDialogCellDecorator;
    }

    @Override
    public AdapterCommonGenericDialogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.p0_cell_common_list_dialog, parent, false);
        return new AdapterCommonGenericDialogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterCommonGenericDialogViewHolder holder, final int position) {

        T item = data.get(position);

        try {

            iGenericDialogCellDecorator.onBindViewHolder(holder, data.get(position));

            holder.textViewReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mClickListener.onItemClick(data.get(position));
                }
            });


        } catch (Exception e) {
            ErrorController.showError(e);
        }

    }

    public static class AdapterCommonGenericDialogViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewReport;

        public AdapterCommonGenericDialogViewHolder(View view) {
            super(view);
            this.textViewReport = (TextView) view.findViewById(R.id.textViewReport);

        }
    }

    public interface IGenericDialogCellDecorator<T> {

        void onBindViewHolder(AdapterCommonGenericDialogViewHolder viewHolder, T item);
    }


}
