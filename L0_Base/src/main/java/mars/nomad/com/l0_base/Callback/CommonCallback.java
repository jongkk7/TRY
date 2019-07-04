package mars.nomad.com.l0_base.Callback;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by SJH on 2017-02-28.
 * 함수형 콜백을 받기 위한 베이스 인터페이스들을 제공하는 클래스.
 */
public class CommonCallback {

    /**
     * 성공 시 특정 오브젝트(T)를 onSuccess를 통해 리턴해준다. 실패하면 원인을 담은 fault 문자열을 onFailed를 통해 리턴해준다.
     *
     * @param <T> Success시 리턴 받고 싶은 객체.
     */
    public interface SingleObjectCallback<T> {

        void onSuccess(T result);

        void onFailed(String fault);
    }

    public interface DoubleObjectCallback<T, J> {
        void onSuccess(T result1, J result2);

        void onFailed(String fault);
    }


    public interface SingleObjectActionCallback<T> {
        void onAction(T data);
    }


    public interface ProgressCallback<T> {
        void onProgress(T progress);

        void onSuccess(T success);

        void onFailed(String fault);
    }

    /**
     * 성공 시 특정 오브젝트(T)의 리스트를 onSuccess를 통해 리턴해준다. 실패하면 원인을 담은 fault 문자열을 onFailed를 통해 리턴해준다.
     *
     * @param <T> Success시 리스트로 리턴 받고 싶은 객체.
     */
    public interface ListCallback<T> {
        void onSuccess(List<T> result);

        void onFailed(String fault);
    }

    public interface SingleActionCallback {
        void onAction();
    }

    public interface SingleSelectionCallback<T> {
        void onSelection(T selection);
    }

    /**
     * 실패하는 경우만 출력해야하는 경우 사용됨.
     */
    public interface SingleFailureCallback {
        void onFailed(String fault);
    }

    /**
     * 두가지 액션을 해야할 경우 사용됨
     */
    public interface DoubleActionCallback {
        void onAction1();

        void onAction2();
    }

    public interface Function<T, F> {
        void runFunction(T item, F item2);
    }

    public interface VoidFunction {
        void runFunction();
    }

    public interface SingleObjectCallbackWithLogin<T>{

        void onSuccess(T result);

        void onLoginRequired();

        void onFailed(String fault);
    }


    public interface UserSelectionCallback<T>{

        void onPositiveSelection(T item);

        void onNegativeSelection();
    }

    public interface DoubleSelectionCallback<T, F>{

        void onSelection(T value1, F value2);
    }


    public interface BasicDialogCallback<T>{

        void onStartLoading();

        void onStopLoading();

        void onShowDialog(String dialogString);
    }

    public interface DialogSingleObjectActionCallback<T> extends BasicDialogCallback<T>{

        void onAction(T data);
    }


    public interface DialogSingleActionCallback<T> extends BasicDialogCallback<T>{

        void onAction();
    }

    public static abstract class BasicAdapterPagerCallback<T> implements Parcelable {

        public abstract void onStartLoading();

        public abstract void onStopLoading();

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

        }
    }
}
