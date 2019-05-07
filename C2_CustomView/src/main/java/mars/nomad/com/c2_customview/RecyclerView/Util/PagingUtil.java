package mars.nomad.com.c2_customview.RecyclerView.Util;

import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;

import mars.nomad.com.l0_base.Logger.ErrorController;


/**
 * Created by 김창혁, NomadSoft.Inc on 2018-07-06.
 */

/**
 * 기존의 Refresh가 마음에 들지 않는다는 의견이 많아서 기존의 로직을 해치지 않는 선에서 페이징 처리
 * onPaging는 ex) loadList(false)
 */
public class PagingUtil {
    /**
     * recycler의 페이징 처리
     * 이때는 nestedScrollingEnabled 상태가 true여야 가능. (따로 설정하지 않았다면 true)
     */
    public static void onRefreshSwipyRecycler(RecyclerView recyclerView, final SwipyRefreshLayout swipyRefreshLayout, final onPaging onRefreshCallback) {
        try {
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if (dy > 0) {
                        int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                        int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                        int pastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                        if (!swipyRefreshLayout.isRefreshing()) {
                            if ((visibleItemCount + pastVisibleItem) >= totalItemCount) {
                                swipyRefreshLayout.setRefreshing(true);
                                onRefreshCallback.onPaging();
                            }
                        }
                    }
                }
            });


        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    private static boolean isDelay = false;

    /**
     * recyclerview만 있는 경우의 페이징
     * (이경우에는 로딩을 알수없으니 딜레이로 판단함.)
     * 변수로 만들순 있으나, 그경우 로딩 딜레이를 없애야 하는 메소드를 다시 호출해야하므로 매우 귀찮음. 따라서 딜레이로 책정
     * @param recyclerView
     * @param onRefreshCallback
     */
    public static void onRefreshRecyclerView(RecyclerView recyclerView, final onPaging onRefreshCallback) {
        try {


            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    if (dy > 0 && !isDelay) {
                        int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                        int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                        int pastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                        if ((visibleItemCount + pastVisibleItem) >= totalItemCount) {
                            isDelay = true;
                            onRefreshCallback.onPaging();

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    isDelay = false;
                                }
                            }, 1000);
                        }
                    }
                }
            });
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    /**
     * nestedScrollView 의 페이징 처리
     *
     * @param nestedScrollView
     * @param recyclerView
     * @param swipyRefreshLayout
     * @param onRefreshCallback
     */
    public static void onRefreshNestedSwipyRecycler(NestedScrollView nestedScrollView, final RecyclerView recyclerView, final SwipyRefreshLayout swipyRefreshLayout, final onPaging onRefreshCallback) {
        try {
            if (recyclerView.getLayoutManager() == null) {
                return;
            }

            nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                        int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                        int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                        int pastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                        if (!swipyRefreshLayout.isRefreshing()) {
                            if ((visibleItemCount + pastVisibleItem) >= totalItemCount) {
                                swipyRefreshLayout.setRefreshing(true);
                                onRefreshCallback.onPaging();
                            }
                        }
                    }
                }
            });

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    /**
     * nestedScrollView 의 페이징 처리
     * 가끔 두개의 recyclerView가 하나에 묶여서 조건에 따라 움직이는 케이스가 존재해서
     * 그 경우 조건이 true면 1번
     * false면 2번을 refresh한다.
     *
     * @param nestedScrollView
     * @param recyclerView1
     * @param recyclerView2
     * @param swipyRefreshLayout
     * @param onRefreshCallback
     */
    public static void onRefreshNestedTwoRecyclerView(NestedScrollView nestedScrollView, final RecyclerView recyclerView1, final RecyclerView recyclerView2, final SwipyRefreshLayout swipyRefreshLayout,
                                                      final onPaging onRefreshCallback, final boolean isCondition) {
        try {

            nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                        if (isCondition) {
                            int visibleItemCount = recyclerView1.getLayoutManager().getChildCount();
                            int totalItemCount = recyclerView1.getLayoutManager().getItemCount();
                            int pastVisibleItem = ((LinearLayoutManager) recyclerView1.getLayoutManager()).findFirstVisibleItemPosition();
                            if (!swipyRefreshLayout.isRefreshing()) {
                                if ((visibleItemCount + pastVisibleItem) >= totalItemCount) {
                                    swipyRefreshLayout.setRefreshing(true);
                                    onRefreshCallback.onPaging();
                                }
                            }
                        } else {
                            int visibleItemCount = recyclerView2.getLayoutManager().getChildCount();
                            int totalItemCount = recyclerView2.getLayoutManager().getItemCount();
                            int pastVisibleItem = ((LinearLayoutManager) recyclerView2.getLayoutManager()).findFirstVisibleItemPosition();
                            if (!swipyRefreshLayout.isRefreshing()) {
                                if ((visibleItemCount + pastVisibleItem) >= totalItemCount) {
                                    swipyRefreshLayout.setRefreshing(true);
                                    onRefreshCallback.onPaging();
                                }
                            }
                        }

                    }
                }
            });

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    public interface onPaging {
        void onPaging();
    }
}
