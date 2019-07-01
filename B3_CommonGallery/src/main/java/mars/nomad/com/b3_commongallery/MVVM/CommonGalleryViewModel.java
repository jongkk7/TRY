package mars.nomad.com.b3_commongallery.MVVM;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import mars.nomad.com.b3_commongallery.ActivityCommonGallery;
import mars.nomad.com.b3_commongallery.DataModel.CommonGalleryDataModel;
import mars.nomad.com.b3_commongallery.DataModel.CommonGalleryTotalDataModel;
import mars.nomad.com.l0_base.Callback.CommonCallback;
import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.l12_applicationutil.String.StringChecker;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-05-21.
 */
public class CommonGalleryViewModel extends ViewModel {


    private MutableLiveData<List<CommonGalleryDataModel>> galleryListLive = new MutableLiveData<>();

    private List<CommonGalleryDataModel> galleryList = new ArrayList<>();
    private String action = "";

    public MutableLiveData<List<CommonGalleryDataModel>> getGalleryListLive() {
        return galleryListLive;
    }

    private MutableLiveData<List<CommonGalleryTotalDataModel>> galleryTotalListLive = new MutableLiveData<>();

    private List<CommonGalleryTotalDataModel> galleryTotalList = new ArrayList<>();

    public MutableLiveData<List<CommonGalleryTotalDataModel>> getGalleryTotalListLive() {
        return galleryTotalListLive;
    }

    private Map<String, CommonGalleryDataModel> selectListMap = new HashMap<>();

    public void loadList(Activity activity) {
        try {

            loadTotalList(activity);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    private void loadTotalList(final Activity activity) {
        try {

            Map<String, String> map = new HashMap<String, String>();
            Map<String, Integer> childCount = new HashMap<>();

            List<CommonGalleryTotalDataModel> data = new ArrayList<CommonGalleryTotalDataModel>();
            final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
            final String orderBy = MediaStore.Images.Media._ID;

            Cursor mediaCursor = null;

            if (action.equalsIgnoreCase(ActivityCommonGallery.PICTURE)) {
                mediaCursor = activity.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);
            } else {
                mediaCursor = activity.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);
            }

            if (mediaCursor != null && mediaCursor.getCount() > 0) {

                String currentDirectory = " ";

                while (mediaCursor.moveToNext()) {

                    int dataColumnIndex = 0;
                    if (action.equalsIgnoreCase(ActivityCommonGallery.PICTURE)) {
                        dataColumnIndex = mediaCursor.getColumnIndex(MediaStore.Images.Media.DATA);
                    } else {
                        dataColumnIndex = mediaCursor.getColumnIndex(MediaStore.Video.Media.DATA);
                    }

                    String fullPath = mediaCursor.getString(dataColumnIndex);

                    // Drop empty files (Files with only names and no contents).
                    if (new File(fullPath).length() != 0) {
                        String temp = getDirectoryFullName(fullPath);
                        if (!currentDirectory.equals(temp)) {
                            currentDirectory = temp;
                            try {
                                map.put(currentDirectory, fullPath);
                                childCount.put(currentDirectory, 1);
                            } catch (Exception e) {
                            }
                        } else {
                            if (childCount.containsKey(currentDirectory)) {
                                childCount.put(currentDirectory, childCount.get(currentDirectory) + 1);
                            }
                        }
                    }// end of if
                }// end of while

                for (Map.Entry<String, String> e : map.entrySet()) {
                    String key = e.getKey();
                    String value = e.getValue();

                    int childCnt = 0;

                    try {

//                        File dir = new File(key);
//
//                        File[] childList = dir.listFiles(new FilenameFilter() {
//                            @Override
//                            public boolean accept(File dir, String name) {
//                                if (action.equalsIgnoreCase(ActivityCommonGallery.PICTURE)) {
//                                    return ExtMimeUtil.getExtType(name).equalsIgnoreCase(ExtMimeUtil.TYPE_IMAGE);
//                                } else if (action.equals(ActivityCommonGallery.VIDEO)) {
//                                    return ExtMimeUtil.getExtType(name).equalsIgnoreCase(ExtMimeUtil.TYPE_VIDEO);
//                                }
//
//                                return false;
//                            }
//                        });
//
//                        if (childList != null) {
//
////                            for (File file : childList) {
////                                if(file != null && file.exists()) {
////                                    file.getName();
////                                }
////                            }
//
//                            childCnt = childList.length;.
//                        }
                        if (childCount.containsKey(key)) {
                            childCnt = childCount.get(key);
                        }


                    } catch (Exception ee) {
                        ErrorController.showError(ee);
                    }

                    if (childCnt != 0) {
                        data.add(new CommonGalleryTotalDataModel(value, getDirectoryName(value), key, childCnt));
                    }
                }// end of for
            }//end of if

            for (CommonGalleryTotalDataModel albumDataModel : data) {
                ErrorController.showMessage(albumDataModel.toString());

            }

            List<CommonGalleryTotalDataModel> result = new ArrayList<>();


            for (CommonGalleryTotalDataModel galleryDataModel : data) {

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
                        result.add(galleryDataModel);
                    }
                    continue;
                }

                if (galleryDataModel.getDirectoryName().contains("KakaoTalk") || galleryDataModel.getDirectoryName().contains("카카오톡")) {
                    if (result.size() > 2) {
                        result.add(2, galleryDataModel);
                    } else {
                        result.add(galleryDataModel);
                    }
                    continue;
                }
            }

