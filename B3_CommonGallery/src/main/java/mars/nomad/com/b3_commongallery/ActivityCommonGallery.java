package mars.nomad.com.b3_commongallery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import mars.nomad.com.b3_commongallery.Adapter.AdapterCommonGallery;
import mars.nomad.com.b3_commongallery.Adapter.AdapterCommonGalleryTotal;
import mars.nomad.com.b3_commongallery.Callback.CommonGalleryClickListener;
import mars.nomad.com.b3_commongallery.Callback.CommonGalleryTotalClickListener;
import mars.nomad.com.b3_commongallery.DataModel.CommonGalleryDataModel;
import mars.nomad.com.b3_commongallery.DataModel.CommonGalleryTotalDataModel;
import mars.nomad.com.b3_commongallery.MVVM.CommonGalleryViewModel;
import mars.nomad.com.c2_customview.Adapter.NsGeneralListAdapter;
import mars.nomad.com.c3_baseaf.BaseActivity;
import mars.nomad.com.l0_base.Callback.CommonCallback;
import mars.nomad.com.l0_base.Callback.SingleClickListener;
import mars.nomad.com.l0_base.Logger.ErrorController;

public class ActivityCommonGallery extends BaseActivity {

    public static final String PICTURE = "PICTURE";
    public static final String VIDEO = "VIDEO";
    public static final String SELECT_DATA = "SELECT_DATA";

