package br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by JoHN on 05/06/2018.
 */

public class webClient extends WebViewClient {

    private Context mContext;
    private ProgressBar mbar;
    private WebView mweb;
    private TextView merro;

    public webClient(Context mContext, ProgressBar mbar, WebView mweb, TextView merro) {
        this.mContext = mContext;
        this.mbar = mbar;
        this.mweb = mweb;
        this.merro = merro;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);

        mbar.setVisibility(View.VISIBLE);
        mweb.setVisibility(View.GONE);

    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);

        mbar.setVisibility(View.GONE);
        mweb.setVisibility(View.VISIBLE);

    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);

        mbar.setVisibility(View.GONE);
        mweb.setVisibility(View.GONE);
        merro.setVisibility(View.VISIBLE);

    }

    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        super.onReceivedHttpError(view, request, errorResponse);

        mbar.setVisibility(View.GONE);
        mweb.setVisibility(View.GONE);
        merro.setVisibility(View.VISIBLE);

    }
}
