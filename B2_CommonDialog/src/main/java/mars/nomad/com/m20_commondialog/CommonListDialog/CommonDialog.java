package mars.nomad.com.m20_commondialog.CommonListDialog;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import mars.nomad.com.c2_customview.Dialog.BaseNsDialog;
import mars.nomad.com.l0_base.Callback.CommonCallback;
import mars.nomad.com.l0_base.Callback.RecyclerViewClickListener;

import java.util.List;

import mars.nomad.com.l12_applicationutil.String.StringProcesser;
import mars.nomad.com.l4_language.NsLanguagePack;
import mars.nomad.com.m20_commondialog.CommonListDialog.Adapter.AdapterReport;
import mars.nomad.com.m20_commondialog.R;

/**
 * Created by SJH on 2017-06-05.
 */
public class CommonDialog extends BaseNsDialog {

    private String mTitle;

    private List<String> mMenuList;

    private CommonCallback.SingleSelectionCallback<String> mCallback;
    private TextView textViewTitle;
    private RecyclerView recyclerViewItems;
    private Button buttonClose;
    private RelativeLayout relativeLayoutClose;
    private LinearLayout linearLayoutRoot;


    public CommonDialog(Context context, String title, List<String> menuList, CommonCallback.SingleSelectionCallback<String> callback) {

        super(context);

        setContentView(R.layout.p0_common_list_dialog);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND, WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setDimAmount(0.3f);

        this.mTitle = title;
        this.mMenuList = menuList;
        this.mCallback = callback;

        initView();
        setEvent();
        initList();
    }

    protected void initView() {

        this.linearLayoutRoot = (LinearLayout) findViewById(R.id.linearLayoutRoot);
        this.buttonClose = (Button) findViewById(R.id.buttonClose);
        this.recyclerViewItems = (RecyclerView) findViewById(R.id.recyclerViewItems);
        this.relativeLayoutClose = (RelativeLayout) findViewById(R.id.relativeLayoutClose);
        this.textViewTitle = (TextView) findViewById(R.id.textViewTitle);

        recyclerViewItems.setLayoutManager(new LinearLayoutManager(getContext()));
        textViewTitle.setText(mTitle + "");

        NsLanguagePack.setTextLanguage(linearLayoutRoot);
    }


    protected void setEvent() {
        this.relativeLayoutClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        this.buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }


    private void initList() {

        recyclerViewItems.setAdapter(new AdapterReport(getContext(), mMenuList, new RecyclerViewClickListener<String>() {
            @Override
            public void onItemClick(String item) {
                dismiss();
                mCallback.onSelection(item);

            }
        }));
    }

}
