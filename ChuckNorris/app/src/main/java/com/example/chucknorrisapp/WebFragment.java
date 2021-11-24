package com.example.chucknorrisapp;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class WebFragment extends Fragment implements IOnBackPressed {
    public static final String LINK_API = "https://www.icndb.com/api/";
    private WebView webView;
    private Bundle webViewBundle;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web, container, false);
        webView = view.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        setSettingsWeb();
        if (webViewBundle != null) {
            webView.restoreState(webViewBundle);
        } else {
            webView.loadUrl(LINK_API);
        }
        webView.setWebViewClient(new MyWebViewClient());
        return view;
    }

    private void setSettingsWeb() {
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        webViewBundle = new Bundle();
        webView.saveState(webViewBundle);
    }

    // back button implementation
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        }
    }

    //to open links in the browser
    private static class MyWebViewClient extends WebViewClient {
        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }

        // For old device
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}