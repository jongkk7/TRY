package mars.nomad.com.b3_commongallery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mars.nomad.com.c3_baseaf.BaseActivity;
import mars.nomad.com.l12_applicationutil.Animation.CommonAnimation;
import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.b3_commongallery.Adapter.PictureAlbumAdapter;
import mars.nomad.com.b3_commongallery.Adapter.PictureTotalAlbumAdapter;
import mars.nomad.com.b3_commongallery.Callback.DoubleSelectionClickListener;
import mars.nomad.com.b3_commongallery.DataModel.AlbumDataModel;


public class ActivityCommonGallery extends BaseActivity {

    /**
     * 사진 선택 후 전송 시의 액션을 정의한다.<br>
     * edit : 편집 화면으로 이동
     * return : 이전 화면에 선택된 리스트를 반환함.
     */
    private String mAction = null;

    /**
     * 선택 가능한 최대수를 정의한다.
     */
    private int mMaxSelection = 0;


    private Context mContext;
    private ImageButton imageButtonBack;
    private TextView textViewTitle;
    private ImageView imageViewTouch;
    private LinearLayout linearLayoutTouch;
    private TextView textViewSendCount;
    private LinearLayout linearLayoutSend;
    private RecyclerView recyclerViewAlbum;
    private RecyclerView recyclerViewTotalAlbum;
    private LinearLayout activitypicturealbum;


    /**
     * 실제 사진을 고르는 어댑터
     */
    private PictureAlbumAdapter mMainScreenAdapter;

