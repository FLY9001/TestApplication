package com.eyesmart.testapplication.android;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.eyesmart.testapplication.R;

/**
 * WebView使用小结：https://blog.csdn.net/u012730980/article/details/53213147
 */
public class WebViewActivity extends AppCompatActivity {
    private static final String TAG = "WebViewActivity";
    private WebView webView;

    //启动该Activity
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, WebViewActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        webView = (WebView) findViewById(R.id.webView);

        //支持javascript
        webView.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        webView.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        webView.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        webView.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);

        //在网页跳转的时候调用
        //如果不设置WebViewClient，请求会跳转系统浏览器
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d(TAG, "shouldOverrideUrlLoading(view,url)");

                //该方法在Build.VERSION_CODES.LOLLIPOP以前有效，从Build.VERSION_CODES.LOLLIPOP起，
                // 建议使用shouldOverrideUrlLoading(WebView, WebResourceRequest)} instead
                if (url.toString().contains("sina.cn")) {
                    view.loadUrl("http://ask.csdn.net/questions/178242");
                    return true;
                }

                return false;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.d(TAG, "shouldOverrideUrlLoading(view,request)");

                //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址），均交给webView自己处理，这也是此方法的默认处理
                //返回true，说明你自己想根据url，做新的跳转，比如在判断url符合条件的情况下，我想让webView加载http://ask.csdn.net/questions/178242
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (request.getUrl().toString().contains("sina.cn")) {
                        view.loadUrl("http://ask.csdn.net/questions/178242");
                        return true;
                    }
                }

                return false;
            }

        });
        //加载assets文件夹下的test.html页面
        //webview.loadUrl("file:///android_asset/test.html");//要写成android_asset
        //加载网页
        webView.loadUrl("http://www.baidu.com");

        //1、调用H5中无参无返回值的方法 show()
        webView.loadUrl("JavaScript:show()");
        //2、调用H5中带返回值的方法 sum(a,b)
        //只有安卓4.4以上才能用，当版本低于4.4的时候，常用的思路是 java调用js方法，js方法执行完毕，再次调用java代码将值返回
        webView.evaluateJavascript("sum(1,2)", new ValueCallback() {
            @Override
            public void onReceiveValue(Object value) {
                Log.e(TAG, "js的返回结果为=" + value);

            }
        });
        //3、调用H5中带参数的方法
        webView.loadUrl("javascript:alertMessage('哈哈')");//当传入固定字符串时，用单引号括起来即可
        String content = "9880";
        webView.loadUrl("javascript:alertMessage(\" " + content + "\")");//当传入变量名时，需要用到转义符


        //H5调用android中的方法
        webView.addJavascriptInterface(new JsInteration(), "android");//android为别名
//        function s () {
//            var result = window.android.back();   //格式为 window.别名.android中的方法名(parameter Values)
//            document.getElementById("p").innerHTML = result;
//        }

    }

    /**
     * 自己写一个类，里面是提供给H5访问的方法
     */
    public class JsInteration {
        @JavascriptInterface
        public String back() {
            return "hello world";
        }
    }
}
