package mars.nomad.com.b0_generaltemplate.YJK.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mars.nomad.com.b0_generaltemplate.R;
import mars.nomad.com.b0_generaltemplate.YJK.DataModel.YJKDataModel;
import mars.nomad.com.c2_customview.Adapter.NsBaseRecyclerViewAdapter;
import mars.nomad.com.l0_base.Callback.RecyclerViewClickListener;
import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by SJH, NomadSoft.Inc, 2019-07-02
 */
public class AdapterYJK extends NsBaseRecyclerViewAdapter<AdapterYJK.AdapterYJKViewHolder, YJKDataModel> {


    public AdapterYJK(Context mContext, List<YJKDataModel> data) {
        super(mContext, data);
    }

    public AdapterYJK(Context mContext, List<YJKDataModel> data, RecyclerViewClickListener<YJKDataModel> mClickListener) {
        super(mContext, data, mClickListener);
    }

    @Override
    public AdapterYJKViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_cell_yjk, parent, false);
        return new AdapterYJKViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterYJKViewHolder holder, int position) {

        YJKDataModel item = data.get(position);

        try {


        } catch (Exception e) {
            ErrorController.showError(e);
        }

    }

    public class AdapterYJKViewHolder extends RecyclerView.ViewHolder {

        public AdapterYJKViewHolder(View view) {
            super(view);
        }
    }
}
