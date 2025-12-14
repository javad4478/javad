package ir.zipanel.tv;

   import android.annotation.SuppressLint;
   import android.app.Activity;
   import android.os.Bundle;
   import android.view.KeyEvent;
   import android.view.View;
   import android.view.WindowManager;
   import android.webkit.WebChromeClient;
   import android.webkit.WebSettings;
   import android.webkit.WebView;
   import android.webkit.WebViewClient;

   public class MainActivity extends Activity {
       
       private WebView webView;
       private static final String URL = "https://zipanel.ir/p/index.html";

       @SuppressLint("SetJavaScriptEnabled")
       @Override
       protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           
           getWindow().setFlags(
               WindowManager.LayoutParams.FLAG_FULLSCREEN,
               WindowManager.LayoutParams.FLAG_FULLSCREEN
           );
           
           getWindow().getDecorView().setSystemUiVisibility(
               View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
               | View.SYSTEM_UI_FLAG_FULLSCREEN
               | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
           );
           
           setContentView(R.layout.activity_main);
           
           webView = findViewById(R.id.webview);
           setupWebView();
           webView.loadUrl(URL);
       }

       private void setupWebView() {
           WebSettings settings = webView.getSettings();
           settings.setJavaScriptEnabled(true);
           settings.setDomStorageEnabled(true);
           settings.setDatabaseEnabled(true);
           settings.setCacheMode(WebSettings.LOAD_DEFAULT);
           settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
           settings.setUserAgentString("Mozilla/5.0 (Linux; Android 5.0; TCL Android TV) AppleWebKit/537.36 Chrome/90.0");
           
           settings.setSupportZoom(false);
           settings.setBuiltInZoomControls(false);
           settings.setDisplayZoomControls(false);
           
           settings.setMediaPlaybackRequiresUserGesture(false);
           
           webView.setWebViewClient(new WebViewClient() {
               @Override
               public boolean shouldOverrideUrlLoading(WebView view, String url) {
                   view.loadUrl(url);
                   return true;
               }
           });
           
           webView.setWebChromeClient(new WebChromeClient());
           
           webView.setFocusable(true);
           webView.setFocusableInTouchMode(true);
           webView.requestFocus();
       }

       @Override
       public boolean onKeyDown(int keyCode, KeyEvent event) {
           if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER || 
               keyCode == KeyEvent.KEYCODE_ENTER) {
               
               webView.evaluateJavascript(
                   "var el = document.activeElement; if(el) el.click();",
                   null
               );
               return true;
           }
           
           if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
               webView.goBack();
               return true;
           }
           
           return super.onKeyDown(keyCode, event);
       }

       @Override
       protected void onDestroy() {
           if (webView != null) {
               webView.destroy();
           }
           super.onDestroy();
       }
   }