    private CommonGalleryViewModel mVmodel;
    private NsGeneralListAdapter<CommonGalleryDataModel> mGalleryAdapter;
    private NsGeneralListAdapter<CommonGalleryTotalDataModel> mGalleryTotalAdapter;
    private LinearLayout activityPictureAlbum;
    private ImageButton imageButtonBack;
    private LinearLayout linearLayoutTouch;
    private TextView textViewTitle;
    private LinearLayout linearLayoutSend;
    private TextView textViewSendCount;
    private TextView textViewSend;
    private RecyclerView recyclerViewAlbum;
    private RecyclerView recyclerViewTotalAlbum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLightStatusBar(getWindow().getDecorView(), this);
        setStatusBarColor(R.color.colorWhite);
        initView();
        setEvent();
        getData();
        initGalleryAdapter();
        initGalleryTotalAdapter();
        loadList();
    }


    @Override
    protected void initView() {

        try {

            this.mContext = this;

            this.mVmodel = ViewModelProviders.of(this).get(CommonGalleryViewModel.class);
            setContentView(R.layout.activity_common_gallery);

            activityPictureAlbum = (LinearLayout) findViewById(R.id.activity_picture_album);
            imageButtonBack = (ImageButton) findViewById(R.id.imageButtonBack);
            linearLayoutTouch = (LinearLayout) findViewById(R.id.linearLayoutTouch);
            textViewTitle = (TextView) findViewById(R.id.textViewTitle);
            linearLayoutSend = (LinearLayout) findViewById(R.id.linearLayoutSend);
            textViewSendCount = (TextView) findViewById(R.id.textViewSendCount);
            textViewSend = (TextView) findViewById(R.id.textViewSend);
            recyclerViewAlbum = (RecyclerView) findViewById(R.id.recyclerViewAlbum);
            recyclerViewTotalAlbum = (RecyclerView) findViewById(R.id.recyclerViewTotalAlbum);

            recyclerViewAlbum.setItemAnimator(null);
            recyclerViewTotalAlbum.setItemAnimator(null);

        } catch (Exception e) {
            ErrorController.showError(e);
        }


    }

    @Override
    protected void setEvent() {

        try {

            this.imageButtonBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });

            this.linearLayoutSend.setOnClickListener(new SingleClickListener(new CommonCallback.SingleActionCallback() {
                @Override
                public void onAction() {

                    Intent intent = new Intent();
                    intent.putExtra(SELECT_DATA, (Serializable) mVmodel.getSelectedItem());
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    private void getData() {
        try {

            mVmodel.getData(getIntent());

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    private void initGalleryAdapter() {
        try {

            mGalleryAdapter = new NsGeneralListAdapter<CommonGalleryDataModel>(getActivity(), new AdapterCommonGallery(getActivity()), new ArrayList<CommonGalleryDataModel>(), new CommonGalleryClickListener() {
                @Override
                public void onClick(CommonGalleryDataModel item) {
                    mVmodel.setSelectedItem(item, new CommonCallback.SingleObjectActionCallback<Integer>() {
                        @Override
                        public void onAction(Integer data) {

                            if (data > 0) {
                                textViewSendCount.setVisibility(View.VISIBLE);
                                textViewSendCount.setText(data + "");
                            } else {
                                textViewSendCount.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }, new DiffUtil.ItemCallback<CommonGalleryDataModel>() {
                @Override
                public boolean areItemsTheSame(@NonNull CommonGalleryDataModel left, @NonNull CommonGalleryDataModel right) {
                    return left.getFullPath().equalsIgnoreCase(right.getFullPath());
                }

                @Override
                public boolean areContentsTheSame(@NonNull CommonGalleryDataModel left, @NonNull CommonGalleryDataModel right) {
                    return left.equals(right);
                }
            });

            mVmodel.getGalleryListLive().observe(this, new Observer<List<CommonGalleryDataModel>>() {
                @Override
                public void onChanged(List<CommonGalleryDataModel> galleryDataModelList) {
                    mGalleryAdapter.submitList(galleryDataModelList);
                }
            });

            recyclerViewAlbum.setAdapter(mGalleryAdapter);


        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    private void initGalleryTotalAdapter() {
        try {

            mGalleryTotalAdapter = new NsGeneralListAdapter<CommonGalleryTotalDataModel>(getActivity(), new AdapterCommonGalleryTotal(getActivity()), new ArrayList<CommonGalleryTotalDataModel>(), new CommonGalleryTotalClickListener() {
                @Override
                public void onClick(CommonGalleryTotalDataModel item) {
                    textViewTitle.setText(item.getDirectoryName());
                    mVmodel.loadDirectoryFileList(getActivity(), item.getDirectoryPath());

                    recyclerViewTotalAlbum.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recyclerViewTotalAlbum.setVisibility(View.GONE);
                            recyclerViewAlbum.setVisibility(View.VISIBLE);
                        }
                    }, 100);
                }
            }, new DiffUtil.ItemCallback<CommonGalleryTotalDataModel>() {
                @Override
                public boolean areItemsTheSame(@NonNull CommonGalleryTotalDataModel left, @NonNull CommonGalleryTotalDataModel right) {
                    return left.getDirectoryPath().equalsIgnoreCase(right.getDirectoryPath());
                }

                @Override
                public boolean areContentsTheSame(@NonNull CommonGalleryTotalDataModel left, @NonNull CommonGalleryTotalDataModel right) {
                    return left.equals(right);
                }
            });


            mVmodel.getGalleryTotalListLive().observe(this, new Observer<List<CommonGalleryTotalDataModel>>() {
                @Override
                public void onChanged(List<CommonGalleryTotalDataModel> commonGalleryTotalDataModelList) {
                    mGalleryTotalAdapter.submitList(commonGalleryTotalDataModelList);
                }
            });

            recyclerViewTotalAlbum.setAdapter(mGalleryTotalAdapter);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    @Override
    public void onBackPressed() {


        try {

            if (recyclerViewAlbum.getVisibility() == View.VISIBLE) {
                recyclerViewAlbum.setVisibility(View.GONE);
                recyclerViewTotalAlbum.setVisibility(View.VISIBLE);
                textViewTitle.setText("갤러리");
            } else {
                super.onBackPressed();
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }


    }

    private void loadList() {
        try {

            mVmodel.loadList(getActivity());

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


}