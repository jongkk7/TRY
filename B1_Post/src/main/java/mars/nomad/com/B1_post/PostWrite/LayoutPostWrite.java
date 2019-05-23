package mars.nomad.com.B1_post.PostWrite;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import mars.nomad.com.B1_post.DataModel.PostDataModel;
import mars.nomad.com.B1_post.PostWrite.MVVM.LayoutPostWriteViewModel;
import mars.nomad.com.B1_post.R;
import mars.nomad.com.c2_customview.Adapter.Move.NsGeneralListMoveAdapter;
import mars.nomad.com.c2_customview.Adapter.Move.NsGeneralMoveViewHolder;
import mars.nomad.com.c3_baseaf.BaseActivity;
import mars.nomad.com.c3_baseaf.BaseNsCustomView;
import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-05-13.
 */
public class LayoutPostWrite extends BaseNsCustomView {
    private RecyclerView recyclerViewPostWrite;
    private LayoutPostWriteViewModel mVmodel;
    private NsGeneralListMoveAdapter<PostDataModel> mAdapter;

    public LayoutPostWrite(Context context) {
        super(context);
        initView();
        setEvent();
    }

    public LayoutPostWrite(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
        setEvent();
    }

    public LayoutPostWrite(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        setEvent();
    }

    @Override
    protected void initView() {
        try {

            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_post_write_view, this, false);


            mVmodel = new LayoutPostWriteViewModel();

            recyclerViewPostWrite = (RecyclerView) view.findViewById(R.id.recyclerViewPostWrite);

            SimpleItemAnimator itemAnimator = (SimpleItemAnimator) recyclerViewPostWrite.getItemAnimator();
            itemAnimator.setSupportsChangeAnimations(false);

            addView(view);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    @Override
    protected void setEvent() {
        // do nothing
    }

    @Override
    protected void setTypeArray(TypedArray typedArray) {

    }

    public void setAdapter(BaseActivity activity, List<PostDataModel> dataList) {
        try {

            mAdapter = new NsGeneralListMoveAdapter<PostDataModel>(getContext(), new AdapterPostWrite(getContext()), new ArrayList<PostDataModel>(), new PostWriteClickListener() {
                @Override
                public void onItemDismiss(PostDataModel item) {
                    mVmodel.onDelete(item);
                }

                @Override
                public void onItemMove(PostDataModel fromItem, PostDataModel toItem) {
                    mVmodel.onItemMove(fromItem, toItem, mAdapter);
                }

                @Override
                public void onTextChange(PostDataModel item, String text) {
                    mVmodel.onTextChange(item, text);
                }

                @Override
                public void onDelete(PostDataModel item) {
                    mVmodel.onDelete(item);
                }

                @Override
                public void onStartDrag(NsGeneralMoveViewHolder<PostDataModel> holder) {
                    mVmodel.onStartDrag(holder);
                }
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

            mVmodel.getDataListLive().observe(activity, new Observer<List<PostDataModel>>() {
                @Override
                public void onChanged(List<PostDataModel> postDataList) {
                    if (postDataList != null) {
                        mAdapter.submitList(postDataList);
                    }
                }
            });


            mVmodel.setPostDataList(dataList);


            if (recyclerViewPostWrite != null) {
                recyclerViewPostWrite.setAdapter(mAdapter);

                mVmodel.itemTouchHelper(mAdapter, recyclerViewPostWrite);
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerViewPostWrite.setAdapter(mAdapter);

                        mVmodel.itemTouchHelper(mAdapter, recyclerViewPostWrite);
                    }
                }, 500);
            }


        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }
}
