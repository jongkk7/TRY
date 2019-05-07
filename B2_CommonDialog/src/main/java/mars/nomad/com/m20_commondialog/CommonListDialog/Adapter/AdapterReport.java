package mars.nomad.com.m20_commondialog.CommonListDialog.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mars.nomad.com.l0_base.Callback.RecyclerViewClickListener;

import java.util.List;

import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.m20_commondialog.R;


/**
 * Created by SJH on 2017-04-03.
 */

public class AdapterReport extends RecyclerView.Adapter<AdapterReport.AdapterReportViewHolder> {

    private Context mContext;
    private List<String> data;
    private RecyclerViewClickListener<String> mClickListener;


    //생성자
    public AdapterReport(Context mContext, List<String> data) {
        this.mContext = mContext;
        this.data = data;
    }

    public AdapterReport(Context mContext, List<String> data, RecyclerViewClickListener<String> mClickListener) {
        this.mContext = mContext;
        this.data = data;
        this.mClickListener = mClickListener;
    }

    @Override
    public AdapterReportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.p0_cell_common_list_dialog, parent, false);
        return new AdapterReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterReportViewHolder holder, int position) {

        try {

            String item = data.get(position);
            holder.textViewReport.setText(item);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class AdapterReportViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewReport;

        public AdapterReportViewHolder(View itemView) {
            super(itemView);
            this.textViewReport = (TextView) itemView.findViewById(R.id.textViewReport);
            this.textViewReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mClickListener.onItemClick(data.get(getAdapterPosition()));
                }
            });
        }
    }
}
