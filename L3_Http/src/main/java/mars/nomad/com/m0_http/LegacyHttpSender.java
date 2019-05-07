package mars.nomad.com.m0_http;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import mars.nomad.com.l0_base.Logger.ErrorController;


public class LegacyHttpSender {


    public static void sendHttpPost(final String url, final SetHttpParam setParamFunction, final HttpCallback callback) {

        try {

            new AsyncTask<Void, Void, String>() {

                @Override
                protected String doInBackground(Void... voids) {

                    String request = "";

                    try {

                        HttpClient httpclient = new DefaultHttpClient();
                        HttpPost httppost = new HttpPost(url);
                        httppost.addHeader( "content-type" , "application/json;charset=UTF-8" );
                        httppost.addHeader( "Cache-Control" , "no-cache" );
                        httppost.setEntity(new UrlEncodedFormEntity(setParamFunction.setParams()));

                        //HTTP Post 요청 실행
                        HttpResponse response = httpclient.execute(httppost);

                        request = request(response);

                    } catch (Exception e) {
                        ErrorController.showError(e);
                    }
                    return request;
                }

                @Override
                protected void onPostExecute(String s) {
                    callback.onSuccess(s);
                }
            }.execute();


        } catch (Exception e) {
            ErrorController.showError(e);
            callback.onFailed("Exception during processing network.");
        }
    }

    private static String request(HttpResponse response) throws Exception {

        String result = "";

        try {

            InputStream stream = response.getEntity().getContent();

            java.io.ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buff = new byte[5120];
            do {
                int count = stream.read(buff, 0, 5120);
                if (count <= 0)
                    break;
                bos.write(buff, 0, count);
            } while (true);

            result = new String(bos.toByteArray()); //, "UTF-8"
        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return result;
    }


    public interface SetHttpParam {

        List<NameValuePair> setParams();
    }

    public interface HttpCallback {

        void onSuccess(String response);

        void onFailed(String fault);
    }

}
