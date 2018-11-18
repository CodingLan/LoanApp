package com.zhenxing.loanapp.activity;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.zhenxing.loanapp.R;
import com.zhenxing.loanapp.base.BaseActivity;
import com.zhenxing.loanapp.util.IntentUtil;
import com.zhenxing.loanapp.util.TBUtils;
import com.zhenxing.loanapp.view.FixTwinklingRefreshLayout;

/**
 * 公用webview
 * Created by xtdhwl on 20/10/2017.
 */

public class WebViewActivity extends BaseActivity {

    private WebView webView;
    /**
     * 标题
     */
    private String mTitle;

    public static final int FILE_CHOOSER_RESULT_CODE = 11111;
    /**
     * 加载url
     */
    private String mUrl;
    /**
     * 是否更新标题, 默认更新
     */
    private boolean mUpdateTitle = true;

    /**
     * 是否可以下拉刷新
     */
    private boolean canRefresh = true;

    /**
     * 是否可以拨号
     */
    private boolean canMakeCall = true;

    private ProgressBar mProgressBar;

    private FixTwinklingRefreshLayout refreshView;

    private ValueCallback<Uri[]> uploadCallBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        getDependData();
        //  initToolbarWithWhiteColor(this.mTitle);
        initView();
        webView.loadUrl(this.mUrl);
    }

    private void initView() {
        mProgressBar = findViewById(R.id.progressView);
        refreshView = findViewById(R.id.refreshView);
        webView = findViewById(R.id.webView);
        //refreshView.setHeaderView(new CoinRefreshView(getContext()));
        refreshView.setEnableOverScroll(false);
        refreshView.setEnableLoadmore(false);

        refreshView.setEnableRefresh(canRefresh);

        WebSettings settings = this.webView.getSettings();
        settings.setJavaScriptEnabled(true);
        String agentString = settings.getUserAgentString();
        settings.setUserAgentString(agentString + " " + TBUtils.getUserAgent());

        this.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    if (mProgressBar.getVisibility() == View.GONE) {
                        mProgressBar.setVisibility(View.VISIBLE);
                    }
                    mProgressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback,
                FileChooserParams fileChooserParams) {
                uploadCallBack = filePathCallback;
                openImageChooserActivity();
                return true;
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (mUpdateTitle) {
                    //getSupportActionBar().setTitle(title);
                }
            }
        });

        this.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                refreshView.finishRefreshing();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                refreshView(false, "");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
               /* if (filterUrl(url)) {
                    return true;
                }

                if (url.startsWith("tel:")) {
                    if (canMakeCall) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                        startActivity(intent);
                    } else {
                        new BtcDialog(getContext())
                            .setMsg(getString(R.string.please_ask_service))
                            .setPositiveButton(R.string.know)
                            .show();
                    }

                    return true;
                }*/
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description,
                String failingUrl) {
                if (errorCode == WebViewClient.ERROR_HOST_LOOKUP) {
                    refreshView(true, getString(R.string.network_not_good));
                } else {
                    super.onReceivedError(view, errorCode, description, failingUrl);
                }
            }
        });

        refreshView.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                webView.reload();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILE_CHOOSER_RESULT_CODE && uploadCallBack != null) {

            Uri[] result = null;
            if (resultCode == RESULT_OK && data != null) {
                String dataString = data.getDataString();
                ClipData clipData = data.getClipData();
                if (clipData != null) {
                    result = new Uri[clipData.getItemCount()];

                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        result[i] = item.getUri();
                    }
                }
                if (!TextUtils.isEmpty(dataString)) {
                    result = new Uri[] {Uri.parse(dataString)};
                }
            }
            uploadCallBack.onReceiveValue(result);
            uploadCallBack = null;
        }
    }

    public void openImageChooserActivity() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(Intent.createChooser(intent, "Image Chooser"), FILE_CHOOSER_RESULT_CODE);
    }

    //@Override
    public boolean getDependData() {

        Bundle bundle = getIntent().getBundleExtra("params");
        this.mTitle = bundle.getString("title", "");
        this.mUrl = bundle.getString("url", "");
        this.mUpdateTitle = bundle.getBoolean("updateTitle", true);
        this.canRefresh = bundle.getBoolean("canRefresh", true);
        this.canMakeCall = bundle.getBoolean("canMakeCall", true);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.removeAllViews();

        webView.destroy();
    }

    public void refreshView(boolean isError, String desc) {
        if (isError) {
            TextView descView = (TextView)findViewById(R.id.descView);
            descView.setText(desc);
            findViewById(R.id.webView).setVisibility(View.GONE);
            findViewById(R.id.errorLayout).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.webView).setVisibility(View.VISIBLE);
            findViewById(R.id.errorLayout).setVisibility(View.GONE);
        }
    }

    /**
     * @param activity
     * @param url
     * @param title
     * @param updateTitle
     */
    public static void start(Context activity, String url, int title, boolean updateTitle) {
        start(activity, url, activity.getString(title), updateTitle);
    }

    /**
     * 启动webview
     *
     * @param activity
     * @param url
     * @param title
     * @param updateTitle
     */
    public static void start(Context activity, String url, String title, boolean updateTitle) {
        Bundle params = new Bundle();
        params.putString("url", url);
        params.putString("title", title);
        params.putBoolean("updateTitle", updateTitle);
        IntentUtil.start(activity, WebViewActivity.class, params);
    }

    /**
     * 启动webview
     *
     * @param activity
     * @param url
     * @param title
     * @param updateTitle
     */
    public static void start(Context activity, String url, String title, boolean updateTitle, boolean canRefresh,
        boolean canMakeCall) {
        Bundle params = new Bundle();
        params.putString("url", url);
        params.putString("title", title);
        params.putBoolean("updateTitle", updateTitle);
        params.putBoolean("canRefresh", canRefresh);
        params.putBoolean("canMakeCall", canMakeCall);
        IntentUtil.start(activity, WebViewActivity.class, params);
    }
}
