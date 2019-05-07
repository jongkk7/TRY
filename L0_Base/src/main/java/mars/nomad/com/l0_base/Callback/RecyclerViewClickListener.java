package mars.nomad.com.l0_base.Callback;

/**
 * Created by SJH on 2017-03-06.
 * 리사이클러뷰의 아이템 클릭 콜백
 */

public interface RecyclerViewClickListener<T> {
    void onItemClick(T item);
}
