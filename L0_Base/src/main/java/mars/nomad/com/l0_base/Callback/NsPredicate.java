package mars.nomad.com.l0_base.Callback;

/**
 * Created by SJH, NomadSoft.Inc on 2018-01-23.
 */

public abstract class NsPredicate<T> {

    public abstract boolean apply(T listItem);

}
