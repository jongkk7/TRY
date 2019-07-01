package mars.nomad.com.B1_post.PostWrite;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
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
import mars.nomad.com.c1_activitymanager.ActivityManagerCommon;
import mars.nomad.com.c2_customview.Adapter.Move.NsGeneralListMoveAdapter;
import mars.nomad.com.c2_customview.Adapter.Move.NsGeneralMoveViewHolder;
import mars.nomad.com.c3_baseaf.BaseActivity;
import mars.nomad.com.c3_baseaf.BaseNsCustomView;
import mars.nomad.com.l0_base.Callback.CommonCallback;
import mars.nomad.com.l0_base.Callback.SingleClickListener;
import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-05-13.
 */
public class LayoutPostWrite extends BaseNsCustomView {


    private RecyclerView recyclerViewPostWrite;
    private LayoutPostWriteViewModel mVmodel;
    private NsGeneralListMoveAdapter<PostDataModel> mAdapter;
    private ImageView imageViewText;
    private ImageView imageViewCamera;
    private ImageView imageViewGallery;
    private ImageView imageViewSetting;

    private BaseActivity activity;

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

            imageViewText = (ImageView) view.findViewById(R.id.imageViewText);
            imageViewCamera = (ImageView) view.findViewById(R.id.imageViewCamera);
            imageViewGallery = (ImageView) view.findViewById(R.id.imageViewGallery);
            imageViewSetting = (ImageView) view.findViewById(R.id.imageViewSetting);

            SimpleItemAnimator itemAnimator = (SimpleItemAnimator) recyclerViewPostWrite.getItemAnimator();
            itemAnimator.setSupportsChangeAnimations(false);

            addView(view);

        } catch (Exception e) {
            ErrorController.showError(e);
        }

    }

    @Override
    protected void setEvent() {
        try {

            imageViewText.setOnClickListener(new SingleClickListener(new CommonCallback.SingleActionCallback() {
                @Override
                public void onAction() {
                    mVmodel.addEditTextCell();
                }
            }));

            imageViewCamera.setOnClickListener(new SingleClickListener(new CommonCallback.SingleActionCallback() {
                @Override
                public void onAction() {
                    mVmodel.takePicture(activity);
                }
            }));

            imageViewGallery.setOnClickListener(new SingleClickListener(new CommonCallback.SingleActionCallback() {
                @Override
                public void onAction() {
                    ActivityManagerCommon.goActivityCommonGallery(activity, "", 1);
                }
            }));


            imageViewSetting.setOnClickListener(new SingleClickListener(new CommonCallback.SingleActionCallback() {
                @Override
                public void onAction() {
                    imageViewSetting.setSelected(!imageViewSetting.isSelected());

                    mVmodel.setSetting(imageViewSetting.isSelected());
                }
            }));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    @Override
    protected void setTypeArray(TypedArray typedArray) {

    }

    public void setAdapter(BaseActivity activity, List<PostDataModel> dataList) {
        try {

            this.activity = activity;

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
                    ErrorController.showMessage("[getJsonData] : " + getJsonData());
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

    public String getJsonData() {
        return mVmodel.getJsonData();
    }

    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {

        try {

            mVmodel.onActivityResult(activity, requestCode, resultCode, data);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }
}
