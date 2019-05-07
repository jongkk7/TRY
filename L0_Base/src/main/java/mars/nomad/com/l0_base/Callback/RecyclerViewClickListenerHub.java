package mars.nomad.com.l0_base.Callback;

/**
 * Created by SJH on 2017-03-08.
 * 클릭, 롱 클릭 콜백을 모두 갖춘 콜백
 */
public interface RecyclerViewClickListenerHub<T> {
    void onClick(T item, int position);
    void onLongClick(T item, int position);
}
