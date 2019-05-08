package mars.nomad.com.B1_post.PostDetail;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import mars.nomad.com.B1_post.DataModel.PostDataModel;
import mars.nomad.com.B1_post.R;
import mars.nomad.com.c2_customview.RecyclerView.Adapter.NsGeneralListAdapter;
import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-03-26.
 */
public class CustomPostDetailView extends RelativeLayout {
    private RecyclerView recyclerViewPostDetail;
    private NsGeneralListAdapter<PostDataModel> mAdapter;

    public CustomPostDetailView(Context context) {
        super(context);
        initView();
        setAdapter();
    }

    public CustomPostDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        setAdapter();
    }


    protected void initView() {
        try {

            View view = LayoutInflater.from(getContext()).inflate(R.layout.custom_post_detail_view, this, false);

            this.recyclerViewPostDetail = (RecyclerView) view.findViewById(R.id.recyclerViewPostDetail);

            addView(view);


        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    private void setAdapter() {

        try {

            mAdapter = new NsGeneralListAdapter<PostDataModel>(getContext(), new AdapterPostDetail(getContext()), new ArrayList<PostDataModel>(), new PostDetailClickListener() {

            }, new DiffUtil.ItemCallback<PostDataModel>() {
                @Override
                public boolean areItemsTheSame(@NonNull PostDataModel left, @NonNull PostDataModel right) {
                    return left.getType().equalsIgnoreCase(right.getType());
                }

                @Override
                public boolean areContentsTheSame(@NonNull PostDataModel left, @NonNull PostDataModel right) {
                    return left.equals(right);
                }
            });

            recyclerViewPostDetail.setAdapter(mAdapter);
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void setContents
            (final List<PostDataModel> list, String dfsUrl, String accessToken) {
        try {

            // 각각 dfsUrl과 accessToken을 넣는다
            for (PostDataModel postDataModel : list) {
                postDataModel.setUrl(dfsUrl);
                postDataModel.setAccessToken(accessToken);
            }

            if (mAdapter != null) { // 있으면 바로 셋팅
                mAdapter.submitList(list);
            } else { // 어댑터가 없으면 조금 이따가 셋팅함. (생명주기상 꼬일수있어서..)
                this.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.submitList(list);
                    }
                }, 500);
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

}
