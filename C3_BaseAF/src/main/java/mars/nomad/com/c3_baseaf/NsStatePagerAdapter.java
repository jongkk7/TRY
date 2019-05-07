package mars.nomad.com.c3_baseaf;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by SJH, NomadSoft.Inc, 2018-08-16
 */
public abstract class NsStatePagerAdapter<T> extends FragmentStatePagerAdapter {

    protected List<T> mData;

    protected List<BaseFragment> mFragment = new ArrayList<>();

    public NsStatePagerAdapter(FragmentManager fm, List<T> data) {
        super(fm);
        this.mData = data;


    }

    public List<T> getData() {
        return mData;
    }

    public void setData(List<T> mData) {
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mFragment.size();
    }
}
