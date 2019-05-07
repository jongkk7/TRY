package mars.nomad.com.a0_common.DataBase.LitePal;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import mars.nomad.com.l0_base.Callback.NsPredicate;
import mars.nomad.com.l0_base.Logger.ErrorController;


public class CommonDbManager {

    /**
     * 기존 정보들을 모두 삭제 후 현재 정보를 저장한다.
     */
    public static void wipeAndSave(DataSupport newDataModel, Class<?> classInfo) {

        try {

            int delete = DataSupport.deleteAll(classInfo);
            boolean saved = newDataModel.save();

            Print(classInfo);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    public static void wipe(Class<?> classInfo) {

        try {

            DataSupport.deleteAll(classInfo);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public static <T> void Print(Class<T> classInfo) {

        try {

            List<T> select = DataSupport.findAll(classInfo);

            ErrorController.showMessage("Print - " + classInfo.getCanonicalName() + ", size : " + select.size());

            for (T t : select) {

                ErrorController.showMessage(t.toString());
            }

            ErrorController.showMessage("Print - END\n");


        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public static void save(Class<?> classInfo, DataSupport newDataModel) {

        try {

            newDataModel.save();

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public static <T> T getFirstItem(Class<?> classInfo) {

        try {

            List<T> select = (List<T>) DataSupport.findAll(classInfo);

            if (select.size() > 0) {

                return select.get(0);
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return null;
    }

    public static <T> T getFirstItem(Class<?> classInfo, NsPredicate<T> condition) {

        try {

            List<T> select = (List<T>) DataSupport.findAll(classInfo);
            if (select.size() > 0) {

                for (T t : select) {

                    if (condition.apply(t)) {

                        return t;
                    }
                }

            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return null;
    }


    public static <T> List<T> getList(Class<T> classInfo) {

        List<T> result = new ArrayList<>();

        try {

            List<T> select = (List<T>) DataSupport.findAll(classInfo);

            result = select;

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return result;
    }

    public static <T> List<T> getList(Class<T> classInfo, int page, int countPerPage) {

        List<T> result = new ArrayList<>();

        try {

            List<T> select = (List<T>) DataSupport.offset((page - 1) * 30).limit(countPerPage).find(classInfo);

            result = select;

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return result;
    }

    public static <T> List<T> getListWithCondition(Class<T> classInfo, int page, int countPerPage, String startDate, String endDate) {

        List<T> result = new ArrayList<>();

        try {

            List<T> select = (List<T>) DataSupport.where("date >= ? AND date <= ?", startDate, endDate).offset((page - 1) * 30).limit(countPerPage).order("date desc").find(classInfo);

            result = select;

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return result;
    }

    /**
     * 절대로 null이 아닌 결과를 돌려준다. (당연히 비어 있을 수는 있음.)
     * @param classInfo
     * @param condition
     * @param <T>
     * @return
     */
    public static <T> List<T> getList(Class<T> classInfo, NsPredicate<T> condition) {

        List<T> result = new ArrayList<>();

        try {

            List<T> select = (List<T>) DataSupport.findAll(classInfo);

            for (T t : select) {

                if (condition.apply(t)) {

                    result.add(t);
                }
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return result;
    }


}
