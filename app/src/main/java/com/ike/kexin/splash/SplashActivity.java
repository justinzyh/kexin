package com.ike.kexin.splash;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ike.kexin.MainActivity;
import com.ike.kexin.R;
import com.ike.kexin.utils.JsInterface;

/**
 * Created by justinzyh on 2017/2/23.
 */

public class SplashActivity  extends Activity{

    private WebView webView;
    private JsInterface jsInterface = new JsInterface();
    private Context  context;
    private Activity activity;
    private String url = "file:///android_asset/guide/splash.html";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //将屏幕设置为全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        context = this;
        activity = this;
        initView();
    }
    //找控件
    private void initView() {
        webView = (WebView) findViewById(R.id.wv_webview_splash);
        initWebView();
    }

    //判断sdk 加载webview
    @SuppressLint("NewApi")
    private void initWebView() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            WebView.setWebContentsDebuggingEnabled(true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            webView.getSettings().setCacheMode(
                    WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                activity.setTitle("拼命加载中...");
                activity.setProgress(newProgress * 100);
                if (newProgress == 100) {
                    activity.setTitle(R.string.splash);
                }
            }
            @Override
            public boolean onJsAlert(WebView view, String url, String message,
                                     JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });
        loadLocalHtml(url);
    }

    @SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" })
    public void loadLocalHtml(String url){
        WebSettings ws = webView.getSettings();
        ws.setJavaScriptEnabled(true);//开启JavaScript支持
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //重写此方法，用于捕捉页面上的跳转链接
                if ("http://start/".equals(url)){
                    //在html代码中的按钮跳转地址需要同此地址一致
                    Intent intent = new Intent(context,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                return true;
            }
        });
        webView.loadUrl(url);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
