package mars.nomad.com.l12_applicationutil.Intent;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;


import mars.nomad.com.l0_base.Callback.CommonCallback;
import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.l12_applicationutil.String.StringChecker;

/**
 * Created by SJH, NomadSoft.Inc on 2018-03-05.
 */

public class NsIntentUtil {

    public static void getPhoneCallIntent(String phoneNumber, final CommonCallback.SingleObjectCallback<Intent> callback) {

        try {

            if (!StringChecker.isStringNotNull(phoneNumber)) {

                callback.onFailed("전화번호를 입력해주세요.");
                return;
            }

            phoneNumber = phoneNumber.replace("-", "");
            phoneNumber = phoneNumber.trim();

            try {

                Integer.parseInt(phoneNumber);

            } catch (Exception e) {

                callback.onFailed("유효한 전화번호가 아닙니다.");
                return;
            }


            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            callback.onSuccess(intent);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public static void getWebIntent(String webUrl, final CommonCallback.SingleObjectCallback<Intent> callback) {

        try {

            if (!StringChecker.isStringNotNull(webUrl)) {

                callback.onFailed("웹 url을 입력해주세요.");
                return;
            }

            if (!webUrl.startsWith("http")) {

                webUrl = "http://" + webUrl;
            }

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(webUrl));
            callback.onSuccess(browserIntent);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public static void getEmailIntent(String target, final CommonCallback.SingleObjectCallback<Intent> callback) {

        try {
            if (!StringChecker.isStringNotNull(target)) {

                callback.onFailed("이메일 주소가 유효하지 않습니다.");
                return;
            }

            String[] address = {target};

            Intent intent = new Intent(Intent.ACTION_SEND);//common intent
            intent.setType("plain/text");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{target});
            Intent chooser = Intent.createChooser(intent, "Send email");

            callback.onSuccess(chooser);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    public static void getUpdateIntent(Context context, final CommonCallback.SingleObjectCallback<Intent> callback) {

        try {

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=" + context.getPackageName()));
            callback.onSuccess(intent);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


}