    /**
     * 사진이 모여있는 폴더를 보여주는 어댑터
     */
    private PictureTotalAlbumAdapter mFolderAdapter;
    private TextView textViewSend;
    private Uri imageCaptureUri;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_gallery);
        initView();
        setEvent();
        setLightStatusBar(getWindow().getDecorView(), this);
        setStatusBarColor(R.color.colorWhite);
        getData();
        initList();
    }


    @Override
    protected void initView() {

        this.mContext = this;
        this.gson = new Gson();


        this.activitypicturealbum = (LinearLayout) findViewById(R.id.activity_picture_album);
        this.recyclerViewTotalAlbum = (RecyclerView) findViewById(R.id.recyclerViewTotalAlbum);
        this.recyclerViewAlbum = (RecyclerView) findViewById(R.id.recyclerViewAlbum);
        this.linearLayoutSend = (LinearLayout) findViewById(R.id.linearLayoutSend);
        this.textViewSend = (TextView) findViewById(R.id.textViewSend);
        this.textViewSendCount = (TextView) findViewById(R.id.textViewSendCount);
        this.linearLayoutTouch = (LinearLayout) findViewById(R.id.linearLayoutTouch);
        this.imageViewTouch = (ImageView) findViewById(R.id.imageViewTouch);
        this.textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        this.imageButtonBack = (ImageButton) findViewById(R.id.imageButtonBack);

        this.recyclerViewTotalAlbum.setLayoutManager(new LinearLayoutManager(mContext));
        this.recyclerViewAlbum.setLayoutManager(new GridLayoutManager(mContext, 3));

    }

    @Override
    protected void setEvent() {

        this.imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        this.linearLayoutTouch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//상단 카메라롤 클릭

                linearLayoutTouch.setSelected(!linearLayoutTouch.isSelected());

                if (linearLayoutTouch.isSelected()) {
                    CommonAnimation.rotate(imageViewTouch, true);
                    CommonAnimation.expandAlbum(recyclerViewTotalAlbum);
                } else {
                    CommonAnimation.rotate(imageViewTouch, false);
                    CommonAnimation.collapse(recyclerViewTotalAlbum);
                }
            }
        });

        this.linearLayoutSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//전송
                try {
                    List<AlbumDataModel> selection = mMainScreenAdapter.getSelectedData();

                    if (selection.size() == 0) {
                        ErrorController.showInfoToast(mContext, "사진을 선택해주세요.", 1);
                        return;
                    }

                         //리스트에 데이터가 있으면 넣어서 종료한다.
                        Intent intent = new Intent();
                        intent.putExtra("ALBUMS", (Serializable) selection);
                        setResult(RESULT_OK, intent);
                        finish();

//                    if (Constants.P0_ALBUM_ACTION_RETURN.equals(mAction)) {//파일 전송의 경우 선택 후 그냥 선택된 값들을 돌려준다.
//                        //리스트에 데이터가 있으면 넣어서 종료한다.
//                        Intent intent = new Intent();
//                        intent.putExtra(Constants.P0_RESULT_ALBUM_DATA, (Serializable) selection);
//                        setResult(RESULT_OK, intent);
//                        finish();
//
//                    } else if (Constants.P50_ENTERPARK_JOIN_PROFILE.equals(mAction)) {        // 엔터파크 전용 로직
//
//
//                        ActivityManager.goActivityEditPicture(getActivity(), mAction, selection.get(0).getFullPath(), 9090);
///*                        Intent intent = new Intent();
//                        intent.putExtra(Constants.P0_RESULT_ALBUM_DATA, (Serializable) selection);
//                        setResult(RESULT_OK, intent);
//                        finish();*/
//
//                    } else {//프로필 사진, 백그라운드 사진의 등록의 경우 편집 화면으로 이동한다.
//
//                        // ActivityManager.goActivityEditPicture((Activity) mContext, selection.get(0).getFullPath(), mAction);
//
//                        finish();
//                    }


                } catch (Exception e) {
                    ErrorController.showError(e);
                }
            }
        });
    }

    /**
     * action과 maxSelection을 받는다.
     */
    public void getData() {

        try {

            this.mAction = getIntent().getStringExtra("ACTION");

            this.mMaxSelection = getIntent().getIntExtra("MAX_CNT", 0);

            if (mAction == null) {

                ErrorController.showInfoToast(mContext, "Failed to init album.", 2);
                finish();

            } else { //엑션이 재대로 들어옴 -> 액션에 따라 전송 버튼의 txt를 적절히 세팅해준다.

                if ("SEND".equals(mAction)) {

                    textViewSend.setText("전송");

                } else {

                    textViewSend.setText("선택");

                }

            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    /**
     * 리스트를 초기화한다.
     */
    private void initList() {
        //폴더 리스트를 초기화한다.
        mFolderAdapter = new PictureTotalAlbumAdapter(getTitles(this), mContext, new DoubleSelectionClickListener<String, String>() {
            @Override
            public void onClickItem(String fullPath, String name) {
                try {

                    linearLayoutTouch.performClick();
                    textViewTitle.setText(name);
                    recyclerViewAlbum.setLayoutManager(new GridLayoutManager(ActivityCommonGallery.this, 3));

                    if (!TextUtils.isEmpty(fullPath)) {

                        mMainScreenAdapter = new PictureAlbumAdapter(getDetailData(fullPath, ActivityCommonGallery.this, mAction), mContext, mAction, new PictureClicker());

                        recyclerViewAlbum.setAdapter(mMainScreenAdapter);
                    } else {

                        mMainScreenAdapter = new PictureAlbumAdapter(getDetailData(ActivityCommonGallery.this, mAction), mContext, mAction, new PictureClicker());

                        recyclerViewAlbum.setAdapter(mMainScreenAdapter);
                    }
                } catch (Exception e) {
                    ErrorController.showError(e);
                }
            }
        });
        recyclerViewTotalAlbum.setAdapter(mFolderAdapter);

        //초기 사진 리스트(전체)를 초기화한다.
        mMainScreenAdapter = new PictureAlbumAdapter(getDetailData(this, ""), mContext, mAction, new PictureClicker());
        recyclerViewAlbum.setAdapter(mMainScreenAdapter);

    }//end of initList.


    // 카메라 촬영 이벤트
    public void goCameraIntent(Activity context) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        setImageCaptureUri(Uri.fromFile(new File(context.getExternalCacheDir(), System.currentTimeMillis() + ".jpg")));
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, getImageCaptureUri());
        context.startActivityForResult(takePictureIntent, 9093);
    }

    // URI 세팅
    public void setImageCaptureUri(Uri imageCaptureUri) {
        this.imageCaptureUri = imageCaptureUri;
    }

    // Uri 받아오기
    public Uri getImageCaptureUri() {
        return imageCaptureUri;
    }

    /**
     * 사진 선택 콜백
     */
    private class PictureClicker implements DoubleSelectionClickListener<Integer, AlbumDataModel> {

        @Override
        public void onClickItem(Integer position, AlbumDataModel albumDataModel) {

            try {
                if (albumDataModel == null) {       // 첫번째 셀 선택시 데이터모델을 null로 받아옴 -> 카메라 촬영
                    goCameraIntent((Activity) mContext);
                } else if (albumDataModel.isSelected()) {//이미 선택됨 -> 해제한다.

                    albumDataModel.setSelected(false);
                    mMainScreenAdapter.notifyItemChanged(position);

                } else {//선택되어 있지 않음 -> 선택한다.

                    if (mMainScreenAdapter.getSelectedData().size() < mMaxSelection) {//선택 최대 수 보다 낮다.

                        albumDataModel.setSelected(true);
                        mMainScreenAdapter.notifyItemChanged(position);

                    } else {//선택 불가.
                        ErrorController.showInfoToast(mContext, "더 이상 선택할 수 없습니다.", 1);
                    }
                }


                //선택 숫자 업데이트
                try {

                    int count = mMainScreenAdapter.getSelectedData().size();

                    if (count > 0) {
                        textViewSendCount.setText(count + "");
                        textViewSendCount.setVisibility(View.VISIBLE);
                    } else {

                        textViewSendCount.setText("0");
                        textViewSendCount.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    ErrorController.showError(e);
                }


            } catch (Exception e) {
                ErrorController.showError(e);
            }
        }//end on onClickItem.
    }//end of PictureClicker


    public List<AlbumDataModel> getTitles(Activity context) {

        Map<String, String> map = new HashMap<String, String>();

        List<AlbumDataModel> data = new ArrayList<AlbumDataModel>();
        final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
        final String orderBy = MediaStore.Images.Media._ID;

        Cursor imageCursor = context.managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);

        if (imageCursor != null && imageCursor.getCount() > 0) {

            String currentDirectory = " ";

            while (imageCursor.moveToNext()) {

                int dataColumnIndex = imageCursor.getColumnIndex(MediaStore.Images.Media.DATA);

                String fullPath = imageCursor.getString(dataColumnIndex);

                // Drop empty files (Files with only names and no contents).
                if (new File(fullPath).length() != 0) {
                    String temp = getDirectoryFullName(fullPath);
                    if (!currentDirectory.equals(temp)) {
                        currentDirectory = temp;
                        try {
                            map.put(currentDirectory, fullPath);
                        } catch (Exception e) {
                        }
                    }
                }// end of if
            }// end of while

            for (Map.Entry<String, String> e : map.entrySet()) {
                String key = e.getKey();
                String value = e.getValue();

                int childCnt = 0;

                try {

                    File dir = new File(key);

                    File[] childList = dir.listFiles();

                    if (childList != null) {
                        childCnt = childList.length;
                    }

                } catch (Exception ee) {
                    ErrorController.showError(ee);
                }

                data.add(new AlbumDataModel(value, getDirectoryName(value), key, childCnt));
            }// end of for
        }//end of if

        for (AlbumDataModel albumDataModel : data) {
            ErrorController.showMessage(albumDataModel.toString());

        }

        List<AlbumDataModel> result = new ArrayList<>();


        for (AlbumDataModel galleryDataModel : data) {

            if (galleryDataModel.getDirectoryName().contains("Camera") || galleryDataModel.getDirectoryName().contains("카메라")) {
                if (result.size() != 0) {
                    result.add(0, galleryDataModel);
                } else {
                    result.add(galleryDataModel);
                }
                continue;
            }

            if (galleryDataModel.getDirectoryName().contains("Screenshot") || galleryDataModel.getDirectoryName().contains("스크린샷")) {
                if (result.size() > 1) {
                    if (result.size() > 2) {
                        if (result.get(1).getDirectoryName().contains("Camera") || result.get(1).getDirectoryName().contains("카메라")) {
                            result.add(2, galleryDataModel);
                        } else {
                            result.add(1, galleryDataModel);
                        }
                    } else {
                        result.add(1, galleryDataModel);
                    }

                } else {
                    //  ErrorController.showMessage("[GalleryPresenter] Screenshot else: " + galleryDataModel.getDirectoryName());
                    result.add(galleryDataModel);
                }
                continue;
            }

            if (galleryDataModel.getDirectoryName().contains("KakaoTalk") || galleryDataModel.getDirectoryName().contains("카카오톡")) {
                if (result.size() > 2) {
                    // ErrorController.showMessage("[GalleryPresenter] Kakaotalk if: " + galleryDataModel.getDirectoryName());
                    result.add(2, galleryDataModel);
                } else {
                    //  ErrorController.showMessage("[GalleryPresenter] Kakaotalk else: " + galleryDataModel.getDirectoryName());
                    result.add(galleryDataModel);
                }
                continue;
            }
        }

        List<Integer> sortList = new ArrayList<>();
        for (AlbumDataModel galleryDataModel : data) {
            if (!result.contains(galleryDataModel)) {
                result.add(galleryDataModel);
            }
        }
        result.add(0, new AlbumDataModel("", "전체보기", "", -1));

        return result;
    }

    private List<AlbumDataModel> getDetailData(Activity context, String type) {

        List<AlbumDataModel> tempList = new ArrayList<AlbumDataModel>();


        final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
        final String orderBy = MediaStore.Images.Media._ID;

        Cursor imageCursor = context.managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);


        if (imageCursor != null && imageCursor.getCount() > 0) {

            while (imageCursor.moveToNext()) {

                int dataColumnIndex = imageCursor.getColumnIndex(MediaStore.Images.Media.DATA);

                String fullPath = imageCursor.getString(dataColumnIndex);

                if (fullPath != null && !TextUtils.isEmpty(fullPath) && new File(fullPath).length() != 0) {

                    tempList.add(new AlbumDataModel(fullPath, getDirectoryName(fullPath), getDirectoryFullName(fullPath)));

                } else {// end of if -> file may be null.
                    ErrorController.showMessage("Dropped : " + dataColumnIndex + ", Total : " + imageCursor.getCount());
                }

            }// end of while
        }// end of if


        //ErrorController.showMessage(tempList.size() + "");
        Collections.reverse(tempList);
        if ("pfback".equals(type) || "pf".equals(type)) {
            AlbumDataModel item = new AlbumDataModel("", "", "");//아마도 카메라를 위함.
            tempList.add(0, item);
        }
/*
        for (AlbumDataModel albumDataModel : tempList) {
            ErrorController.showMessage("album : " + albumDataModel);
        }*/

        return tempList;
    }

    private List<AlbumDataModel> getDetailData(String fullDirectory, Activity context, String type) {

        List<AlbumDataModel> tempList = new ArrayList<AlbumDataModel>();

        final String[] columns = {MediaStore.Images.Media.DATA,
                MediaStore.Images.Media._ID};
        final String orderBy = MediaStore.Images.Media._ID;

        Cursor imageCursor = context.managedQuery(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
                null, orderBy);

        //ErrorController.showMessage(fullDirectory);

        if (imageCursor != null && imageCursor.getCount() > 0) {

            while (imageCursor.moveToNext()) {
                int dataColumnIndex = imageCursor
                        .getColumnIndex(MediaStore.Images.Media.DATA);

                String fullPath = imageCursor.getString(dataColumnIndex);

                // Drop empty files (Files with only names and no contents).
                if (fullPath != null && !TextUtils.isEmpty(fullPath) && new File(fullPath).length() != 0) {
                    if (fullPath.contains(fullDirectory)) {
                        tempList.add(new AlbumDataModel(fullPath,
                                getDirectoryName(fullPath),
                                getDirectoryFullName(fullPath)));
                    }
                }// end of if
            }// end of while
        }// end of if

        //ErrorController.showMessage(tempList.size() + "");
        Collections.reverse(tempList);
        if ("pfback".equals(type) || "pf".equals(type)) {
            AlbumDataModel item = new AlbumDataModel("", "", "");//아마도 카메라를 위함.
            tempList.add(0, item);
        }
        return tempList;
    }

    public String getDirectoryFullName(String fullPath) {
        String result = "/";
        String[] temp = fullPath.split("/");
        for (int i = 1; i < temp.length - 1; i++) {
            result += temp[i] + "/";
        }
        result = result.substring(0, result.length() - 1);

        return result;
    }

    public String getDirectoryName(String fullPath) {
        String result = "/";
        String[] temp = fullPath.split("/");

        result = temp[temp.length - 2];
        return result;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 9093 && resultCode == Activity.RESULT_OK) {//사진 촬영 성공

                /*Uri imageUri = getImageCaptureUri();
                String path = FileUtil.getPath(mContext, imageUri);
                ErrorController.showMessage(path);
                Intent intent = new Intent(mContext, ActivityEditPicture.class);
                intent.putExtra("fullPath", path);
                intent.putExtra("type", mAction);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();*/

            } else if (requestCode == 9090 && resultCode == Activity.RESULT_OK) {//사진 편집 완료

//                String path = data.getStringExtra("savedPath");
//
//                AlbumDataModel dataModel = new AlbumDataModel(path, path, path);
//
//                List<AlbumDataModel> selection = new ArrayList<>();
//                selection.add(dataModel);
//
//                ErrorController.showMessage("SAVED PATH : " + path);
//
//                Intent intent = new Intent();
//                intent.putExtra(Constants.P0_RESULT_ALBUM_DATA, (Serializable) selection);
//                setResult(RESULT_OK, intent);
//                finish();

            }
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }
}
