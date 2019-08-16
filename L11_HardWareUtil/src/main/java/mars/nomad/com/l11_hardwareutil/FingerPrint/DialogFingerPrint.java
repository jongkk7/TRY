package mars.nomad.com.l11_hardwareutil.FingerPrint;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.airbnb.lottie.LottieAnimationView;

import mars.nomad.com.l0_base.Callback.CommonCallback;
import mars.nomad.com.l0_base.Callback.SingleClickListener;
import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.l11_hardwareutil.FingerPrint.Mvvm.FingerprintViewModel;
import mars.nomad.com.l11_hardwareutil.R;

/**
 * Created by YJK on 2019-07-05
 * 6.0 이상 9.0 미만일 경우 해당 다이얼로그로 지문인식을 진행한다.
 **/
public class DialogFingerPrint extends Dialog {

    private CommonCallback.SingleObjectCallback<FingerprintManager.AuthenticationResult> callback;

    private RelativeLayout relativeLayoutCancel;

    private FingerprintViewModel viewModel;

    private LottieAnimationView imageViewFingerprint; // 성공 애니메이션 뷰
    private LottieAnimationView imageViewFingerprintFailed; // 실패 애니메이션 뷰

    public DialogFingerPrint(@NonNull Context context, CommonCallback.SingleObjectCallback<FingerprintManager.AuthenticationResult> callback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.callback = callback;
        initView();
        setEvent();
        setWindow();
        initFingerPrint(); // 지문인식 초기화
    }

    protected void initView() {

        try {

            viewModel = new FingerprintViewModel();

            setContentView(R.layout.dialog_fingerprint);
            relativeLayoutCancel = (RelativeLayout) findViewById(R.id.relativeLayoutCancel);
            imageViewFingerprint = (LottieAnimationView) findViewById(R.id.imageViewFingerprint);
            imageViewFingerprintFailed = (LottieAnimationView) findViewById(R.id.imageViewFingerprintFailed);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    protected void setEvent() {

        try {

            relativeLayoutCancel.setOnClickListener(new SingleClickListener(new CommonCallback.SingleActionCallback() {
                @Override
                public void onAction() {
                    // 지문인식 핸들러를 종료해주어야한다.
                    viewModel.stopHandler();
                    dismiss();
                }
            }));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    protected void setWindow(){

        try {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND, WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            getWindow().setDimAmount(0.8f);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    /**
     * 지문 인식 준비
     */
    private void initFingerPrint() {
        try {

            viewModel.initFingerPrint(getContext(), new FingerprintViewModel.IFingerprintCallback() {
                @Override
                public void onFailed(String fault) { // 지문 인식 준비 실패
                    startFailedAnimation(fault);
                }

                @Override
                public void onSuccessInit() { // 초기화 성공 ( 지문 애니메이션 셋팅 )
                    imageViewFingerprint.setAnimation("lottie_finger_print.json");
                }

                @Override
                public void onEqual(final FingerprintManager.AuthenticationResult result) { // 지문 인식 성공, 정보 반환
                    startSucceseAnimation(result);
                }

                @Override
                public void onNotEqual(String fault) { // 지문 인식 실패
                    startFailedAnimation(fault);
                }
            });

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    private void startSucceseAnimation(final FingerprintManager.AuthenticationResult result){

        try{

            // 성공 애니메이션 처리
            imageViewFingerprint.addAnimatorListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    imageViewFingerprintFailed.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    callback.onSuccess(result);
                    dismiss();
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });

            imageViewFingerprint.playAnimation();

        }catch (Exception e){
            ErrorController.showError(e);
        }
    }

    private void startFailedAnimation(final String fault){

        try{

            callback.onFailed(fault);

            // 실패 애니메이션 처리
            imageViewFingerprintFailed.addAnimatorListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    imageViewFingerprint.setVisibility(View.GONE);
                    imageViewFingerprintFailed.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    imageViewFingerprint.setVisibility(View.VISIBLE);
                    imageViewFingerprintFailed.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });

            imageViewFingerprintFailed.playAnimation();

        }catch (Exception e){
            ErrorController.showError(e);
        }
    }
}
