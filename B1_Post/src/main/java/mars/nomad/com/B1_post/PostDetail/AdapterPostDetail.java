package mars.nomad.com.B1_post.PostDetail;

import android.content.Context;
import android.view.View;

import java.util.List;

import mars.nomad.com.B1_post.CustomView.CustomPostImageView;
import mars.nomad.com.B1_post.CustomView.CustomPostTextView;
import mars.nomad.com.B1_post.CustomView.CustomPostVideoView;
import mars.nomad.com.B1_post.DataModel.PostDataModel;
import mars.nomad.com.B1_post.R;
import mars.nomad.com.c2_customview.RecyclerView.Adapter.NsGeneralClickListener;
import mars.nomad.com.c2_customview.RecyclerView.Adapter.NsGeneralView;
import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-03-25.
 * Post의 어댑터 구조 신설.
 * <p>
 * 이구조에서 새로운 타입이 추가되면 반드시 새로운 커스텀뷰를 생성하고 그걸로 컨트롤하도록한다.
 * 데이터 모델은 항상 고정이어야하며, 만약 달라질 필요가 존재할경우 해당 데이터모델에 집어넣는 방식으로 진행한다.
 * ex) Vote가 데이터모델에 붙어올경우 Vote의 DataModel안에 집어넣는 형식으로 진행.
 */
public class AdapterPostDetail extends NsGeneralView<PostDataModel> {


    private CustomPostTextView customPostTextView;
    private CustomPostImageView customPostImageView;
    private CustomPostVideoView customPostVideoView;

    public AdapterPostDetail(Context context) {
        super(context);
    }

    @Override
    public int initViewId() {
        return R.layout.adapter_post_detail;
    }

    @Override
    public void initView(View view) {

        customPostTextView = (CustomPostTextView) view.findViewById(R.id.customPostTextView);
        customPostImageView = (CustomPostImageView) view.findViewById(R.id.customPostImageView);
        customPostVideoView = (CustomPostVideoView) view.findViewById(R.id.customPostVideoView);
    }

    @Override
    public void setEvent(List<PostDataModel> data, PostDataModel item, NsGeneralClickListener<PostDataModel> mClickListener) {
        try {
            // 여기서 클릭리스너 정의는 없음.

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    @Override
    public void onBindData(List<PostDataModel> data, PostDataModel item) {
        try {

            // 먼저 모든 뷰를 안보이도록 처리
            customPostTextView.setVisibility(GONE);
            customPostImageView.setVisibility(GONE);
            customPostVideoView.setVisibility(GONE);


            switch (item.getType()) {

                case "text":
                    customPostTextView.setVisibility(VISIBLE);
                    customPostTextView.setContents(item.getUrl(), item.getContents(), item.getAccessToken());
                    break;
                case "image":
                    customPostImageView.setVisibility(VISIBLE);
                    customPostImageView.setContents(item.getUrl(), item.getContents(), item.getAccessToken());
                    break;
                case "video":
                    customPostVideoView.setVisibility(VISIBLE);
                    customPostVideoView.setContents(item.getUrl(), item.getContents(), item.getAccessToken());
                    break;
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

}
