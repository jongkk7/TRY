package mars.nomad.com.c2_customview.RecyclerView;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import mars.nomad.com.l0_base.Callback.CommonCallback;

import mars.nomad.com.c2_customview.RecyclerView.Adapter.NsBaseRecyclerViewAdapter;
import mars.nomad.com.c2_customview.R;
import mars.nomad.com.l0_base.Logger.ErrorController;

/**
 * Created by SJH, NomadSoft.Inc on 2018-03-07.
 */
public class EmptyRecyclerViewLayout extends LinearLayout {

    private RecyclerView recyclerView;
    private TextView textViewMessage;
    private RelativeLayout linearLayoutEmpty;

    /**
     * 없으면 자동으로 linearLayoutManager가 됨.
     */
    private RecyclerView.LayoutManager mManager = null;

    private String message;


    public EmptyRecyclerViewLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
        getAttrs(attrs);
    }

    private void initView(){

        try {

            String infService = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
            View v = li.inflate(R.layout.empty_recyclerview_layout, this, false);
            this.linearLayoutEmpty = (RelativeLayout) v.findViewById(R.id.linearLayoutEmpty);
            this.textViewMessage = (TextView) v.findViewById(R.id.textViewMessage);
            this.recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
            addView(v);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.NsRecyclerViewLayout);
        setTypeArray(typedArray);
    }

    public void setLayoutManager(RecyclerView.LayoutManager manager) {
        this.mManager = manager;
    }

    private void setTypeArray(TypedArray typedArray) {

        try {

            message = typedArray.getString(R.styleable.NsRecyclerViewLayout_nsMessage);
            textViewMessage.setText(message);

            typedArray.recycle();

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }



    public void setAdapter(NsBaseRecyclerViewAdapter adapter){

        try {

            if(mManager == null){

                mManager = new LinearLayoutManager(getContext());
            }

            if(adapter != null && adapter.getItemCount() > 0) {

                adapter.setNotifyCallback(new CommonCallback.SingleObjectActionCallback<Integer>() {


                    @Override
                    public void onAction(Integer size) {

                        if(size > 0){

                            recyclerView.setVisibility(VISIBLE);
                            linearLayoutEmpty.setVisibility(GONE);

                        }else{

                            recyclerView.setVisibility(GONE);
                            linearLayoutEmpty.setVisibility(VISIBLE);
                        }
                    }
                });

                this.recyclerView.setLayoutManager(mManager);
                this.recyclerView.setAdapter(adapter);
                this.recyclerView.setVisibility(VISIBLE);
                this.linearLayoutEmpty.setVisibility(GONE);

            }else{

                if(adapter != null){

                    adapter.setNotifyCallback(new CommonCallback.SingleObjectActionCallback<Integer>() {
                        @Override
                        public void onAction(Integer size) {

                            if(size > 0){

                                recyclerView.setVisibility(VISIBLE);
                                linearLayoutEmpty.setVisibility(GONE);

                            }else{

                                recyclerView.setVisibility(GONE);
                                linearLayoutEmpty.setVisibility(VISIBLE);
                            }
                        }
                    });

                }

                this.recyclerView.setAdapter(adapter);
                this.recyclerView.setLayoutManager(mManager);
                this.recyclerView.setVisibility(GONE);
                this.linearLayoutEmpty.setVisibility(VISIBLE);
            }

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
}
