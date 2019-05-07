package mars.nomad.com.l12_applicationutil.Mock;

import java.util.ArrayList;
import java.util.List;

import mars.nomad.com.l0_base.Callback.NsFunction;
import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by SJH, NomadSoft.Inc on 2018-01-05.
 */

public class MockUtil<T> {

    /**
     * 해당 데이터 만큼의 빈 mock 데이터 리스트를 돌려준다.
     * @param size
     * @return
     */
    public List<T> getMockList(int size, NsFunction.GenericFunction<T> function){

        List<T> result = new ArrayList<>();

        try {

            for(int i = 0; i < size; i++){

                result.add(function.onAction());
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return result;
    }

}
