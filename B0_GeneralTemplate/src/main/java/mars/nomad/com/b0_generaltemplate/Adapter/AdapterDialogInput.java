package mars.nomad.com.b0_generaltemplate.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mars.nomad.com.b0_generaltemplate.DataModel.InputDataModel;
import mars.nomad.com.b0_generaltemplate.R;
import mars.nomad.com.c2_customview.Adapter.NsBaseRecyclerViewAdapter;
import mars.nomad.com.l0_base.Abstract.AbstractTextWatcher;
import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.l12_applicationutil.String.StringChecker;

/**
 * Created by SJH, NomadSoft.Inc, 2019-07-02
 */
public class AdapterDialogInput extends NsBaseRecyclerViewAdapter<AdapterDialogInput.AdapterDialogInputViewHolder, InputDataModel> {


    public AdapterDialogInput(Context mContext, List<InputDataModel> data) {
        super(mContext, data);
    }


    @Override
    public AdapterDialogInputViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_cell_input, parent, false);
        return new AdapterDialogInputViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterDialogInputViewHolder holder, int position) {

        final InputDataModel item = data.get(position);

        try {

            holder.textViewFieldName.setText(item.getFieldName());

            holder.editTextInput.setText(item.getValue());

            holder.editTextInput.addTextChangedListener(new AbstractTextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    try {

                        if (s != null && s.length() > 0) {

                            item.setValue(s.toString());

                        } else {

                            item.setValue("");
                        }
                    } catch (Exception e) {
                        ErrorController.showError(e);
                    }
                }
            });

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public boolean isAllFieldInserted() {

        boolean isAllFieldInserted = true;

        try {

            for (InputDataModel datum : data) {

                if (!StringChecker.isStringNotNull(datum.getValue())) {

                    isAllFieldInserted = false;
                    break;
                }
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return isAllFieldInserted;
    }

    public Map<String, String> getAnswerAsMap() {

        Map<String, String> result = new HashMap<>();

        try {

            for (InputDataModel datum : data) {

                result.put(datum.getFieldName(), datum.getValue());
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
        return result;
    }


    public class AdapterDialogInputViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewFieldName;
        private EditText editTextInput;

        public AdapterDialogInputViewHolder(View view) {
            super(view);
            textViewFieldName = (TextView) view.findViewById(R.id.textViewFieldName);
            editTextInput = (EditText) view.findViewById(R.id.editTextInput);
        }
    }
}
