package mars.nomad.com.b0_generaltemplate.NsAddPackage.Dialog;

import android.content.Context;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

import mars.nomad.com.b0_generaltemplate.NsAddPackage.DataModel.InputDataModel;
import mars.nomad.com.b0_generaltemplate.R;
import mars.nomad.com.b0_generaltemplate.NsAddPackage.Adapter.AdapterDialogInput;
import mars.nomad.com.c2_customview.Dialog.BaseNsDialog;
import mars.nomad.com.l0_base.Callback.CommonCallback;
import mars.nomad.com.l0_base.Callback.SingleClickListener;
import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by SJH, NomadSoft.Inc, 2019-07-02
 */
public class DialogTemplateInput extends BaseNsDialog {

    private RecyclerView recyclerViewOptions;

    private List<InputDataModel> mInputList;

    private CommonCallback.SingleObjectActionCallback<Map<String, String>> mCallback;

    private AdapterDialogInput mAdapter;
    private Button buttonCancel;
    private Button buttonConfirm;

    public DialogTemplateInput(@NonNull Context context, List<InputDataModel> mInputList, CommonCallback.SingleObjectActionCallback<Map<String, String>> mCallback) {
        super(context);
        this.mInputList = mInputList;
        this.mCallback = mCallback;
        setContentView(R.layout.dialog_template_input);
        setWindow();
        initView();
        setEvent();
        loadList();
    }


    @Override
    protected void initView() {

        try {

            buttonCancel = (Button) findViewById(R.id.buttonCancel);
            buttonConfirm = (Button) findViewById(R.id.buttonConfirm);
            recyclerViewOptions = (RecyclerView) findViewById(R.id.recyclerViewOptions);

            recyclerViewOptions.setLayoutManager(new LinearLayoutManager(getContext()));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    @Override
    protected void setEvent() {

        try {

            this.buttonCancel.setOnClickListener(new SingleClickListener(new CommonCallback.SingleActionCallback() {
                @Override
                public void onAction() {
                    dismiss();
                }
            }));

            this.buttonConfirm.setOnClickListener(new SingleClickListener(new CommonCallback.SingleActionCallback() {
                @Override
                public void onAction() {

                    try{

                        if(mAdapter.isAllFieldInserted()){

                            dismiss();
                            mCallback.onAction(mAdapter.getAnswerAsMap());

                        }else{

                            ErrorController.showToast(getContext(), "값을 다 입력해라.");
                        }

                    }catch(Exception e){
                        ErrorController.showError(e);
                    }
                }
            }));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    private void loadList() {

        try {

            this.mAdapter = new AdapterDialogInput(getContext(), mInputList);
            recyclerViewOptions.setAdapter(mAdapter);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }
}
