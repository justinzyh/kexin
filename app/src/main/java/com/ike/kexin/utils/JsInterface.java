package com.ike.kexin.utils;

import android.webkit.JavascriptInterface;

public class JsInterface {
    public static String MAIN2PAGE;

    /*interface for javascript to invokes*/
    public interface wvClientClickListener {
        public void wvHasClickEnvent();
    }

    private wvClientClickListener wvEnventPro = null;

    public void setWvClientClickListener(wvClientClickListener listener) {
        wvEnventPro = listener;
    }

    @JavascriptInterface  //这个注解很重要
    public void javaFunction() {
        if (wvEnventPro != null)
            wvEnventPro.wvHasClickEnvent();
    }
}  