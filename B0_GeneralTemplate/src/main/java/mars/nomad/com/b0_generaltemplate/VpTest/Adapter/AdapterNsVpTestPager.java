package mars.nomad.com.b0_generaltemplate.VpTest.Adapter;

;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import mars.nomad.com.a0_common.DataBase.Room.NsModule.NsModule;
import mars.nomad.com.b0_generaltemplate.VpTest.Fragment.BaseNsVpTestFragment;
import mars.nomad.com.l0_base.Callback.CommonCallback;
import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by SJH, NomadSoft.Inc, 2019-07-04
 */
public class AdapterNsVpTestPager extends FragmentStatePagerAdapter {

    private List<NsModule> mData;

    private List<BaseNsVpTestFragment> mFragmentList = new ArrayList<>();

    public AdapterNsVpTestPager(@NonNull FragmentManager fm, List<NsModule> data, CommonCallback.BasicAdapterPagerCallback<NsModule> callback) {
        super(fm);

        try {

            mData = data;

            for (NsModule datum : data) {
                mFragmentList.add(BaseNsVpTestFragment.newInstance(callback));
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
