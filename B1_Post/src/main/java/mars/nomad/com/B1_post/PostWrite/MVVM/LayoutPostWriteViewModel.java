package mars.nomad.com.B1_post.PostWrite.MVVM;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;

import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import mars.nomad.com.B1_post.DataModel.PostDataModel;
import mars.nomad.com.B1_post.DataModel.PostImageDataModel;
import mars.nomad.com.B1_post.DataModel.PostTextDataModel;
import mars.nomad.com.B1_post.Util.PostConstants;
import mars.nomad.com.b3_commongallery.ActivityCommonGallery;
import mars.nomad.com.b3_commongallery.DataModel.CommonGalleryDataModel;
import mars.nomad.com.c1_activitymanager.ActivityManagerCommon;
import mars.nomad.com.c2_customview.Adapter.Move.NsGeneralListMoveAdapter;
import mars.nomad.com.c2_customview.Adapter.Move.NsGeneralMoveViewHolder;
import mars.nomad.com.c2_customview.RecyclerView.ClickListener.SimpleItemTouchHelperCallback;
import mars.nomad.com.c3_baseaf.BaseActivity;
import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.l12_applicationutil.File.FileUtil;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-05-13.
 */
public class LayoutPostWriteViewModel {

    private final Gson gson;
    private ItemTouchHelper mItemTouchHelper;
    public static final int CAMERA_REQUEST = 9093;
    private File cameraFile;

    public LayoutPostWriteViewModel() {

        gson = new Gson();
    }

    private MutableLiveData<List<PostDataModel>> dataListLive = new MutableLiveData<>();

    public MutableLiveData<List<PostDataModel>> getDataListLive() {
        return dataListLive;
    }

    private List<PostDataModel> dataList = new ArrayList<>();

    public List<PostDataModel> getDataList() {
        return dataList;
    }

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
            int fromOrder = dataList.indexOf(fromItem);
            int toOrder = dataList.indexOf(toItem);
            Collections.swap(dataList, fromOrder, toOrder);


            fromItem.setSort_num(toOrder);
            toItem.setSort_num(fromOrder);

            mAdapter.notifyItemMoved(fromOrder, toOrder);


        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void onTextChange(PostDataModel item, String text) {
        try {

            if (PostConstants.isOld) {
                item.setContents(text);
            } else {
                PostTextDataModel textData = gson.fromJson(item.getContents(), PostTextDataModel.class);
                textData.setContents(text);

                item.setContents(gson.toJson(textData));
            }
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

    public void addEditTextCell() {

        try {
            if (PostConstants.isOld) {
                dataList.add(new PostDataModel("txt", "", getSortNum()));
            } else {
                dataList.add(new PostDataModel("text", gson.toJson(new PostTextDataModel()), getSortNum()));
            }
            dataListLive.postValue(dataList);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void takePicture(BaseActivity activity) {
        try {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());

            cameraFile = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
            cameraFile.createNewFile();

            takePictureIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile));

            activity.startActivityForResult(takePictureIntent, CAMERA_REQUEST);
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        try {

            if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {


                final String path = FileUtil.getPath(activity, Uri.fromFile(cameraFile));

                ErrorController.showMessage(path);

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(cameraFile.getAbsolutePath(), options);

                int imageHeight = options.outHeight;
                int imageWidth = options.outWidth;

                dataList.add(new PostDataModel("image", gson.toJson(new PostImageDataModel(path, imageWidth, imageHeight, path, imageWidth, imageHeight, true)), getSortNum()));

                dataListLive.postValue(dataList);
            } else if (requestCode == ActivityManagerCommon.CommonGalleryRequest && resultCode == Activity.RESULT_OK) {
                List<CommonGalleryDataModel> setting = (List<CommonGalleryDataModel>) data.getSerializableExtra(ActivityCommonGallery.SELECT_DATA);

                if (setting != null && setting.size() > 0) {


                    for (CommonGalleryDataModel commonGalleryDataModel : setting) {

                        File file = new File(commonGalleryDataModel.getFullPath());

                        final String path = FileUtil.getPath(activity, Uri.fromFile(file));

                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = true;
                        BitmapFactory.decodeFile(file.getAbsolutePath(), options);

                        int imageHeight = options.outHeight;
                        int imageWidth = options.outWidth;

                        dataList.add(new PostDataModel("image", gson.toJson(new PostImageDataModel(path, imageWidth, imageHeight, path, imageWidth, imageHeight, true)), getSortNum()));

                    }
                    dataListLive.postValue(dataList);

                }

            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public int getSortNum() {
        int sortNum = 0;

        if (dataList.size() > 0) {
            sortNum = dataList.get(dataList.size() - 1).getSort_num() + 1;
        }
        return sortNum;
    }

    public void setSetting(boolean selected) {
        try {


            for (PostDataModel postDataModel : dataList) {
                postDataModel.setEditOption(selected);
            }

            dataListLive.postValue(dataList);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public String getJsonData() {

        return gson.toJson(dataList);
    }
}
