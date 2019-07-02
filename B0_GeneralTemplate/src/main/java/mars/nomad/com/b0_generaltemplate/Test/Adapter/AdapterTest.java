package mars.nomad.com.b0_generaltemplate.Test.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


import mars.nomad.com.b0_generaltemplate.R;
import mars.nomad.com.b0_generaltemplate.Test.DataModel.TestDataModel;
import mars.nomad.com.c2_customview.Adapter.NsBaseRecyclerViewAdapter;
import mars.nomad.com.l0_base.Callback.RecyclerViewClickListener;
import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by SJH, NomadSoft.Inc, 2019-07-02
 */
public class AdapterTest extends NsBaseRecyclerViewAdapter<AdapterTest.AdapterTestViewHolder, TestDataModel> {


    public AdapterTest(Context mContext, List<TestDataModel> data) {
        super(mContext, data);
    }

    public AdapterTest(Context mContext, List<TestDataModel> data, RecyclerViewClickListener<TestDataModel> mClickListener) {
        super(mContext, data, mClickListener);
    }

    @Override
    public AdapterTestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_cell_test, parent, false);
        return new AdapterTestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterTestViewHolder holder, int position) {

        TestDataModel item = data.get(position);

        try {


        } catch (Exception e) {
            ErrorController.showError(e);
        }

    }

    public class AdapterTestViewHolder extends RecyclerView.ViewHolder {

        public AdapterTestViewHolder(View view) {
            super(view);
        }
    }
}
