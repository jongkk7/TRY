package mars.nomad.com.m0_http;


import mars.nomad.com.l0_base.Logger.ErrorController;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SJH on 2017-06-26.
 */

public class NSCallback<T> implements Callback<T> {


    private SingleObjectCallback<T> mCallback;

    public NSCallback(SingleObjectCallback<T> callback) {
        this.mCallback = callback;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {

        try {
            T res = response.body();

            if (res != null) {

                ErrorController.showMessage(res.toString());

                if (res instanceof BaseResponseModel) {

                    BaseResponseModel baseResponseModel = (BaseResponseModel) res;

                    if (baseResponseModel.isResult()) {

                        mCallback.onSuccess(res);
                    } else {

                        if (baseResponseModel.getMessage() == null || !baseResponseModel.getMessage().equalsIgnoreCase("")) {

                            mCallback.onFailed(baseResponseModel.getCode() + "");

                        } else {

                            mCallback.onFailed(baseResponseModel.getCode() + "");
                        }
                    }

                } else {

                    mCallback.onSuccess(res);

                }

            } else {

                mCallback.onFailed("서버로 부터 재대로된 응답을 받지 못했습니다.");
            }
        } catch (Exception e) {
            ErrorController.showError(e);
            mCallback.onFailed("Exception during processing network.");
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {

        ErrorController.showError(t);
        mCallback.onFailed("Exception during processing network.");
    }

    public interface SingleObjectCallback<T> {

        void onSuccess(T result);

        void onFailed(String fault);
    }
}
