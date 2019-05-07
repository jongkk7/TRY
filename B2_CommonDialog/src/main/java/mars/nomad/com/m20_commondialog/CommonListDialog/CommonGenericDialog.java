package mars.nomad.com.m20_commondialog.CommonListDialog;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import mars.nomad.com.c2_customview.Dialog.BaseNsDialog;
import mars.nomad.com.l0_base.Callback.CommonCallback;
import mars.nomad.com.l0_base.Callback.RecyclerViewClickListener;

import java.util.List;

import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.l12_applicationutil.String.StringChecker;
import mars.nomad.com.m20_commondialog.CommonListDialog.Adapter.AdapterCommonGenericDialog;
import mars.nomad.com.m20_commondialog.R;

public class CommonGenericDialog<T> extends BaseNsDialog {


    private TextView textViewTitle;
    private RelativeLayout relativeLayoutClose;
    private RecyclerView recyclerViewItems;
    private Button buttonClose;
    private LinearLayout linearLayoutRoot;

    private List<T> mData;

    private CommonCallback.SingleObjectActionCallback<T> mCallback;

    private AdapterCommonGenericDialog.IGenericDialogCellDecorator<T> mGenericDecorator;

    private String mTitle;

    public CommonGenericDialog(@NonNull Context context, List<T> data, AdapterCommonGenericDialog.IGenericDialogCellDecorator<T> genericDialogCellDecorator, CommonCallback.SingleObjectActionCallback<T> callback) {
        super(context);
        setContentView(R.layout.p0_common_list_dialog);
        setWindow();
        this.mData = data;
        this.mCallback = callback;
        this.mGenericDecorator = genericDialogCellDecorator;
        initView();
        setEvent();
        loadList();
    }

    public CommonGenericDialog(@NonNull Context context, String title, List<T> data, AdapterCommonGenericDialog.IGenericDialogCellDecorator<T> genericDialogCellDecorator, CommonCallback.SingleObjectActionCallback<T> callback) {
        super(context);
        setContentView(R.layout.p0_common_list_dialog);
        setWindow();
        this.mData = data;
        this.mTitle = title;
        this.mCallback = callback;
        this.mGenericDecorator = genericDialogCellDecorator;
        initView();
        setEvent();
        loadList();
    }


    @Override
    protected void initView() {

        try {

            this.linearLayoutRoot = (LinearLayout) findViewById(R.id.linearLayoutRoot);
            this.buttonClose = (Button) findViewById(R.id.buttonClose);
            this.recyclerViewItems = (RecyclerView) findViewById(R.id.recyclerViewItems);
            this.relativeLayoutClose = (RelativeLayout) findViewById(R.id.relativeLayoutClose);
            this.textViewTitle = (TextView) findViewById(R.id.textViewTitle);

            this.recyclerViewItems.setLayoutManager(new LinearLayoutManager(getContext()));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    @Override
    protected void setEvent() {

        try {

            this.buttonClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

            this.relativeLayoutClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    private void loadList() {

        try {

            if (StringChecker.isStringNotNull(mTitle)) {

                textViewTitle.setText(mTitle);

            } else {

                textViewTitle.setText(getContext().getResources().getString(R.string.app_name));
            }

            recyclerViewItems.setAdapter(new AdapterCommonGenericDialog<T>(getContext(), mData, mGenericDecorator, new RecyclerViewClickListener<T>() {
                @Override
                public void onItemClick(T item) {
                    mCallback.onAction(item);
                    dismiss();
                }
            }));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }
}
