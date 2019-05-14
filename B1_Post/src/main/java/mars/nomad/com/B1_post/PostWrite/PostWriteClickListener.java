package mars.nomad.com.B1_post.PostWrite;

import mars.nomad.com.B1_post.DataModel.PostDataModel;
import mars.nomad.com.c2_customview.RecyclerView.Adapter.Move.NsGeneralMoveClickListener;
import mars.nomad.com.c2_customview.RecyclerView.Adapter.Move.NsGeneralMoveViewHolder;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-05-13.
 */
public interface PostWriteClickListener extends NsGeneralMoveClickListener<PostDataModel> {

    void onTextChange(PostDataModel item, String text);

    void onDelete(PostDataModel item);

    void onStartDrag(NsGeneralMoveViewHolder<PostDataModel> holder);
}
