package mars.nomad.com.B1_post.PostWrite;

import android.content.Context;

import java.util.List;

import mars.nomad.com.B1_post.CustomView.CustomPostEditView;
import mars.nomad.com.B1_post.CustomView.CustomPostImageView;
import mars.nomad.com.B1_post.CustomView.CustomPostVideoView;
import mars.nomad.com.B1_post.DataModel.PostDataModel;
import mars.nomad.com.B1_post.R;
import mars.nomad.com.c2_customview.RecyclerView.Adapter.NsGeneralClickListener;
import mars.nomad.com.c2_customview.RecyclerView.Adapter.NsGeneralView;
import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-05-08.
 */
public class AdapterPostWrite extends NsGeneralView<PostDataModel> {

    private CustomPostEditView customPostTextView;
    private CustomPostImageView customPostImageView;
    private CustomPostVideoView customPostVideoView;

    public AdapterPostWrite(Context context) {
        super(context);
    }

    @Override
    public int initViewId() {
        return R.layout.adapter_post_write;
    }

    @Override
    public void initView() {

        customPostTextView = (CustomPostEditView) findViewById(R.id.customPostTextView);
        customPostImageView = (CustomPostImageView) findViewById(R.id.customPostImageView);
        customPostVideoView = (CustomPostVideoView) findViewById(R.id.customPostVideoView);
    }

    @Override
    public void setEvent(List<PostDataModel> data, PostDataModel item, NsGeneralClickListener<PostDataModel> mClickListener) {
        try {

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    @Override
    public void onBindData(List<PostDataModel> data, PostDataModel item) {
        try {

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }
}
