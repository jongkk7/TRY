package mars.nomad.com.m0_http;

import android.content.Context;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import mars.nomad.com.l0_base.Logger.ErrorController;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * 2018-05-15 SJH, NomadSoft.Inc
 * RetrofitSender 그 2탄.
 */
public class RetrofitSender2 {

    public static String BASE_URL = "http://106.247.29.134:16500";//"http://192.168.0.135:16500";//"http://13.209.3.226:80";//"http://192.168.0.59:16151";//"http://218.234.17.221:16132";
    public static String MATCHING_SERVER_URL = "http://nomad1505.iptime.org:16113";//"http://192.168.0.110:16113";//
    public static String URL_IMG_BASE = "http://106.247.29.134:16174/";//"http://218.234.17.221:16174/";//"http://218.234.17.221:16134/";//"http://61.98.231.98:15015/";//"http://192.168.0.162:15015/";


    private static Retrofit retrofit = null;


    private static OkHttpClient httpClientNoAuth = null;

    private static OkHttpClient httpClient = null;
    private static Context context = null;

    public static void setBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
    }


    /**
     * SSL을 사용하는 경우 BaseApplication에서 Context를 셋팅해줘야한다. (init 단계)
     *
     * @param applcationContext
     */
    private static void setBaseApplcationContext(Context applcationContext) {
        try {

            context = applcationContext;

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public static <T> T initAndGetBaseEndPoint(Class<T> classType) {

        T result = null;

        initHttpClient();

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(httpClient)
                .build();

        result = (T) retrofit.create(classType);
        return result;
    }



    public static <T> T initAndGetBaseEndPointXML(Class<T> classType) {

        T result = null;

        initHttpClient();


        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(SimpleXmlConverterFactory.create()).client(httpClient)
                .build();

        result = (T) retrofit.create(classType);
        return result;
    }

    public static <T> T initAndGetFileEndPoint(Class<T> classType) {

        T result = null;

        initHttpClient();

        retrofit = new Retrofit.Builder().baseUrl(URL_IMG_BASE).addConverterFactory(GsonConverterFactory.create()).client(httpClient)
                .build();

        result = (T) retrofit.create(classType);
        return result;
    }


    public static <T> T initAndGetBaseEndPoint(String url, Class<T> classType) {

        T result = null;

        initHttpClient();

        retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).client(httpClient)
                .build();

        result = (T) retrofit.create(classType);
        return result;
    }


    public static <T> T initAndGetBaseEndPointXML(String url, Class<T> classType) {

        T result = null;

        initHttpClient();


        retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(SimpleXmlConverterFactory.create()).client(httpClient)
                .build();

        result = (T) retrofit.create(classType);
        return result;
    }

    public static <T> T initAndGetFileEndPoint(String url, Class<T> classType) {

        T result = null;

        initHttpClient();

        retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).client(httpClient)
                .build();

        result = (T) retrofit.create(classType);
        return result;
    }


    private static void initHttpClient() {
        try {
            if (httpClient == null) {
                if (context != null) {

                    OkHttpClient.Builder tempOkhttp = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).
                            addInterceptor(new Interceptor() {
                                @Override
                                public okhttp3.Response intercept(Chain chain) throws IOException {

                                    Request request = chain.request();

                                    Request.Builder newRequest = request.newBuilder().header("Content-Type", "application/json");

                                    ErrorController.showMessage("URL : " + request.url().toString());

                                    return chain.proceed(newRequest.build());
                                }
                            });

                    tempOkhttp.sslSocketFactory(SSLUtil.getSSLSocketFactory(context));

                    httpClient = tempOkhttp.build();
                } else {
                    httpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
                }
            }


        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

}
