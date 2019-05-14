package mars.nomad.com.l12_applicationutil.Filter;

import mars.nomad.com.l0_base.Callback.NsPredicateObject;

import java.util.ArrayList;
import java.util.List;

import mars.nomad.com.l0_base.Logger.ErrorController;


/**
 * Created by SJH, NomadSoft.Inc on 2017-09-13.
 */

public class FilterUtil {

    public static <T> Boolean removeItem(List<T> list, NsPredicateObject<T> predicate) {

        boolean result = false;

        int position = -1;

        try {

            if (list == null) {
                return false;
            }

            for (T t : list) {

                if (predicate.apply(t)) {
                    position = list.indexOf(t);
                    break;
                }
            }


            if (position != -1) {

                list.remove(position);
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }

        return true;
    }

    public static <T> Boolean isItemSelected(List<T> list, NsPredicateObject<T> predicate) {

        try {

            if (list == null) {
                return null;
            }

            boolean result = false;

            for (T t : list) {
                if (predicate.apply(t)) {
                    result = true;
                    break;
                }
            }

            return result;

        } catch (Exception e) {
            ErrorController.showError(e);
        }

        return false;
    }



    /**
     * list에서 condition을 만족하는 값의 index를 리턴한다.
     *
     * @param list      검색될 리스트
     * @param condition 조건식
     * @param <T>
     * @return 0보다 크다 : 조건식을 만족한 객체의 리스트 안의 인덱스, -1 : 조건식을 만족하는 값이 없다.
     */
    public static <T> int getPosition(final List<T> list, NsPredicateObject<T> condition) {

        int result = -1;

        try {

            for (T t : list) {

                if (condition.apply(t)) {

                    result = list.indexOf(t);
                    break;
                }
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return result;
    }


    public static <T> boolean isItemExist(List<T> list, NsPredicateObject<T> predicate) {

        try {

            if (list == null) {
                list = new ArrayList<>();
                return false;
            }

            for (T t : list) {
                if (predicate.apply(t)) {
                    return true;
                }
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return false;
    }

    public static <T> T isItemExistReturnObj(List<T> list, NsPredicateObject<T> predicate) {

        try {

            if (list == null) {
                list = new ArrayList<>();
                return null;
            }

            for (T t : list) {
                if (predicate.apply(t)) {
                    return t;
                }
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return null;
    }

    public static <T> int getCount(List<T> list, NsPredicateObject<T> predicate) {
        try {

            int cnt = 0;

            if (list == null) {
                list = new ArrayList<>();
                return 0;
            }

            for (T t : list) {
                if (predicate.apply(t)) {
                    cnt++;
                }
            }

            return cnt;
        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return 0;
    }

}
