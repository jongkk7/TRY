package mars.nomad.com.c3_baseaf;

/**
 * Created by SJH, NomadSoft.Inc on 2018-03-21.
 */

public abstract class BaseApplicationActivity extends BaseActivity {

    /**
     * 이 어플리케이션에서 실행 가능한 activity들을 ActivityManager에 등록하도록 한다.
     */
    protected abstract void setActivityManager();

    /**
     * ActivityManager 세팅을 마친 후 다음 본 액티비티로 이동하도록 한다.
     */
    protected abstract void goToNextActivity();

}
