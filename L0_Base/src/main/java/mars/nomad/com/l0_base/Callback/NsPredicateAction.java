package mars.nomad.com.l0_base.Callback;

/**
 * Created by 김창혁, NomadSoft.Inc on 2018-01-23.
 * 아이템없이 조건만으로 predicated. 맞을 경우 액션을 취함
 */
public abstract class NsPredicateAction {

    public abstract boolean apply();

}
