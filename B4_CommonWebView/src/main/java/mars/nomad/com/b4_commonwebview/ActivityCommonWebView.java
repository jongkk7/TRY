package mars.nomad.com.b4_commonwebview;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import mars.nomad.com.c3_baseaf.BaseActivity;
import mars.nomad.com.l0_base.Logger.ErrorController;
import mars.nomad.com.l12_applicationutil.String.StringChecker;


public class ActivityCommonWebView extends BaseActivity {

    private TextView textViewTitle;
    private WebView webView;


    private String mFullUrl = null;
    private String mTitle = null;

    private Context mContext;
    private android.widget.RelativeLayout relativeLayoutClose;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_web_view);
        initView();
        setEvent();
        setDarkStatusBar(getWindow().getDecorView(), this);
        setStatusBarColor(R.color.colorTopBar1Background);
        getData();
        loadWebView();
    }

    @Override
    protected void initView() {

        try {

            this.mContext = this;

            this.webView = (WebView) findViewById(R.id.webView);
            this.textViewTitle = (TextView) findViewById(R.id.textViewTitle);
            this.relativeLayoutClose = (RelativeLayout) findViewById(R.id.relativeLayoutClose);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    @Override
    protected void setEvent() {

        this.relativeLayoutClose.setOnClickListener(new View.OnClickListener() {//백버튼
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }//end of setEvent.


    /**
     * intent로 부터 full url을 받는다.
     */
    private void getData() {
        try {

            mFullUrl = getIntent().getStringExtra("fullUrl");

            mTitle = getIntent().getStringExtra("title");

            if (!StringChecker.isStringNotNull(mFullUrl)) {

                ErrorController.showInfoToast(mContext, "화면 정보를 받아오지 못했습니다.", 2);
                finish();

            } else {

                //제목 설정
                if (StringChecker.isStringNotNull(mTitle)) {

                    textViewTitle.setText(mTitle);

                } else {

                    textViewTitle.setText("웹뷰");
                }

            }
        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }


    /**
     * 웹뷰를 초기화하고 mFullUrl을 화면에 불러온다.
     */
    private void loadWebView() {

        try {

            webView.getSettings().setLoadsImagesAutomatically(true);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setUseWideViewPort(true);
            webView.setBackgroundColor(Color.TRANSPARENT);

            webView.setWebChromeClient(new WebChromeClient());

            webView.setWebViewClient(new WebViewClient());

            ErrorController.showMessage("[webview] url : " + mFullUrl);

            webView.loadUrl(mFullUrl);

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }//end of loadWebView


    @Override
    public void onBackPressed() {

        try {

            if (webView != null && webView.canGoBack()) {
                webView.goBack();
            } else {
                finish();
            }
        } catch (Exception e) {
            ErrorController.showError(e);
            finish();
        }
    }
}
