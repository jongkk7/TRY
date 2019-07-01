package mars.nomad.com.c1_activitymanager;

import android.app.Activity;
import android.content.Intent;
import androidx.core.app.ActivityOptionsCompat;

import java.io.Serializable;
import java.util.List;

import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by SJH, NomadSoft.Inc on 2018-03-28.
 */

public class ActivityManagerCommon extends ActivityManager {


    /**
     * 공통 웹뷰 액티비티를 를 호출한다.
     *
     * @param context     화면의 context.
     * @param fullUrl     호출할 페이지의 풀 URL
     * @param title       웹뷰의 제목
     * @param hasTitleBar 0: 타이틀바 안보임, 1 : 타이틀바 보임
     */
    public static void goActivityCommonWebView(Activity context, String fullUrl, String title, final int hasTitleBar) {

        try {


            Intent intent = new Intent(context, getClassByName("ActivityCommonWebView"));
            intent.putExtra("fullUrl", fullUrl);
            intent.putExtra("title", title);
            intent.putExtra("hasTitleBar", hasTitleBar);
            context.startActivity(intent);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public static void goActivitySinglePictureView(final Activity activity, final ActivityOptionsCompat options, final String title, final String fullPath) {

        try {

            Intent intent = new Intent(activity, getClassByName("ActivitySinglePictureView"));
            intent.putExtra("title", title);
            intent.putExtra("fullPath", fullPath);
            startActivity(activity, intent, options);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    public static void goActivityMediaViewer(Activity activity, ActivityOptionsCompat options, String action, List<String> pictureList, int position) {

        try {

            Intent intent = new Intent(activity, getClassByName("ActivityMediaViewer"));
            intent.setAction(action);
            intent.putExtra("pictureList", (Serializable) pictureList);
            intent.putExtra("position", position);

            startActivityForResult(activity, null, options, intent, 3389);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }



    /////////////////////////////////////////////////

    public static int CommonGalleryRequest = 15001;
    /**
     * 갤러리 화면으로 이동
     * @param activity
     * @param action SEND, PICK -> 상단의 글씨 버튼이 각각 '전송', '선택'으로 설정됨.
     * @param maxSelectionCount 1 이상의 정수 - 선택 가능한 개수
     */
    public static void goActivityCommonGallery(Activity activity, String action, int maxSelectionCount) {

        try {

            Intent intent = new Intent(activity, getClassByName("ActivityCommonGallery"));
            intent.setAction( action);
            intent.putExtra("MAX_CNT", maxSelectionCount);
            activity.startActivityForResult(intent, CommonGalleryRequest);
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }




    public static void goActivityEditPicture(Activity activity, String action, String fullPath, int requestCode) {

        try {

            Intent intent = new Intent(activity, getClassByName("ActivityEditPicture"));
            intent.putExtra("fullPath", fullPath);
            intent.putExtra("type", action);
            activity.startActivityForResult(intent, requestCode);
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }
}