            for (CommonGalleryTotalDataModel galleryDataModel : data) {
                if (!result.contains(galleryDataModel)) {
                    result.add(galleryDataModel);
                }
            }
            result.add(0, new CommonGalleryTotalDataModel("", "전체보기", "", -1));

            galleryTotalList.clear();
            galleryTotalList.addAll(result);

            galleryTotalListLive.postValue(galleryTotalList);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    private List<CommonGalleryDataModel> getDetailData(Activity activity, String fullDirectory) {

        List<CommonGalleryDataModel> tempList = new ArrayList<CommonGalleryDataModel>();
        if (action.equalsIgnoreCase(ActivityCommonGallery.PICTURE)) {
            getImageDetailData(activity, tempList, fullDirectory);
        } else {
            getVideoDetailData(activity, tempList, fullDirectory);
        }
        return tempList;
    }

    private void getImageDetailData(Activity activity, List<CommonGalleryDataModel> tempList, String fullDirectory) {
        try {

            final String[] columns = {MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media._ID};
            final String orderBy = MediaStore.Images.Media._ID;

            Cursor imageCursor = activity.getContentResolver().query(
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

                            CommonGalleryDataModel commonTemp = new CommonGalleryDataModel(fullPath);
                            commonTemp.setSelected(selectListMap.containsKey(fullPath));
                            tempList.add(commonTemp);
                        }
                    }// end of if
                }// end of while
            }// end of if

            //ErrorController.showMessage(tempList.size() + "");
            Collections.reverse(tempList);
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    private void getVideoDetailData(Activity activity, List<CommonGalleryDataModel> tempList, String fullDirectory) {
        try {

            final String[] columns = {MediaStore.Video.Media.DATA,
                    MediaStore.Video.Media._ID};
            final String orderBy = MediaStore.Video.Media._ID;

            Cursor videoCursor = activity.getContentResolver().query(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI, columns, null,
                    null, orderBy);

            //ErrorController.showMessage(fullDirectory);

            if (videoCursor != null && videoCursor.getCount() > 0) {

                while (videoCursor.moveToNext()) {
                    int dataColumnIndex = videoCursor
                            .getColumnIndex(MediaStore.Video.Media.DATA);

                    String fullPath = videoCursor.getString(dataColumnIndex);

                    // Drop empty files (Files with only names and no contents).
                    if (fullPath != null && !TextUtils.isEmpty(fullPath) && new File(fullPath).length() != 0) {
                        if (fullPath.contains(fullDirectory)) {

                            CommonGalleryDataModel commonTemp = new CommonGalleryDataModel(fullPath);
                            commonTemp.setSelected(selectListMap.containsKey(fullPath));
                            tempList.add(commonTemp);
                        }
                    }// end of if
                }// end of while
            }// end of if

            //ErrorController.showMessage(tempList.size() + "");
            Collections.reverse(tempList);
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    private String getDirectoryFullName(String fullPath) {
        String result = "/";
        try {
            String[] temp = fullPath.split("/");
            for (int i = 1; i < temp.length - 1; i++) {
                result += temp[i] + "/";
            }
            result = result.substring(0, result.length() - 1);
        } catch (Exception e) {
            ErrorController.showError(e);
        }

        return result;
    }

    private String getDirectoryName(String fullPath) {
        String result = "/";
        try {
            String[] temp = fullPath.split("/");

            result = temp[temp.length - 2];
        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return result;
    }

    public void loadDirectoryFileList(Activity activity, String directoryPath) {
        try {
            galleryList.clear();


            galleryList.addAll(getDetailData(activity, directoryPath));

            galleryListLive.postValue(galleryList);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void getData(Intent intent) {
        try {

            if (StringChecker.isStringNotNull(intent.getAction())) {

                action = intent.getAction();


            } else {
                // 액션을 못받으면 그냥 그림타입으로 취급함.

                action = ActivityCommonGallery.PICTURE;

            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void setSelectedItem(CommonGalleryDataModel item, CommonCallback.SingleObjectActionCallback<Integer> callback) {
        try {
            item.setSelected(!item.isSelected());

            if (item.isSelected()) {
                // 이제 셀렉트됬는데 추가되어있는게 말이안된느데.. 일단 지움
                if (selectListMap.containsKey(item.getFullPath())) {
                    selectListMap.remove(item.getFullPath());
                }
                selectListMap.put(item.getFullPath(), item);
            } else {
                // 셀렉트를 풀었는데 추가가 되어있지 않다는것도 말이안되지만...
                if (selectListMap.containsKey(item.getFullPath())) {
                    selectListMap.remove(item.getFullPath());
                }
            }

            galleryListLive.postValue(galleryList);

            callback.onAction(selectListMap.size());

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public List<CommonGalleryDataModel> getSelectedItem() {
        return new ArrayList<CommonGalleryDataModel>(selectListMap.values());
    }
}
