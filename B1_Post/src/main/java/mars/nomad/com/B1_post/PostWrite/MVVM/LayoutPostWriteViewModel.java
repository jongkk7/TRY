package mars.nomad.com.B1_post.PostWrite.MVVM;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import mars.nomad.com.B1_post.DataModel.PostDataModel;
import mars.nomad.com.B1_post.DataModel.PostTextDataModel;
import mars.nomad.com.c2_customview.RecyclerView.Adapter.Move.NsGeneralListMoveAdapter;
import mars.nomad.com.c2_customview.RecyclerView.Adapter.Move.NsGeneralMoveViewHolder;
import mars.nomad.com.c2_customview.RecyclerView.ClickListener.SimpleItemTouchHelperCallback;
import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-05-13.
 */
public class LayoutPostWriteViewModel {

    private final Gson gson;
    private ItemTouchHelper mItemTouchHelper;

    public LayoutPostWriteViewModel() {

        gson = new Gson();
    }

    private MutableLiveData<List<PostDataModel>> dataListLive = new MutableLiveData<>();

    public MutableLiveData<List<PostDataModel>> getDataListLive() {
        return dataListLive;
    }

    private List<PostDataModel> dataList = new ArrayList<>();

    /**
     * 데이터 리스트 셋팅
     *
     * @param dataList
     */
    public void setPostDataList(List<PostDataModel> dataList) {
        try {
            this.dataList.clear();

            this.dataList.addAll(dataList);

            this.dataListLive.postValue(dataList);
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    /**
     * 삭제
     *
     * @param item
     */
    public void onDelete(PostDataModel item) {
        try {

            for (PostDataModel postDataModel : dataList) {
                if (item.equals(postDataModel)) {
                    dataList.remove(postDataModel);
                    break;
                }
            }

            this.dataListLive.postValue(dataList);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void onItemMove(PostDataModel fromItem, PostDataModel toItem, NsGeneralListMoveAdapter<PostDataModel> mAdapter) {
        try {
            int fromOrder = fromItem.getOrder_num();
            int toOrder = toItem.getOrder_num();
            Collections.swap(dataList, fromOrder, toOrder);


            fromItem.setOrder_num(toOrder);
            toItem.setOrder_num(fromOrder);

            mAdapter.notifyItemMoved(fromItem.getOrder_num(), toItem.getOrder_num());

            dataListLive.postValue(dataList);


        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void onTextChange(PostDataModel item, String text) {
        try {

            PostTextDataModel textData = gson.fromJson(item.getContents(), PostTextDataModel.class);
            textData.setContents(text);

            item.setContents(gson.toJson(textData));

            dataListLive.postValue(dataList);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void onStartDrag(NsGeneralMoveViewHolder<PostDataModel> holder) {
        try {

            if (mItemTouchHelper != null) {
                mItemTouchHelper.startDrag(holder);
            }
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void itemTouchHelper(NsGeneralListMoveAdapter<PostDataModel> mAdapter, RecyclerView recyclerViewPostWrite) {
        try {
            ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter, false);
            mItemTouchHelper = new ItemTouchHelper(callback);
            mItemTouchHelper.attachToRecyclerView(recyclerViewPostWrite);
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }
}
