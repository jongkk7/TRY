package mars.nomad.com.b0_generaltemplate.YJK;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import mars.nomad.com.b0_generaltemplate.R;
import mars.nomad.com.b0_generaltemplate.YJK.Adapter.AdapterYJK;
import mars.nomad.com.b0_generaltemplate.YJK.DataModel.YJKDataModel;
import mars.nomad.com.b0_generaltemplate.YJK.mvvm.YJKViewModel;
import mars.nomad.com.c3_baseaf.BaseActivity;
import mars.nomad.com.l0_base.Callback.CommonCallback;
import mars.nomad.com.l0_base.Callback.RecyclerViewClickListener;
import mars.nomad.com.l0_base.Callback.SingleClickListener;
import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.l11_hardwareutil.KeyBoard.BehaviorUtil;


/**
 * Created by SJH, NomadSoft.Inc, 2019-07-02
 */
public class ActivityYJK extends BaseActivity {

    private ImageButton imageButtonBack;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView textViewNoContents;

    private YJKViewModel mViewModel;

    private AdapterYJK mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setEvent();
        setStatusBarWrapper();
        loadList(true);
    }

    @Override
    protected void initView() {

        try {

            setContentView(R.layout.activity_yjk);

            imageButtonBack = (ImageButton) findViewById(R.id.imageButtonBack);
            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
            textViewNoContents = (TextView) findViewById(R.id.textViewNoContents);

            this.mContext = this;
            this.mViewModel = ViewModelProviders.of(this).get(YJKViewModel.class);

            this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            disableRecyclerViewFlickering(recyclerView);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    @Override
    protected void setEvent() {

        try {

            this.imageButtonBack.setOnClickListener(new SingleClickListener(new CommonCallback.SingleActionCallback() {
                @Override
                public void onAction() {
                    finish();
                }
            }));

            this.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    loadList(true);
                }
            });

            this.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {//리스트의 다음 항목 불러오기
                    super.onScrollStateChanged(recyclerView, newState);

                    if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {

                        loadList(false);
                    }
                }
            });

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    /**
     * 리스트를 불러온다.
     *
     * @param isFirst
     */
    private void loadList(final boolean isFirst) {

        try {

            mViewModel.loadList(isFirst, new CommonCallback.ListCallback<YJKDataModel>() {
                @Override
                public void onSuccess(List<YJKDataModel> result) {

                    try {


                        if (isFirst || mAdapter == null) {

                            mAdapter = new AdapterYJK(mContext, result, new RecyclerViewClickListener<YJKDataModel>() {
                                @Override
                                public void onItemClick(YJKDataModel item) {
                                    //todo process click action.
                                }
                            });

                            recyclerView.setAdapter(mAdapter);

                        } else {

                            mAdapter.addAll(result);
                        }

                        //컨탠츠가 없을 때 보여줄 텍스트 출력
                        textViewNoContents.setVisibility(BehaviorUtil.booleanToVisibility(!(mAdapter != null && mAdapter.getData() != null && mAdapter.getData().size() > 0)));

                        //로딩 종료
                        swipeRefreshLayout.setRefreshing(false);

                    } catch (Exception e) {
                        ErrorController.showError(e);
                    }
                }

                @Override
                public void onFailed(String fault) {
                    showSimpleAlertDialog(fault);
                }
            });

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }
}
