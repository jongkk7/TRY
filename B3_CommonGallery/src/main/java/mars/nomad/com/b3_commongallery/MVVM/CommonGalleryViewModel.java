package mars.nomad.com.b3_commongallery.MVVM;

import android.app.Activity;
import android.database.Cursor;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import mars.nomad.com.b3_commongallery.DataModel.AlbumDataModel;
import mars.nomad.com.b3_commongallery.DataModel.CommonGalleryDataModel;
import mars.nomad.com.b3_commongallery.DataModel.CommonGalleryTotalDataModel;
import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-05-21.
 */
public class CommonGalleryViewModel extends ViewModel {

    private MutableLiveData<List<CommonGalleryDataModel>> galleryListLive = new MutableLiveData<>();

    private List<CommonGalleryDataModel> galleryList = new ArrayList<>();

    public MutableLiveData<List<CommonGalleryDataModel>> getGalleryListLive() {
        return galleryListLive;
    }

    private MutableLiveData<List<CommonGalleryTotalDataModel>> galleryTotalListLive = new MutableLiveData<>();

    private List<CommonGalleryTotalDataModel> galleryTotalList = new ArrayList<>();

    public MutableLiveData<List<CommonGalleryTotalDataModel>> getGalleryTotalListLive() {
        return galleryTotalListLive;
    }

    public void loadList(Activity activity) {
        try {

            loadTotalList(activity);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    private void loadTotalList(Activity activity) {
        try {

            Map<String, String> map = new HashMap<String, String>();

            List<CommonGalleryTotalDataModel> data = new ArrayList<CommonGalleryTotalDataModel>();
            final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
            final String orderBy = MediaStore.Images.Media._ID;

            Cursor imageCursor = activity.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);

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

                    data.add(new CommonGalleryTotalDataModel(value, getDirectoryName(value), key, childCnt));
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

            for (CommonGalleryTotalDataModel galleryDataModel : data) {
                if (!result.contains(galleryDataModel)) {
                    result.add(galleryDataModel);
                }
            }
            result.add(0, new CommonGalleryTotalDataModel("", "전체보기", "", -1));

            galleryTotalList.clear();
            galleryTotalList.addAll(result);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    private List<CommonGalleryDataModel> getDetailData(Activity activity, String fullDirectory) {

        List<CommonGalleryDataModel> tempList = new ArrayList<CommonGalleryDataModel>();
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
                            tempList.add(new CommonGalleryDataModel(fullPath));
                        }
                    }// end of if
                }// end of while
            }// end of if

            //ErrorController.showMessage(tempList.size() + "");
            Collections.reverse(tempList);
        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return tempList;
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
            getDetailData(activity, directoryPath);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }
}
