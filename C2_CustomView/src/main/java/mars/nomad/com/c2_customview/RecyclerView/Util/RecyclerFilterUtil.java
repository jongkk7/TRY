package mars.nomad.com.c2_customview.RecyclerView.Util;

import android.app.Activity;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import mars.nomad.com.c2_customview.RecyclerView.Adapter.NsBaseRecyclerViewAdapter;
import mars.nomad.com.l0_base.Callback.NsPredicate;
import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by 김창혁, NomadSoft.Inc on 2019-05-07.
 */
public class RecyclerFilterUtil {

    /**
     * 어댑터에서 해당 아이템을 제거 처리한다.
     *
     * @param adapter
     * @param list
     * @param predicate
     * @param <T>
     */
    public static <T> void removeItem(RecyclerView.Adapter adapter, List<T> list, NsPredicate<T> predicate) {

        try {

            int position = -1;

            if (list == null) {
                return;
            }

            for (T t : list) {
                if (predicate.apply(t)) {
                    position = list.indexOf(t);
                    break;
                }
            }

            if (position != -1) {
                list.remove(position);

                if (adapter instanceof NsBaseRecyclerViewAdapter) {

                    ((NsBaseRecyclerViewAdapter) adapter).nsNotifyItemRemoved(position);

                } else {

                    adapter.notifyItemRemoved(position);
                }

            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public static <T> void getAlignedPosition(RecyclerView.Adapter adapter, List<T> list, T item, NsPredicate<T> predicate) {

        try {


            int position = -1;

            if (list == null) {
                return;
            }

            for (T currentItem : list) {

                if (predicate.apply(currentItem)) {

                    position = list.indexOf(currentItem);
                    break;
                }
            }

            if (position != -1) {

                list.add(position, item);

                if (adapter instanceof NsBaseRecyclerViewAdapter) {

                    ((NsBaseRecyclerViewAdapter) adapter).nsNotifyItemInserted(position);

                } else {

                    adapter.notifyItemInserted(position);
                }

            }


        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    /**
     * 목록에서 해당 아이템을 업데이트 처리한다.
     *
     * @param adapter
     * @param list
     * @param condition
     * @param <T>
     */
    public static <T> void updateItem(RecyclerView.Adapter adapter, List<T> list, NsPredicate<T> condition, NsPredicate<T> action) {

        try {

            int position = -1;

            if (list == null) {
                return;
            }

            for (T t : list) {
                if (condition.apply(t)) {
                    position = list.indexOf(t);
                    action.apply(t);
                    break;
                }
            }

            if (position != -1) {

                if (adapter instanceof NsBaseRecyclerViewAdapter) {

                    ((NsBaseRecyclerViewAdapter) adapter).nsNotifyItemChanged(position);

                } else {

                    adapter.notifyItemChanged(position);
                }
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    /**
     * 리스트의 아이템을 교체한다.
     * (화면엔 보여주되 리스트의 아이템만 삭제시켜야할 경우 보여준다)
     *
     * @param mAdapter
     * @param list
     */
    public static <T> void updateItem2(RecyclerView.Adapter mAdapter, List<T> list, T item, NsPredicate<T> predicate) {
        try {

            int position = -1;

            if (list == null) {
                return;
            }

            for (T t : list) {

                if (predicate.apply(t)) {

                    position = list.indexOf(t);
                    break;
                }
            }

            if (position != -1) {
                list.remove(position);

                list.add(position, item);

                if (mAdapter instanceof NsBaseRecyclerViewAdapter) {

                    ((NsBaseRecyclerViewAdapter) mAdapter).nsNotifyItemChanged(position);

                } else {

                    mAdapter.notifyItemChanged(position);
                }
            }


        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    /**
     * 목록에서 해당 아이템을 업데이트 처리한다.
     *
     * @param <T>
     * @param adapter
     * @param list
     * @param mActivity
     * @param condition
     */
    public static <T> void updateItem(final RecyclerView.Adapter adapter, final List<T> list, final T item, Activity mActivity, NsPredicate<T> condition) {

        try {

            int position = -1;

            if (list == null) {
                return;
            }

            for (T t : list) {
                if (condition.apply(t)) {
                    position = list.indexOf(t);
                    break;
                }
            }

            final int finalPosition = position;

            if (position != -1) {

                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        list.remove(finalPosition);
                        list.add(finalPosition, item);

                        if (adapter instanceof NsBaseRecyclerViewAdapter) {

                            ((NsBaseRecyclerViewAdapter) adapter).nsNotifyItemChanged(finalPosition);

                        } else {

                            adapter.notifyItemChanged(finalPosition);
                        }
                    }
                });

            } else {

                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        list.add(item);
                        if (adapter instanceof NsBaseRecyclerViewAdapter) {

                            ((NsBaseRecyclerViewAdapter) adapter).nsNotifyItemInserted(list.indexOf(item));

                        } else {

                            adapter.notifyItemInserted(list.indexOf(item));
                        }
                    }
                });
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }




}
