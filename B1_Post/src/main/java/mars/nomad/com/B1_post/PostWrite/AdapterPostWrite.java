package mars.nomad.com.B1_post.PostWrite;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import androidx.core.view.MotionEventCompat;
import mars.nomad.com.B1_post.CustomView.CustomPostEditView;
import mars.nomad.com.B1_post.CustomView.CustomPostImageView;
import mars.nomad.com.B1_post.CustomView.CustomPostVideoView;
import mars.nomad.com.B1_post.DataModel.PostDataModel;
import mars.nomad.com.B1_post.R;
import mars.nomad.com.c2_customview.Adapter.Move.NsGeneralMoveClickListener;
import mars.nomad.com.c2_customview.Adapter.Move.NsGeneralMoveView;
import mars.nomad.com.c2_customview.Adapter.Move.NsGeneralMoveViewHolder;
import mars.nomad.com.l0_base.Abstract.AbstractTextWatcher;
import mars.nomad.com.l0_base.Callback.NsPredicateAction;
import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-05-08.
 */
public class AdapterPostWrite extends NsGeneralMoveView<PostDataModel> {

    private CustomPostImageView customPostImageView;
    private CustomPostVideoView customPostVideoView;
    private CustomPostEditView customPostEditView;
    private LinearLayout linearLayoutControl;
    private ImageView imageViewDelete;
    private ImageView imageViewEdit;

    public AdapterPostWrite(Context context) {
        super(context);
    }

    @Override
    public int initViewId() {
        return R.layout.adapter_post_write;
    }

    @Override
    public void initView(View view) {

        customPostImageView = (CustomPostImageView) view.findViewById(R.id.customPostImageView);
        customPostVideoView = (CustomPostVideoView) view.findViewById(R.id.customPostVideoView);
        customPostEditView = (CustomPostEditView) view.findViewById(R.id.customPostEditView);
        linearLayoutControl = (LinearLayout) view.findViewById(R.id.linearLayoutControl);
        imageViewDelete = (ImageView) view.findViewById(R.id.imageViewDelete);
        imageViewEdit = (ImageView) view.findViewById(R.id.imageViewEdit);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void setEvent(List<PostDataModel> data, final NsGeneralMoveViewHolder<PostDataModel> holder, final PostDataModel item, NsGeneralMoveClickListener<PostDataModel> mClickListener) {
        final PostWriteClickListener clickListener = (PostWriteClickListener) mClickListener;

        try {

            imageViewDelete.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onDelete(item);
                }
            });

            imageViewEdit.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                        clickListener.onStartDrag(holder);
                    }
                    return false;
                }
            });


            customPostEditView.addTextChange(new AbstractTextWatcher() {
                @Override
                public void onTextChanged(CharSequence text, int start, int before, int count) {
                    clickListener.onTextChange(item, text.toString());
                }
            });
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    @Override
    public void onBindData(List<PostDataModel> data, final PostDataModel item) {
        try {

            // 먼저 모든 뷰를 안보이도록 처리
            customPostEditView.setVisibility(GONE);
            customPostImageView.setVisibility(GONE);
            customPostVideoView.setVisibility(GONE);

            // 타입에 따라 나누기
            switch (item.getType()) {

                case "text":
                    customPostEditView.setVisibility(VISIBLE);
                    customPostEditView.setContents(item.getUrl(), item.getContents(), item.getAccessToken());
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

            // edit 가능 여부
            setVisibility(linearLayoutControl, new NsPredicateAction() {
                @Override
                public boolean apply() {
                    return item.getEditOption() == 1;
                }
            });


        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }
}
